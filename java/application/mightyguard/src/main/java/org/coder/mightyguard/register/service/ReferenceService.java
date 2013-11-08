/**
 * 
 */
package org.coder.mightyguard.register.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.resource.cci.Connection;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.coder.gear.mongo.aggregation.Query;
import org.coder.gear.query.QueryFactoryFinder;
import org.coder.gear.query.criteria.query.ListReadQuery;
import org.coder.mightyguard.register.domain.Version;
import org.coder.mightyguard.register.domain.application.AppVersion;
import org.eclipse.persistence.internal.nosql.adapters.mongo.MongoConnection;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author yoshida-n
 *
 */
@Path("/version")
public class ReferenceService {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mightyguard");
	
	private static final QueryFactoryFinder finder = new QueryFactoryFinder();
	
	/**
	 * @param limit the limit
	 * @return get list
	 */
	@Path("allTheList")
	@GET
	@Produces("application/json")
	public Collection<Version> allTheList(@QueryParam("limit") @DefaultValue("10") int limit){

		DBCollection cl = getCollection();
		Query query = new Query(cl).keys("VERSION","DATE").limit(limit);
		return getSortedList(query.aggregate());
		
	}
	
	/**
	 * Gets the version list
	 * @param version
	 * @return
	 */
	@Path("list")
	@GET
	@Produces("application/json")
	public Collection<Version> previousList(@QueryParam("current") String version,@QueryParam("limit") @DefaultValue("10") int limit){
		
		DBCollection cl = getCollection();
		Query query = new Query(cl);
		query.lt("DATE", getDate(version,cl)).keys("VERSION","DATE").limit(limit);
		return getSortedList(query.aggregate());
	}
	
	/**
	 * 日付取得
	 * @param version
	 * @param cl
	 * @return
	 */
	private String getDate(String version,DBCollection cl){
		Query query = new Query(cl);
		query.eq("VERSION",version).keys("DATE","VERSION");
		BasicDBList dateResult =query.aggregate();
		DBObject object = (DBObject)dateResult.get(0);
		DBObject resultId = (DBObject)object.get("_id");
		String lastDate = (String)resultId.get("DATE");
		return lastDate;
	}

	/**
	 * @param version
	 * @param previousVersion
	 * @return
	 */
	@Path("diff")
	@GET
	@Produces("application/json")
	public List<AppVersion> diff(@QueryParam("current") String version,@QueryParam("previous") String previousVersion){
		EntityManager em = factory.createEntityManager();
		return doDiff(version,previousVersion,em);		
	}
	
	/**
	 * @param version
	 * @param previousVersion
	 * @return
	 */
	@Path("show")
	@GET
	@Produces("application/json")
	public List<AppVersion> show(@QueryParam("current") String version){
		EntityManager em = factory.createEntityManager();		
		
		AppVersion app = finder.createCriteriaQueryFactory().createSingleReadQuery(AppVersion.class, em).eq("version", version).call();
		
		ListReadQuery<AppVersion> currentQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> cList = currentQuery.lt("date", app.date).call();
		
		TreeMap<String, String> map = new TreeMap<String,String>();
		for(AppVersion e : cList){
			map.put(e.date,e.version);
		}		
		String prevVer = map.descendingMap().values().iterator().next();
		return doDiff(version,prevVer,em);		
	}
	
	
	/**
	 * @param version
	 * @param previous
	 * @param em
	 * @return
	 */
	private List<AppVersion> doDiff(String version , String previous , EntityManager em){
				
		ListReadQuery<AppVersion> currentQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> cList = currentQuery.eq("version", version).call();
		
		ListReadQuery<AppVersion> previousQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> pList = previousQuery.eq("version", previous).call();
		
		//前回バージョンのリビジョン設定
    	Map<String,String> moduleMaxRevs = new HashMap<String,String>();
    	for(AppVersion e : pList){
    		moduleMaxRevs.put(e.moduleId, e.info.entry.commit.revision);
    	}
    	
    	//過去リビジョンのカット
    	for(AppVersion e : cList){
    		e.log.removeUnderRevision(Integer.parseInt(moduleMaxRevs.get(e.moduleId)));    	
    	}
    	return cList;		
	}
	
	/**
	 * @return the collection
	 */
	private DBCollection getCollection(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();		
		MongoConnection mc = MongoConnection.class.cast(em.unwrap(Connection.class));
		DB db = mc.getDB();
		DBCollection cl = db.getCollection(AppVersion.class.getSimpleName().toUpperCase());
		return cl;
	}
	
	/**
	 * @param result sorted list
	 * @return
	 */
	private Collection<Version> getSortedList(BasicDBList result){	
		TreeMap<String,Version> versions = new TreeMap<String,Version>(); 
		for(Object e : result){
			BasicDBObject o = BasicDBObject.class.cast(e);
			BasicDBObject ver = (BasicDBObject)o.get("_id");
			Version v = new Version();
			v.date = ver.getString("DATE");
			v.version = ver.getString("VERSION");
			versions.put(v.date,v);
		}	
		return versions.descendingMap().values();
	}	
}
