/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import kosmos.framework.core.logics.log.LogWriter;
import kosmos.framework.core.logics.log.LogWriterFactory;
import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.activation.SpringServiceLocator;
import kosmos.framework.sqlengine.builder.ConstCache;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.JNDIConnector;
import org.eclipse.persistence.sessions.server.ClientSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class ServiceUnit extends Assert{
	
	/** ログ */
	protected static final LogWriter LOG = LogWriterFactory.getLog(LogWriter.class);
	
	/** サービスロケータ */
	protected static SpringServiceLocator locator = null;
	
	protected IDatabaseConnection connection = null;

	protected ServiceTestContextImpl context = null;

	@org.junit.Before
	public void before(){
		MDC.put("transactionId",System.nanoTime()+"");
	}
	
	@After
	public void after(){
		MDC.remove("transactionId");
	}
	
	/**
	 * コンチE��ストロード、個別チE��トケースで使用するServiceLocatorめEontextConfigurationを使用できるようにする
	 * AutowiredよりもResourceの方がタイミング皁E��早ぁE��めResourceを使用する
	 * 
	 * @param applicationContext
	 */
	@Resource
	public void setApplicationContext(final ApplicationContext applicationContext){
		
		locator = createLocator(applicationContext);
		locator.construct();	
		
		context = new ServiceTestContextImpl();	
		context.initialize();	
		

		try{
			Class<?> clazz = Class.forName(CachableConst.class.getName());					
			Field[] fs = clazz.getFields();
			for(Field f: fs){
				if(!Modifier.isStatic(f.getModifiers()) || !Modifier.isFinal(f.getModifiers()) || Modifier.isInterface(f.getModifiers())){
					continue;
				}
				Object value = f.get(null);
				if( ConstCache.containsKey(f.getName())){
					throw new IllegalArgumentException(" duplicate filed name = " + f.getName());
				}else{
					ConstCache.put(f.getName(), value);
				}
			}
		}catch(Exception e){
			throw new IllegalStateException("failed to load cache",e);
		}	


		beforeTest();
	}
	
	/**
	 * @param applicationContext
	 * @return
	 */
	protected SpringServiceLocator createLocator(final ApplicationContext applicationContext){
		return new SpringServiceLocator() {
			public void destroy() {}
			public void construct() {
				context = ConfigurableApplicationContext.class.cast(applicationContext);
				delegate = this;
			}
		};
	}
	
	/**
	 * @param dataPath チE�EタのセチE��アチE�E
	 */
	protected void setUpData(String dataPath){
		EntityManager em = ((EntityManagerProvider)(ServiceLocator.lookup("entityManagerProvider"))).getEntityManager();
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		String user = accessor.getLogin().getUserName();
		try{
			Connection con = accessor.getConnection();
			connection = new DatabaseConnection(con,user.toUpperCase());
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param dataPath チE�EタのセチE��アチE�E
	 */
	protected void setUpDataForceCommit(String dataPath){
	
		EntityManager em =  ((EntityManagerProvider)(ServiceLocator.lookup("entityManagerProvider"))).getEntityManager();		
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		DatabaseLogin login = (DatabaseLogin)accessor.getLogin();
		Connection con = null;
		try{
			
			JNDIConnector connector = (JNDIConnector)login.getConnector();
			DriverManagerDataSource ds = (DriverManagerDataSource)connector.getDataSource();
			con = ds.getConnection();
			con.setAutoCommit(false);			
			
			connection = new DatabaseConnection(con,login.getUserName().toUpperCase());
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				if( con != null){
					con.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * @param dataFileName
	 * @param replace
	 * @return
	 */
	protected IDataSet loadDataSet(String dataFileName, Map<Object, Object> replace) {

		AbstractDataSet dataset = null;

		try {

			// 拡張子がxlsの場合、XlsDataSetを生戁E
			if (dataFileName.endsWith(".xls")) {
				dataset = new XlsDataSet(this.getClass().getResourceAsStream(dataFileName));
			} else if (dataFileName.endsWith(".xml")) {
				dataset = new XmlDataSet(this.getClass().getResourceAsStream(dataFileName));
			} else {
				throw new IllegalArgumentException("File is not supported. Supported file types are .xml, .xls, and .csv");
			}
			
			return dataset;

		} catch (IOException e) {			
			throw new RuntimeException(e);
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 初期処琁E
	 */
	protected void beforeTest(){
		
	}
	
	/**
	 * 終亁E�E琁E
	 */
	@After
	public void afterTest(){
		ConstCache.destroy();
		context.release();
		
	}

}
