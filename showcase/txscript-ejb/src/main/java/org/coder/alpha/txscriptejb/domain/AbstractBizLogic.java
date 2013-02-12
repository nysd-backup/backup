/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.txscriptejb.tableentity.AbstractTableEntity;
import org.coder.alpha.txscriptejb.tableentity.TableEntityFinder;

/**
 * Base of the BizLogic.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractBizLogic {

	@PersistenceContext(unitName="default")
	private EntityManager em;

	
	protected <T extends AbstractTableEntity> TableEntityFinder<T> createTableEntityFinder(Class<T> entityClass){
		TableEntityFinder<T> finder = new TableEntityFinder<T>(em,entityClass);
		return finder;
	}
	
}
