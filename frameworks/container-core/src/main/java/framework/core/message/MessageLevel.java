/**
 * Copyright 2011 the original author
 */
package framework.core.message;

/**
 * The message level.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public enum MessageLevel {

	Info("I",0),
	Warn("W",1),
	Error("E",2),
	Fatal("F",3);
	
	/** the level */
	private int level = 0;
	
	/** the initial */
	private String initial = "I";
	
	/**
	 * @param initial the initial
	 * @param level the level
	 */
	private MessageLevel( String initial,int level){
		this.level = level;
		this.initial = initial;
	}
	
	/**
	 * @return the level
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * @return the initial
	 */
	public String getInitial(){
		return this.initial;
	}
	
	/**
	 * @param initial the initial
	 * @return the level
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
	
	/**
	 * @param initial the initial
	 * @return the level
	 */
	public static MessageLevel find(int target){
		MessageLevel[] levels = values();
		for(MessageLevel level : levels){
			if( level.getLevel() == target){
				return level;
			}
		}
		return null;
	}
}
