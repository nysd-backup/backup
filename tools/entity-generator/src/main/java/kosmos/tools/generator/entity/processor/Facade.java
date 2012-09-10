/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator.entity.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import kosmos.tools.generator.Utils;
import kosmos.tools.generator.entity.model.Table;
import kosmos.tools.generator.entity.template.EntityTemplate;

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
	public void output(String user , String pass , String url , String sql ,String versionColName, String basePkg) throws Exception{
		
		List<Table> tables = ModelCreator.createModel(user,pass, url, sql,versionColName);			
	
		for(Table table :tables){			
			//エンティティ
			table.basePkgName = basePkg;
			String data = new EntityTemplate().generate(table);
			File file = new File(
					String.format("target/%s/gen/%s.java",basePkg,StringUtils.capitalize(Utils.toCamelCase(table.physicalName))));
			file.getParentFile().mkdirs();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(data);
			writer.close();
		}
	}

}
