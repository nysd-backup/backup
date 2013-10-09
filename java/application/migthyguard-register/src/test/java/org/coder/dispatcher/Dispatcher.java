/**
 * 
 */
package org.coder.dispatcher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.coder.mightyguard.register.repository.DataRepository;
import org.coder.mightyguard.register.service.RegisterService;
import org.junit.Test;

/**
 * @author yoshida-n
 *
 */
public class Dispatcher {

	@Test
	public void execute() throws Exception{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("migthyguard");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		DataRepository dr = new DataRepository();
		dr.setSvnRootDir("https://nasa.future.co.jp/bne/svn/marketing/trunk/product/");		
		RegisterService sv = new RegisterService();
		dr.setEntityManager(em);
		sv.setDataRepository(dr);		
		sv.setDownloadDir("C:/Project/BNE/marketing/svn/trunk/tools/version-register/target/dependency");
		sv.register("1.0.277");
		em.flush();
	}
}
