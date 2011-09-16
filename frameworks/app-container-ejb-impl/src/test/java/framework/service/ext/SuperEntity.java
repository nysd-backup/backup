/**
 * Use is subject to license terms.
 */
package framework.service.ext;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import framework.core.entity.AbstractEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/02/25 created.
 */
@MappedSuperclass
public class SuperEntity extends AbstractEntity{

	@Id
	@Column
	protected String test;

	public void setTest(String test){
		this.test = test;
	}
	
	public String getTest(){
		return this.test;
	}

}
