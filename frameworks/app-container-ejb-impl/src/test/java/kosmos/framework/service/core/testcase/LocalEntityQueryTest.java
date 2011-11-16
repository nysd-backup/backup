/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.testcase;

import java.sql.SQLException;

import kosmos.framework.service.core.ServiceUnit;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.entity.ITestEntity;

import org.junit.Test;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalEntityQueryTest extends ServiceUnit implements ITestEntity{

	private LocalEntityQueryTestBean bean(){
		return ServiceLocator.lookup(LocalEntityQueryTestBean.class.getSimpleName());
	}
	
	/**
	 * 条件追加
	 * @throws SQLException 
	 */
	@Test
	public void allCondition() throws SQLException{			
		bean().allCondition();
	}
	
	/**
	 * チE��タチE��
	 */
	@Test
	public void disableDetach(){
		
		bean().disableDetach();
	}
	
	/**
	 * 更新後検索
	 */
	@Test
	public void updateAny(){
		
		
		bean().updateAny();
	}
	
	/**
	 * 1件取得　降頁E��ーチE
	 */
	@Test
	public void getSingleResultWithDesc(){
		
		bean().getSingleResultWithDesc();
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */
	@Test
	public void getSingleResultWithAsc(){
		bean().getSingleResultWithAsc();
	}
	

	/**
	 * 2件目取征E
	 */
	@Test
	public void getSingleResultSetFirstWithDesc(){
		
		bean().getSingleResultSetFirstWithDesc();
	}
	
	/**
	 * 2件目から取征E
	 */
	@Test
	public void getResultSetFirst(){
		
		bean().getResultSetFirst();
	}
	
	/**
	 * 2件目から3件目取征E
	 */
	@Test
	public void getResultSetFirstMax2(){
		
		bean().getResultSetFirstMax2();
	}
	
	/**
	 * 0件シスチE��エラー
	 */
	@Test
	public void nodataError(){
		bean().nodataError();
	}
	
	/**
	 * PK検索
	 */
	@Test
	public void find(){	
		bean().find();
	}
	
	/**
	 * PK検索、更新
	 */
	@Test
	public void findDisableDetach(){
	
		bean().findDisableDetach();
	}

	/**
	 * 0件シスチE��エラー
	 */
	@Test
	public void findNodataError(){
		bean().findDisableDetach();
	}
	
	/**
	 * 条件追加
	 */
	@Test
	public void findAny(){
		
		bean().findAny();
	}
	
	/**
	 * 条件追加 更新
	 */
	@Test
	public void findAnyDisableDetach(){
		
		bean().findAnyDisableDetach();
	}
	
	/**
	 * 0件シスチE��エラー
	 */
	@Test
	public void findAnyNodataError(){
		bean().findAnyNodataError();
	}
	

	/**
	 * ANY褁E��件存在
	 */
	@Test
	public void findAnyMultiResultError(){
		
		bean().findAnyMultiResultError();
	}

	/**
	 *  存在チェチE�� not 
	 */
	@Test
	public void exists(){	
		
		bean().exists();
	}
	
	/**
	 * PK存在チェチE��
	 */
	@Test
	public void isEmptyPK(){
			
		bean().isEmptyPK();
	}
	
	/**
	 * PK存在チェチE��
	 */
	@Test
	public void existsPK(){
		
		bean().existsPK();
	}
	
	/**
	 * ANY存在チェチE�� 
	 */
	@Test
	public void existsByAny(){
	
		bean().existsByAny();
	}
	
	/**
	 * ANY褁E��件存在チェチE��
	 */
	@Test
	public void existsByAnyMultiResultError(){
		
		bean().existsByAnyMultiResultError();
	}
	
	/**
	 * 一意制紁E��ラー
	 */
	@Test	
	public void uniqueConstraintError(){
		
		bean().uniqueConstraintError();
	}
	
	/**
	 * 一意制紁E��ラー無要E
	 */
	@Test	
	public void ignoreUniqueConstraintError(){
		
		bean().ignoreUniqueConstraintError();
	}
	
	/**
	 * ロチE��連番チェチE��エラー
	 */
	@Test	
	public void versionNoError(){	
		
		bean().versionNoError();
	}
	/**
	 * ロチE��連番チェチE��エラー無要E
	 */
	@Test	
	public void ignoreVersionNoError(){		
		
		bean().ignoreVersionNoError();
	}
	
	/**
	 * 悲観ロチE��エラー無効匁E
	 * @throws SQLException 
	 */
	@Test
	public void invalidFindWithLockNoWaitError(){	
		bean().invalidFindWithLockNoWaitError();		
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void findWithLockNoWaitError(){	
	
		bean().findWithLockNoWaitError();	
		
	}
	
	/**
	 * 自律トランザクション先で例外にぎり潰した時、E
	 * 自律トランザクションで例外になっても呼び出し�EでキャチE��してぁE��ば問題なぁE
	 * 
	 * @throws SQLException 
	 */
	@Test
	public void crushExceptionInAutonomousTransaction(){	
		bean().crushExceptionInAutonomousTransaction();
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void queryPessimisticLockError(){	
		bean().queryPessimisticLockError();
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	@Test
	public void invalidQueryPessimisticLockError(){	
		bean().invalidQueryPessimisticLockError();
	}
	
	
	/**
	 * メチE��ージ持E��E
	 */
	@Test
	public void existsMesasgeByAnyTrue(){
		bean().existsMesasgeByAnyTrue();
	}
	
	/**
	 * メチE��ージ持E��E
	 */
	@Test
	public void dateCheck(){
		bean().dateCheck();
	}
	
	/**
	 * EasyUpdate#executeのチE��チE
	 */
	@Test
	public void easyUpdate(){		
		bean().easyUpdate();
	}
	
	/**
	 * EasyQuery#listのチE��チE
	 */
	@Test
	public void easyList(){
		bean().easyList();
	}
	
	/**
	 * EasyQuery#singleのチE��チE
	 */
	@Test
	public void easySingle(){
		bean().easySingle();
	}
	
	/**
	 * カスケーチE
	 */
	@Test
	public void cascade(){
		bean().cascade();
	}
	
	@Test
	public void remote(){
		bean().remote();
	}
}
