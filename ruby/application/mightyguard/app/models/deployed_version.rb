class DeployedVersion < ActiveRecord::Base
  self.table_name = 'DEPLOYED_VERSION'
  
  #	get the commit log differences
  def DeployedVersion.versionByEnvid(envid)
 	version = select("DISTINCT VERSION").where("ENVID=?",envid).limit(2).reload
 	if version.size == 1 then 
		return version[0].version	
	elsif version.size == 0 then
		return 'Undeployed'
	else
		return 'Illegal State'	
  	end	
  end
  
end
