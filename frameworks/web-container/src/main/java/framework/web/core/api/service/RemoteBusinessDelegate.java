/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import framework.api.dto.ReplyDto;
import framework.api.dto.RequestDto;
import framework.api.service.RequestListener;

/**
 * リモートサービス実行プロキシ.
 *
 * @author yoshida-n
 * @version	2011/05/10 created.
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RemoteBusinessDelegate extends AbstractBusinessDelegate implements ApplicationContextAware {

	/** リスナー名 */
	private String remoteListenerName = null;

	private ApplicationContext context = null;
	
	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	/**
	 * @param remoteListenerName サービスインターフェース
	 */
	public void setRemoteListenerName(String remoteListenerName){		
		this.remoteListenerName = remoteListenerName;
	}
	/**
	 * @see framework.web.core.api.service.AbstractBusinessDelegate#processService(framework.api.dto.RequestDto)
	 */
	@Override
	protected ReplyDto processService(RequestDto dto) {
		RequestListener listener = RequestListener.class.cast(context.getBean(remoteListenerName));
		return listener.processService(dto);
	}
	

}
