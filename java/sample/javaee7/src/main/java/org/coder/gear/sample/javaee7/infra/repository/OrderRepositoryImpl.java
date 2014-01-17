/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.infra.repository;

import org.coder.gear.sample.javaee7.domain.entity.Order;
import org.coder.gear.sample.javaee7.domain.repository.OrderRepository;
import org.coder.gear.trace.Traceable;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
public class OrderRepositoryImpl extends GenericRepository<Order> implements OrderRepository{

	
}
