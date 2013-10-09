package org.coder.mightyguard.register.service;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import org.coder.mightyguard.register.domain.Data;
import org.coder.mightyguard.register.repository.DataRepository;

/**
 * @author yoshida-n
 *
 */
public class RegisterService {

    private DataRepository dataRepository;
    
    private String downloadDir = "target/dependency";
    
    /**
     * @param dataRepository to set
     */
    public void setDataRepository(DataRepository dataRepository){
    	this.dataRepository = dataRepository;
    }
    
    /**
     * @param downloadDir to set
     */
    public void setDownloadDir(String downloadDir){
    	this.downloadDir = downloadDir;
    }

    /**
     * @param version
     * @throws Exception
     */
    public void register(String version) throws Exception {
    
        // MANIFEST読み込み
        File versionDir = new File(downloadDir);
        File[] versionFiles = versionDir.listFiles(
    		new FileFilter() {			
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile() && ( pathname.getName().endsWith(".jar") || pathname.getName().endsWith(".war"));
				}	
    		}
        );

        Date date = new Date();
        for (File f : versionFiles) {           
            Data data = dataRepository.load(f);
            data.version = version;
            data.date = date.toString(); 
            dataRepository.persist(data);
        }       
    }

}
