/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.querymodel;

import kosmos.framework.querymodel.QueryModel;
import kosmos.framework.querymodel.QueryProcessorFacade;

/**
 * The updating facade.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryProcessorFacadeImpl  implements QueryProcessorFacade{

	/**
	 * @see kosmos.framework.querymodel.QueryProcessorFacade#execute(kosmos.framework.querymodel.QueryModel)
	 */
	@Override
	public QueryModel execute(QueryModel model) {
		model.accept(new DefaultVisitableQueryProcessorImpl());
		return model;
	}
}
