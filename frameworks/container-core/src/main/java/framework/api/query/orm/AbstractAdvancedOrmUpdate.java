/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import framework.jpqlclient.api.orm.JPAOrmCondition;
import framework.jpqlclient.api.orm.JPAOrmUpdate;
import framework.sqlclient.api.orm.OrmCondition;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * AbstractAdvancedOrmUpdate.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractAdvancedOrmUpdate<T> implements AdvancedOrmUpdate<T>{

	/** エンジン */
	protected OrmUpdate<T> delegate;
	
	/**
	 * @param entityClass エンティティクラス
	 */
	public AbstractAdvancedOrmUpdate(OrmUpdate<T> delegate){
		this.delegate = delegate;
	}

	/**
	 * @see framework.api.query.orm.AdvancedOrmUpdate#setCondition(framework.sqlclient.api.orm.OrmCondition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  <Q extends AbstractAdvancedOrmUpdate<T>> Q  setCondition(OrmCondition<T> condition) {
		if(condition instanceof JPAOrmCondition){
			((JPAOrmUpdate<T>)delegate).setCondition((JPAOrmCondition<T>)condition);
		}else{
			throw new UnsupportedOperationException();
		}
		return (Q)this;
	}
	
	/**
	 * @see framework.api.query.orm.AdvancedOrmUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Q extends AbstractAdvancedOrmUpdate<T>> Q setHint(String key, Object value){
		if(delegate instanceof JPAOrmUpdate){
			((JPAOrmUpdate<T>)delegate).setHint(key, value);
		}else{
			throw new UnsupportedOperationException();
		}
		return (Q)this;
	}
}
