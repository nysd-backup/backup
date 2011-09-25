/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import framework.api.service.Remote;

/**
 * サービスファサードにプロキシをインジェクションする.
 *
 * @author yoshida-n
 * @version	2011/05/11 created.
 */
public class ServceFacadeAnnotationBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware{
	
	/** ローカル用のBusinessDelegate */
	private String handlerBeanName = null;
	
	/** リモート用のBusinessDelegate */
	private String remoteHandlerBeanName = null;	
	
	/** ホワイトリスト */
	private Pattern whiteList = null;
	
	/** ホワイトリスト */
	private boolean allowOnlyMarked = false;

	/** コンテキスト */
	private ApplicationContext context = null;
	
	/**
	 * @param handlerBeanName the handlerBeanName to set
	 */
	public void setHandlerBeanName(String handlerBeanName){
		this.handlerBeanName = handlerBeanName;
	}
	
	/**
	 * @param remoteHandlerBeanName the remoteHandlerBeanName to set
	 */
	public void setRemoteHandlerBeanName(String remoteHandlerBeanName){
		this.remoteHandlerBeanName = remoteHandlerBeanName;
	}
	
	/**
	 * @param whiteList インジェクションを許可するサービス
	 */
	public void setWhiteList(String whiteList){
		if(StringUtils.isNotEmpty(whiteList)){
			this.whiteList = Pattern.compile(whiteList);
		}
	}
	
	/**
	 * @param allowOnlyMarked true:アノテーションが設定されているクラスのみインジェクションする
	 */
	public void setAllowOnlyMarked(boolean allowOnlyMarked){
		this.allowOnlyMarked = allowOnlyMarked;
	}
	
	/**
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

	/**
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		
		if(bean == null){
			return bean;
		}
		
		if(allowOnlyMarked && bean.getClass().getAnnotation(ServiceCallable.class) == null){
			return bean;
		}
		
		if( whiteList != null){
			Matcher matcher = whiteList.matcher(beanName);
			if( !matcher.find()){
				return bean;
			}
		}
		
		for (Class<?> cls = bean.getClass(); cls != Object.class; cls = cls.getSuperclass()) {

			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {

				// except static
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				// except interface
				if (Modifier.isInterface(f.getModifiers())) {
					continue;
				}
				
				ServiceFacade a = f.getAnnotation(ServiceFacade.class) ;
				if(a == null){
					continue;
				}
				
				f.setAccessible(true);
				
				//プロキシの設定
				BusinessDelegate handler = null;
				if( f.getType().getAnnotation(Remote.class) != null){
					handler = BusinessDelegate.class.cast(context.getBean(remoteHandlerBeanName));
				}else{
					handler = BusinessDelegate.class.cast(context.getBean(handlerBeanName));				
				}
				try {
				
					if( !a.alias().isEmpty()){
						handler.setAlias(a.alias());
					}
					f.set(bean, Proxy.newProxyInstance(f.getType().getClassLoader(), new Class[]{f.getType()}, handler));
				} catch (Throwable ex) {
					throw new BeanCreationException(beanName, "Injection of service dependencies failed", ex);
				}
			}

		}	
		return bean;
	}

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
		
	}
}