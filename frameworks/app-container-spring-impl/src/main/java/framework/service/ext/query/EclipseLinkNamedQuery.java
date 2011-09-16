/**
 * Use is subject to license terms.
 */
package framework.service.ext.query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import framework.jpqlclient.api.free.AbstractNamedQuery;
import framework.jpqlclient.api.free.NamedQuery;

/**
 * EclipseLinkのオプションを使用したNamedQuery.

 * @author yoshida-n
 * @version	2011/06/05 created.
 */
@SuppressWarnings("unchecked")
public abstract class EclipseLinkNamedQuery extends AbstractNamedQuery{

	/**
	 * リフレッシュモードを設定する.
	 * @return self
	 */
	public <T extends NamedQuery> T setRefleshMode(){
		setHint(QueryHints.REFRESH, HintValues.TRUE);
		return (T)this;
	}
	
	/**
	 * ヒント句を設定する.
	 * @param <T> hintString ヒント句
	 * @return self
	 */
	public <T extends NamedQuery> T setHintString(String hintString){		
		setHint(QueryHints.HINT, hintString);
		return (T)this;
	}

}
