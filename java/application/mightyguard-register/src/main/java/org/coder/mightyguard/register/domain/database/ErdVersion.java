/**
 * 
 */
package org.coder.mightyguard.register.domain.database;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * @author yoshida-n
 *
 */
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class ErdVersion {

	@Id
	public String version;
	
	@Id
	public String owner;

	@Id
	public String tablename;

	@Id
	public String columnname;

	public String tablecomments;
	
	public String columncomments;
	
	public String ispk;
	
	public String dataType;
	
	public int dataLength;
	
	public int dataPrecision;
	
	public int dataScale;
	
	public int columnId;
	
	public String nullable;
	
	public String charUsed;
	
	public int charLength;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date createDate;
}
