require 'net/http'
# coding: utf-8
class DeployController < ApplicationController

  # GET /deploy
  # GET /deploy.json
  def index
   	
   	#全てのバージョン
   	verList =  Version.allVersions
	@verList = verList
	
	@envList = Environment.all
	@versionPerEnv = Hash.new
	
	@envList.each { |e|
		envid = e.env_id
		v = DeployedVersion.versionByEnvid(envid)
		@versionPerEnv.store(envid,v)
	}
    respond_to do |format|
      format.html # index.html.erb
    end
  end

  
  # GET /deploy/check/:host/:port
  def healthcheck
  	host = params[:host].gsub('_','.')
  	port = params[:port]
  	url = params[:url]
  
  	puts host + ":" + port
  	
  	@result = Hash.new
  	@result.store( 'PNG', '/red.png')
  	
  	begin	
	  	client = Net::HTTP.new(host,port)
	  	client.open_timeout = 1
	    client.read_timeout = 2
		result = client.get(url).body
	 	if result.include?('HEALTH_CHECK_RESULT=0') then
			@result.store( 'css', 'success')
		else
			@result.store( 'css', 'failure')
		end	
  	rescue
			@result.store( 'css', 'failure')
	end 
	
	respond_to do |format|
	      	format.json { render json: @result } 
	end	
	 
  end
  
   # GET /deploy/deployTo
  # GET /deploy/deployTo/:version/:targetJob.json
  def deploy
   	
   	version = params[:version].gsub('_','.')
   	targetJob = params[:targetJob]
   	
  	latestversion = null	#TODO 
	latestversion = latestversion.chomp.strip
	latestversion = '1.0.' + latestversion
	
	hash = Hash.new
	
	if latestversion != version then
		hash.store('LATEST',latestversion);	
	else 	
		client = Net::HTTP.new('10.23.25.2','8080')
   		url = '/jenkins/job/' + targetJob + '/buildWithParameters?BUILD_VERSION=' + version
	   	puts client.get(url).body
		hash.store('URL','http://10.23.25.2:8080/jenkins/job/' + targetJob)			
	end

    respond_to do |format|
      format.html # deploy.html.erb
      format.json { render json: hash }
    end
  end
 
end
