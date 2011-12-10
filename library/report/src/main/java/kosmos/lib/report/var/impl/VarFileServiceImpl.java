/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.var.impl;

import java.util.Iterator;

import kosmos.lib.report.var.DataCommand;
import kosmos.lib.report.var.RequestTarget;
import kosmos.lib.report.var.VarFileRequest;
import kosmos.lib.report.var.VarFileService;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class VarFileServiceImpl implements VarFileService {
	
	/**
	 * @see kosmos.lib.report.var.VarFileService#create(kosmos.lib.report.var.VarFileRequest)
	 */
	@Override
	public void create(VarFileRequest request) {

		RequestTarget[] targets = request.getTargets();
		for(RequestTarget rt : targets){
			DataCommand<?> c = rt.getCommand();			
			try{
				
				if(c.getTitle() != null && c.getTitle().length > 0){
					request.getOpendWriter().write(c.getTitle());
				}
				
				Iterator<?> iterator = c.getResultList().iterator();
				int count = 0;
				while(iterator.hasNext()){
					count++;
					Object bean = iterator.next();
					request.getOpendWriter().write(bean,rt.getIncludes());
				}
				if( count == 0 && rt.getEmptyHandler() != null){
					rt.getEmptyHandler().handle();
				}else if(rt.getMaxSize() < count && rt.getOverLimitHandler() != null){
					rt.getOverLimitHandler().handle();
				}
			}finally{
				c.close();
			}
		}
		
	}

}
