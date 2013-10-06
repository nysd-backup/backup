class CommitLog < ActiveRecord::Base
  self.table_name = 'COMMIT_LOG'
  
  #	get the commit log differences
  def CommitLog.diffWith(cver , dver)
  	sourceQuery = "SELECT REVISION,MODULEID,COMMITER,TICKETNO, MESSAGE ,COMMITDATE FROM COMMIT_LOG WHERE "
  	orderBy = "ORDER BY TO_NUMBER(REVISION) DESC"
  	c = send(:sanitize_sql_array, ["VERSIONNO = ?",cver])
  	d = send(:sanitize_sql_array, ["VERSIONNO = ?",dver])
  	commitCur = connection.execute("SELECT * FROM( #{sourceQuery} #{c} MINUS #{sourceQuery} #{d}) #{orderBy}")  	
  	begin
	  	commitLogs = Array.new;  	  	
	  	while rec = commitCur.fetch_hash  	  	
	  		commitLogs.push rec
			rec["COMMITDATE"] = rec["COMMITDATE"][0..18]
			rec["MESSAGE"] = '' if rec["MESSAGE"] == nil
	  	end  	
		return commitLogs 
	ensure 	
	  	commitCur.close	 
	end  	
  
  end
  
end
