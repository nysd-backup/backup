class CreateDate < ActiveRecord::Base
  self.table_name = 'CREATE_DATE'
  
   #	get the commit log differences
  def CreateDate.localVersions()
 		return select("VERSION,CREATEDATE").where("OWNER = ?",'A999').order("CREATEDATE DESC").limit(10)
  end
  
  #	get the commit log differences
  def CreateDate.martVersions()
 		return select("VERSION,CREATEDATE").where("OWNER = ?",'M999L').order("CREATEDATE DESC").limit(10)
  end
  
   #	get the commit log differences
  def CreateDate.execVersions()
 		return select("VERSION,CREATEDATE").where("OWNER = ?",'E999L').order("CREATEDATE DESC").limit(10)
  end

  
end
