/**
 * Use is subject to license terms.
 */
package framework.service.ext.locator;


/**
 * リソースパスのコンテキストを初期化する.
 *
 * @author yoshida-n
 * @version	2011/05/11 created.
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
