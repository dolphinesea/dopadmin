<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>发布席间消息</title>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script src="${pageContext.request.contextPath}/scripts/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="styles/dialogsPage.css" />

<script type="text/javascript">
$(document).ready(function() {

	$("#release").click(function(){
		self.parent.addBbs($("#level").val(), $("#writer").val(), $("#body").val());
	});

	addValidate();
});

function addValidate() {

	// validate signup form on keyup and submit
$("#form").validate( {
		rules : {
			body : {
				maxlength : 140,
				required : true,
			}
		},
		messages : {
			body : {
				maxlength:"字数必须小于140个",
				required : "*内容必须填写"
			}
		}
	});

}
</script>
<style type="text/css">
	#body{
		width: 550px;
		height: 175px;
		resize: none;
		position: relative;
		left: -20px;
	}
	
	#release{
		position: relative;
		left: -20px;
	}
</style>
	</head>
	<body>
		<form id="form">
		<div class="dialog">
			<ul><li><textarea id="body" title="" name="body"></textarea></li></ul>
			<ul class="buttons">
				<div id="button2" class="button" style="margin-left: 14px; margin-right: 5px;" onclick="parent.jQuery.colorbox.close();">返回</div>
				<div id="release" class="button">发布</div>
			</ul>
			<input type="hidden" id="writer" name="writer" value="${sessionScope.user.account}" />
			<input type="hidden" id="level" name="level" value="${sessionScope.user.level}" />
		</div>
		</form>
	</body>

</html>
