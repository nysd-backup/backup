/**
 * Copyright 2011 the original author
 */
package service.testcase;


import org.coder.alpha.framework.registry.ServiceLocator;
import org.junit.Test;


import service.ServiceUnit;
import service.entity.ITestEntity;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalNativeQueryTest extends ServiceUnit implements ITestEntity{

	private LocalNativeQueryTestBean bean(){
		return ServiceLocator.getService(LocalNativeQueryTestBean.class.getSimpleName());
	}
	
	/**
	 * 通常検索
	 */
	@Test
	public void paging(){
		bean().paging();			
	}
	
	/**
	 * 通常検索
	 */
	@Test
	public void fetch(){
		bean().fetch();			
	}

	/**
	 * 通常検索
	 */
	@Test
	public void select(){
		bean().select();			
	}
	
	/**
	 * 通常検索if刁E
	 */
	@Test
	public void selectIfAttr(){
		bean().selectIfAttr();	
	}
	

	/**
	 * if斁E��索
	 * 数値比輁E��not null、文字�E比輁E
	 */
	@Test
	public void selectIfAttr2(){
		bean().selectIfAttr2();
	}
	
	/**
	 * 結果0件シスチE��エラー
	 */
	@Test
	public void nodataError(){
		//bean().nodataError();
	}
//	
//	/**
//	 * exists
//	 */
//	@Test
//	public void exists(){
//		bean().exists();
//	}
	
	/**
	 * getSingleResult
	 */
	@Test
	public void getSingleResult(){
		bean().getSingleResult();
	}
	
	/**
	 * setMaxSize
	 */
	@Test
	public void setMaxSize(){
		bean().setMaxSize();
	}
	
	/**
	 * setFirstResult、E件目�E�E件目取征E
	 */
	@Test
	public void setFirstResult(){
		bean().setFirstResult();
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	@Test
	public void constTest(){
	
		bean().constTest();
	}

	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void constAttr(){
		bean().constAttr();
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void constVersionNo(){
		bean().constVersionNo();
	}
	
	/**
	 * ヒット件数等取征E
	 */
	@Test
	public void count(){
		bean().count();
	}
	
	/**
	 * total result
	 */
	@Test
	public void getHitCount(){
		bean().getHitCount();
	}
	
	/**
	 * ResultSetフェチE��取征E
	 */
	@Test
	public void lazySelect(){
		bean().lazySelect();
	}
	

	/**
	 * test
	 */
	@Test 
	public void updateConstTest(){
		bean().updateConstTest();
	}
	

	/**
	 * attr
	 */
	@Test 
	public void updateConstAttr(){
		bean().updateConstAttr();
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	@Test
	public void updateConstVersionNo(){
		bean().updateConstVersionNo();
	}

}
