package org.coder.mightyguard.register.domain.log;

import java.io.Serializable;
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
}
