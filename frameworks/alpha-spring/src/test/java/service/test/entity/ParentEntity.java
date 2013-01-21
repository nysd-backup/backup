/**
 * Copyright 2011 the original author
 */
package service.test.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="parent3")
public class ParentEntity{
	
	@Id
	@Column
	private String test;

	@Column
	private String attr;
	
	@Column
	private int attr2;
	
	@Version
	@Column
	private int version;
	
	@OneToMany(cascade={CascadeType.ALL,CascadeType.DETACH},mappedBy="parent")
	private List<ChildEntity> childs;
	
	/**
	 * @return
	 */
    public List<ChildEntity> getChilds(){
		return childs;
	}
	
	/**
	 * @param childs
	 */
	public void setChilds(List<ChildEntity> childs){
		this.childs = childs;
	}
	
	/**
	 * @param test the test to set
	 */
	public ParentEntity setTest(String test) {
		this.test = test;
		return this;
	}

	/**
	 * @return the test
	 */
	@Id
	@Column
	public String getTest() {
		return test;
	}

	/**
	 * @param attr the attr to set
	 */
	public ParentEntity setAttr(String attr) {
		this.attr = attr;
		return this;
	}

	/**
	 * @return the atstr
	 */
	@Column
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public ParentEntity setAttr2(int attr2) {
		this.attr2 = attr2;
		return this;
	}

	/**
	 * @return the attr2
	 */
	@Column
	public int getAttr2() {
		return attr2;
	}

	/**
	 * @param version the version to set
	 */
	public ParentEntity setVersion(int version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the version
	 */
	@Version
	@Column
	public int getVersion() {
		return version;
	}
}
