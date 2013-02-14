/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.txscriptejb.datasource.repository.DefaultTableRepository;

/**
 * Base of the BizLogic.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class BizLogic {

	@PersistenceContext(unitName="default")
	private EntityManager em;

	protected <T> DefaultTableRepository<T> createDefaultRepository(Class<T> entityClass){
		DefaultTableRepository<T> c =  new DefaultTableRepository<T>(em);
		c.setEntityClass(entityClass);
		return c;
	}
	
	protected <T extends DefaultTableRepository<?>> T createRepository(Class<T> repoClass){
		T repo = null;
		try {
			repo = repoClass.getConstructor(EntityManager.class).newInstance(em);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repo;
	}
	
}
