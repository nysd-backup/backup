package org.coder.mightyguard.register.domain.headers;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author yoshida-n
 *
 */
public class Commit implements Serializable{

	private static final long serialVersionUID = 1L;

    @XmlAttribute
    public String revision;

    public String author;

    public String date;
}
