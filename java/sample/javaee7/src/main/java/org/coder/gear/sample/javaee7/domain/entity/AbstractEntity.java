/**
 * 
 */
package org.coder.gear.sample.javaee7.domain.entity;

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
	 */
}
