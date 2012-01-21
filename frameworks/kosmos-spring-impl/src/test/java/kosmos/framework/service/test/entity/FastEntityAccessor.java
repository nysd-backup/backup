/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import kosmos.framework.bean.Pair;
import kosmos.framework.bean.PropertyAccessor;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FastEntityAccessor implements PropertyAccessor<FastEntity> {

	@Override
	public FastEntity create() {
		return new FastEntity();
	}

	@Override
	public void setProperties(Map<String, Object> values, FastEntity targetObject) {
		if(values.containsKey(ITestEntity.TEST.name())){
			targetObject.setTest((String)values.get(ITestEntity.TEST.name()));
		}
		if(values.containsKey(ITestEntity.ATTR.name())){
			targetObject.setAttr((String)values.get(ITestEntity.ATTR.name()));
		}
		if(values.containsKey(ITestEntity.ATTR2.name())){
			targetObject.setAttr2((Integer)values.get(ITestEntity.ATTR2.name()));
		}
		if(values.containsKey(ITestEntity.VERSION.name())){
			targetObject.setVersion((Integer)values.get(ITestEntity.VERSION.name()));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Pair<Class<?>>> getProperties(FastEntity targetObject) {
		Map<String,Pair<Class<?>>> property = new LinkedHashMap<String,Pair<Class<?>>>();
		property.put(ITestEntity.TEST.name(), new Pair(String.class,targetObject.getTest()));
		property.put(ITestEntity.ATTR.name(), new Pair(String.class,targetObject.getAttr()));
		property.put(ITestEntity.ATTR2.name(), new Pair(int.class,targetObject.getAttr2()));
		property.put(ITestEntity.VERSION.name(), new Pair(int.class,targetObject.getVersion()));			
		return property;
	}

}
