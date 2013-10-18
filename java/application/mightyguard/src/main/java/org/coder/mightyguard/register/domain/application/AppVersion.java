package org.coder.mightyguard.register.domain.application;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * @author yoshida-n
 *
 */
@XmlRootElement
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class AppVersion {

	@Id
	@GeneratedValue
	@Field(name="_id")
	public String id;
	
    public String moduleId;

    public Info info;

    public Log log;
	
	public String version;

	public String date;
}
