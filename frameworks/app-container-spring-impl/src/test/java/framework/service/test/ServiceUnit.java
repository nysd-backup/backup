/**
 * Copyright 2011 the original author
 */
package framework.service.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import framework.api.dto.ClientSessionBean;
import framework.jpqlclient.api.EntityManagerProvider;
import framework.logics.log.LogWriter;
import framework.logics.log.LogWriterFactory;
import framework.service.core.locator.ServiceLocator;
import framework.service.ext.locator.SpringServiceLocator;
import framework.sqlengine.builder.ConstCache;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class ServiceUnit extends Assert{
	
	/** 繝ｭ繧ｰ */
	protected static final LogWriter LOG = LogWriterFactory.getLog(LogWriter.class);
	
	/** 繧ｵ繝ｼ繝薙せ繝ｭ繧ｱ繝ｼ繧ｿ */
	protected static SpringServiceLocator locator = null;
	
	private IDatabaseConnection connection = null;

	private ServiceTestContextImpl context = null;
	
	/**
	 * 繧ｳ繝ｳ繝・く繧ｹ繝医Ο繝ｼ繝峨∝句挨繝・せ繝医こ繝ｼ繧ｹ縺ｧ菴ｿ逕ｨ縺吶ｋServiceLocator繧・ontextConfiguration繧剃ｽｿ逕ｨ縺ｧ縺阪ｋ繧医≧縺ｫ縺吶ｋ
	 * Autowired繧医ｊ繧３esource縺ｮ譁ｹ縺後ち繧､繝溘Φ繧ｰ逧・↓譌ｩ縺・◆繧ヽesource繧剃ｽｿ逕ｨ縺吶ｋ
	 * 
	 * @param applicationContext
	 */
	@Resource
	public void setApplicationContext(final ApplicationContext applicationContext){
		locator = createLocator(applicationContext);
		locator.construct();	
		
		context = new ServiceTestContextImpl();
		ClientSessionBean clientSessionBean = new ClientSessionBeanImpl();
		clientSessionBean.setLocale(Locale.JAPAN);		
		context.initialize();
		context.setClientSessionBean(clientSessionBean);		

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
	 * @param dataPath 繝・・繧ｿ縺ｮ繧ｻ繝・ヨ繧｢繝・・
	 */
	protected void setUpData(String dataPath){
		//javaagent繧剃ｽｿ逕ｨ縺励↑縺・腰菴薙ユ繧ｹ繝医・蝣ｴ蜷医√さ繝溘ャ繝医＆繧後ｋ
		EntityManager em = ServiceLocator.lookupByInterface(EntityManagerProvider.class).getEntityManager();		
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
	 * @param dataPath 繝・・繧ｿ縺ｮ繧ｻ繝・ヨ繧｢繝・・
	 */
	protected void setUpDataForceCommit(String dataPath){
	
		EntityManager em = ServiceLocator.lookupByInterface(EntityManagerProvider.class).getEntityManager();		
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
	private IDataSet loadDataSet(String dataFileName, Map<Object, Object> replace) {

		AbstractDataSet dataset = null;

		try {

			// 諡｡蠑ｵ蟄舌′xls縺ｮ蝣ｴ蜷医々lsDataSet繧堤函謌・
			if (dataFileName.endsWith(".xls")) {
				dataset = new XlsDataSet(this.getClass().getResourceAsStream(dataFileName));
			} else if (dataFileName.endsWith(".xml")) {
				dataset = new XmlDataSet(this.getClass().getResourceAsStream(dataFileName));
			} else {
				throw new IllegalArgumentException("File is not supported. Supported file types are .xml, .xls, and .csv");
			}
			
			return dataset;

		} catch (IOException e) {			
			throw new RuntimeException("繝・せ繝医ョ繝ｼ繧ｿ繝輔ぃ繧､繝ｫ縺ｮI/O荳ｭ縺ｫ繧ｨ繝ｩ繝ｼ縺檎匱逕溘＠縺ｾ縺励◆縲・, e);
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 蛻晄悄蜃ｦ逅・
	 */
	protected void beforeTest(){
		
	}
	
	/**
	 * 邨ゆｺ・・逅・
	 */
	@After
	public void afterTest(){
		ConstCache.destroy();
		context.release();
	}

}
