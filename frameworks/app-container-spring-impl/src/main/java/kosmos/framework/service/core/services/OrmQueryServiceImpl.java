/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.annotation.Resource;

import kosmos.framework.api.query.orm.AdvancedOrmQueryFactory;
import kosmos.framework.api.query.services.OrmQueryService;
import kosmos.framework.core.entity.AbstractEntity;
import kosmos.framework.service.core.services.AbstractOrmQueryService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrmQueryServiceImpl<T extends AbstractEntity> extends AbstractOrmQueryService<T> implements OrmQueryService<T>{
	
	/** the factory to create query */
	@Resource
	private AdvancedOrmQueryFactory ormQueryFactory;

	/**
	 * @see kosmos.framework.service.core.services.AbstractOrmQueryService#getQueryFactory()
	 */
	@Override
	protected AdvancedOrmQueryFactory getQueryFactory() {
		return ormQueryFactory;
	}

}
