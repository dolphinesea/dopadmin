<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <title>接入码配置</title>
 <%@ include file="/commons/taglibs.jsp"%>
 <%@ include file="/commons/meta.jsp"%>
 
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/slick.grid.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/smoothness/jquery-ui-1.8.16.custom.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/examples.css" />
 
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
 <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
 <script src="${ctxPath}/scripts/thickbox.js"></script>
 
 <style type="text/css">
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
</style>

</head>

<body>
	<div class="topstyle">
		<jsp:include page="../../../commons/top.jsp" />
	</div>
    <h2 class="title1">呼叫接入码配置</h2>
    <table class="operatortable">
    	<tr>
    		<td>
    			<div class="content-container">
    				<p class="button-container">
        				<a class="button create"  href="javascript:self.parent.tb_show('新建接入码配置','${ctxPath}/system/toAddAccessCode.do?keepThis=true&TB_iframe=true&height=300&width=510')">新建</a>
						<a class="button refresh" href="javascript:" onclick="window.location.reload(true)">刷新</a>
						<a id="delrecord" class="button delete" href="javascript:">删除</a>
						<a id="uprecord" class="button edit" href="javascript:">更新</a>
    				</p>
    			</div>
    		</td>
    	</tr>
		<tr>
			<td valign="top" align="center">
				<div id="myGrid" style="width:1000px;height:450px;"></div>
			</td>
		</tr>
    </table>
    
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
	
<script type="text/javascript">
// 设置值
$("#code").val("${code}");
var grid;
var data = [];

var columns = [ {id : "code", name : "接入码",field : "code", width : 330, cssClass: "tablesytle"},
                {id : "name", name : "名称",field : "name", width : 330, cssClass: "tablesytle"}, 
                {id : "description",name : "描述",field : "description", width : 340, cssClass: "tablesytle"}				 
              ];

var options = {
    			editable: true,
    			enableAddRow: true,
    			enableCellNavigation: true,
    			enableColumnReorder: false,
    			asyncEditorLoading: false,
    			autoEdit: false
              };
              
$(document).ready(function(){
	$("#myGrid").offset({left: 72});
	$(".content-container").offset({left: $("#myGrid").offset().left});
	data = ${accessCodeList};
	grid = new Slick.Grid("#myGrid", data, columns, options);
	grid.setSelectionModel(new Slick.RowSelectionModel());
	grid.setActiveCell(0, 0);
	
    grid.onAddNewRow.subscribe(function (e, args) {
    	var item = args.item;
      	grid.invalidateRow(data.length);
      	data.push(item);
      	grid.updateRowCount();
      	grid.render();
    });

	$("#delrecord").click(function(){	
  		var dd = grid.getData();
  		var current_row = grid.getActiveCell().row;
		deleteConfirm(dd, current_row);
	});
			
	$("#uprecord").click(function(){
		var dd = grid.getData();
		var current_row = grid.getActiveCell().row;
		upCode(dd, current_row);
	});

	$(window).resize(function(){
		$("#myGrid").offset({left: 72});
		$(".content-container").offset({left: $("#myGrid").offset().left});
	});
    
});

function deleteConfirm(dd, current_row) {
	var deleteinfo = "确定删除接入码" + dd[current_row].code + "？";	
	if (confirm(deleteinfo))
	{
		var code = dd[current_row].code;
  		dd.splice(current_row,1);
  		var r = current_row;
  		while (r<dd.length){
    		grid.invalidateRow(r);
    		r++;
  		}
  		grid.updateRowCount();
  		grid.render();
  		grid.scrollRowIntoView(current_row-1);
  				
		var url = "system/delAccessCode/";
			url += code;
			url += ".do";
			$.get(url);
	}
				
}

function upCode(dd, current_row){
			var url = "system/getAccessCode/";
			url += dd[current_row].code;
			url += ".do?keepThis=true&TB_iframe=true&height=350&width=510";
			self.parent.tb_show('修改话务员帐号', url);
}

function accessCodeDo(type) {
if (type == "add") {
		self.parent.dialog('${ctxPath}/system/toAddAccessCode.do?keepThis=true&TB_iframe=true&height=300&width=510')
	} else if (type == "edit") {
		if(${empty code}){
			alert('请选择接入码');
			return;
		}
		self.parent.dialog('${ctxPath}/system/getAccessCode/${code}.do?keepThis=true&TB_iframe=true&height=300&width=510')
	} else if (type == "del") {
		if(${empty code}){
			alert('请选择接入码');
			return;
		}
		deleteConfirm('${ctxPath}/system/delAccessCode/${code}.do')
	} else if (type == "set") {
		if(${empty code}){
			alert('请选择接入码');
			return;
		}
		self.parent
				.dialog('${ctxPath}/system/toSetCallTypeIdentification/${code}.do?keepThis=true&TB_iframe=true&height=300&width=510')
	}

}

function callTypeDo(type) {
	if (type == "add") {
		self.parent
				.dialog('${ctxPath}/system/toAddCallType.do?keepThis=true&TB_iframe=true&height=250&width=510')
	} else if (type == "edit") {
		
		if($("#callType").val()==null){
			alert('请选择呼叫类型');
			return;
		}
		self.parent
				.dialog('${ctxPath}/system/getCallType/' + $("#callType").val() + '.do?keepThis=true&TB_iframe=true&height=250&width=510')
	} else if (type == "del") {
		if($("#callType").val()==null){
			alert('请选择呼叫类型');
			return;
		}
		deleteConfirm('${ctxPath}/system/delCallType/' + $("#callType").val() + '.do')
	}
}

function checkUserExist() {//如何post传参数
	var account = $("#account").val();
	if ($.trim(account) != "") {
		$.post("${ctxPath}/user/checkUserExist/" + account + ".do", {
			id : "add" //意思：没有不行
			}, function(result) {
				var res = result;
				if (res == "exist") {
					alert("This user already exists, replace it please!");
				}
			});

	}
}
</script>	
	
</body>

</html>
