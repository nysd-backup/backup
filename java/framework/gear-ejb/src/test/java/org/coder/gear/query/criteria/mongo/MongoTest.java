/**
 * 
 */
package org.coder.gear.query.criteria.mongo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.coder.gear.query.QueryFactoryFinder;
import org.coder.gear.query.criteria.query.SingleReadQuery;
import org.junit.Ignore;
import org.junit.Test;

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
	    Order order = new Order();
	    order.setDescription("desc");
	    order.setTotalCost(100);
	    em.getTransaction().begin();
	    em.persist(order);
	    em.flush();	   
	    
	    SingleReadQuery<Order> query = new QueryFactoryFinder().createCriteriaQueryFactory().createSingleReadQuery(Order.class, em);
		query.eq("totalCost", 100);
		Order result = query.call();
		System.out.println(ToStringBuilder.reflectionToString(result));
	}
}
