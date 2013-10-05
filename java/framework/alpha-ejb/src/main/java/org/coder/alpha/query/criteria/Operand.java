/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.criteria;

import java.util.List;

import org.coder.alpha.query.free.query.Conditions;



/**
 * The operand.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public enum Operand {
	/** ＞ */
	GreaterThan(" > "),
	/** ＜ */
	LessThan(" < "),
	/** = */
	Equal(" = "),
	/** != */
	NotEqual(" != "),
	/** ≧ */
	GreaterEqual(" >= "),
	/** ≦ */
	LessEqual(" <= "),
	/** between */
	Between(" BETWEEN "){
		@Override
		public String getExpression(String colName,String bindName,Object value) {
			@SuppressWarnings("unchecked")
			List<Object> values = (List<Object>)value;
			Object from = values.get(0);
			Object to = values.get(1);
			if(from instanceof FixString){
				from = from.toString();
			}else{
				from = ":" + bindName;
			}	
			if(to instanceof FixString){
				to = from.toString();
			}else{
				to = ":" + bindName + "_to";
			}	
			return String.format(" %s %s %s and %s ",colName,getOperand(),from,to);
		}
		
		@Override
		public void setParameter(Conditions delegate,String bindName,Object value){
			@SuppressWarnings("unchecked")
			List<Object> values = (List<Object>)value;
			Object from = values.get(0);
			Object to = values.get(1);
			if(!(from instanceof FixString)){
				delegate.getParam().put(bindName, from);
			}
			if(!(to instanceof FixString)){
				delegate.getParam().put(bindName + "_to",to);
			}				
		}
	},
	/** IS NOT NULL */
	IsNotNull(" IS NOT NULL"){
		@Override
		public String getExpression(String colName,String bindName,Object value) {			
			return String.format(" %s %s ",colName,getOperand());
		}
		@Override
		public void setParameter(Conditions delegate,String bindName,Object value){			
		}
	},
	/** IS NULL */
	IsNull("IS NULL"){
		@Override
		public String getExpression(String colName,String bindName,Object value) {			
			return String.format(" %s %s ",colName,getOperand());
		}
		@Override
		public void setParameter(Conditions delegate,String bindName,Object value){			
		}
	},
	/** IN */
	IN("IN"){
		@Override
		public String getExpression(String colName,String bindName,Object value) {
			List<?> val = List.class.cast(value);
			StringBuilder in = new StringBuilder();
			for(int i = 0 ; i <val.size(); i++){
				in.append(":").append(bindName).append("_").append(i).append(",");
			}
			if(in.length() > 0){
				in = new StringBuilder(in.substring(0,in.length()-1));
			}			
			return String.format(" %s %s(%s) ",colName,getOperand(),in.toString());
		}
		@Override
		public void setParameter(Conditions delegate,String bindName,Object value){	
			List<?> val = List.class.cast(value);		
			int cnt = -1;
			for(Object v : val){
				cnt++;
				delegate.getParam().put(String.format("%s_%d", bindName,cnt),v);
			}
			
		}
	},
	/** LIKE */
	LIKE("LIKE");

	/** the operand */
	private String operand = null;

	/**
	 * @param operand the operand
	 */
	private Operand(String operand) {
		this.operand = operand;
	}

	/**
	 * @return the operand
	 */
	public String getOperand() {
		return operand;
	}
	
	public String getExpression(String colName,String bindName,Object value){
		if(value instanceof FixString){
			return String.format(" %s %s %s ",colName,operand,value.toString());
		}else{
			return String.format(" %s %s :%s ",colName,operand,bindName);
		}			
	}
	
	public void setParameter(Conditions delegate,String bindName,Object value){
		if(!(value instanceof FixString)){
			delegate.getParam().put(bindName, value);
 		}
	}
	
}
