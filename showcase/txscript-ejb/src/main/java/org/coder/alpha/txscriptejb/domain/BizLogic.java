/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.txscriptejb.tableentity.TableEntityFinder;

/**
 * Base of the BizLogic.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class BizLogic {

	@PersistenceContext(unitName="default")
	private EntityManager em;

	protected <T extends TableEntityFinder<?>>  T createTableFinder(Class<T> finderClass){
		T finder = null;
		try {
			finder = finderClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finder.setEntityManager(em);
		return finder;
	}
	
}
