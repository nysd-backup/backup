// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, vendor/assets/javascripts,
// or vendor/assets/javascripts of plugins, if any, can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// the compiled file.
//
// WARNING: THE FIRST BLANK LINE MARKS THE END OF WHAT'S TO BE PROCESSED, ANY BLANK LINE SHOULD
// GO AFTER THE REQUIRES BELOW.
//
var Connector = function () {
}

Connector.prototype = {
	get:function(url,callback,loader) {
		$.ajax({
				type: "GET",
				url: url,
				dataType: "json",
				async: true,
				statusCode: {
						404: function() {
							alert("url not found");
						},
						500: function() {
							alert("internal server error");
						}
				},
				beforeSend : function() {
					if(loader == undefined){
					$('div.preloader').show();
				}
				},
				complete :function() {
							$('div.preloader').hide();
				},
				error : function(req,status,trace) {

				},
				success : function(data){
					var json = eval(data);
					callback(json);
				}
		});
	},

}

var TableSorter = function() {
}

TableSorter.prototype = {

	enable : function(id) {
		$("#" + id).tablesorter(
					{
				theme : 'blue',
				widgets : ['zebra', 'filter']	,
				widgetOptions : {
							filter_childRows : false,
						filter_columnFilters : true,
						filter_cssFilter : 'tablesorter-filter',
						filter_filteredRow   : 'filtered',
						filter_formatter : null,
						filter_functions : null,
							filter_hideFilters : false, // true, (see note in the options section above)
					filter_ignoreCase : true,
						filter_liveSearch : true,
							filter_reset : 'button.reset',
						filter_searchDelay : 300,
							filter_serversideFiltering: false,
							filter_startsWith : false,
							filter_useParsedData : false
				}
			}
				);
	}

}