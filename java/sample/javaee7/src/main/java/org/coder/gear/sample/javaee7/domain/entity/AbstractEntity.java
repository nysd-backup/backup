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
	 * DDDのサンプルだとEntityにRepositoryのIFを定義してDIさせてActiveRecordのようにしているが、
	 * JPAの場合、EntityManagerがEntityのライフサイクルを管理しているので向かない。
	 * ここはDataMapperパターンを採用してEntityにはRepositoryを定義せずにServiceでRepositoryを呼び出すのがよいと思う。	
	 */
}
