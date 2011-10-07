/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.ejb.Stateless;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.query.services.OrmQueryService;
import framework.core.entity.AbstractEntity;
import framework.service.core.services.AbstractOrmQueryService;
import framework.service.ext.locator.ServiceLocatorImpl;

/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
public class OrmQueryServiceImpl<T extends AbstractEntity> extends AbstractOrmQueryService<T> implements OrmQueryService<T>{

	/**
	 * @see framework.service.core.services.AbstractOrmQueryService#getQueryFactory()
	 */
	@Override
	protected AdvancedOrmQueryFactory getQueryFactory() {
		return ServiceLocatorImpl.getComponentBuilder().createOrmQueryFactory();
	}

}
