<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>DOP用户管理</title>
  <%@ include file="/commons/taglibs.jsp"%>
  <%@ include file="/commons/meta.jsp"%>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  
  	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/slick.grid.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/smoothness/jquery-ui-1.8.16.custom.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/examples.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/toolbar.css" />
	
	<script src="${ctxPath}/scripts/jquery-1.7.js"></script>
	<script src="${ctxPath}/scripts/jquery.colorbox.js"></script>
	<script src="${ctxPath}/scripts/jquery.upload-1.0.2.js"></script>
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
		border: solid 1px gray;
		overflow: auto;
		margin-top: 3px;
	}
	
	input[type=text]{
		font:normal 1em "verdana", 微软雅黑;
	}
	
	#call_priority{
		width: 90px;
		height: 25px;
		font:normal 0.9em "verdana", 微软雅黑;
	}
	
	.toolbutton span{
		position: relative;
		top: 2px;
		left: -5px;
	}
	
</style>
</head>
<body>
		<div class="topstyle">
			<jsp:include page="../../../commons/top.jsp" />
		</div>
		
		<ul class="toolbar">
			<li id= "create" class="toolbutton" href="${ctxPath}/user/toAddSubscriber.do" style="width:100px;"><img src="images/icon/premium.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -10px;" /><span>创建</span></li>
			<li id="exportExcel" class="toolbutton" href="javascript:" style="width:100px;"><img src="images/icon/statistics.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -10px;"/><span>导出表</span></li>
			<li id="refresh" class="toolbutton" href="javascript:" style="width:100px;"><img src="images/icon/refresh.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -10px;"/><span>刷新</span></li>
			<li id="delrecord" class="toolbutton" href="javascript:" style="width:100px;"><img src="images/icon/delete.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -10px;"/><span>删除</span></li>
			<li id="uprecord" class="toolbutton" href="" style="width:100px;"><img src="images/icon/edit.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -10px;"/><span>修改</span></li>
			<li id="printExcel" class="toolbutton" href="javascript:" style="width:100px;"><span>打印表</span></li>
			<li id="outputsize" class="toolbutton" href="${ctxPath}/user/toSetPageSizeForOutput.do" style="width:100px;"><img src="images/icon/settings.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -10px;"/><span>导出打印设置</span></li>
			<li id="query" class="toolbutton" style="width:100px;"><span>查询</span></li>
			<input type="file"name="file" id="importExcel" value="导入CSV文件" style="display: none;">
		</ul>
		
		<form id="form" method="POST" action="${ctxPath}/user/subscriberList.do">
			<ul id="searchform" class="toolbar form" style="top: 82px;">
    			<li style="margin-left: -20px;">
        			<span class="label">号码</span>
        			<input type="text" name="number" id="number" autocomplete="off" value="${subscriberJdbcQuery.number}" style="width: 80px"/>
    			</li>
    			<li style="margin-left: -20px;">
        			<span class="label">用户类别</span>
        			<select name="linetype" id="linetype"  style="width:60px;">
        				<option value="">全部</option>
        				<option value="0" 
        					<c:if test="${subscriberJdbcQuery.linetype == 0}">
						 		selected="selected"
							</c:if>	
        				>自动</option>
        				<option value="1"
 	 						<c:if test="${subscriberJdbcQuery.linetype == 1}">
						 		selected="selected"
						 	</c:if>       					
        				>供电</option>
        				<option value="2"
 	 						<c:if test="${subscriberJdbcQuery.linetype == 2}">
						 		selected="selected"
						 	</c:if>       					
        				>网关</option>
        			</select>
    			</li>
    			<li style="margin-left: -20px;">
       				<span class="label">用户级别</span>
        			<select name="call_priority" id="call_priority" style="width:80px;">
						<option value="-1">未选择</option>
						<c:forEach items="${cpList}" var="cpList">
							<option value="${cpList.priority}"
								<c:if test="${subscriberJdbcQuery.call_priority == cpList.priority}">
											selected="selected"
								</c:if>
							>${cpList.name}
							</option>
						</c:forEach>
        			</select>
    			</li>
    			<li style="margin-left: -20px;">
        			<span class="label">免打扰</span>
        			<select name="dnd" id="dnd" style="width:60px;">
						<option value="">全部</option>
        				<option value="true" 
        					<c:if test="${subscriberJdbcQuery.dnd == true}">
						 		selected="selected"
							</c:if>	
        				>是</option>
        				<option value="false"
 	 						<c:if test="${subscriberJdbcQuery.dnd == false}">
						 		selected="selected"
						 	</c:if>       					
        				>否</option>
        			</select>
    			</li>
    			<li style="margin-left: -20px;">
        			<span class="label">备注</span>
        			<input type="text" name="description" id="description" autocomplete="off" value="${subscriberJdbcQuery.description}" style="width: 10em;"/>
    			</li>
    			<li style="margin-left: -20px;">
    				<input id="searchButton" type="submit" value="确定" class="button" style="height: 27px; width: 50px;" />
    			</li>
			</ul>
		</form>
		
		<div id="myGrid" style="margin-top: -2px"></div>
		
		<script src="${ctxPath}/scripts/jquery.validate.min.js"></script>
		<script src="${ctxPath}/scripts/jquery-ui.min.js"></script>
		<script src="${ctxPath}/scripts/common.js"></script>
		<script src="${ctxPath}/scripts/jquery.event.drag-2.2.js"></script>
		<script src="${ctxPath}/scripts/jquery.json-2.3.min.js"></script>
		<script src="${ctxPath}/scripts/slick/slick.core.js"></script>		
		<script src="${ctxPath}/scripts/slick/plugins/slick.detailtooltips.js"></script>
		<script src="${ctxPath}/scripts/slick/plugins/slick.cellrangedecorator.js"></script>
		<script src="${ctxPath}/scripts/slick/plugins/slick.cellrangeselector.js"></script>
		<script src="${ctxPath}/scripts/slick/plugins/slick.cellselectionmodel.js"></script>
		<script src="${ctxPath}/scripts/slick/plugins/slick.rowselectionmodel.js"></script>
		<script src="${ctxPath}/scripts/slick/slick.formatters.js"></script>
		<script src="${ctxPath}/scripts/slick/slick.editors.js"></script>
		<script src="${ctxPath}/scripts/slick/slick.grid.js"></script>
		<script src="${ctxPath}/scripts/dialogwarning.js"></script>
			
		<script>
		var grid;
		var pagesize;
		var data = [];
		var deleteinfo;
		var usernumber;
		var sign, field; 
		

		var columns = [ {id : "serialnumber",name : "序号",field : "serialnumber", width : 40, cssClass: "tablesytle" },
						{id : "number",name : "用户号码",field : "number", width : 100, sortable: true, cssClass: "tablesytle" },
						{id : "linetype",name : "用户类别",field : "linetype", width : 95, sortable: true, cssClass: "tablesytle" }, 
                		{id : "call_priority_name",name : "用户级别",field : "call_priority_name", width : 80, sortable: true, cssClass: "tablesytle" },
                		{id : "dnd",name : "免打扰",field : "dnd", width : 65, sortable: true, cssClass: "tablesytle" },
                		{id : "description",name : "备注",field : "description", width : 400, sortable: true, cssClass: "tablesytle" }				 
              		  ];

		var options = {
    					editable: true,
    					enableAddRow: true,
    					enableCellNavigation: true,
    					enableColumnReorder: false,
    					forceFitColumns : true,
    					autoEdit: false
              		  };
              		  
        $(document).ready(function(){
        	window.onresize = function() { resize_id("myGrid"); }
			resize_id("myGrid");
			
			$("#query").click(function(){
				$("#searchform").toggleClass("slideout");
			});
			
			data = ${subscriberList};
			resetdndandlinetype();

			enableButton("searchButton",true);

			grid = new Slick.Grid("#myGrid", data, columns, options);
			gridSorter("number", true, grid, data);
			grid.setSortColumn("number", true);
			grid.setSelectionModel(new Slick.RowSelectionModel());
			grid.setActiveCell(0, 0);
		
			grid.onAddNewRow.subscribe(function (e, args) {
				var item = args.item;
    			grid.invalidateRow(data.length);
    			data.push(item);
    			grid.updateRowCount();
    			grid.render();
			});
			
    		grid.onSort.subscribe(function (e, args) {
 				gridSorter(args.sortCol.field, args.sortAsc, grid, data);
    		});
    		
    				
    		$("#create").colorbox({opacity: 0, closeButton: false, innerWidth: "632px", innerHeight: "398px"});
    		
    		$("#refresh").click(function(){
    			$("#form").submit();
    		});
			
			$("#delrecord").click(function(){	
  				var dd = grid.getData();
  				var current_row = grid.getActiveCell().row;
				deleteConfirm(dd, current_row);
			});
			
			$("#uprecord").click(function(){
				var dd = grid.getData();
				var current_row = grid.getActiveCell().row;
				upSubscriber(dd, current_row);
			});
			
			$("#outputsize").colorbox({opacity: 0, closeButton: false, innerWidth: "442px", innerHeight: "173px"});
			
			$(window).resize(function(){
				grid.resizeCanvas();
				$(".content-container").offset({left: $("#myGrid").offset().left});
			});
			
			$("#exportExcel").click(function() {
			
				var p_orderBy = (field == 'call_priority_name')?'call_priority':field;
				var p_orderDir = (sign == 1)?'asc':'desc';
				
				var req = $("#form").serialize();
				
				if(pagesize == null){
					req += "&p_pageSize=" + "&p_orderBy=" + p_orderBy + "&p_orderDir=" + p_orderDir;
				}
				else{
					req += "&p_pageSize=" + pagesize + "&p_orderBy=" + p_orderBy + "&p_orderDir=" + p_orderDir;
				}
				
				$.ajax({
					url: "${ctxPath}/user/exportSubscriberExcel.do",
					type: "POST",
					dataType: "json",
					data: req,
					success: function(meg){
						if(meg[0].ok == "1" && meg[0].failure == "0"){
							$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						}
						else{
							$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
						}
					}
				});

			});
			
			$("#printExcel").click(function() {
			
				var p_orderBy = (field == 'call_priority_name')?'call_priority':field;
				var p_orderDir = (sign == 1)?'asc':'desc';
				//var req = "number=" + $("#number").val() + "&name=" + $("#name").val() + "&linetype=" + $("#linetype").val() + ""
				var req = $("#form").serialize();
				
				if(pagesize == null){
					req += "&p_pageSize=" + "&p_orderBy=" + p_orderBy + "&p_orderDir=" + p_orderDir;
				}
				else{
					req += "&p_pageSize=" + pagesize + "&p_orderBy=" + p_orderBy + "&p_orderDir=" + p_orderDir;
				}
				
				$.ajax({
					url: "${ctxPath}/user/printSubscriberExcel.do",
					type: "POST",
					dataType: "json",
					data: req,
					success: function(meg){
						if(meg[0].ok == "1" && meg[0].failure == "0"){
							$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						}
						else{
							$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
						}
					}
				});

			});
			
			$("#importbt").click(function(){
				$("#importExcel").click();
			});
			
			$("#importExcel").change(function() {
				str=   this.value; 
				strs=str.toLowerCase(); 
				lens=strs.length; 
				extname=strs.substring(lens-4,lens);
				if(extname!= ".csv" && extname!= ".CSV") 
				{ 
					$.colorbox({opacity: 0, closeButton: false, innerWidth: "265px", innerHeight: "130px", html: dialogerrorNoCause("请选择合法CSV文件！")}) 
					return   (false); 
				}
				$(this).upload('${ctxPath}/user/importSubscriberExcel.do',
								function(res) {
									if(res!="ok")
									{
										$.colorbox({opacity: 0, closeButton: false, innerWidth: "300px", innerHeight: "250px", html: dialogerrorNoCause("操作失败！导入的csv文件格式有错误。")});
									}else{
										$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
									}
								}, 'html');
			});
			
			//addValidate();
        });     		  
        
        function deleteConfirm(dd, current_row) {	
        	deleteinfo = "确定删除用户号码为" + dd[current_row].number + "的电话用户吗？";	
			usernumber=  dd[current_row].number;
			$("#delrecord").colorbox({opacity: 0, closeButton: false, innerWidth: "340px", innerHeight: "100px", href: "user/todelSubscriber.do"});
		}
		
		function upSubscriber(dd, current_row){
				var url = "user/getSubscriber/";
				url += dd[current_row].number;
				url += ".do";
				$("#uprecord").colorbox({opacity: 0, closeButton: false, innerWidth: "632px", innerHeight: "398px", href: url});
		}

		function resetdndandlinetype(){
			for(var i = 0; i < data.length; i++){
				data[i].dnd = dndtoString(data[i].dnd);
				data[i].linetype = linetypetoString(data[i].linetype);
				data[i].serialnumber = i + 1;
			}
		}
		
		function dndtoString(dnd){
			return dnd == 1 ? "是" : "否";
		}
		
		function linetypetoString(linetype){
			return (linetype == 0) ? "自动" : (linetype == 1) ? "供电" : "网关";
		}
		
		function converttolinetype(linetype){
			return (linetype == "0") ? "自动" : (linetype == "1") ? "供电" : "网关";
		}
		
		var res = false;
		function addValidate() {
			/* 设置默认属性 */
			$.validator.setDefaults( {
				submitHandler : function(form) {
					enableButton('searchButton',false);
					form.submit();
				}
			});
			// 联系电话(手机/电话皆可)验证   
			jQuery.validator.addMethod("isPhone", function(value, element) {
				var length = value.length;
				//var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
				var tel = /^\d{1,19}$/;
				return this.optional(element) || (tel.test(value));

			}, "请正确填写1到19位的号码");

			// validate signup form on keyup and submit
			$("#form").validate({
				rules: {
					number: { isPhone:true,
				
					},

				},
				messages: {
					number: {

						maxlength:"长度必须小于20"
					},

				}
			});
	
		}
		
   		var gridSorter = function(columnField, isAsc, grid, gridData) {
   			var tmp;
       		sign = isAsc ? 1 : -1;
       		field = columnField;
       		gridData.sort(function (dataRow1, dataRow2) {
            	var value1 = dataRow1[field], value2 = dataRow2[field];
              	var result = (value1 == value2) ?  0 :
                         ((value1 > value2 ? 1 : -1)) * sign;         
                     
              	return result;
       		});
       		
       		for(var i = 0; i < data.length; i++){
				data[i].serialnumber = i + 1;
			}
       		grid.invalidate();
       		grid.render();
   		}
   		
   		function createUser(number, dnd, call_priority, description, linetype){
			var req = "number=" + number + "&dnd=" + dnd + "&call_priority=" + call_priority + "&description=" + description + "&linetype=" + linetype;
			$.ajax({
				url: "${ctxPath}/user/addSubscriber.do",
				type: "POST",
				dataType: "json",
				data: req,
				success: function(meg){
					if(meg[0].ok == "1" && meg[0].failure == "0"){
						$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						var item = {number: number, linetype: converttolinetype(linetype), call_priority_name: meg[0].cause, dnd: dndtoString(dnd), description: description};
						var griddata = grid.getData();
						griddata.splice(0, 0, item);
						grid.setData(griddata);
						gridSorter("number", true, grid, data);
						var index = getIndex(number);
						grid.setActiveCell(index, 0);
					}
					else{
						$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
					}
					
				}
			});
		}
		
		function updateUser(number, dnd, call_priority, description, linetype){
			var req = "number=" + number + "&dnd=" + dnd + "&call_priority=" + call_priority + "&description=" + description + "&linetype=" + linetype;
			$.ajax({
				url: "${ctxPath}/user/updateSubscriber.do",
				type: "POST",
				dataType: "json",
				data: req,
				success: function(meg){
					if(meg[0].ok == "1" && meg[0].failure == "0"){
						$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
						var item = {number: number, linetype: converttolinetype(linetype), call_priority_name: meg[0].cause, dnd: dnd, description: description};
						var current_row = grid.getActiveCell().row;
						var griddata = grid.getData();
		
						griddata[current_row].number = item.number;
						griddata[current_row].linetype = item.linetype;
						griddata[current_row].name = item.name;
						griddata[current_row].call_priority_name = item.call_priority_name;
						griddata[current_row].dnd = item.dnd;
						griddata[current_row].description = item.description;
						
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
			var url = "user/delSubscriber/" + usernumber + ".do";
			$.ajax({
				url: url,
				type: "POST",
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
		
		function getIndex(number){
   			var index = 0;
   			while(data[index].number != number){
   				index++;
   			}
   			
   			return index;
   		}
                	
    	</script>
	</body>
</html>
