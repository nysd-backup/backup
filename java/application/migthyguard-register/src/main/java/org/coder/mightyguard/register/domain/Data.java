package org.coder.mightyguard.register.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.coder.mightyguard.register.domain.headers.Info;
import org.coder.mightyguard.register.domain.log.Log;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * @author yoshida-n
 *
 */
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Data {

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
