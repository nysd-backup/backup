/**
 * 
 */
package org.coder.gear.sample.javaee7.domain.factory;

import javax.inject.Inject;

import org.coder.gear.sample.javaee7.domain.entity.Order;
import org.coder.gear.sample.javaee7.domain.repository.OrderRepository;

/**
 * @author yoshida-n
 *
 */
public class OrderFactory extends AbstractFactory<Order>{

	@Inject
	private OrderRepository repository;
	
	/**
	 * @param no
	 * @return
	 */
	public Order find(Long no ){
		return repository.find(no);
	}
}
