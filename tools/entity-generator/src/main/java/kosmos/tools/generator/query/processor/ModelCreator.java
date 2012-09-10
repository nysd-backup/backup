/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator.query.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kosmos.tools.generator.query.model.Arguments;
import kosmos.tools.generator.query.model.ArgumentsMeta;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ModelCreator {
	
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-zA-Z0-9][a-zA-Z0-9_]*)");
	private static final Pattern VELOCITY_BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+)\\$([a-zA-Z0-9][a-zA-Z0-9_]*)");

	/**
	 * 引数リストを検索する
	 * @param dir
	 * @return
	 */
	public static List<ArgumentsMeta> createArguments(String dir){

		URL url = Thread.currentThread().getContextClassLoader().getResource(dir);
		File f = null;
		if( url == null){
			f = new File(dir);
		}else {
			f = new File(url.getFile());
		}
		
		File[] files = f.listFiles();
		if( files == null || files.length == 0){
			throw new RuntimeException("no file in " + f.getAbsolutePath());
		}
		List<ArgumentsMeta> meta = new ArrayList<ArgumentsMeta>();
		for(File e : files){
			if(!e.getName().endsWith(".sql") && !e.getName().endsWith(".jpql")){
				continue;
			}
			if(!e.getName().contains("_")){
				throw new IllegalArgumentException("query file must contain _");
			}
			int index = e.getName().indexOf("_");
			String typeName = e.getName().substring(index+1);
			ArgumentsMeta am = new ArgumentsMeta();
			am.packageName = "";		
			am.named = e.getName().endsWith(".jpql");
			am.update = !e.getName().startsWith("Sel_");				
			am.fileName = typeName.substring(0,typeName.indexOf(".")) + (am.named ? "Named" :"") + (am.update ? "ModifyQuery" : "ReadQuery");			
			am.arguments = new ArrayList<Arguments>();
			am.filePath = typeName;
			
			String sql = getSql(e);
			
			Set<String> bind = getBindList(sql);
			bind.addAll(getVelocityBindList(sql));
			for(String name: bind){
				Arguments a = new Arguments();
				a.name = name;
				a.bindable = true;			
				am.arguments.add(a);
			}						
			meta.add(am);			
		}
		return meta;
		
	}
	
	/**
	 * SQLを検索する
	 * @param path
	 * @return
	 */
	private static String getSql(File path){
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try{
			reader = new BufferedReader(new InputStreamReader( new FileInputStream(path)));			
			String temp = null;
			while((temp = reader.readLine()) != null){
				builder.append(temp).append(" ");				
			}
		}catch(Exception ee){
			throw new RuntimeException(ee);
		}finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e1) {						
					throw new RuntimeException(e1);
				}
			}
		}
		return builder.toString();
	}
	
	/**
	 * バインドパラメータ用の文字列を検索
	 * @param sql
	 * @return
	 */
	private static Set<String> getBindList(String sql){
		Matcher match = BIND_VAR_PATTERN.matcher(sql);
		Set<String> bindName = new HashSet<String>();
		// バインド変数にマッチした部分を置換
		while (match.find()) {
			// マッチしたバインド変数名を取得後、空白、最初のコロンを除去する
			String variableName = match.group(2);
			if(variableName.startsWith("c_")){
				continue;
			}
			bindName.add(variableName);			
		}	
		return bindName;
	}
	
	/**
	 * テンプレート用の文字列を検索
	 * @param sql
	 * @return
	 */
	private static Set<String> getVelocityBindList(String sql){
		Matcher match = VELOCITY_BIND_VAR_PATTERN.matcher(sql);
		Set<String> bindName = new HashSet<String>();
		// バインド変数にマッチした部分を置換
		while (match.find()) {
			// マッチしたバインド変数名を取得後、空白、最初のコロンを除去する
			String variableName = match.group(2);			
			bindName.add(variableName);			
		}
		return bindName;		
	}

}
