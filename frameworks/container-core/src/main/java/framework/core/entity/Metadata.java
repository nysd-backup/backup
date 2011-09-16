/**

 * Use is subject to license terms.
 */
package framework.core.entity;

/**
 * エンティティのメタデータ.
 *
 * @author yoshida-n
 * @version	2011/04/20 created.
 */
public class Metadata<C,T> {
	
	/** カラム名称 */
	private final String name;
	
	/**
	 * @param name カラム名称
	 */
	public Metadata(String name){
		this.name = name;
	}
	
	/**
	 * @return カラム名称
	 */
	public String name(){
		return this.name;
	}
}
