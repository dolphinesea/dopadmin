<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="styles/dialogsPage.css" />

<script type="text/javascript">
$(document).ready(function(){
	filloperator();
	$("#account").change(function(){
		var value = $("#tmpaccount").val();
		
		if(value == null || value == ""){
			$("#tmpaccount").val($("select[name=account]").find("option:selected").text());
		}
		else{
			$("#tmpaccount").val(value + "," + $("select[name=account]").find("option:selected").text());
		}
	});
	
	$("#button1").click(function(){
		self.parent.$("input[name=operatorName]").val($("#tmpaccount").val());
		parent.jQuery.colorbox.close();
	});
});

function filloperator(){
	for(var i = 0; i < operators.length; i++) {
		$("#account").append("<option>"+ operators[i].account +"</option>");
	}
}

</script>

<style type="text/css">
#level{
	width: 241px;
	font:normal 0.9em "verdana", 微软雅黑;
}
</style>

</head>
<body>
<form id="form">
<div class="dialog" style="width: 400px;">
    <ul>
        <li>
        	<span>工号 :</span> 
        	<select name="account" id="account" style="width: 182px; margin-left: -50px; font-size: 1em; text-align:center;">
				<option value="">--未选择--</option>
			</select>
		</li>
		<li style="font-size: 1em;">
			<input type="text" name="tmpaccount" id="tmpaccount" />
		</li>
    </ul>

    <ul class="buttons">
        <div>
            <span id="button1" class="button">确定</span>
            <span id="button2" class="button" onclick="parent.jQuery.colorbox.close();">返回</span>
        </div>
    </ul>
</div>
</form>
</body>
</html>
