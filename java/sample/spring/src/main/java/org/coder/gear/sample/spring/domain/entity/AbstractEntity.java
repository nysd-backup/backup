/**
 * 
 */
package org.coder.gear.sample.spring.domain.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ドメイン層のエンティティの規定 .
 * 
 * @author yoshida-n
 *
 */
public abstract class AbstractEntity {

	/**
	 * DDDのサンプルだとEntityにRepositoryのinterfaceを定義してDIさせてActiveRecordにしているが、
	 * EJB+JPAの場合、EntityManagerがEntityのライフサイクルを管理しているので向かない。
	 * 
	 * EJBの場合ActiveRecordにはせずにEntityにはRepositoryを定義せずにアプリケーション層とかServiceでRepositoryを呼び出すのがよいと思う。	
	 * ただそれだと貧血ドメインモデルになりがち、EJBでActiveRecordにするにはどうするか。
	 * 
	 * そもそもJPAのEntityをドメインオブジェクトとして使用することに無理があるのではないか。
	 * JPAのEntityをインフラストラクチャのエンティティとみるならPOJOをドメインオブジェクトにしてRepository内でマップするのがよい。
	 * しかしそれだとJPAはただの永続化機能の意味しかないな。。。	
	 */
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}
