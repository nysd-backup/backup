/**
 * 
 */
package org.coder.gear.mongo.aggregation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.resource.cci.Connection;

import org.eclipse.persistence.internal.nosql.adapters.mongo.MongoConnection;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * @author yoshida-n
 *
 */
@Ignore
public class QueryTest {

	@Test
	public void test(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mightyguard");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();		
		MongoConnection mc = MongoConnection.class.cast(em.unwrap(Connection.class));
		DB db = mc.getDB();
		DBCollection cl = db.getCollection("APPVERSION");
		
		Query query = new Query(cl);
		BasicDBList result = query.lt("DATE", "20131201").keys("VERSION","DATE").sum("count").desc("count").aggregate();
		for(Object o : result){
			System.out.println(o);
		}
	}
	
}
