/**
 * 
 */
package org.coder.gear.query.criteria.mongo;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * @author yoshida-n
 *
 */
@Entity
@Table(name="log")
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Logger {
	
	@Id
	@GeneratedValue
	@Field(name="_id")
	private String id;
	@Basic
	private String month;
	@Basic
	private int uid = 0;
	@Basic
	private String time = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}