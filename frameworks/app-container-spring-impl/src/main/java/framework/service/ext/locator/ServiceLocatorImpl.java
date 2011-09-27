/**
 * Copyright 2011 the original author
 */
package framework.service.ext.locator;

/**
 * APコンテナ専用のコンテキストを初期化する.
 * 
 * <pre>
 * WEB/APでSpringコンテナを分割する場合に使用する。
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorImpl extends SpringServiceLocator{

	/**
	 * @param resource 外部リソースパス
	 */
	public void construct(String resource) {		
		initialize(resource);
	}
	
	/**
	 * @see framework.service.ext.locator.SpringServiceLocator#construct()
	 */
	@Override
	public void construct() {		
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.service.ext.locator.SpringServiceLocator#destroy()
	 */
	@Override
	public void destroy() {
		terminate();
	}

}
