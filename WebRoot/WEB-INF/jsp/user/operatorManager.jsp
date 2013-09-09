<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>DOP话务员管理</title>

<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/slick.grid.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/smoothness/jquery-ui-1.8.16.custom.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/examples.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/toolbar.css" />

<script src="${ctxPath}/scripts/jquery-1.7.js"></script>
<script src="${ctxPath}/scripts/jquery.colorbox.js"></script>
<script src="${ctxPath}/scripts/autosize.js"></script>


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

#myGrid{
    overflow: auto;
}

</style>

</head>
	<body>
		<div class="topstyle">
			<jsp:include page="../../../commons/top.jsp" />
		</div>
		
		<ul class="toolbar">
			<li id="createrecord" class="toolbutton" href="user/toAddOperator.do"><img src="images/icon/premium.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;" />创建</li>
			<li class="toolbutton" href="javascript:" onclick="window.location.reload(true)"><img src="images/icon/refresh.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;"/>刷新</li>
			<li id="delrecord" class="toolbutton" href=""><img src="images/icon/delete.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;"/>删除</li>
			<li id="uprecord" class="toolbutton" href=""><img src="images/icon/edit.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;"/>修改</li>
		</ul>

		<div id="myGrid"></div>
		
	 
		<script src="${ctxPath}/scripts/jquery.event.drag-2.2.js"></script>
		<script src="${ctxPath}/scripts/jquery.json-2.3.min.js"></script>
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
		var admin = "${sessionScope.user.account}";
		var grid;
		var deleteinfo;
		var id;
		var data = [];
		var fsapi;

		var columns = [ {id : "account", name : "工号", field : "account", width: 300, cssClass: "tablesytle", sortable: true},
                		{id : "name", name : "姓名", field : "name", width: 400, cssClass: "tablesytle"}, 
                		{id : "level", name : "级别", field : "level", width: 300, cssClass: "tablesytle", sortable: true}				 
              		  ];

		var options = {
						editable: true,
    					enableAddRow: true,
    					enableCellNavigation: true,
    					enableColumnReorder: false,
    					asyncEditorLoading: false,
    					autoEdit: false,
    					forceFitColumns: true,
               		  };
               		  
        window.onblur=function (){self.focus();}	
               
		$(document).ready(function(){
			resize_id("myGrid");
			
			data = ${operatorlist};
			for(var i = 0; i < data.length; i++) {
				data[i].level = levelToString(data[i].level);
			}
			grid = new Slick.Grid("#myGrid", data, columns, options);
			gridSorter("account", true, grid, data);
			grid.setSortColumn("account", true);
			grid.setSelectionModel(new Slick.RowSelectionModel());
			grid.setActiveCell(0, 0);
    		grid.onSort.subscribe(function (e, args) {
 				gridSorter(args.sortCol.field, args.sortAsc, grid, data);
    		});
    		
    		$("#createrecord").colorbox({opacity: 0, closeButton: false, innerWidth: "611px", innerHeight: "338px"});
			
			$("#delrecord").click(function(){	
  				var dd = grid.getData();
  				var current_row = grid.getActiveCell().row;
				deleteConfirm(dd, current_row);
			});
			
			$("#uprecord").click(function(){
				var dd = grid.getData();
				var current_row = grid.getActiveCell().row;
				upUser(dd, current_row);	
			});
			
			$(window).resize(function(){
				resize_id("myGrid");
				grid.resizeCanvas();
			});
		});
		
    	
		function deleteConfirm(dd, current_row) {
			//alert(current_row + ": " + JSON.stringify(data[current_row]));
			if (admin == dd[current_row].account) {
				$.colorbox({opacity: 0, closeButton: false, innerWidth: "250px", innerHeight: "50px", html: dialogerrorNoCause("不可删除当前登入的账号！")});
			}
			else{
				deleteinfo = "确定删除工号为" + dd[current_row].account + "的账号吗？";
				id = dd[current_row].id;
				$("#delrecord").colorbox({opacity: 0, closeButton: false, innerWidth: "300px", innerHeight: "100px", href: "user/todelOperator.do"});
			}
		}
		
		function upUser(dd, current_row){
				var url = "${ctxPath}/user/getOperator/";
				url += dd[current_row].id;
				url += ".do";
				$("#uprecord").colorbox({opacity: 0, closeButton: false, innerWidth: "611px", innerHeight: "338px", href: url});
		}	
		
		function createUser(account, password, name, level, confirm_password){
			var req = "account=" + account + "&name=" + name + "&password=" + password + "&level=" + level + "&confirm_password=" + confirm_password;
			$.ajax({
				url: "${ctxPath}/user/addOperator.do",
				type: "POST",
				dataType: "json",
				data: req,
				success: function(meg){
					if(meg[0].ok == "1" && meg[0].failure == "0"){
						$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						var item = {account: account, name: name, level: levelToString(level), id: meg[0].id};
						var griddata = grid.getData();
						griddata.splice(0, 0, item);
						grid.setData(griddata);
						gridSorter("account", true, grid, data);
						var index = getIndex(meg[0].id);
						grid.setActiveCell(index, 0);
					}
					else{
						$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
					}
					
				}
			});
		}
		
		function updateUser(account, password, name, level, id, confirm_password){
			var req = "account=" + account + "&name=" + name + "&password=" + password + "&level=" + level + "&id=" + id + "&confirm_password=" + confirm_password;
			$.ajax({
				url: "${ctxPath}/user/updateOperator.do",
				type: "POST",
				dataType: "json",
				data: req,
				success: function(meg){
					if(meg[0].ok == "1" && meg[0].failure == "0"){
						$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						var item = {account: account, name: name, level: levelToString(level)};
						var current_row = grid.getActiveCell().row;
						var griddata = grid.getData();
		
						griddata[current_row].account = item.account;
						griddata[current_row].name = item.name;
						griddata[current_row].level = item.level;
						
						grid.updateRow(current_row);
						grid.render();
  						
					}
					else{
						$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
					}
				}
			});
		}
		
		
		function delUser(){
			var url = "user/delOperator/" + id + ".do";
			$.ajax({
				url: url,
				type: "GET",
				dataType: "json",
				success: function(meg){
					if(meg[0].ok == "1" && meg[0].failure == "0"){
						$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						var current_row = grid.getActiveCell().row;
						var griddata = grid.getData();
						griddata.splice(current_row,1);
						grid.invalidateAllRows();
						grid.updateRowCount();
  						grid.render();		
					}
					else{
						$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
					}
				}
			});
		}
		

		function levelToString(level) {
			return level == "1" ? "话务员" :
				   level == "2" ? "班长" :
				   level == "3" ? "管理员" : "未知";
		}
		function stringToLevel(str) {
			return str == "话务员" ? 1 : str == "班长" ? 2 : str == "管理员" ? 3 : 0;
		}

		var gridSorter = function(columnField, isAsc, grid, gridData) {
       			var sign = isAsc ? 1 : -1;
       			var field = columnField;
       			gridData.sort(function (dataRow1, dataRow2) {
            		var value1 = dataRow1[field], value2 = dataRow2[field];
            		if (columnField == "level") {
            			value1 = stringToLevel(value1);
            			value2 = stringToLevel(value2);
            		}
              		return (value1 > value2 ? 1 : value1 < value2 ? -1 : 0) * sign; 
       			});
       			grid.invalidate();
       			grid.render();
   		}
   		
   		
   		function getIndex(id){
   			var index = 0;
   			while(data[index].id != id){
   				index++;
   			}
   			
   			return index;
   		}
   				
		</script>
	</body>
</html>


