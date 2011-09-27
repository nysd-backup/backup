/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.sql.Connection;

import javax.persistence.EntityManager;

import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.server.ClientSession;

import framework.jpqlclient.api.EntityManagerProvider;
import framework.sqlclient.api.ConnectionProvider;

/**
 * SQL繧ｨ繝ｳ繧ｸ繝ｳ逕ｨ縺ｮ繧ｳ繝阪け繧ｷ繝ｧ繝ｳ蜿門ｾ夕繧ｨ繝ｳ繝・ぅ繝・ぅ繝槭ロ繝ｼ繧ｸ繝｣邨檎罰].
 * <b>Spring蟆ら畑</b>
 * 
 * <pre>
 * 蜑肴署縺ｨ縺励※縲゛TA莉･螟悶・繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ繝槭ロ繝ｼ繧ｸ繝｣縺ｧ菴ｿ逕ｨ縺吶ｋ縺薙→縲・
 * JTA縺ｮ譎ゅ・DataSource縺九ｉ蜿門ｾ励＠縺ｦ繧ゅさ繝阪け繧ｷ繝ｧ繝ｳ荳肴紛蜷医ｒ襍ｷ縺薙＆縺ｪ縺・◆繧∽ｽｿ逕ｨ縺吶ｋ蠢・ｦ√・縺ｪ縺・・
 * 
 * <p>
 * DataSource縺九ｉ縺ｮ逶ｴ謗･蜿門ｾ励ｄDataSourceUtils#getConnection(DataSource)縺ｧ縺ｯ縲・
 * 迴ｾ蝨ｨ螳溯｡御ｸｭ縺ｮ繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ縺御ｽｿ逕ｨ縺励※縺・ｋ繧ｳ繝阪け繧ｷ繝ｧ繝ｳ縺ｨ縺ｯ逡ｰ縺ｪ繧九さ繝阪け繧ｷ繝ｧ繝ｳ縺御ｽｿ逕ｨ縺輔ｌ縺ｦ縺励∪縺・◆繧√ョ繝ｼ繧ｿ荳肴紛蜷医→縺ｪ繧九◆繧・
 * 蟶ｳ逾ｨ蜃ｺ蜉帙↑縺ｩ繝薙ず繝阪せ繝ｭ繧ｸ繝・け縺ｧSQL繧ｨ繝ｳ繧ｸ繝ｳ繧貞茜逕ｨ縺吶ｋ蝣ｴ蜷医・縺薙・繧ｯ繝ｩ繧ｹ繧剃ｽｿ逕ｨ縺励※繧ｳ繝阪け繧ｷ繝ｧ繝ｳ繧貞叙蠕励☆繧九％縺ｨ縲・
 * 讀懃ｴ｢縺励°螳溯｡後＠縺ｪ縺・・縺ｧ縺ゅｌ縺ｰDataSource邨檎罰縺ｧ繧ょ撫鬘後↑縺・・
 *
 * 縺薙・繧ｯ繝ｩ繧ｹ繧剃ｽｿ逕ｨ縺吶ｋ蝣ｴ蜷医ヨ繝ｩ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ繧帝幕蟋九＠縺ｦ縺・↑縺・→getConnection()譎ゅ↓NPE縺檎匱逕溘☆繧九◆繧√∝ｿ・★繝医Λ繝ｳ繧ｶ繧ｯ繧ｷ繝ｧ繝ｳ繧帝幕蟋九＠縺ｦ螳溯｡後☆繧九％縺ｨ縲・
 * Transactional繧｢繝弱ユ繝ｼ繧ｷ繝ｧ繝ｳ繧偵▽縺代※縺・※繧ＳeadOnly=true縺ｨ縺ｪ縺｣縺ｦ縺・◆繧丑G縲・
 * </p>
 * 
 * </pre>
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class EntityManagerConnectionProviderImpl implements ConnectionProvider{

	/** 繧ｨ繝ｳ繝・ぅ繝・ぅ繝槭ロ繝ｼ繧ｸ繝｣ */
	private EntityManager em;
	
	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();		
	}	

	/**
	 * @see framework.sqlclient.api.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();		
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		return accessor.getConnection();
	}

}
