/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.base;

import java.util.Collection;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import kosmos.framework.base.AbstractEntity;
import kosmos.framework.core.exception.BusinessException;
import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.core.exception.SystemException;
import kosmos.framework.core.logics.Condition;
import kosmos.framework.core.logics.Converter;
import kosmos.framework.core.logics.log.LogWriter;
import kosmos.framework.core.logics.log.LogWriterFactory;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageId;
import kosmos.framework.core.message.MessageLevel;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 基底サービス.
 * 主にDeveloper向けのシンタックスシュガーを提供する。
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractService {
	
	/** ログ */
	private final LogWriter logger = LogWriterFactory.getLog(getClass());
	
	/** 判断ユーティリティ */
	protected final Condition cond = new Condition();
	
	/** 変換ユーティリティ */
	protected final Converter conv = new Converter();
	
	/**
	 * @param message デバッグメッセージ
	 */
	protected void debug(String message){
		logger.debug(message);
	}
	
	/**
	 * collectionが1件でない場合にNonUniqueResultExceptionをスローする.
	 * 
	 * @param collection 検索結果
	 * @throws NonUniqueResultException 検索結果1件でない場合
	 */
	protected void assertIfMultiResult(Collection<?> collection){
		if(collection != null && !collection.isEmpty() && collection.size() > 1){
			throw new NonUniqueResultException("result size is " + collection.size());
		}		
	}
	
	/**
	 * valueが0件でない場合UnexpectedDataFoundExceptionをスローする.
	 * 
	 * @param value 検索結果
	 * @throws UnexpectedDataFoundException 検索結果ありの場合
	 */
	protected void assertEmpty(Object value){
		if(value != null){
			if(value instanceof Collection){
				if(!((Collection<?>) value).isEmpty()){
					throw new SystemException(MessageId.UNEXPECTED_DATAFOUND_ERROR);
				}
			}else{
				throw new SystemException(MessageId.UNEXPECTED_DATAFOUND_ERROR);
			}			
		}		
	}
	
	/**
	 * valueが0件の場合にUnexpectedNoDataFoundExceptionをスローする.
	 * 
	 * @param value 検索結果
	 * @throws UnexpectedNoDataFoundException 検索結果なしの場合
	 */
	protected void assertExists(Object value){
		if(value == null){
			if(value instanceof Collection){
				if(((Collection<?>) value).isEmpty()){
					throw new SystemException(MessageId.UNEXPECTED_NODATA_ERROR);
				}
			}else{
				throw new SystemException(MessageId.UNEXPECTED_NODATA_ERROR);
			}					
		}
	}
	
	/**
	 * 業務エラー例外をスローする.
	 * 
	 * @param bean 例外発生時に追加するメッセージ
	 * @throws BusinessException the exception
	 */
	protected void throwBizError(MessageBean bean) {
		addError(bean);
		throwBizError();
	}
		
	/**
	 * 業務エラー例外をスローする.
	 * 
	 * @throws BusinessException the exception
	 */
	private void throwBizError() {
		throw ServiceLocator.createDefaultBusinessException();
	}
		
	/**
	 * トランザクションが失敗している場合に業務エラーをスローする.<br/>
	 * 業務エラーが発生していたら処理を停止しタイ場合に実行する.
	 * 
	 * <pre>
	 * OneQuery query = createQuery(OneQuery.class);
	 * List<?> result = query.getResultList();
	 * probablyExists(result);
	 * if(conv.isNum(result.get(0).getAttr())) addMessage(bean);
	 * if(conv.eq(result.get(0).getAttr2(),"aa"))addMessage(bean);
	 * 
	 * probablyCommitable();
	 * 
	 * OneService service = createSender(OneService.class);
	 * service.execute();
	 * ・・・
	 * </pre>
	 * 
	 * @throws BusinessException the exception
	 */
	protected void throwIfHasError() {	
		if(isRollbackOnly()){
			throwBizError();
		}		
	}
	
	/**
	 * 検索結果が指定した上限件数を超過している場合に業務エラーをスローする.
	 * 
	 * @param bean 上限超過時に追加するメッセージ
	 * @param maxSize 上限件数
	 * @param result 検索結果
	 * @throws BusinessException 上限超過時
	 */
	protected void throwIfOverLimit(MessageBean bean ,List<?> result , int limit){
		if(result == null || result.isEmpty()){
			return;
		}
		if( result.size() > limit ){
			throwBizError(bean);
		}		
	}
		
	/**
	 * 検索結果が0件の場合に業務エラーをスローする.
	 * 
	 * @param bean 検索結果0件時に追加するメッセージ
	 * @param result 検索結果
	 * @throws BusinessException 検索結果0件時
	 */
	protected void throwIfEmpty(MessageBean bean ,Object value){
		if(value == null){
			if(value instanceof Collection){
				if(((Collection<?>) value).isEmpty()){
					throwBizError(bean);
				}
			}else{
				throwBizError(bean);
			}					
		}		
	}
	
	/**
	 * 検索結果が存在する場合に業務エラーをスローする.
	 * 
	 * @param bean 検索結果が存在する場合の追加するメッセージ
	 * @param result 検索結果
	 * @throws BusinessException 検索結果が存在する場合
	 */
	protected void throwIfExists(MessageBean bean ,Object value){
		if(value != null){
			if(value instanceof Collection){
				if(!((Collection<?>) value).isEmpty()){
					throwBizError(bean);
				}
			}else{
				throwBizError(bean);
			}			
		}		
	}
	
	/**
	 * 業務エラーを追加する.
	 * 
	 * @param bean the MessageBean
	 */
	private void addError(MessageBean bean) {
		MessageResult result = ServiceLocator.createDefaultMessageBuilder().load(bean,ServiceContext.getCurrentInstance().getLocale());
		if(result.getLevel() < MessageLevel.E.ordinal()){
			bug();
		}
		ServiceContext.getCurrentInstance().addMessage(result);
	}
	
	/**
	 * 画面に表示するメッセージを追加する.
	 * エラーレベル以上のメッセージが追加された場合トランザクションは必ずロールバックすることになる<br/>
	 * 
	 * <pre>
	 * エラーレベル以上のメッセージが追加された場合、このクラス内のメソッドは以下のように振る舞う。
	 * isRollbackOnly　→　true
	 * probablyCommitable　→　BusinessExeptionがスローされる
	 * </pre>
	 * 
	 * @param bean メッセージ定義
	 */
	protected void addMessage(MessageBean bean){
		MessageResult result = ServiceLocator.createDefaultMessageBuilder().load(bean,ServiceContext.getCurrentInstance().getLocale());
		ServiceContext.getCurrentInstance().addMessage(result);
	}
	
	/**
	 * 業務メッセージ定義を生成する.
	 * 
	 * <pre>
	 * 	MessageBean bean = cerateMessage(Messages.ONE_ERROR,arguments);
	 *  addMessage(bean);
	 * </pre>
	 * 
	 * @param messageCode
	 * @param args
	 * @return
	 */
	protected MessageBean createMessage(String messageId , Object... args){
		return new MessageBean(messageId, args);
	}
	
	/**
	 * 実装ミスであることを示す。
	 * この構文が実行されるようなケースがあれば直ちに修正が必要。
	 */
	protected void bug(){
		throw new PoorImplementationException();
	}
	
	/**
	 *　トランザクションがロールバック状態になっているかを判定する。
	 * この結果がtrueの場合必ずトランザクションはロールバックされる。
	 * エラーレベル以上のメッセージが実行中のトランザクション内で1度でも発生していれば必ずtrueが返却される。
	 * 
	 * @return true:ロールバック状態
	 */
	protected boolean isRollbackOnly(){
		ServiceContext context = ServiceContext.getCurrentInstance();
		return context.getCurrentUnitOfWork().isRollbackOnly();
	}
	
	/**
	 * @param dest
	 * @param orig
	 */
	protected void copyObject(Object dest , Object orig){
		if(orig instanceof AbstractEntity && dest instanceof AbstractEntity && dest.getClass().equals(orig.getClass())){
			throw new UnsupportedOperationException("Use the AbstractEntity#clone");
		}
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e){
			throw new PoorImplementationException("invalid bean", e);
		}
	}
	
	
}
