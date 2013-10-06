class Version < ActiveRecord::Base
  self.table_name = 'VERSION'
  
  #	get the sorted list
  def Version.allVersions
  	return select("DISTINCT VERSIONNO,CREATEDATE").order("CREATEDATE DESC")
  end
   
  # comparable version list 
  def Version.comparableVersions(version)
  	return select("DISTINCT VERSIONNO").where("versionno < ?",version).order("VERSIONNO DESC").limit(10)
  end
   
  def Version.modules(version)
  	return Version.where("versionno = ?" , version).order("LASTCHANGEDDATE DESC")
  end
   
end
