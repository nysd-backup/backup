/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.querymodel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import kosmos.framework.core.logics.message.MessageBuilder;
import kosmos.framework.core.message.MessageBean;
import kosmos.framework.core.message.MessageResult;
import kosmos.framework.core.query.StrictQuery;
import kosmos.framework.core.query.StrictUpdate;
import kosmos.framework.querymodel.Checker;
import kosmos.framework.querymodel.NativeQueryModel;
import kosmos.framework.querymodel.NativeUpdateModel;
import kosmos.framework.querymodel.QueryModel;
import kosmos.framework.querymodel.QueryProcessor;
import kosmos.framework.querymodel.StrictQueryModel;
import kosmos.framework.querymodel.StrictUpdateModel;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.free.NativeUpdate;
import kosmos.framework.utility.ReflectionUtils;


/**
 * DefaultQueryProcessorImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultQueryProcessorImpl implements QueryProcessor{

	/** previous result */
	private Object previousResult = null;

	/**
	 * @see kosmos.framework.querymodel.QueryProcessor#getResultList(kosmos.framework.querymodel.StrictQueryModel)
	 */
	@Override
	public void getResultList(StrictQueryModel model) {
		
		StrictQuery<Object> query = ServiceLocator.createDefaultOrmQueryFactory().createStrictQuery(model.getRequest().getEntityClass());
		query.setCondition(model.getRequest());	
		
		//実行
		model.setValue(query.getResultList());
		
		//後処理
		processNextQuery(model);
	}

	/**
	 * @see kosmos.framework.querymodel.QueryProcessor#update(kosmos.framework.querymodel.StrictUpdateModel)
	 */
	@Override
	public void update(StrictUpdateModel model) {
		
		StrictUpdate<Object> query = ServiceLocator.createDefaultOrmQueryFactory().createStrictUpdate(model.getRequest().getEntityClass());
		query.setCondition(model.getRequest());
		
		//実行
		model.setValue(query.update());
		
		//後処理
		processNextUpdate(model);
		
	}
	/**
	 * @see kosmos.framework.querymodel.QueryProcessor#update(kosmos.framework.querymodel.NativeUpdateModel)
	 */
	@Override
	public void update(NativeUpdateModel model) {
		
		NativeUpdate query = ServiceLocator.createDefaultQueryFactory().createUpdate(model.getBaseClass());
		Map<String,Object> savedParam = new HashMap<String,Object>(model.getRequest().getParam());
		Map<String,Object> savedBranch = new HashMap<String,Object>(model.getRequest().getBranchParam());
		query.setCondition(model.getRequest());
		
		if(	model.isContinuePreviousResult() ){
			Map<String,Object> previousResult = getParameter();
			if(!previousResult.isEmpty()){
				for(Map.Entry<String, Object> e : getParameter().entrySet()){
					query.setParameter(e.getKey(), e.getValue()).setBranchParameter(e.getKey(), e.getValue());
				}				
				for(Map.Entry<String, Object> e: savedParam.entrySet()){
					query.setParameter(e.getKey(), e.getValue());
				}				
				for(Map.Entry<String, Object> e: savedBranch.entrySet()){
					query.setBranchParameter(e.getKey(), e.getValue());
				}
			}
		}
		
		//実行
		model.setValue(query.update());
		
		//後処理
		processNextUpdate(model);
		
	}

	/**
	 * @see kosmos.framework.querymodel.QueryProcessor#getResultList(kosmos.framework.querymodel.NativeQueryModel)
	 */
	@Override
	public void getResultList(NativeQueryModel model) {
		
		NativeQuery query = ServiceLocator.createDefaultQueryFactory().createQuery(model.getBaseClass());
		Map<String,Object> savedParam = new HashMap<String,Object>(model.getRequest().getParam());
		Map<String,Object> savedBranch = new HashMap<String,Object>(model.getRequest().getBranchParam());
		query.setCondition(model.getRequest());
		
		if(	model.isContinuePreviousResult() ){
			
			Map<String,Object> previousResult = getParameter();
			if(!previousResult.isEmpty()){
				for(Map.Entry<String, Object> e : getParameter().entrySet()){
					query.setParameter(e.getKey(), e.getValue()).setBranchParameter(e.getKey(), e.getValue());
				}				
				for(Map.Entry<String, Object> e: savedParam.entrySet()){
					query.setParameter(e.getKey(), e.getValue());
				}				
				for(Map.Entry<String, Object> e: savedBranch.entrySet()){
					query.setBranchParameter(e.getKey(), e.getValue());
				}
			}
			
		}
		
		//実行	
		model.setValue(query.getResultList());
		//後処理
		processNextQuery(model);
		
	}
	
	/**
	 * Process after updating the model.
	 * 
	 * @param model the model
	 * @return the check result
	 */
	private void processNextUpdate(QueryModel model){
		boolean success = afterUpdate(model);
		if(!success && model.isStopIfFail()){
			return;			
		}
		if(model.getChild() != null){
			model.getChild().accept(this);
		}
	}
	
	/**
	 * Process after updating the model.
	 * 
	 * @param model the model
	 * @return the check result
	 */
	private void processNextQuery(QueryModel model){
		
		boolean success = afterUpdate(model);
		if(!success){
			if(model.isStopIfFail()){
				return;
			}
		}else {
			List<Object> value = model.getValue();
			if(!value.isEmpty()){
				previousResult = value.get(0);
			}			
		}
		if(model.getChild() != null){
			model.getChild().accept(this);
		}
	}
	
	/**
	 * Process after updating the model.
	 * 
	 * @param model the model
	 * @return the check result
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean afterUpdate(QueryModel model){
		MessageBuilder builder = ServiceLocator.createDefaultMessageBuilder();
		ServiceContext context = ServiceContext.getCurrentInstance();
		//チェック
		boolean success = true;
		for(Checker chk :model.getCheckerList()){
			boolean result = chk.check(model.getValue());
			if(!result){
				
				//メッセージ追加
				MessageBean bean = chk.getMessageBean();
				MessageResult messageResult = builder.load(bean);
				context.addError(messageResult);
				
				//チェック終了
				success = false;
				if(chk.isStopIfFail()){
					break;
				}
			}
		}
		//チェック結果失敗時
		if(!success){
			if(model.isThrowIfFail()){
				throw ServiceLocator.createDefaultBusinessException();
			}
		}
		return success;
	}
	
	/**
	 * @return the previous result
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> getParameter(){
		if(previousResult == null){
			return new HashMap<String,Object>();
		}
		
		if(previousResult instanceof Map){
			return (Map<String,Object>)previousResult;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//Entity
		if(previousResult.getClass().getAnnotation(Table.class) != null){
			Field[] fs = ReflectionUtils.getAllAnotatedField(previousResult.getClass(), Column.class);
			for(Field f :fs){
				String name = f.getAnnotation(Column.class).name();
				if(name == null){
					name = f.getName();
				}
				map.put(name, ReflectionUtils.get(f, previousResult));
			}
			return map;
			
		//Native	
		}else {
			Method[] ms = previousResult.getClass().getMethods();
			for(Method m : ms){
				if(!ReflectionUtils.isGetter(m)){
					continue;
				}
				map.put(ReflectionUtils.getPropertyNameFromGetter(m),ReflectionUtils.invokeMethod(m, previousResult));
			}
			return map;
		}
	}
	
}
