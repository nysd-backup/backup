/**
 * Use is subject to license terms.
 */
package framework.api.query.orm.impl;

import framework.api.query.orm.AbstractAdvancedOrmUpdate;
import framework.api.query.orm.StrictUpdate;
import framework.core.entity.Metadata;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * ORマッピングクエリ.
 *
 * @author yoshida-n
 * @version	2011/04/02 created.
 */
public class DefaultStrictUpdate<T> extends AbstractAdvancedOrmUpdate<T> implements StrictUpdate<T>{

	/**
	 * @param delegate
	 */
	public DefaultStrictUpdate(OrmUpdate<T> delegate) {
		super(delegate);
	}

	/**
	 * @see framework.jpqlclient.api.AdvancedOrmUpdate#eq(framework.core.entity.Metadata, V)
	 */
	@Override
	public <V> StrictUpdate<T> eq(Metadata<T,V> column , V value ){
		delegate.eq(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.jpqlclient.api.AdvancedOrmUpdate#gt(framework.core.entity.Metadata, V)
	 */
	@Override
	public <V> StrictUpdate<T> gt(Metadata<T,V> column , V value ){
		delegate.gt(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.jpqlclient.api.AdvancedOrmUpdate#lt(framework.core.entity.Metadata, V)
	 */
	@Override
	public <V> StrictUpdate<T> lt(Metadata<T,V> column , V value ){
		delegate.lt(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.jpqlclient.api.AdvancedOrmUpdate#gtEq(framework.core.entity.Metadata, V)
	 */
	@Override
	public <V> StrictUpdate<T> gtEq(Metadata<T,V> column , V value ){
		delegate.gtEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.jpqlclient.api.AdvancedOrmUpdate#ltEq(framework.core.entity.Metadata, V)
	 */
	@Override
	public <V> StrictUpdate<T> ltEq(Metadata<T,V> column , V value ){
		delegate.ltEq(column.name(), value);
		return this;
	}
	
	/**
	 * @see framework.jpqlclient.api.AdvancedOrmUpdate#between(framework.core.entity.Metadata, V, V)
	 */
	@Override
	public <V> StrictUpdate<T> between(Metadata<T,V> column , V from , V to ){
		delegate.between(column.name(), from,to);
		return this;
	}
	
	/**
	 * @see framework.api.query.orm.StrictUpdate#set(framework.core.entity.Metadata, java.lang.Object)
	 */
	@Override
	public <V> StrictUpdate<T> set(Metadata<T,V> column , V value ){
		delegate.set(column.name(), value);
		return this;
	}

	/**
	 * @see framework.api.query.orm.StrictUpdate#update()
	 */
	@Override
	public int update() {
		return delegate.update();
	}

}

