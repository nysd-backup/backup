class ChangedSource < ActiveRecord::Base
  self.table_name = 'CHANGED_SOURCE'
  
  #	get the commit log differences
  def ChangedSource.diffWith(cver , dver)
  	sourceQuery = "SELECT REVISION,MODULEID,TYPE,PATH FROM CHANGED_SOURCE WHERE "
  	orderBy = "ORDER BY TO_NUMBER(REVISION) DESC"
  	c = send(:sanitize_sql_array, ["VERSIONNO = ?",cver])
  	d = send(:sanitize_sql_array, ["VERSIONNO = ?",dver])
  	return connection.select_all("SELECT * FROM( #{sourceQuery} #{c} MINUS #{sourceQuery} #{d}) #{orderBy}")  	  
  end
  
end
