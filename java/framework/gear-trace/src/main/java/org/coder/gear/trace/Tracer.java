/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.trace;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 * Tracer.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class Tracer {
	
	/** instances */
	private static ThreadLocal<Tracer> instances = new ThreadLocal<Tracer>();
	
	/** the watchers */
	private Deque<InvocationWatcher> watchers = new ArrayDeque<InvocationWatcher>();
	
	/**
	 * Starts watch.
	 * @param declaringClass the target class
	 * @param methodName the target method name
	 */
	public static void start(Class<?> declaringClass , String methodName){
		Tracer trace = instances.get();
		if(trace == null){
			trace = new Tracer();
			instances.set(trace);
		}
		InvocationWatcher watcher = new InvocationWatcher(declaringClass, methodName, 0);
		watcher.start();
		trace.watchers.push(watcher);
	}
	
	
	/**
	 * Stops watch
	 */
	public static void stop() {
		Tracer trace = instances.get();
		if(trace == null){
			throw new IllegalStateException();
		}
		InvocationWatcher watchers = trace.watchers.pop();
		watchers.stop();
		if(trace.watchers.isEmpty()){
			instances.remove();
		}	
	}
	
}
