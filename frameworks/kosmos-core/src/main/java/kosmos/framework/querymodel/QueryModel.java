/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;

import java.util.ArrayList;
import java.util.List;

import kosmos.framework.core.dto.MessageReplyable;
import kosmos.framework.core.message.MessageResult;

/**
 * QueryModel.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class QueryModel implements MessageReplyable{

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
	 * Accepts the VisitableQueryProcessor.
	 * 
	 * @param updater the updater to execute
	 * @return result of update
	 */
	public abstract void accept(VisitableQueryProcessor updater);

	
	/**
	 * @param child the child 
	 */
	public QueryModel setChild(QueryModel child){
		this.child = child;
		return this;
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
	public <T> QueryModel setValue(T result) {
		this.result = result;
		return this;
	}

	/**
	 * @param checker the checker to add
	 */
	public QueryModel addChecker(Checker<?> checker) {
		checkerList.add(checker);
		return this;
	}

	/**
	 * Sets stopIfFail to true;
	 */
	public QueryModel setStopIfFail() {
		this.stopIfFail = true;
		return this;
	}

	/**
	 * Sets throwIfFail to true;
	 */
	public QueryModel setThrowIfFail() {
		this.throwIfFail = true;
		return this;
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
	

	@Override
	public void setMessageList(MessageResult[] messageList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageResult[] getMessageList() {
		// TODO Auto-generated method stub
		return null;
	}

}
