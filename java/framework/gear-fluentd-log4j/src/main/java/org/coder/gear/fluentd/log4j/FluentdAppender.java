/**
 * 
 */
package org.coder.gear.fluentd.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.fluentd.logger.FluentLogger;

/**
 * Fluentd for log4j .
 * 
 * @author yoshida-n
 *
 */
public class FluentdAppender extends AppenderSkeleton{

	/**
	 * Logger .
	 */
	private FluentLogger fluentLogger;
    
	/**
	 * Fluentd host
	 */
    private String host;

    /**
     * Fluentd port
     */
    private int port = 24224;
    
    /**
     * label
     */
    private String label;
    
    /**
     * tag name
     */
    private String tag;
    
    
    /**
     * @see org.apache.log4j.AppenderSkeleton#activateOptions()
     */
    @Override
    public void activateOptions() {    	    	
    	fluentLogger = FluentLogger.getLogger(tag, host, port);    	
    	super.activateOptions();
    }
    
    /**
     * @see org.apache.log4j.Appender#close()
     */
    @Override
    public void close() {
    	fluentLogger.flush();
    }
            
    /**
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    @Override
    public boolean requiresLayout() {           
    	return true;
    }

    /**
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    protected void append(LoggingEvent event) {
    	String message = layout.format(event);
    	fluentLogger.log(message, label, message);
    }

	/**
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label to label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
