/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;

import framework.jpqlclient.api.EntityManagerProvider;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SessionBean {

	@EJB
	private EntityManagerProvider provider;
	
//	@EJB
//	private TestBean bean;
	
	public void test(){
		EntityManager em = provider.getEntityManager();
		List<?> l = em.createNativeQuery("SELECt * FROM TESTA").getResultList();
		System.out.println(l.size());
		
	//	bean.test();
	}
}
