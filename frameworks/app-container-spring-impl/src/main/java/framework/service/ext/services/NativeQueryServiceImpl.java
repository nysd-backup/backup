/**
 * Copyright 2011 the original author
 */
package framework.service.ext.services;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framework.api.query.services.NativeQueryService;
import framework.service.core.services.AbstractNativeQueryService;
import framework.sqlclient.api.free.QueryFactory;

/**
 * リモートから�Eクエリ実行�Eインターフェース.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class NativeQueryServiceImpl extends AbstractNativeQueryService implements NativeQueryService{

	/** クエリファクトリ */
	@Resource
	private QueryFactory clientQueryFactory;

	/**
	 * @see framework.service.core.services.AbstractNativeQueryService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return clientQueryFactory;
	}

	
}
