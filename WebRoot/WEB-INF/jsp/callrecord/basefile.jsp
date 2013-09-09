<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>backup audio manger</title>
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
 
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/jquery-ui.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/slick.grid.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/smoothness/jquery-ui-1.8.16.custom.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/examples.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/toolbar.css" />
 
 <style type="text/css">
 
html,body{
	margin: 0;
    padding: 0;
	overflow: hidden;
}
 
 .operatortable{
	width:100%;
	cellspacing: 0;
	cellpadding: 0;
}
 
 .topstyle{
	background: url(${ctxPath}/images/texture/blue_grid.png);
    height: 50px;
	width: 100%;
	border-bottom: 1px solid orange;
	border-bottom: 2px solid #FB9500;
}

#RecordBox{
	display:none;
	width: 950px;
	height: 40px;
}

#Recordframe{
	position: relative;
	top:-19px;
	width: 100%;
	height: 550px;
	overflow:hidden;
	z-index:-1;
	
}

.popdivhead{
	background: url('${ctxPath}/images/texture/lightgray.png');
	height: 50px;
}

.popdivhead h3{
	text-align: left;
	text-indent:50px;
	font-size:32px;
	position: relative;
	top: 7px;
}

#titleicon{
	width: 40px;
	height: 40px;
	position: absolute;
	left: 5px;
	top: 7px;
}

#titleicon img{
	width: 32px;
	height: 32px;
}

#popclose{
	width: 40px;
	height: 40px;
	position: absolute;
	left: 900px;
	top: 7px;
}

#myGrid{
	border: solid 1px gray;
	overflow: auto;
}
 </style>
 </head>
 <body>
  	<div class="topstyle">
		<jsp:include page="../../../commons/top.jsp" />
  	</div>
  	<ul class="toolbar">
  		<li id="delrecord" class="toolbutton" href=""><img src="images/icon/delete.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;"/>删除</li>
  		<li class="toolbutton" href="javascript:" onclick="window.location.reload(true)"><img src="images/icon/refresh.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;"/>刷新</li>
  	</ul>
	<div id="myGrid"></div>
	<div id="RecordBox">
		<div class="popdivhead">
			<span id="titleicon"><img src="${ctxPath}/images/icon/playcurrent.png" /></span>
			<h3></h3>
			<a id="popclose" href="javascript:" ><img src="${ctxPath}/images/icon/logout.png" /></a>
		</div>
		<iframe id="Recordframe" name="Recordframe" src="" 
		marginWidth="0" marginHeight="0" frameBorder="0" ></iframe>
	</div>
	
	<script src="${ctxPath}/scripts/jquery-1.7.js"></script>
	<script src="${ctxPath}/scripts/autosize.js"></script>
	<script src="${ctxPath}/scripts/jquery.colorbox.js"></script>	
	<script src="${ctxPath}/scripts/jquery.validate.min.js"></script>
	<script src="${ctxPath}/scripts/jquery-ui.min.js"></script>
	<script src="${ctxPath}/scripts/common.js"></script>
	<script src="${ctxPath}/scripts/jquery.event.drag-2.2.js"></script>
	<script src="${ctxPath}/scripts/jquery.json-2.3.min.js"></script>
	<script src="${ctxPath}/scripts/jquery.blockUI.js"></script>
	<script src="${ctxPath}/scripts/slick/slick.core.js"></script>
	<script src="${ctxPath}/scripts/slick/plugins/slick.cellrangedecorator.js"></script>
	<script src="${ctxPath}/scripts/slick/plugins/slick.cellrangeselector.js"></script>
	<script src="${ctxPath}/scripts/slick/plugins/slick.cellselectionmodel.js"></script>
	<script src="${ctxPath}/scripts/slick/plugins/slick.rowselectionmodel.js"></script>
	<script src="${ctxPath}/scripts/slick/slick.formatters.js"></script>
	<script src="${ctxPath}/scripts/slick/slick.editors.js"></script>
	<script src="${ctxPath}/scripts/slick/slick.grid.js"></script>
	<script src="${ctxPath}/scripts/dialogwarning.js"></script>
	
	<script type="text/javascript">
		var grid;
		var data = [];
		var filecount = "${filecount}";
		var urls = ${urls};
		var dates = ${dates};
		var deleteinfo, dirname;

		var columns = [ {id : "BackupDate", name : "标签", field : "BackupDate", width : 100, sortable: true, cssClass: "tablesytle"},
                		{id : "BackupUrl", name : "", field : "BackupUrl", width : 100, formatter: formatter, cssClass: "tablesytle"} 			 
              		  ];
        
        var options = {
    					enableCellNavigation: true,
    					enableColumnReorder: false,
    					forceFitColumns : true,
    					autoWidth:true,
    					forceFitColumns: true
               		  };

        $(document).ready(function(){
        	window.onresize = function() { resize_id("myGrid"); }
			resize_id("myGrid");
			
			$(window).resize(function(){
				grid.resizeCanvas();
			});
        
        	if(urls == 0 && dates == 0 && filecount == 0){
        		//$("#myGrid").hide();
        		var errorinfo = "${errorinfo}";
        		$.colorbox({opacity: 0, closeButton: false, html: dialogerrorNoCause(errorinfo)});
        	}
        	else{
        		for(var i = 0; i < filecount; i++){
        			data[i] = {BackupDate: dates[i], BackupUrl: "<a id=\"RecordLink" + i + "\" href=\"javascript:\">查看</a>"};
        		}
        		
        		grid = new Slick.Grid("#myGrid", data, columns, options);
        		gridSorter("BackupDate", true, grid, data);
				grid.setSortColumn("BackupDate", true);
        		grid.setSelectionModel(new Slick.RowSelectionModel());
        		
        		grid.onSort.subscribe(function (e, args) {
 					gridSorter(args.sortCol.field, args.sortAsc, grid, data);
    			});
        	
        		grid.onActiveCellChanged.subscribe(function(e, args){
        			var IndexRow = args.grid.getActiveCell().row;
        			var dd = args.grid.getData();
        			var IndexData = parseInt(dd[IndexRow].BackupUrl.match(/\d+/));
        			var url = urls[IndexData];
        			$("#RecordLink" + IndexData).click(function(){
        				$("h3:first").text(dd[IndexRow].BackupDate);
        				$("#Recordframe").attr("src", url);
        				$.blockUI({
        					showOverlay: false,
        					message: $("#RecordBox"),
        					css:{
        							top: '50%',
        							left: '50%',
        							marginLeft: '-480px',
        							marginTop: '-300px',
        							width: '950px',
        							height: '600px'
        						}
        				});
        				$("#popclose").click($.unblockUI);
   
        			});
        		});
        	}
        	
        	$("#delrecord").click(function(){	
  				var dd = grid.getData();
  				var current_row = grid.getActiveCell().row;
				var IndexData = parseInt(dd[current_row].BackupUrl.match(/\d/));
				//urls.splice(IndexData, 1);
				deleteinfo = "确定删除时间为" + dd[current_row].BackupDate + "的备份吗？";
				dirname = dd[current_row].BackupDate;//getdirname(urls[IndexData]);
				$("#delrecord").colorbox({opacity: 0, closeButton: false, innerWidth: "400px", innerHeight: "100px", href: "backupRecord/todelbasefile.do"});
			});
        	
        });
        
        function getdirname(str){
        	var tmp1 = str.substr(str.length - 19);
        	var ret = tmp1.split("\.");
        	
        	return ret[0];
        }
        
        function delfile(){
        	var req = "dirname=" + dirname;
			$.ajax({
				url: "${ctxPath}/backupRecord/delbasefile.do",
				type: "GET",
				dataType: "json",
				data: req,
				success: function(meg){
					if(meg[0].ok == "1" && meg[0].failure == "0"){
						$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						var current_row = grid.getActiveCell().row;
						var griddata = grid.getData();
						griddata.splice(current_row,1);
						grid.invalidateAllRows();
  						grid.render();
					}
					else{
						$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
					}		
				}
			});
			
        }
        
        function formatter(row, cell, value, columnDef, dataContext) {
        	return value;
    	}
    	
    	var gridSorter = function(columnField, isAsc, grid, gridData) {
       		var sign = isAsc ? 1 : -1;
       		var field = columnField;
       		gridData.sort(function (dataRow1, dataRow2) {
            	var value1 = dataRow1[field], value2 = dataRow2[field];
              	var result = (value1 == value2) ?  0 :
                         ((value1 > value2 ? 1 : -1)) * sign;
              	return result;
       		});
       		
       		grid.invalidate();
       		grid.render();
   		}
   				  
	</script>
	
 </body>
</html>
