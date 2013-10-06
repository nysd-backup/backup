class TableVersion < ActiveRecord::Base
  self.table_name = 'TABLE_VERSION'
  
  #	get the commit log differences
  def TableVersion.localDiffWith(cver , dver)
 	file = open("./tablediff.sql")
  	query = file.read 
  	query = sanitize_sql_array([query,cver,'A999',dver,'A999',cver,'A999',dver,'A999'])  	  	  	
  	return connection.select_all(query)  	
  end
  
  #	get the commit log differences
  def TableVersion.execDiffWith(cver , dver)
 	file = open("./tablediff.sql")
  	query = file.read 
  	query = sanitize_sql_array([query,cver,'E999L',dver,'E999L',cver,'E999L',dver,'E999L'])  	  	  	
  	return connection.select_all(query)  	
  end
  
  #	get the commit log differences
  def TableVersion.martDiffWith(cver , dver)
 	file = open("./tablediff.sql")
  	query = file.read 
  	query = sanitize_sql_array([query,cver,'M999L',dver,'M999L',cver,'M999L',dver,'M999L'])  	  	  	
  	return connection.select_all(query)  	
  end
  
end
