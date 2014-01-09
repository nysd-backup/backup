package org.coder.sample.s2jdbc.service;

import javax.annotation.Resource;
import javax.transaction.UserTransaction;

import org.coder.sample.s2jdbc.domain.Child;
import org.coder.sample.s2jdbc.domain.Parent;
import org.coder.sample.s2jdbc.repository.ParentRepository;

/**
 * @author yoshida-n
 *
 */
public class VersionService {
	
	@Resource
	private ParentRepository parentRepository;
	
	@Resource
	private UserTransaction userTransaction;
    
    /**
     * @param version
     * @throws Exception
     */
    public void register() throws Exception {
    	
    	Parent parent = new Parent();
    	parent.id = "id";
    	parent.attr = "attr";   
    	for(int i = 0 ; i < 10; i ++){
    		Child child = new Child();
    		child.childAttr = "childAttr" + i;
    		child.childId = String.valueOf(i);
    		child.parentId = parent.id;
    		child.parent = parent;
    		parent.child.add(child);    		
    	}
    	userTransaction.begin();
    	parentRepository.persist(parent);
    	userTransaction.commit();
    }
    
    /**
     * @throws Exception
     */
    public Parent find() throws Exception {
    	Parent parent = parentRepository.find("id");
    	return parent;
    }

}
