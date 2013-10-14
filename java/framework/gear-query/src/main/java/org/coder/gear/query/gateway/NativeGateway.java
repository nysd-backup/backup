/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.gateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.coder.gear.query.free.RecordFilter;
import org.coder.gear.query.free.loader.PreparedQuery;
import org.coder.gear.query.free.loader.QueryLoader;
import org.coder.gear.query.free.loader.QueryLoaderTrace;
import org.coder.gear.query.free.mapper.DefaultMetadataMapperFactory;
import org.coder.gear.query.free.mapper.MetadataMapper;
import org.coder.gear.query.free.mapper.MetadataMapperFactory;
import org.coder.gear.query.free.query.Conditions;
import org.coder.gear.query.free.query.ReadingConditions;
import org.coder.gear.query.free.result.CloseableIterator;
import org.coder.gear.query.free.result.FetchIterator;
import org.coder.gear.query.free.result.TotalList;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.ScrollableCursor;



/**
 * An internal query using EclipseLink.
 * 
 * <pre>
 * 	Gets the result binded not to EntiyBean but to POJO.
 * </pre>
 *
 * @author yoshida-n
 * @version	1.0
 */
public class NativeGateway implements PersistenceGateway {
			
	/**
     * <pre>
     *      QueryLoader .
     * </pre>
     */
    private QueryLoader loader = new QueryLoaderTrace();

    /**
     * <pre>
     *      MetadataMapperFactory .
     * </pre>
     */
    private MetadataMapperFactory metadataMapperFactory = new DefaultMetadataMapperFactory();

    /**
     * set query loader.
     * 
     * @param loader
     *            QueryLoader
     */
    public void setQueryLoader(QueryLoader loader) {
        this.loader = loader;
    }

    /**
     * <pre>
     *    set MetadataMapperFactory .
     * </pre>
     * 
     * @param metadataMapperFactory
     *            MetadataMapperFactory
     */
    public void setMetadataMapperFactory(
            MetadataMapperFactory metadataMapperFactory) {
        this.metadataMapperFactory = metadataMapperFactory;
    }

    /**
     * @see org.coder.gear.query.gateway.PersistenceGateway#getResultList(org.coder.gear.query.free.query.ReadingConditions)
     */
    @SuppressWarnings("unchecked")
	@Override
    public <T> List<T> getResultList(ReadingConditions parameter) {

        Query query = setRangeAndCursor(parameter.getFirstResult(),
                parameter.getMaxResults(), createQuery(parameter));
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
        	ResultSet rs = cursor.getResultSet();
        	List<T> result = new ArrayList<T>();				
    		MetadataMapper mapper = metadataMapperFactory.create(parameter.getResultType(), rs);
    		RecordFilter filter = parameter.getFilter();
    		
    		while (rs.next()) {			    			
    			result.add((T)getRecord(mapper, rs, filter));
    		}
    		return result;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            cursor.close();
        }
    }

    /**
     * @see org.coder.gear.query.gateway.PersistenceGateway#getTotalResult(org.coder.gear.query.free.query.ReadingConditions)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> TotalList<T> getTotalResult(final ReadingConditions parameter) {

        Query query = setRangeAndCursor(parameter.getFirstResult(), 0,
                createQuery(parameter));
        TotalList<T> result = new TotalList<T>();	
        RecordFilter filter = parameter.getFilter();        
        Class<T> resultType = (Class<T>) parameter.getResultType();
        int maxSize = parameter.getMaxResults();
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
            ResultSet rs = cursor.getResultSet();            		
    		MetadataMapper mapper = metadataMapperFactory.create(resultType, rs);
    		int hitCount = 0;
    		int startPosition = rs.getRow();
    		while (rs.next()) {	
    			hitCount++;
    			//最大件数超過していたら終了
    			if( !result.isExceededLimit() ){
    				if( maxSize > 0 && hitCount > maxSize){
    					result.exceededLimit();
    					if(rs.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE){
    						rs.last();	
    						hitCount = rs.getRow();
    						break;
    					}else{
    						continue;
    					}
    				}
    				result.add((T)getRecord(mapper,rs,filter));
    			}			
    		}
    		hitCount = result.isExceededLimit() ? hitCount:hitCount+startPosition;
    		result.setHitCount(hitCount);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            cursor.close();
        }
        return result;
    }
    
    /**
     * Get record.
     * 
     * @param mapper the mapper
     * @param rs the result set
     * @param filter the filter
     * @return record
     * @throws SQLException
     */
    private <T> T getRecord(MetadataMapper mapper,ResultSet rs ,RecordFilter filter) throws SQLException{
    	T row = mapper.getRecord(rs);		
		
		//必要に応じて加工
		if( filter != null){
			filter.edit(row);
		}
		return row;
    }

    /**
     * @see org.coder.gear.query.gateway.PersistenceGateway#getFetchResult(org.coder.gear.query.free.query.ReadingConditions)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public CloseableIterator getFetchResult(ReadingConditions parameter) {
        Query query = setRangeAndCursor(parameter.getFirstResult(),
                parameter.getMaxResults(), createQuery(parameter));
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
            return new FetchIterator(cursor, metadataMapperFactory.create(
                    parameter.getResultType(), cursor.getResultSet()),
                    parameter.getFilter());
        } catch (SQLException e) {
            cursor.close();
            throw new PersistenceException(e);
        }
    }

    /**
     * @see org.coder.gear.query.gateway.PersistenceGateway#executeUpdate(org.coder.gear.query.free.query.Conditions)
     */
    @Override
    public int executeUpdate(Conditions param) {
        return createQuery(param).executeUpdate();
    }

    /**
     * Get query.
     * @param param the param
     * @return the query
     */
    protected Query createQuery(Conditions param) {

        // build the sql
        String str = param.getSql();
        if (!param.isUseRowSql()) {
            str = loader.build(param.getQueryId(), str);
            str = loader.evaluate(str, param.getParam(), param.getQueryId());
        }
        PreparedQuery preparedQuery = loader.prepare(str,
        		param.getParam(), param.getWrappingClause(),
                param.getQueryId());

        Query query = param.getEntityManager().createNativeQuery(
                preparedQuery.getQueryStatement());

        // hints
        for (Map.Entry<String, Object> h : param.getHints().entrySet()) {
            query.setHint(h.getKey(), h.getValue());
        }
        // parameter
        for (int i = 0; i < preparedQuery.getBindList().size(); i++) {
            query.setParameter(i + 1, preparedQuery.getBindList().get(i));
        }
        return query;
    }

    /**
     * Set range.
     * 
     * @param firstResult the firstResult
     * @param maxSize the maxSize
     * @param query the query
     * @return the query
     */
    protected Query setRangeAndCursor(int firstResult, int maxSize, Query query) {

        if (firstResult > 0) {
            query.setFirstResult(firstResult);
        }
        if (maxSize > 0) {
            query.setMaxResults(maxSize);
        }
        // ResultSetの取得を可能とする。
        query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
        return query;
    }

}
