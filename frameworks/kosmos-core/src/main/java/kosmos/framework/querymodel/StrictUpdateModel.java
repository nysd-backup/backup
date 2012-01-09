/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import kosmos.framework.core.query.LimitedOrmUpdate;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;

/**
 * QueryModel for OrmUpdate.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StrictUpdateModel extends QueryModel {

	private OrmUpdateParameter<Object> parameter = null;
	
	/**
	 * @param the update
	 */
	public StrictUpdateModel(LimitedOrmUpdate<Object> update){
		parameter = update.getCurrentParams();
	}
	
	/**
	 * @see kosmos.framework.querymodel.QueryModel#accept(kosmos.framework.querymodel.VisitableQueryProcessor)
	 */
	@Override
	public void accept(VisitableQueryProcessor updater){
		updater.update(this);
	}

	/**
	 * @return
	 */
	public OrmUpdateParameter<Object> getRequest() {
		return parameter;
	}

}
