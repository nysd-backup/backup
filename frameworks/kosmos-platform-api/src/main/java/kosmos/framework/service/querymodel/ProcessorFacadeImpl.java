/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.querymodel;

import kosmos.framework.querymodel.QueryModel;
import kosmos.framework.querymodel.ProcessorFacade;

/**
 * The updating facade.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ProcessorFacadeImpl  implements ProcessorFacade{

	/**
	 * @see kosmos.framework.querymodel.ProcessorFacade#execute(kosmos.framework.querymodel.QueryModel)
	 */
	@Override
	public void execute(QueryModel model) {
		model.accept(new DefaultQueryProcessorImpl());
	}
}
