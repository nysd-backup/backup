/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import framework.api.dto.ReplyDto;
import framework.api.dto.RequestDto;
import framework.api.service.RequestListener;

/**
 * ローカルサービス実行プロキシ.
 *
 * @author yoshida-n
 * @version	2011/05/10 created.
 */
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LocalBusinessDelegate extends AbstractBusinessDelegate{

	/** サービスインターフェース */
	private RequestListener listener;

	/**
	 * @param listener サービスインターフェース
	 */
	public void setRequestListener(RequestListener listener){		
		this.listener = listener;
	}

	/**
	 * @see framework.web.core.api.service.AbstractBusinessDelegate#processService(framework.api.dto.RequestDto)
	 */
	@Override
	protected ReplyDto processService(RequestDto dto) {
		return listener.processService(dto);
	}

	
}
