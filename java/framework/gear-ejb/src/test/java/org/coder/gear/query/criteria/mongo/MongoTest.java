/**
 * 
 */
package org.coder.gear.query.criteria.mongo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.nosql.adapters.mongo.MongoConnection;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

/**
 * @author yoshida-n
 *
 */
@Ignore
public class MongoTest {

	@Test
	public void test(){
		
		EntityManagerFactory factory= Persistence.createEntityManagerFactory("mongo-example");
	    EntityManager em = factory.createEntityManager();
	    em.getTransaction().begin();
	    for(int i = 0; i < 0; i++){
	    	Logger time = new Logger();
	    	time.setMonth((i % 11 + 1) + "");
	    	time.setTime("122400");
	    	time.setUid(i+1);
	    	em.persist(time);
	    }
	    em.createQuery("delete from Logger").executeUpdate();
	    em.flush();
	    DB db = ((MongoConnection)em.unwrap(javax.resource.cci.Connection.class)).getDB();
	    DBCollection col = db.getCollection("LOGGER");
	  
	    WriteResult result = col.update(new BasicDBObject("UID",2633),new BasicDBObject("TIME","23"));
	    System.out.println( result.getN() );
	    
//	    Order order = new Order();
//	    order.setDescription("desc");
//	    order.setTotalCost(100);
//	    em.getTransaction().begin();
//	    em.persist(order);
//	    em.flush();	   
//	    
//	    SingleReadQuery<Order> query = new QueryFactoryFinder().createCriteriaQueryFactory().createSingleReadQuery(Order.class, em);
//		query.eq("totalCost", 100);
//		Order result = query.call();
//		
//		
//		System.out.println(ToStringBuilder.reflectionToString(result));
	}
}
