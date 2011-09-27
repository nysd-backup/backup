/**
 * Copyright 2011 the original author
 */
package framework.core.message;

/**
 * メッセージレベル.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public enum MessageLevel {

	Info("I",0),
	Warn("W",1),
	Error("E",2),
	Fatal("F",3);
	
	/** レベル */
	private int level = 0;
	
	/** イニシャル */
	private String initial = "I";
	
	/**
	 * @param initial イニシャル
	 * @param level レベル
	 */
	private MessageLevel( String initial,int level){
		this.level = level;
		this.initial = initial;
	}
	
	/**
	 * @return レベル
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * @return イニシャル
	 */
	public String getInitial(){
		return this.initial;
	}
	
	/**
	 * @param initial オブジェクト
	 * @return メッセージレベル
	 */
	public static MessageLevel find(String initial){
		MessageLevel[] levels = values();
		for(MessageLevel level : levels){
			if( level.getInitial().equals(initial)){
				return level;
			}
		}
		return null;
	}
}
