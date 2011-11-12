/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.query.orm.impl;

import kosmos.framework.api.query.orm.StrictUpdate;
import kosmos.framework.core.entity.Metadata;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;

/**
 * StrictUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultStrictUpdate<T> extends AbstractAdvancedOrmUpdate<T> implements StrictUpdate<T>{

	/**
	 * @param delegate the delegate
	 */
	public DefaultStrictUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#eq(kosmos.framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#gt(kosmos.framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#lt(kosmos.framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#gtEq(kosmos.framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#ltEq(kosmos.framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#between(kosmos.framework.core.entity.Metadata, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#set(kosmos.framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> set(Metadata<T,V> column , V value ){
		delegate.set(column.name(), value);
		return this;
	}

	/**
	 * @see kosmos.framework.api.query.orm.StrictUpdate#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

}

