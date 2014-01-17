/**
 * 
 */
package org.coder.gear.sample.javaee7.domain.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.beanutils.BeanUtils;
import org.coder.gear.sample.javaee7.domain.entity.AbstractEntity;

/**
 * @author yoshida-n
 *
 */
public class AbstractFactory<T extends AbstractEntity> {

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T createFrom(T dto){
		ParameterizedType t = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = t.getActualTypeArguments();		
		T dst = null;
		try {
			dst = ((Class<T>)types[0]).newInstance();
			BeanUtils.copyProperties(dst, dto);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {		
			throw new RuntimeException(e);
		} 
		return dst;
		
	}
}
