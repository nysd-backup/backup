/**
 * 
 */
package org.coder.dispatcher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.coder.mightyguard.register.repository.DataRepository;
import org.coder.mightyguard.register.service.ApplicationVersionService;
import org.coder.mightyguard.register.service.DatabaseVersionService;
import org.coder.mightyguard.register.service.ReferenceService;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author yoshida-n
 *
 */
@Ignore
public class Dispatcher {
	
	@Test
	public void refer() throws Exception {
	
		ReferenceService refer = new ReferenceService();
		System.out.println( refer.allTheList(10) );
		System.out.println( refer.previousList("1.0.128", 10) );
	}
	
	public void db() throws Exception {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mightyguard");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		EntityManagerFactory remoteFactory = Persistence.createEntityManagerFactory("target");
		EntityManager rem = remoteFactory.createEntityManager();
		
		DatabaseVersionService dvs  =  new DatabaseVersionService();
		dvs.setEntityManager(em);
		dvs.setSql("oracleSql.sql");
		dvs.setRemoteEntityManager(rem);
		dvs.register("1.0.277", "A999");
		em.flush();
	}

	public void app() throws Exception{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mightyguard");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		DataRepository dr = new DataRepository();
		dr.setSvnRootDir("https://nasa.future.co.jp/bne/svn/marketing/trunk/product/");		
		ApplicationVersionService sv = new ApplicationVersionService();
		dr.setEntityManager(em);
		sv.setDataRepository(dr);		
		sv.setDownloadDir("C:/Project/BNE/marketing/svn/trunk/tools/version-register/target/dependency");
		sv.register("1.0.277");
		em.flush();
	}
}
