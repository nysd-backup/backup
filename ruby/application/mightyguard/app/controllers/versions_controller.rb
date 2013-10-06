# coding: utf-8
class VersionsController < ApplicationController
	
  # GET /versions
  # GET /versions.json
  def index  	

	# get distinct sorted list
  	@versions = Version.allVersions 	  	

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @versions }
    end
  end
  
  	
  # GET /diff/erd
  # GET /diff/erd.json
  def show_erd  	

	# get distinct sorted list
  	@verLocalList = CreateDate.localVersions()
  	get_erd_diff(@verLocalList){ |current,previous|
  	#	@tableData = TableVersion.localDiffWith(current.version,previous.version)
  		@tableRoot = current
  	}
  	
  	@verExecList = CreateDate.execVersions()
  	get_erd_diff(@verExecList){ |current,previous|
  	#	@tableExecData = TableVersion.execDiffWith(current.version,previous.version)
  		@tableExecRoot = current
  	}
  
  	@verMartList = CreateDate.martVersions()
  	get_erd_diff(@verMartList){ |current,previous|
  	#	@tableMartData = TableVersion.martDiffWith(current.version,previous.version)
  		@tableMartRoot = current
  	}
	
    respond_to do |format|
      format.html # show_erd.html.erb
    end
  end

  # GET /versions/1
  # GET /versions/1.json
  def show
   	version = params[:id].gsub("_",".")
   	
   	#全てのバージョン
   	verList = Version.comparableVersions(version)
  	prevVer = verList[0].versionno		
   
   	#ヘッダ情報
   	@headerData = Version.modules(version)   		  	

	@verList = verList
	@prevVer = 	prevVer
	@currentVersion = params[:id]
	
    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @version }
    end
  end
  
  # GET /versions/diff/revisions/:cver/:dver.json
  def diff_revisions
   	currentVersion = params[:cver].gsub("_",".")
   	diffVersion = params[:dver].gsub("_",".")
   	
   	#コミットログ	  	
  	@commitData = CommitLog.diffWith(currentVersion,diffVersion)	
   	
   	respond_to do |format|
      format.json { render json: @commitData }
      format.csv  { send_data @commitData, type: 'text/csv; charset=utf-8', filename: "revision.csv" }
    end
  end 
  
   # GET /versions/diff/srcs/:cver/:dver.json
  def diff_srcs
   	currentVersion = params[:cver].gsub("_",".")
   	diffVersion = params[:dver].gsub("_",".")
   	
   	#Sources	  	
  	@srcData = ChangedSource.diffWith(currentVersion,diffVersion)
   	
   	respond_to do |format|
      format.json { render json: @srcData }
      format.csv  { send_data @srcData, type: 'text/csv; charset=utf-8', filename: "changed_sources.csv" } 
    end
  end 
  
  # GET /versions/diff/tables/:cver/:dver.json
  def diff_tables
   	currentVersion = params[:cver].gsub("_",".")
   	diffVersion = params[:dver].gsub("_",".")
   	
   	@tableData = TableVersion.localDiffWith(currentVersion,diffVersion)
   	
   	respond_to do |format|
      format.json { render json: @tableData }
      format.csv  { send_data @tableData, type: 'text/csv; charset=utf-8', filename: "local_table.csv" } 
    end
  end 
  
   # GET /versions/diff/tables/:cver/:dver.json
  def diff_tablesExec
   	currentVersion = params[:cver].gsub("_",".")
   	diffVersion = params[:dver].gsub("_",".")
   	
   	@tableExecData = TableVersion.execDiffWith(currentVersion,diffVersion)
   	
   	respond_to do |format|
      format.json { render json: @tableExecData }
      format.csv  { send_data @tableExecData, type: 'text/csv; charset=utf-8', filename: "exec_table.csv" } 
    end
  end 
  
   # GET /versions/diff/tables/:cver/:dver.json
  def diff_tablesMart
   	currentVersion = params[:cver].gsub("_",".")
   	diffVersion = params[:dver].gsub("_",".")
   	
   	@tableMartData = TableVersion.martDiffWith(currentVersion,diffVersion)
   	
   	respond_to do |format|
      format.json { render json: @tableMartData }
       format.csv  { send_data @tableMartData, type: 'text/csv; charset=utf-8', filename: "mart_table.csv" } 
    end
  end 
 
   
  # get erd diff
  def get_erd_diff( versionList ) 
  	if versionList == nil || versionList.size == 0 then 
  		yield(0,0)
  		return
  	end 
  	current = versionList[0]  	
  	if versionList.size > 1 then 			
	  	previous = versionList[1]	
  	elsif 
  		previous = current
  	end
  	versionList.shift
  	yield(current,previous)
  end

 
end
