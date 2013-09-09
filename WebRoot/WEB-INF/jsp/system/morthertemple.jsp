<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>DOP话务管理平台母版</title>
	<%@ include file="/commons/taglibs.jsp"%>
	<%@ include file="/commons/meta.jsp"%>
	<script src="${ctxPath}/scripts/jquery.js"></script>
	<script src="${ctxPath}/scripts/thickbox.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
	
<script type="text/javascript">
var topparamter = window.dialogArguments;
var url, title, toptype;
$(document).ready(function(){
	$("#topFrame").height("80px");
	url = "${url}";
	title = "${title}";
	toptype = "${toptype}";
	$("#mainFrame").attr("src", url);
	
	switch(title)
	{
		case "hwy":
			document.title = "话务员账号";
			break;
		case "dhyh":
			document.title = "电话用户管理";
			break;
		case "hdgl":
			document.title = "话单管理";
			break;
		case "tjbb":
			document.title = "统计报表";
			break;
		case "xxbb":
			document.title = "详细报表";
			break;
		case "xjxx":
			document.title = "席间消息";
			break;
		case "jrmpz":
			document.title = "接入码配置";
			break;
		case "hjlxpz":
			document.title = "呼叫类型配置";
			break;
		case "bflygl":
			document.title = "录音文件的备份";
			break;
		default:
			document.title = "无标题";
	}
	
	function dialog(url) {
    	alert(url);

    	$("#remotehref").attr("href", url);
    	$("#remotehref").click();
	}
	
	function dialog(url, title) {

    	$("#remotehref").attr("href", url);
    	$("#remotehref").attr("title", title);

    	$("#remotehref").click();
	}

});


</script>
	
	<style type="text/css">
	body {
		margin: 0;
		padding: 0;
	}
	
	#topFrame {
		display: block;
		height:20px;
		width: 100%;
		overflow-y: hidden;
		border-bottom: 1px solid orange;
		border-bottom: 2px solid #FB9500;
		box-shadow: 2px 0 5px black;
		position: relative;
		z-index: 10;
	}
	
	#mainFrame {
	display: block;
	height: 100%;
	min-width: 400px;
	}
	
	</style>	
  </head>
  
  <body>
		<iframe id="topFrame" name="topFrame" src="commons/top.jsp" 
		marginWidth="0" marginHeight="0" frameBorder="0" scrolling="no"></iframe>
		
		<a id="remotehref" href="" class="thickbox" style="display: none;"></a>
		
		<iframe id="mainFrame" name="mainFrame" marginWidth="0" height="100%" width="100%" 
		marginHeight="0" src=""
		frameBorder="0"></iframe>
  </body>
</html>

