/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.flat.impl;

import java.util.Iterator;

import kosmos.lib.report.flat.DataCommand;
import kosmos.lib.report.flat.FlatFileRequest;
import kosmos.lib.report.flat.FlatFileService;
import kosmos.lib.report.flat.FlatFileWriter;
import kosmos.lib.report.flat.RequestTarget;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FlatFileServiceImpl implements FlatFileService {

	private FlatFileWriter writer = new CsvWriterImpl();
	
	/**
	 * @param writer the writer to set
	 */
	public void setFlatFileWriter(FlatFileWriter writer){
		this.writer = writer;
	}
	
	/**
	 * @see kosmos.lib.report.flat.FlatFileService#create(kosmos.lib.report.flat.FlatFileRequest)
	 */
	@Override
	public void create(FlatFileRequest request) {

		RequestTarget[] targets = request.getTargets();
		for(RequestTarget rt : targets){
			DataCommand<?> c = rt.getCommand();
			Iterator<?> iterator = c.getResultList().iterator();
			try{
				int count = 0;
				while(iterator.hasNext()){
					count++;
					Object bean = iterator.next();
					writer.write(request.getOpendWriter(),bean,rt.getIncludes());
				}
				if( count == 0 && rt.getEmptyHandler() != null){
					rt.getEmptyHandler().handle();
				}else if(rt.getMaxSize() < count && rt.getOverLimitHandler() != null){
					rt.getOverLimitHandler().handle();
				}
			}finally{
				iterator.remove();
			}
		}
		
	}

}
