/**
 * 
 */
package org.coder.gear.sample.spring.domain.entity;


/**
 * @author yoshida-n
 *
 */
public class OrderDetailPK {

	public Long order;	//関連の複合主キーの場合は型はカラムの型だがプロパティ名は関連の名前にすること（orderNoとはしない）
	
	public Long detailNo;
}
