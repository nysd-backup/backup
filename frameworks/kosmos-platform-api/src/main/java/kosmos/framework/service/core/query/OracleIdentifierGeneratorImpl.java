/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

import java.math.BigDecimal;
import java.util.HashMap;

import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.sqlclient.api.free.DynamicNativeQuery;
import kosmos.framework.sqlclient.api.free.QueryFactory;

/**
 * IdentifierGenerator for ORACLE Database.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OracleIdentifierGeneratorImpl implements IdentifierGenerator{
	
	/**
	 * @see kosmos.framework.service.core.query.IdentifierGenerator#generate(java.lang.String, java.lang.Object[])
	 */
	@Override
	public long generate(String name, Object... arguments) {		
		QueryFactory factory = ServiceLocator.createDefaultQueryFactory();
		DynamicNativeQuery query = factory.createQuery(DynamicNativeQuery.class);
		query.setSql(String.format("SELECT %s.NEXTVAL AS VAL FROM DUAL", name));
		HashMap<String,BigDecimal> decimal = query.getSingleResult();
		return decimal.get("VAL").longValue();		
	}
	
}
