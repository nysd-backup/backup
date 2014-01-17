/**
 * Copyright 2011 the original author
 */
package org.coder.gear.sample.javaee7.infra.repository;

import java.util.HashMap;
import java.util.Map;

import org.coder.gear.sample.javaee7.domain.entity.Order;
import org.coder.gear.sample.javaee7.domain.entity.OrderDetail;
import org.coder.gear.sample.javaee7.domain.entity.OrderDetailPK;
import org.coder.gear.trace.Traceable;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;


/**
 * OrderRepository.
 * 
 * this class is not required.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
public class OrderRepository extends GenericRepository<Order>{

	/**
	 * ドメインルートから経由で取得するのが正しいため本来このように子だけ検索するのはよくない。
	 * @param pk
	 * @return
	 */
	@Deprecated
	public OrderDetail findChild(OrderDetailPK pk){
		em.flush();
		Map<String,Object> hint = new HashMap<>();
		hint.put(QueryHints.REFRESH, HintValues.TRUE);
		return em.find(OrderDetail.class,pk,hint);
	}
}
