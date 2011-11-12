/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.annotation.Resource;

import kosmos.framework.api.query.services.PagingService;
import kosmos.framework.service.core.services.AbstractLatestPagingService;
import kosmos.framework.sqlclient.api.free.QueryFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * a paging service.
 * 
 * <pre>
 * Always execute SQL to get latest data.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service("LatestPagingServiceImpl")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class LatestPagingServiceImpl extends AbstractLatestPagingService implements PagingService{

	/** the factory to create query */
	@Resource
	private QueryFactory clientQueryFactory;

	/**
	 * @see kosmos.framework.service.core.services.AbstractLatestPagingService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return clientQueryFactory;
	}
	
}
