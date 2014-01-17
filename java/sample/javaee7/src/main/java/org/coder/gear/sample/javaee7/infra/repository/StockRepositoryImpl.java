/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.infra.repository;

import org.coder.gear.sample.javaee7.domain.entity.Stock;
import org.coder.gear.sample.javaee7.domain.repository.StockRepository;
import org.coder.gear.trace.Traceable;


/**
 * ItemRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
public class StockRepositoryImpl extends GenericRepository<Stock> implements StockRepository{
	
}
