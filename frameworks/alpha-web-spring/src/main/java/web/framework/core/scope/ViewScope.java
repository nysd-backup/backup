package web.framework.core.scope;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * ビュースコープ.
 * 
 * @author yoshida-n
 * @version 2010/11/04 新規作成
 */
public class ViewScope implements Scope {

	/**
	 * @see org.springframework.beans.factory.config.Scope#get(java.lang.String, org.springframework.beans.factory.ObjectFactory)
	 */
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();

		if (viewMap.containsKey(name)) {
			return viewMap.get(name);
		} else {
			Object object = objectFactory.getObject();
			viewMap.put(name, object);

			return object;
		}
	}

	/**
	 * @see org.springframework.beans.factory.config.Scope#remove(java.lang.String)
	 */
	@Override
	public Object remove(String name) {
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
	}

	/**
	 * @see org.springframework.beans.factory.config.Scope#getConversationId()
	 */
	@Override
	public String getConversationId() {
		return null;
	}

	/**
	 * @see org.springframework.beans.factory.config.Scope#registerDestructionCallback(java.lang.String, java.lang.Runnable)
	 */
	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		// Not supported
	}

	/**
	 * @see org.springframework.beans.factory.config.Scope#resolveContextualObject(java.lang.String)
	 */
	@Override
	public Object resolveContextualObject(String s) {
		return null;
	}

}
