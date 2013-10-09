package org.coder.mightyguard.register.service;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.coder.mightyguard.register.domain.database.ErdVersion;
import org.eclipse.persistence.internal.nosql.adapters.mongo.MongoConnection;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * @author yoshida-n
 *
 */
public class DatabaseVersionService {

	private EntityManager rem;
	
    private EntityManager em;

    /**
     * @param em to set
     */
    public void setEntityManager(EntityManager em){
    	this.em = em;
    }
    
    /**
     * @param rem to set
     */
    public void setRemoteEntityManager(EntityManager rem){
    	this.rem = rem;
    }
    

    /**
     * @param version
     * @throws Exception
     */
    public void register(String version,String owner) throws Exception {
    
    	//削除
    	DB db = ((MongoConnection)em.unwrap(javax.resource.cci.Connection.class)).getDB();
    	DBCollection collection = db.getCollection("ERDVERSION");
    	collection.remove(new BasicDBObjectBuilder().add("VERSION", version).add("OWNER", owner).get());
    	
    	InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("oracleSql.sql");    	
    	StringWriter writer = new StringWriter();
    	IOUtils.copy(stream,writer,"UTF-8");
    	String sql = writer.toString();
    
    	Query query = rem.createNativeQuery(String.format(sql,version,owner,"%$%",owner,"%$%"), ErdVersion.class);

    	List<ErdVersion> tables = (List<ErdVersion>)query.getResultList();
    	for(ErdVersion t : tables){
    		em.persist(t);
    	}    	
    }
}
