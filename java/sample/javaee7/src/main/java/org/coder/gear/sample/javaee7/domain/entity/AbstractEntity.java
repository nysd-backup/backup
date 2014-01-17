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
	 * ここはDataMapperパターンを採用してEntityにはRepositoryを定義せずにアプリケーション層とかServiceでRepositoryを呼び出すのがよいと思う。	
	 * ただそれだとRepsoitory持っていくと貧血ドメインモデルになりがち、EJBだとActiveRecordは難しいか。
	 */
}
