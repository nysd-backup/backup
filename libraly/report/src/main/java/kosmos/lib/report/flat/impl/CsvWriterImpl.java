/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.flat.impl;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import kosmos.lib.report.flat.FlatFileWriter;
import kosmos.lib.report.flat.IORuntimeException;

import org.supercsv.io.ICsvListWriter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class CsvWriterImpl implements FlatFileWriter{
	
	/**
	 * @see kosmos.lib.report.flat.FlatFileWriter#write(org.supercsv.io.ICsvListWriter, java.lang.Object)
	 */
	@Override
	public void write(ICsvListWriter writer,Object target, HashSet<String> includes){
		try{		
			if(target instanceof Map){
				writeMap(writer,target,includes);
			}else {
				writeBean(writer,target,includes);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		
	}
	
	/**
	 * Writes the bean to csv.
	 * 
	 * @param writer the writer
	 * @param target the target
	 * @param includes the include columns
	 * @throws IOException
	 */
	protected void writeBean(ICsvListWriter writer,Object target,HashSet<String> includes) throws IOException{
		
		List<Object> val = new ArrayList<Object>();
		Method[] ms = target.getClass().getDeclaredMethods();
		if(includes != null){
			for(String in : includes){
				try {
					PropertyDescriptor desc = new PropertyDescriptor(in, target.getClass());
					Method m = desc.getReadMethod();						
					val.add(convert(m.invoke(target)));
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}else{
			for(Method m : ms){
				if(java.lang.reflect.Modifier.isFinal(m.getModifiers())){
					continue;
				}
				if(m.getName().startsWith("get")){
					try {
						val.add(convert(m.invoke(target)));
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		writer.write(val);
	}
	
	/**
	 * Writes the map to csv.
	 * 
	 * @param writer the writer
	 * @param target the target
	 * @param includes the include columns
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void writeMap(ICsvListWriter writer, Object target,HashSet<String> includes) throws IOException{
		List<String> l = new ArrayList<String>(); 
		if(includes != null){
			for(String in : includes){
				l.add(convert(((Map) target).get(in)));
			}
		}else{
			Collection<Object> values = Map.class.cast(target).values();					
			for(Object o : values ){
				l.add(convert(o));
			}					
		}
		writer.write(l);
	}
	
	/**
	 * Null to blank.
	 *  
	 * @param value the value
	 * @return the value
	 */
	protected String convert(Object value){
		if(value == null){
			return "";
		}
		String v = value.toString();
		if( v.charAt(0) == '0'){
			return "=\"" + v + "\"";
		}else if( v.contains(",")){
			return "\"" + v + "\""; 
		}else {
			return v;
		}
	}

}
