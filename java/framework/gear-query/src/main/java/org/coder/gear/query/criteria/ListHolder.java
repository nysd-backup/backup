/**
 * 
 */
package org.coder.gear.query.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Owner
 *
 */
public class ListHolder<T> {
	
	private List<T> elements = new ArrayList<T>();
	
	public void add(T e){
		elements.add(e);
	}
	
	/**
	 * @param firstOnly
	 * @param others
	 * @param common
	 */
	public void forEach(Consumer<T> firstOnly , Consumer<T> others, BiConsumer<T,Integer> common){
		boolean first = true;
		int index = 0;
		for(T criteria : elements){
			if(first){
				firstOnly.accept(criteria);
				first = false;
			}else{
				others.accept(criteria);
			}
			common.accept(criteria,index);
			index++;
		}
	}
	
	public void eachWithIndex(BiConsumer<T,Integer> others){
		int index = 0;
		for(T criteria : elements){
			others.accept(criteria,index);
			index++;
		}
	}

}
