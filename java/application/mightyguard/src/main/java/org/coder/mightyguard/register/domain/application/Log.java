package org.coder.mightyguard.register.domain.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author yoshida-n
 *
 */
@XmlRootElement
public class Log implements Serializable{

	private static final long serialVersionUID = 1L;

    public List<Logentry> logentry;
    
    /**
     * Remove previous log
     * 
     * @param previous previous version's log
     */
    public void removeUnderRevision(int revison){
    		
    	List<Logentry> alival = new ArrayList<Logentry>();
    	for(Logentry e : logentry){
    		int rev = Integer.parseInt(e.revision);
    		if(rev > revison){
    			alival.add(e);
    		}
    	}
    	this.logentry = alival;
    }
}
