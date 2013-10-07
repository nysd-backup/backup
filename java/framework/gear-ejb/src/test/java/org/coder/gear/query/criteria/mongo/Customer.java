/**
 * 
 */
package org.coder.gear.query.criteria.mongo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * @author yoshida-n
 *
 */
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Customer implements Serializable {
	@Id
    private String id;
    private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
