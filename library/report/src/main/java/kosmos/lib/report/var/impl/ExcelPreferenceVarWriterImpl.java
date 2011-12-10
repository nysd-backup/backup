/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.var.impl;

import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kosmos.lib.report.var.IORuntimeException;
import kosmos.lib.report.var.Metadata;
import kosmos.lib.report.var.VarFileWriter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExcelPreferenceVarWriterImpl implements VarFileWriter{
	
	private String delim = ",";
	
	private String lineSep = "\n";
	
	private Writer writer = null;
	
	/**
	 * @param file
	 * @param charset
	 * @param delim
	 * @param lineSep
	 */
	public ExcelPreferenceVarWriterImpl(File file , String charset ,String lineSep , String delim){
		this(file,charset);
		this.delim = delim;
		this.lineSep = lineSep;
		
	}
	
	/**
	 * @param file
	 * @param charset
	 * @param delim
	 * @param lineSep
	 */
	public ExcelPreferenceVarWriterImpl(File file , String charset ){
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),charset));
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} 
	}
	
	/**
	 * @see kosmos.lib.report.var.VarFileWriter#write(java.lang.Object, kosmos.lib.report.var.Metadata[])
	 */
	@Override
	public void write(Object target, Metadata... includes){
		try{		
			if(target instanceof Map){
				writeMap(target,includes);
			}else {
				writeBean(target,includes);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		
	}
	

	/**
	 * @see kosmos.lib.report.var.VarFileWriter#write(java.lang.String[])
	 */
	@Override
	public void write(String... target) {
		write(Arrays.asList(target));		
	}
	
	/**
	 * Writes the bean to csv.
	 * 
	 * @param writer the writer
	 * @param target the target
	 * @param includes the include columns
	 * @throws IOException
	 */
	protected void writeBean(Object target,Metadata... includes) throws IOException{
		
		List<String> val = new ArrayList<String>();
		Method[] ms = target.getClass().getDeclaredMethods();
		if(includes != null && includes.length != 0){
			for(Metadata in : includes){
				try {
					PropertyDescriptor desc = new PropertyDescriptor(in.getName(), target.getClass());
					Method m = desc.getReadMethod();				
					val.add(convert(in,m.invoke(target)));
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}else{
			for(Method m : ms){
				if(!java.lang.reflect.Modifier.isPublic(m.getModifiers())){
					continue;
				}
				if(m.getName().startsWith("get") ||
						(m.getName().startsWith("is") && 
								( m.getReturnType().equals(boolean.class) || m.getReturnType().equals(Boolean.class))
						)
				){
					try {
						val.add(convert(null,m.invoke(target)));
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		write(val);
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
	protected void writeMap( Object target,Metadata... includes) throws IOException{
		List<String> l = new ArrayList<String>(); 
		if(includes != null && includes.length != 0){
			for(Metadata in : includes){
				l.add(convert(in,((Map) target).get(in)));
			}
		}else{
			Collection<Object> values = Map.class.cast(target).values();					
			for(Object o : values ){
				l.add(convert(null,o));
			}					
		}
		write(l);
	}
	
	/**
	 * @param oneRecord
	 */
	private void write(List<String> oneRecord ){
		try {
			boolean first = true;
			for(String v : oneRecord){	
				v = (v == null) ? "" : v;
				if(!first){
					writer.write(delim);	
				}else{
					first = false;
				}
				writer.write(v);							
			}
			writer.write(lineSep);
		} catch (IOException e) {
			close();
			throw new IORuntimeException(e);
		}
	}

	
	/**
	 * Null to blank.
	 *  
	 * @param value the value
	 * @return the value
	 */
	protected String convert(Metadata meta, Object value){
		if(value == null || value.toString().isEmpty()){
			return "";
		}
		String v = value.toString();
		if(meta != null){
			if(value instanceof Date){
				return new SimpleDateFormat(meta.getFormat()).format((Date)value);
			}
			if( v.charAt(0) == '0' && meta.isZeroEscape()){
				return "=\"" + v + "\"";
			}
		}
		
		if( v.contains(delim)){
			return "\"" + v + "\""; 
		}
		
		return v;
	}

	/**
	 * @see kosmos.lib.report.var.VarFileWriter#close()
	 */
	@Override
	public void close() {
		if(writer != null){
			try {
				writer.close();
			} catch (IOException e) {
				throw new IORuntimeException(e);
			}
		}
		
	}


}
