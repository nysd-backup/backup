/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.strategy;

import java.util.List;

import alpha.query.criteria.CriteriaModifyingConditions;
import alpha.query.criteria.CriteriaReadingConditions;
import alpha.query.free.strategy.InternalQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractDataMapper implements DataMapper{

	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}
	
	/**
	 * Creates the QueryStatementBuilder
	 * @return builder
	 */
	protected abstract QueryStatementBuilder createBuilder();
	
	/**
	 * @see alpha.query.criteria.strategy.DataMapper#getResultList(alpha.query.criteria.CriteriaReadingConditions)
	 */
	@Override
	public <E> List<E> getResultList(CriteriaReadingConditions<E> condition) {
		return internalQuery.getResultList(condition.buildSelect(createBuilder()));
	}

	/**
	 * @see alpha.query.criteria.strategy.DataMapper#update(alpha.query.criteria.CriteriaReadingConditions, java.util.Map)
	 */
	@Override
	public int update(CriteriaModifyingConditions<?> condition) {		
		return internalQuery.executeUpdate(condition.buildUpdate(createBuilder()));
	}

	/**
	 * @see alpha.query.criteria.strategy.DataMapper#delete(alpha.query.criteria.CriteriaReadingConditions)
	 */
	@Override
	public int delete(CriteriaModifyingConditions<?> condition) {	
		return internalQuery.executeUpdate(condition.buildDelete(createBuilder()));
	}

	/**
	 * @see alpha.query.criteria.strategy.DataMapper#getFetchResult(alpha.query.criteria.CriteriaReadingConditions, alpha.query.free.QueryCallback)
	 */
	@Override
	public <E> List<E> getFetchResult(CriteriaReadingConditions<E> condition) {
		return internalQuery.getFetchResult(condition.buildSelect(createBuilder()));
	}
}
