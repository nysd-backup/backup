/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * Lookup by JavaEE6's unified JNDI name.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UnifiedComponentFinderImpl extends AbstractEJBComponentFinder{
	
	/** the JNDI prefix */
	private static final String PREFIX = "java:module";
	
	/**
	 * @see alpha.framework.domain.activation.AbstractEJBComponentFinder#lookup(java.lang.String, java.util.Properties)
	 */
	protected Object lookup(String serviceName, Properties prop){
		InitialContext context = null;
		try{			
			String format = String.format("%s/%s",PREFIX , serviceName);		
			if(prop == null){				
				context = new InitialContext(); 				
			}else{
				context = new InitialContext(prop); 		
			}
			return context.lookup(format);
		}catch(NamingException ne){
			throw new IllegalArgumentException("Failed to load alpha.domain ", ne);
		}finally{
			if(context != null){
				try {
					context.close();
				} catch (NamingException e) {					
					e.printStackTrace();
				}
			}
		}
	}

}

