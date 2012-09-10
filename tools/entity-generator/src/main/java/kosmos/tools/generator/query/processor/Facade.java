/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator.query.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import kosmos.tools.generator.query.model.ArgumentsMeta;
import kosmos.tools.generator.query.template.NamedModifyQueryTemplate;
import kosmos.tools.generator.query.template.NamedReadQueryTemplate;
import kosmos.tools.generator.query.template.NativeModifyQueryTemplate;
import kosmos.tools.generator.query.template.NativeReadQueryTemplate;

import org.apache.commons.lang.StringUtils;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Facade {
	
	/**
	 * @param user
	 * @param pass
	 * @param url
	 */
	public void output(String dir) throws Exception{
		List<ArgumentsMeta> tables = ModelCreator.createArguments(dir);				
		for(ArgumentsMeta table :tables){			
			String data = null;
			//更新
			if(table.update){				
				if(table.named){
					data = new NamedModifyQueryTemplate().generate(table);
				}else{
					data = new NativeModifyQueryTemplate().generate(table);
				}
			//検索	
			}else{								
				if(table.named){
					data = new NamedReadQueryTemplate().generate(table);
				}else{
					data = new NativeReadQueryTemplate().generate(table);
				}				
			}
			
			File file = new File(
					String.format("target/%s/%s.java",table.packageName,StringUtils.capitalize(table.fileName)));
			file.getParentFile().mkdirs();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(data);
			writer.close();
		}
	}

}
