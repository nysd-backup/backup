package org.coder.mightyguard.register.domain.log;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author yoshida-n
 *
 */
public class Path implements Serializable{

	private static final long serialVersionUID = 1L;

    @XmlAttribute
    public String action;

    @XmlAttribute
    public String kind;

    @XmlValue
    public String value;

}
