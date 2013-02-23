/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.trace;

import java.util.Stack;


/**
 * TraceStack.
 *
 * @author yoshida-n
 * @version	created.
 */
public class TraceContext {
	
	/** instances */
	private static ThreadLocal<TraceContext> instances = new ThreadLocal<TraceContext>();
	
	/** the watchers */
	private Stack<TraceWatcher> watchers = new Stack<TraceWatcher>();
	
	/**
	 * @return the TraceContext
	 */
	public static TraceContext getCurrentInstance() {
		return instances.get();
	}
	
	/**
	 * Starts watch.
	 * @param declaringClass the target class
	 * @param methodName the target method name
	 */
	public void startWatch(Class<?> declaringClass , String methodName){
		TraceWatcher target = createWatcher(declaringClass,methodName,watchers.size());
		target.start();
		watchers.add(target);
	}
	
	
	/**
	 * Stops watch
	 */
	public void stopWatch() {
		TraceWatcher target = watchers.pop();
		target.stop();
	}
	
	/**
	 * Initialize the context
	 */
	public void initialize() {
		release();
		instances.set(this);
	}
	
	/**
	 * Releases context.
	 */
	public void release() {
		watchers.clear();
		instances.remove();
	}
	
	/**
	 * Creates the watcher.
	 * @param declaringClass the target class
	 * @param methodName the target method name
	 * @return the watcher
	 */
	protected TraceWatcher createWatcher(Class<?> declaringClass , String methodName,int callStack){
		return new TraceWatcher(declaringClass,methodName,callStack);
	}
	
	
}
