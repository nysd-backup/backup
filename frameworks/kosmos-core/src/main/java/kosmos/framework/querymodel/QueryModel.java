/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryModel.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class QueryModel{

	/** the updating target */
	private Object result = null;
	
	/** true:stop updating model if check ng */
	private boolean stopIfFail = false;
	
	/** true:throws the business exception if check ng */
	private boolean throwIfFail = false;
	
	/** the checker for result */
	private List<Checker<?>> checkerList = new ArrayList<Checker<?>>();
	
	/** the child */
	private QueryModel child = null;	

	/**
	 * Accepts the QueryProcessor.
	 * 
	 * @param updater the updater to execute
	 * @return result of update
	 */
	public abstract void accept(QueryProcessor updater);

	
	/**
	 * @param child the child 
	 */
	public void setChild(QueryModel child){
		this.child = child;
	}
	
	/**
	 * @return the child
	 */
	public QueryModel getChild(){
		return this.child;
	}
	
	/**
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T)result;
	}
	
	/**
	 * @param result the result to set
	 */
	public <T> void setValue(T result) {
		this.result = result;
	}

	/**
	 * @param checker the checker to add
	 */
	public void addChecker(Checker<?> checker) {
		checkerList.add(checker);
	}

	/**
	 * Sets stopIfFail to true;
	 */
	public void setStopIfFail() {
		this.stopIfFail = true;
	}

	/**
	 * Sets throwIfFail to true;
	 */
	public void setThrowIfFail() {
		this.throwIfFail = true;
	}

	/**
	 * @return the checker
	 */
	public List<Checker<?>> getCheckerList() {
		return this.checkerList;
	}

	/**
	 * @return the stopIfFail
	 */
	public boolean isStopIfFail() {
		return this.stopIfFail;
	}

	/**
	 * @return the throwIfFaile
	 */
	public boolean isThrowIfFail() {
		return this.throwIfFail;
	}

}
