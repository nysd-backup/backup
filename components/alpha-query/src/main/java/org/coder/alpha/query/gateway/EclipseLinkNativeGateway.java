/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.gateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.coder.alpha.jdbc.domain.PreparedQuery;
import org.coder.alpha.jdbc.domain.TotalList;
import org.coder.alpha.jdbc.service.ModifyingRequest;
import org.coder.alpha.jdbc.service.QueryService;
import org.coder.alpha.jdbc.service.impl.QueryServiceImpl;
import org.coder.alpha.jdbc.strategy.MetadataMapperFactory;
import org.coder.alpha.jdbc.strategy.QueryLoader;
import org.coder.alpha.jdbc.strategy.ResultSetHandler;
import org.coder.alpha.jdbc.strategy.impl.DefaultMetadataMapperFactory;
import org.coder.alpha.jdbc.strategy.impl.DefaultResultSetHandler;
import org.coder.alpha.jdbc.strategy.impl.QueryLoaderTrace;
import org.coder.alpha.query.free.Conditions;
import org.coder.alpha.query.free.LazyList;
import org.coder.alpha.query.free.ReadingConditions;
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
 * @version	created.
 */
public class EclipseLinkNativeGateway implements PersistenceGateway {
	
	private QueryService queryService = new QueryServiceImpl(); 
		
	/**
     * <pre>
     *      QueryLoader .
     * </pre>
     */
    private QueryLoader loader = new QueryLoaderTrace();

    /**
     * <pre>
     *      ResultSetHandler .
     * </pre>
     */
    private ResultSetHandler handler = new DefaultResultSetHandler();

    /**
     * <pre>
     *      MetadataMapperFactory .
     * </pre>
     */
    private MetadataMapperFactory recordHandlerFactory = new DefaultMetadataMapperFactory();

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
     * set query service.
     * 
     * @param queryService
     *            QueryLoader
     */
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * <pre>
     *    ResultSetHandlerを設定する .
     * </pre>
     * 
     * @param handler
     *            ResultSetHandler
     */
    public void setResultSetHandler(ResultSetHandler handler) {
        this.handler = handler;
    }

    /**
     * <pre>
     *    MetadataMapperFactoryを設定する .
     * </pre>
     * 
     * @param recordHandlerFactory
     *            MetadataMapperFactory
     */
    public void setRecordHandlerFactory(
            MetadataMapperFactory recordHandlerFactory) {
        this.recordHandlerFactory = recordHandlerFactory;
    }

    /**
     * <pre>
     *    結果リストを取得する .
     * </pre>
     * 
     * @param <T>
     *            テンプレート
     * @param parameter
     *            ReadingConditions
     * @return 結果リスト
     * @see jp.co.benesse.marketing.framework.ejb.query.free.gateway.PersistenceGateway#getResultList(jp.co.benesse.marketing.framework.ejb.query.free.ReadingConditions)
     */
    @Override
    public <T> List<T> getResultList(ReadingConditions parameter) {

        Query query = setRangeAndCursor(parameter.getFirstResult(),
                parameter.getMaxResults(), createQuery(parameter));
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
            return handler.getResultList(cursor.getResultSet(),
                    parameter.getResultType(), parameter.getFilter());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            cursor.close();
        }
    }

    /**
     * <pre>
     *    全結果を取得する .
     * </pre>
     * 
     * @param <T>
     *            テンプレート
     * @param parameter
     *            ReadingConditions
     * @return 全結果
     * @see jp.co.benesse.marketing.framework.ejb.query.free.gateway.PersistenceGateway#getTotalResult(jp.co.benesse.marketing.framework.ejb.query.free.ReadingConditions)
     */
    @Override
    public <T> TotalList<T> getTotalResult(final ReadingConditions parameter) {

        Query query = setRangeAndCursor(parameter.getFirstResult(), 0,
                createQuery(parameter));
        TotalList<T> result;
        @SuppressWarnings("unchecked")
        Class<T> resultType = (Class<T>) parameter.getResultType();
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
            ResultSet rs = cursor.getResultSet();
            result = handler.getResultList(rs, resultType,
                    parameter.getFilter(), parameter.getMaxResults());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            cursor.close();
        }
        return result;
    }

    /**
     * <pre>
     *    Fetch結果を取得する .
     * </pre>
     * 
     * @param parameter
     *            ReadingConditions
     * @return Fetch結果
     * @see jp.co.benesse.marketing.framework.ejb.query.free.gateway.PersistenceGateway#getFetchResult(jp.co.benesse.marketing.framework.ejb.query.free.ReadingConditions)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List getFetchResult(ReadingConditions parameter) {
        Query query = setRangeAndCursor(parameter.getFirstResult(),
                parameter.getMaxResults(), createQuery(parameter));
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
            return new LazyList(cursor, recordHandlerFactory.create(
                    parameter.getResultType(), cursor.getResultSet()),
                    parameter.getFilter());
        } catch (SQLException e) {
            cursor.close();
            throw new PersistenceException(e);
        }
    }

    /**
     * <pre>
     *    カウントする .
     * </pre>
     * 
     * @param param
     *            ReadingConditions
     * @return カウント結果
     * @see jp.co.benesse.marketing.framework.ejb.query.free.gateway.PersistenceGateway#count(jp.co.benesse.marketing.framework.ejb.query.free.ReadingConditions)
     */
    @Override
    public long count(ReadingConditions param) {
        Query query = createQuery(param);
        Object value = query.getSingleResult();
        return Long.parseLong(value.toString());
    }

    /**
     * <pre>
     *    更新処理を行う .
     * </pre>
     * 
     * @param param
     *            ModifyingConditions
     * @return 処理した行数
     * @see jp.co.benesse.marketing.framework.ejb.query.free.gateway.PersistenceGateway#executeUpdate(jp.co.benesse.marketing.framework.ejb.query.free.ModifyingConditions)
     */
    @Override
    public int executeUpdate(Conditions param) {
        return createQuery(param).executeUpdate();
    }

    /**
     * <pre>
     *    Queryを生成する .
     * </pre>
     * 
     * @param param
     *            Conditions
     * @return Query
     */
    @SuppressWarnings("unchecked")
    protected Query createQuery(Conditions param) {

        // build the sql
        String str = param.getSql();
        if (!param.isUseRowSql()) {
            str = loader.build(param.getQueryId(), str);
            str = loader.evaluate(str, param.getParam(), param.getQueryId());
        }
        PreparedQuery preparedQuery = loader.prepare(str,
                Arrays.asList(param.getParam()), param.getWrappingClause(),
                param.getQueryId());

        Query query = param.getEntityManager().createNativeQuery(
                preparedQuery.getQueryStatement());

        // hints
        for (Map.Entry<String, Object> h : param.getHints().entrySet()) {
            query.setHint(h.getKey(), h.getValue());
        }
        // parameter
        for (int i = 0; i < preparedQuery.getFirstList().size(); i++) {
            query.setParameter(i + 1, preparedQuery.getFirstList().get(i));
        }
        return query;
    }

    /**
     * <pre>
     *    範囲を設定する .
     * </pre>
     * 
     * @param firstResult
     *            開始位置
     * @param maxSize
     *            最大数
     * @param query
     *            Query
     * @return Query
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

	/**
	 * @see org.coder.alpha.query.gateway.elink.free.gateway.PersistenceGateway#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<Conditions> param) {
		
		//TODO 要検証		
		
		List<ModifyingRequest> engineParams = new ArrayList<ModifyingRequest>();
		for(Conditions p: param){
			ModifyingRequest ep = new ModifyingRequest();
			ep.setAllParameter(p.getParam());		
			ep.setSqlId(p.getQueryId());
			ep.setSql(p.getSql());
			ep.setUseRowSql(p.isUseRowSql());	
			Object value = p.getHints().get(QueryHints.JDBC_TIMEOUT);
			if( value != null){
				ep.setTimeoutSeconds((Integer)value);
			}
			engineParams.add(ep);
		}		
		
		Connection con = param.get(0).getEntityManager().unwrap(Connection.class);
		
		return queryService.executeBatch(engineParams, con);
		
	}

}
