/**
 * 
 */
package org.coder.gear.sample.javaee7.domain.factory;

import javax.inject.Inject;

import org.coder.gear.sample.javaee7.domain.entity.Stock;
import org.coder.gear.sample.javaee7.domain.repository.StockRepository;

/**
 * @author yoshida-n
 *
 */
public class StockFactory extends AbstractFactory<Stock>{

	@Inject
	private StockRepository repository;
	
	/**
	 * @param no
	 * @return
	 */
	public Stock find(Long no ){
		return repository.find(no);
	}
}
