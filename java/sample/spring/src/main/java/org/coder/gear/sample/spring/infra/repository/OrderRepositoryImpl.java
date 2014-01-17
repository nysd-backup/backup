/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.infra.repository;

import org.coder.gear.sample.spring.domain.entity.Order;
import org.coder.gear.sample.spring.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Repository
public class OrderRepositoryImpl extends GenericRepository<Order> implements OrderRepository{

	
}
