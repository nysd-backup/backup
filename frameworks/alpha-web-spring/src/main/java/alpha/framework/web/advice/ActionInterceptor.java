/**
 * Copyright 2011 the original author
 */
package alpha.framework.web.advice;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;

import alpha.framework.core.exception.BusinessException;
import alpha.framework.core.message.Message;
import alpha.framework.core.message.TargetClient;
import alpha.framework.domain.transaction.ServiceContext;
import alpha.framework.domain.transaction.ServiceContextImpl;
import alpha.utility.StringUtils;


/**
 * Actionメソッド用のインターセプター.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ActionInterceptor{

	//@Autowired
	//private MessageBuilder mb;

	/**
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint invocation) throws Throwable {

		ServiceContext serviceContext = new ServiceContextImpl();
		serviceContext.initialize();
		serviceContext.setLocale(FacesContext.getCurrentInstance().getViewRoot().getLocale());
		try{
			//リクエストIDの引き継ぎ
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String requestId = (String)request.getAttribute("application.web.requestId");
			serviceContext.setRequestId(requestId);
			
			Object ret = invocation.proceed();
			
			//メッセージをJSFに連携する
			boolean disableNavigation = commitMessage(FacesContext.getCurrentInstance(),serviceContext);
			
			//エラーレベル以上のメッセージが存在していたら画面遷移禁止
			return disableNavigation ? null : ret;
	
		}catch(Throwable t){
			handleThrowable(FacesContext.getCurrentInstance(),serviceContext,t);
			return null;
		}finally{
			serviceContext.release();
		}

	}
	
	/**
	 * 例外ハンドリング
	 * @param t 例外
	 */
	protected void handleThrowable(FacesContext fc, ServiceContext sc,Throwable t) {
		//すろーされていた場合はここで止める
		if(t instanceof BusinessException){
			//メッセージをJSFに連携する
			commitMessage(fc,sc);
			return;
		}else{
			//業務例外以外はRuntimeException/Errorがスロー
			//MessageArgument message = emf.getBizMessageFrom(t);
			//ServiceContext.getCurrentInstance().addMessage(mb.load(message,fc.getViewRoot().getLocale()));

			//メッセージをJSFに連携する
			commitMessage(fc,sc);
		}
	}
	
	/**
	 * @param context
	 */
	private boolean commitMessage(FacesContext fc,ServiceContext context ){
		List<Message> messages = context.getMessageList();	
		boolean disableNavigation = false;
		for(Message m: messages){
						
			FacesMessage.Severity severity = null;
			if(m.getLevel() == FacesMessage.SEVERITY_FATAL.getOrdinal()){
				severity = FacesMessage.SEVERITY_FATAL;
				disableNavigation = true;
			}else if(m.getLevel() == FacesMessage.SEVERITY_ERROR.getOrdinal()){
				severity = FacesMessage.SEVERITY_ERROR;
				disableNavigation = true;
			}else if(m.getLevel() == FacesMessage.SEVERITY_WARN.getOrdinal()){
				severity = FacesMessage.SEVERITY_WARN;
			}else if(m.getLevel() == FacesMessage.SEVERITY_INFO.getOrdinal()){
				severity = FacesMessage.SEVERITY_INFO;
			}
			
			String detail = String.format("%d:%s",m.getCode(), m.getMessage());
			FacesMessage message = new FacesMessage(severity,detail,detail);
			UIViewRoot root = fc.getViewRoot();
			//指定したクライアントIDにメッセージを紐づける
			List<TargetClient> list = m.getTargetClients();
			if(!list.isEmpty()){
				for(TargetClient c : list){
					String clientId = null;
					if(StringUtils.isNotEmpty(c.getNamingContainerId())){
						//一覧表内の場合
						if(StringUtils.isNotEmpty(c.getComponentId())){
							UIData data = findComponent(root,c.getNamingContainerId());
							data.setRowIndex((int)c.getRowIndex());
							UIComponent component = findComponent(data, c.getComponentId());
							clientId = component.getClientId(fc);
						}
					}else{
						if(StringUtils.isNotEmpty(c.getComponentId())){
							UIComponent component = findComponent(root,c.getComponentId());
							clientId = component.getClientId(fc);
						}
					}					
					fc.addMessage(clientId,message);
					
				}
			}else{
				fc.addMessage(null,message);
			}
		}
		//追加したら削除
		messages.clear();
		
		return disableNavigation;
	}
	
	/**
	 * コンポーネントを検索する
	 * @param base
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends UIComponent> T findComponent(UIComponent base, String id) {
		if (id.equals(base.getId())) {
			return (T) base;
		}

		UIComponent kid = null;
		UIComponent result = null;
		Iterator<UIComponent> kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				break;
			}
			result = findComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return (T) result;
	}
	
}
