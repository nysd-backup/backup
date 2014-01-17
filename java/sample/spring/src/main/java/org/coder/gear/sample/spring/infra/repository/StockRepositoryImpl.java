/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.spring.infra.repository;

import org.coder.gear.sample.spring.domain.entity.Stock;
import org.coder.gear.sample.spring.domain.repository.StockRepository;
import org.springframework.stereotype.Repository;


/**
 * ItemRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Repository
public class StockRepositoryImpl extends GenericRepository<Stock> implements StockRepository{
	
}
