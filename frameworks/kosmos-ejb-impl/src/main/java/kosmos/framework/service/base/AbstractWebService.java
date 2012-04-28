/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.message.MessageReplyable;

/**
 * SOAP WEBサービスの基底.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractWebService {

	/**
	 * 業務例外を正常メッセージに変換する。
	 * 
	 * <pre>
	 *  Sample:
	 * 
	 *  XxxWebServiceResponse response = new XxxWebServiceResponse();
	 *  XxxRequest ejbResuqest = new XxxRequest();
	 *  ejbResuqest.setXxxDto(request.getXxxDto());
	 * 	try {
	 * 		XxxResponse ejbResponse = ejbService.serviceMethod(ejbResuqest);
	 * 		response.setXxxDto(ejbResponse.getXxxDto);
	 *  }catch(BusinessException be ){
	 *  	handleException(response,be);
	 *  }
	 *  return response
	 * </pre>
	 * @param be
	 */
	protected final void handleException( MessageReplyable response, BusinessException be){
		response.setMessageList(be.getMessageList());
	}
}
