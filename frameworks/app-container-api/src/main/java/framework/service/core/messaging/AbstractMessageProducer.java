/**
 * Use is subject to license terms.
 */
package framework.service.core.messaging;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import framework.api.dto.RequestDto;
import framework.service.core.transaction.ServiceContext;

/**
 * メッセージプロデューサ.
 *
 * @author	yoshida-n
 * @version	2010/12/30 new create
 */
public abstract class AbstractMessageProducer implements InvocationHandler{
	
	/** 宛先セレクタ */
	private DestinationSelecter destinationSelecter;
	
	/**
	 * @param selecter セレクタ
	 */
	public void setDestinationSelecter(DestinationSelecter selecter){
		this.destinationSelecter = selecter;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		ServiceContext context = ServiceContext.getCurrentInstance();

		Serializable[] serial = null;
		if( args == null){
			serial = new Serializable[0];
		}else{
			serial = new Serializable[args.length];
			for(int i = 0 ; i < args.length; i++){
				serial[i] = Serializable.class.cast(args[i]);
			}
		}
		RequestDto dto = new RequestDto(
			method.getDeclaringClass(),
			method.getParameterTypes(),
			serial,
			null,
			method.getName(),
			context.getClientSessionBean(),
			context.getClientRequestBean()
		);
		
		//宛先生成
		String dst = null;
		if(destinationSelecter != null){
			dst = destinationSelecter.createDestinationName(method);
		}
	
		return invoke(dto,dst);
	}
	
	/**
	 * 非同期サービス起動
	 * @param dto DTO
	 * @param destinationName 宛先名称
	 * @return 戻り値
	 * @throws Throwable　例外
	 */
	protected abstract Object invoke(RequestDto dto ,String destinationName) throws Throwable;
}
