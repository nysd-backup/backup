/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.sample.pattern.domainmodel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.framework.registry.DefaultQueryFactoryFinder;
import org.coder.alpha.query.criteria.ComparingOperand;
import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.sample.common.Traceable;
import org.coder.alpha.sample.pattern.domainmodel.domain.Account;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
public class AccountRepository {

	@PersistenceContext
	protected EntityManager em;
	/**
	 * @param customerId
	 * @return
	 */
	public List<Account> findByCustomer(String customerId){
		ListReadQuery<Account> query = new DefaultQueryFactoryFinder().createCriteriaQueryFactory().createListReadQuery(Account.class, em);
		query.addCriteria("customerId",ComparingOperand.Equal , customerId);
		return query.call();
	}

	/**
	 * Finds the entity.
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public Account find(long pk){
		Account entity = em.find(Account.class,pk);
		return entity;
	}
	
	/**
	 * Persists the entity.
	 * @param entity
	 */
	public void persist(Account entity){
		em.persist(entity);
	}
	
}
