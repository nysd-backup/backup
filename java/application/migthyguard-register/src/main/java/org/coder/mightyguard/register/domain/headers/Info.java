package org.coder.mightyguard.register.domain.headers;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yoshida-n
 *
 */
@XmlRootElement
public class Info implements Serializable{

	private static final long serialVersionUID = 1L;

    public Entry entry;
}
