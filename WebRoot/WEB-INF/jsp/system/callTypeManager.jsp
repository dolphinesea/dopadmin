<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>DOP用户管理</title>

    <%@ include file="/commons/taglibs.jsp"%>
    <%@ include file="/commons/meta.jsp"%>
    
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/slick.grid.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/smoothness/jquery-ui-1.8.16.custom.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/examples.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
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
		<li id="createrecord" class="toolbutton" href="${ctxPath}/system/toAddCallType.do"><img src="images/icon/premium.png" style="width: 16px; height: 16px; position: relative; top: -2px; left: -4px;" />创建</li>
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

	// 设置值
	$("#code").val("${code}");
	var grid;
	var deleteinfo;
	var CTName;
	var AC;
	var data = [];

	var columns = [{id : "accessCode", name : "接入码", field : "accessCode", width : 100, cssClass : "tablesytle", sortable : true},
		  		   {id : "calltypename", name : "呼叫类型", field : "calltypename", width : 100,cssClass : "tablesytle"}];

	var options = {
		editable : true,
		enableAddRow : true,
		enableCellNavigation : true,
		enableColumnReorder : false,
		asyncEditorLoading : false,
		autoEdit : false,
		forceFitColumns: true
	};

	$(document).ready(function() {
		window.onresize = function() {
			resize_id("myGrid");
		}
		resize_id("myGrid");

		$(".content-container").offset({
			left : $("#myGrid").offset().left
		});
		data = ${callTypeIdentificationList};
		grid = new Slick.Grid("#myGrid", data, columns, options);
		gridSorter("accessCode", true, grid, data);
		grid.setSortColumn("accessCode", true);
		grid.setSelectionModel(new Slick.RowSelectionModel());
		grid.setActiveCell(0, 0);

		grid.onAddNewRow.subscribe(function(e, args) {
			var item = args.item;
			grid.invalidateRow(data.length);
			data.push(item);
			grid.updateRowCount();
			grid.render();
		});

		grid.onSort.subscribe(function(e, args) {
			gridSorter(args.sortCol.field, args.sortAsc, grid, data);
		});

		$("#createrecord").colorbox({
			opacity: 0,
			closeButton : false,
			innerWidth : "611px",
			innerHeight : "229px"
		});

		$("#delrecord").click(function() {
			var dd = grid.getData();
			var current_row = grid.getActiveCell().row;
			deleteConfirm(dd, current_row);
		});

		$("#uprecord").click(function() {
			var dd = grid.getData();
			var current_row = grid.getActiveCell().row;
			upCallType(dd, current_row);
		});

		$(window).resize(function() {
			grid.resizeCanvas();
			$(".content-container").offset({
				left : $("#myGrid").offset().left
			});
		});

	});

	function deleteConfirm(dd, current_row) {
		deleteinfo = "确定删除呼叫类型：" + dd[current_row].calltypename + ",接入码:"
				+ dd[current_row].accessCode + "的配置项？";
		CTName = dd[current_row].calltypename;
		AC = dd[current_row].accessCode;
		$("#delrecord").colorbox({
			opacity: 0,
			closeButton : false,
			innerWidth : "400px",
			innerHeight : "100px",
			href : "system/todelCallType.do"
		});
	}

	function upCallType(dd, current_row) {
		var url = "system/getCallType/";
		url += dd[current_row].accessCode;
		url += ".do";
		$("#uprecord").colorbox({opacity: 0, closeButton : false, innerWidth : "611px",innerHeight : "229px", href: url});
	}

	function create(name, accessCode) {
		var req = "name=" + name + "&accessCode=" + accessCode;
		$.ajax({
			url : "${ctxPath}/system/addCallType.do",
			type : "POST",
			dataType : "json",
			data : req,
			success : function(meg) {
				if (meg[0].ok == "1" && meg[0].failure == "0") {
					$.colorbox({
						opacity: 0,
						closeButton : false,
						innerWidth : "180px",
						innerHeight : "130px",
						html : dialogsuccess("操作成功！")
					});
					var item = {
						accessCode : accessCode,
						calltypename : name
					};
					var griddata = grid.getData();
					griddata.splice(0, 0, item);
					grid.setData(griddata);
					gridSorter("accessCode", true, grid, data);
					var index = getIndex(accessCode);
					grid.setActiveCell(index, 0);
					//grid.render();
					//grid.scrollRowIntoView(0, false);
				} else {
					$.colorbox({
						opacity: 0,
						closeButton : false,
						html : dialogerror("操作失败！", meg[0].cause)
					});
				}

			}
		});
	}

	function update(new_name, new_accessCode, or_name, or_accessCode) {
		var req = "new_name=" + new_name + "&new_accessCode=" + new_accessCode
				+ "&or_name=" + or_name + "&or_accessCode=" + or_accessCode;
		$.ajax({
			url : "${ctxPath}/system/updateCallType.do",
			type : "POST",
			dataType : "json",
			data : req,
			success : function(meg) {
				if (meg[0].ok == "1" && meg[0].failure == "0") {
					$.colorbox({
						opacity: 0,
						closeButton : false,
						innerWidth : "180px",
						innerHeight : "130px",
						html : dialogsuccess("操作成功！")
					});
					var item = {
						calltypename : new_name,
						accessCode : new_accessCode
					};
					var current_row = grid.getActiveCell().row;
					var griddata = grid.getData();

					griddata[current_row].calltypename = item.calltypename;
					griddata[current_row].accessCode = item.accessCode;

					grid.updateRow(current_row);
					grid.render();

				} else if (meg[0].ok == "1" && meg[0].failure == "1") {
					$.colorbox({
						opacity: 0,
						html : "<div>" + meg[0].cause + "</div>"
					});
				} else {
					$.colorbox({
						opacity: 0,
						closeButton : false,
						html : dialogerror("操作失败！", meg[0].cause)
					});
				}
			}
		});
	}

	function del() {
		var url = "system/delCallTypeIdentification.do";
		var req = "CallTypeName=" + CTName + "&accessCode=" + AC;
		$.ajax({
			url : url,
			type : "POST",
			dataType : "json",
			data : req,
			success : function(meg) {
				if (meg[0].ok == "1" && meg[0].failure == "0") {
					$.colorbox({
						opacity: 0,
						closeButton : false,
						innerWidth : "180px",
						innerHeight : "130px",
						html : dialogsuccess("操作成功！")
					});
					var current_row = grid.getActiveCell().row;
					var griddata = grid.getData();
					griddata.splice(current_row, 1);
					grid.invalidateAllRows();
					grid.updateRowCount();
					grid.render();
				} else {
					$.colorbox({
						opacity: 0,
						closeButton : false,
						html : dialogerror("操作失败！", meg[0].cause)
					});
				}
			}
		});
	}

	var gridSorter = function(columnField, isAsc, grid, gridData) {
		var sign = isAsc ? 1 : -1;
		var field = columnField;
		gridData.sort(function(dataRow1, dataRow2) {
			var value1 = dataRow1[field], value2 = dataRow2[field];
			var result = (value1 == value2) ? 0 : ((value1 > value2 ? 1 : -1))
					* sign;
			return result;
		});
		grid.invalidate();
		grid.render();
	}

	function getIndex(accessCode) {
		var index = 0;
		while (data[index].accessCode != accessCode) {
			index++;
		}

		return index;
	}
</script>
</body>
</html>
