/**
 * Use is subject to license terms.
 */
package framework.web.core.api.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import framework.api.dto.ClientRequestBean;
import framework.api.dto.ClientSessionBean;
import framework.api.dto.ReplyDto;
import framework.api.dto.RequestDto;
import framework.core.message.BuildedMessage;
import framework.web.core.context.WebContext;

/**
 * サービス実行プロキシ.
 *
 * @author yoshida-n
 * @version	2011/05/10 created.
 */
public abstract class AbstractBusinessDelegate implements BusinessDelegate{

	private String alias = null;
	
	/**
	 * @see framework.web.core.api.service.BusinessDelegate#setAlias(java.lang.String)
	 */
	public void setAlias(String alias){
		this.alias = alias;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		WebContext context = WebContext.getCurrentInstance();
		ClientSessionBean clientBean = null;
		ClientRequestBean webBean = null;
		if( context != null){
			clientBean = context.getClientSessionBean();
			webBean = context.getClientRequestBean();
		}		
		Serializable[] serial = null;
		if( args == null){
			serial = new Serializable[0];
		}else{
			serial = new Serializable[args.length];
			for(int i = 0 ; i < args.length; i++){
				serial[i] = Serializable.class.cast(args[i]);
			}
		}
			
		RequestDto dto  = new RequestDto(
			method.getDeclaringClass(),
			method.getParameterTypes(),
			serial,
			alias,
			method.getName(), 
			clientBean,
			webBean
			);
		
		ReplyDto reply = processService(dto);
		
		//メッセージがある場合はメッセージを追加
		if( context != null){
			List<BuildedMessage> messageList = reply.getMessageList();
			for(BuildedMessage message : messageList){	
				context.addMessage(message);
			}
		}
		return reply.getReply();
	}
	
	/**
	 * サービス起動
	 * @param dto DTO
	 * @return リプライ
	 */
	protected abstract ReplyDto processService(RequestDto dto);

}
