package org.coder.mightyguard.register.domain.database;

import javax.persistence.Embeddable;

@Embeddable
public class ErdVersionPK {

	public String version;
	
	public String owner;

	public String tableName;

	public String columnName;
}
