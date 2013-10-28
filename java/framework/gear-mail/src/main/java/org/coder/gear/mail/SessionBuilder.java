package org.coder.gear.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * @author yoshida-n
 *
 */
public class SessionBuilder {

    private String jndiName;
    
    private final Properties props = new Properties();

    private Authenticator auth;
    
    private boolean debug = false;
   
    /**
     * For SMTP Auth .
     * @param auth to set
     * @return self
     */
    public SessionBuilder withAuth(Authenticator auth){
    	this.auth = auth;
    	this.props.setProperty("mail.smtp.auth", String.valueOf(true));
    	return this;
    }
    
    /**
     * @param debug to set
     * @return self
     */
    public SessionBuilder withDbug(boolean debug){
    	this.debug = debug;
    	return this;
    }
    
    /**
     * @param smtpPort to port
     * @return self
     */
    public SessionBuilder withSmtpPort(int smtpPort){
    	this.props.setProperty("mail.smtp.port", String.valueOf(smtpPort));
    	return this;
    }
    
    /**
     * @param conTimeout to set
     * @return self
     */
    public SessionBuilder withSmtpConTimeout(int conTimeout){
    	this.props.setProperty("mail.smtp.connectiontimeout	", String.valueOf(conTimeout));
    	return this;
    }
    
    /**
     * @param timeout to set
     * @return self
     */
    public SessionBuilder withSmtpTimeout(int timeout){
    	this.props.setProperty("mail.smtp.timeout", String.valueOf(timeout));
    	return this;
    }
    
    /**
     * @param from to set
     * @return self
     */
    public SessionBuilder withSmtpFrom(String from){
    	this.props.setProperty("mail.smtp.from", from);
    	return this;
    }
    
    /**
     * Constructor .
     */
    private SessionBuilder() {
        
    }
    
    /**
     * @param jndiName to set
     * @return self
     */
    public static SessionBuilder managedBuilder(String jndiName){
    	SessionBuilder builder = new SessionBuilder();
    	builder.jndiName = jndiName;
    	return builder;
    }
    
    /**
     * @param smtpHost to set
     * @return self
     */
    public static SessionBuilder nativeBuilder(String smtpHost){
    	SessionBuilder builder = new SessionBuilder();
    	builder.props.setProperty("mail.smtp.host", smtpHost);
    	return builder;
    }

    /**
     * @return session
     */
    public Session build() {
    	
    	Session session = null;
    	if(jndiName != null){	
	    	Context context = null;	    	
	    	try{
		    	context = new InitialContext();
		        session = (Session) context.lookup(jndiName);		       
	    	}catch(NamingException e){
	    		throw new IllegalStateException(e);
	    	}finally {
				try {
					context.close();
				} catch (NamingException e) {
					throw new IllegalStateException(e);
				}
	    	}
    	}else {    		
    		if(auth == null){
    			session = Session.getInstance(props);    		
    		}else{
    			session = Session.getInstance(props, auth);
    		}
    	}
    	
    	if (debug) {
    		session.setDebug(debug);    		
	    }
    	if(session.getDebug()){
    		session.setDebugOut(new ApplicationLoggingPrintStream());
    	}
	    return session;
	        
    }

}
