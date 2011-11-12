/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.annotation.Resource;

import kosmos.framework.api.query.services.NativeQueryService;
import kosmos.framework.service.core.services.AbstractNativeQueryService;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * A native query service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class NativeQueryServiceImpl extends AbstractNativeQueryService implements NativeQueryService{

	/** the factory to create query */
	@Resource
	private QueryFactory clientQueryFactory;

	/**
	 * @see kosmos.framework.service.core.services.AbstractNativeQueryService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return clientQueryFactory;
	}

	
}
