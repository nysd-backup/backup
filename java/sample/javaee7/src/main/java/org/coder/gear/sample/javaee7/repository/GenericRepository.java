/**
 * 
 */
package org.coder.gear.sample.javaee7.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author yoshida-n
 *
 */
public abstract class GenericRepository<T> {

	@PersistenceContext
	private EntityManager em;
	
	private Class<T> entityClass;
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public GenericRepository(){
		ParameterizedType t = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = t.getActualTypeArguments();		
		entityClass = (Class<T>)types[0];
	}
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public T find(Object pk) {
		return em.find(entityClass, pk);
	}
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public boolean exists(Object pk){
		return find(pk) != null;
	}
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public boolean isEmpty(Object pk){
		return !exists(pk);
	}
	
	/**
	 * @param pk 主キー
	 * @return 結果
	 */
	public void remove(T entity){
		em.remove(entity);
	}
	
	/**
	 * @param entityList
	 */
	@SuppressWarnings("unchecked")
	public void persist(T... entityList){
		for(T e : entityList){
			em.persist(e);
		}
	}
}
