/**
 * 
 */
package org.coder.mightyguard.register.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.coder.gear.query.QueryFactoryFinder;
import org.coder.gear.query.criteria.query.ListReadQuery;
import org.coder.mightyguard.register.domain.Version;
import org.coder.mightyguard.register.domain.application.AppVersion;

/**
 * @author yoshida-n
 *
 */
@Path("/version")
public class ReferenceService {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mightyguard");
	
	private static final QueryFactoryFinder finder = new QueryFactoryFinder();
	
	/**
	 * Gets the version list
	 * @param version
	 * @return
	 */
	@Path("list")
	@GET
	@Produces("application/json")
	public List<Version> previousList(@QueryParam("current") String version){
		QueryFactoryFinder finder = new QueryFactoryFinder();
		EntityManager em = factory.createEntityManager();
		ListReadQuery<AppVersion> currentQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> cList = currentQuery.lt("version", version).call();
		Map<String,String> tree = new TreeMap<String,String>();
		for(AppVersion v : cList){
			tree.put(v.date, v.version);
		}
		List<Version> vlist = new ArrayList<Version>();
		for(Map.Entry<String, String> v : tree.entrySet()){
			Version e = new Version();
			e.date = v.getKey();
			e.version = v.getValue();
			vlist.add(e);
		}
		return vlist;
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
		
		QueryFactoryFinder finder = new QueryFactoryFinder();
		
		EntityManager em = factory.createEntityManager();
		
		ListReadQuery<AppVersion> currentQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> cList = currentQuery.lt("version", version).call();
		
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
}
