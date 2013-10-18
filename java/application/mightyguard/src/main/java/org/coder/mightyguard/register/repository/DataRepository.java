/**
 * 
 */
package org.coder.mightyguard.register.repository;

import java.io.File;
import java.io.StringReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.Manifest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.coder.mightyguard.register.domain.application.AppVersion;
import org.coder.mightyguard.register.domain.application.Info;
import org.coder.mightyguard.register.domain.application.Log;
import org.coder.mightyguard.register.service.TaskOutputSweaper;

/**
 * @author yoshida-n
 *
 */
public class DataRepository {
	

	private static JAXBContext INFO_CONTEXT;	
	private static JAXBContext LOGS_CONTEXT;
	 
	static {
	        try {
	            INFO_CONTEXT = JAXBContext.newInstance(Info.class);
	            LOGS_CONTEXT = JAXBContext.newInstance(Log.class);
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	}
	
	private String svnRootDir = null;
	
    private EntityManager em;

	/**
	 * @param svnRootDir to set
	 */
	public void setSvnRootDir(String svnRootDir){
		this.svnRootDir = svnRootDir;
	}
	
    /**
     * @param em to set
     */
    public void setEntityManager(EntityManager em){
    	this.em = em;
    }
    
    /**
     * @param data the data to persist
     */
    public void persist(AppVersion data){
    	Query query = em.createQuery("select e from AppVersion e where e.version = :version and e.moduleId = :moduleId");
    	query.setParameter("version", data.version);
    	query.setParameter("moduleId", data.moduleId);
    	try{
    		AppVersion found = (AppVersion)query.getSingleResult();
        	em.remove(found);        	
    	}catch(NoResultException nre){    		
    	}
    	em.persist(data);
    	    	
    }
	
	/**
	 * Selects the data 
	 * @param f file 
	 * @return domain object
	 */
	public AppVersion load(File f) throws Exception{
		  AppVersion data = new AppVersion();

          // モジュール名
          data.moduleId = f.getName().split("\\.")[0];

          // マニフェスト取得
          URL jarUrl = new URL(String.format("jar:file:/%s!/",
                  f.getAbsolutePath()));
          JarURLConnection jarConnection = (JarURLConnection) jarUrl
                  .openConnection();
          Manifest manifest = jarConnection.getManifest();
          String url = svnRootDir
                  + manifest.getMainAttributes().getValue("SVN-Root");
          String revision = manifest.getMainAttributes().getValue(
                  "SVN-Revision");

          // SVNリポジトリから取得
          Info info = getFromRepository(INFO_CONTEXT, "svn", "info", url,
                  "-r", revision, "--xml");
          Log log = getFromRepository(LOGS_CONTEXT, "svn", "log", url + "@"
                  + revision, "--xml", "--verbose", "--limit", "50");
          data.info = info;
          data.log = log;
          return data;
	}
	
    /**
     * <pre>
     *    リポジトリから情報を取得する.
     * </pre>
     * 
     * @param type
     *            タイプ
     * @param command
     *            コマンド
     * @return
     * @throws Exception
     */
    private <T> T getFromRepository(JAXBContext context, String... command)
            throws Exception {

        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
        	System.out.println(Arrays.asList(command).toString());
            Process process = new ProcessBuilder().command(command)
                    .redirectErrorStream(true).start();
            TaskOutputSweaper stdout = new TaskOutputSweaper(
                    process.getInputStream());

            // sweaper実行
            Future<String> futureReturn = service.submit(stdout);
            int res = process.waitFor();
            String xmlMessage = futureReturn.get();
            if (res != 0) {
                throw new RuntimeException(xmlMessage);
            }
            System.out.println(xmlMessage);
            @SuppressWarnings("unchecked")
            T e = (T) context.createUnmarshaller().unmarshal(
                    new StringReader(xmlMessage));
            return e;
        } finally {
            service.shutdown();
        }
    }
    
   

}
