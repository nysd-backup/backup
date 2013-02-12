/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.coder.alpha.txscriptejb.rowdatagateway.AbstractRowdataGateway;
import org.coder.alpha.txscriptejb.rowdatagateway.RowdataGatewayFinder;

/**
 * Base of the BizLogic.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractBizLogic {

	@PersistenceContext(unitName="default")
	private EntityManager em;

	
	protected <T extends AbstractRowdataGateway> RowdataGatewayFinder<T> createFinder(Class<T> entityClass){
		RowdataGatewayFinder<T> finder = new RowdataGatewayFinder<T>(em,entityClass);
		return finder;
	}
	
}
