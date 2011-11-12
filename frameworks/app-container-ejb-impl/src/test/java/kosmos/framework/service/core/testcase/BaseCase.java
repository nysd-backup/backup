/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.testcase;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;

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
import org.eclipse.persistence.sessions.server.ClientSession;
import org.junit.Assert;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BaseCase extends Assert{

	@Resource
	protected SessionContext context;

	@EJB
	protected EntityManagerProvider per;
	
	private IDatabaseConnection connection = null;
	
	protected void setUpData(String dataPath){
		try{
			EntityManager em = per.getEntityManager();		
			em.createNativeQuery("SELECT * FROM DUAL").getSingleResult();
			EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();
			ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
			DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
			Connection con = accessor.getConnection();
			connection = new DatabaseConnection(con,"yoshida");
			IDataSet dataSet = loadDataSet(String.format("/testdata/%s",dataPath), null);
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			throw new RuntimeException(e);
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
}
