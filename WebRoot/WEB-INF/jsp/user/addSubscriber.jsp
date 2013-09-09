<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="${pageContext.request.contextPath}/scripts/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="styles/dialogsPage.css" />

<script type="text/javascript">
$(document).ready(function(){
	changeUpdateModel();
	
	addValidate();
	
	$("#button1").click(function(){
		add_update();
	});
	
});

function dndtoString(dnd){return dnd == "true" ? "是" : "否"}

function changeUpdateModel() {
	if ("${requestScope.edit}" == "edit") {
		$(".dialogtitle span").text("修改话务员账号");
		$('#nb').val("${requestScope.user.number}");
		$('#gdzd').val("${requestScope.user.linetype}");
		$('#mdr').val(dndtoString("${requestScope.user.dnd}"));
		$('#cp').val("${requestScope.user.call_priority}");
		$('#desc').val("${requestScope.user.description}");
		$('#button1').text("修改");
		$('#button2').text("返回");
		$("input[name=nb]").attr("readonly", "readonly")//将input元素设置为readonly
	}
}

var res = false;
function addValidate() {

	// 联系电话(手机/电话皆可)验证   
	jQuery.validator.addMethod("isPhone", function(value, element) {
		var length = value.length;
		//var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
			var tel = /^\d{3,19}$/;
			return this.optional(element) || (tel.test(value));

		}, "请正确填写3到19位的数字");

	jQuery.validator.addMethod("checkExist", function(value, element) {
		if ($('#edit').val() == "edit") {
			return true;
		}
		var number = $("#nb").val();
		if ($.trim(number) != "") {
			$.post("${pageContext.request.contextPath}/user/checkSubscriberExist/" + number + ".do", {
				id : "add" //意思：没有不行
				}, function(result) {

					if (result == "exist") {
						res = false;
					} else {
						res = true;
					}
				});
			return res;
		} else {
			return false;
		}
	}, "号码已存在");
if ("${requestScope.edit}" == "edit") {
	// validate signup form on keyup and submit
$("#form1").validate({
		rules: {
			nb: { isPhone:true,
					
				required: true,
			},
		},
		messages: {
			nb: {
				required: "*号码必须填写"
			},
		}
	});
	}
else
	{
	$("#form1").validate({
		rules: {
			nb: { 
				isPhone:true,
				remote:"${pageContext.request.contextPath}/user/isExistSubscriber.do",
				required: true,
			},
		},
		messages: {
			nb: {
				remote:"*号码已存在",
				required: "*号码必须填写"
			},
		}
	});
	}
}

function add_update(){
	if ("${requestScope.edit}" == "edit"){
		self.parent.updateUser(trim($("#nb").val()), $("#mdr").val(), $("#cp").val(), $("#desc").val(), $("#gdzd").val());
	}
	else{
		
		self.parent.createUser(trim($("#nb").val()), $("#mdr").val(), $("#cp").val(), $("#desc").val(), $("#gdzd").val());
	}
}

function trim(str){
	return str.replace(/^ +/, "").replace(/ +$/, "");
}
</script>

<style type="text/css">

#cp,#mdr,#gdzd{
	width: 241px;
	font:normal 0.9em "verdana", 微软雅黑;
}

#desc{
	width: 240px;
	height: 100px;
	resize: none;
	font:normal 1em "verdana", 微软雅黑;
}

.desc-span{
	position: relative;
	top: -80px;
}

</style>

</head>
<body>
<form id="form1">
	<div class="dialog" style="width: 620px;">
    <ul>
    	<li class="dialogtitle"><img src="images/icon/user.png" style="position:relative;top: -8px;left: 150px; -moz-user-select: none;"/><span style="width: 200px;position:relative;top: -12px;left: 155px; font-size: 1.5em;">创建普通用户</span></li>
        <li><span>号码 ：</span> <input type="text" name="nb" id="nb" autocomplete="off" /></li>
        <li>
        	<span>用户类别:</span>
        	<select name="gdzd" id="gdzd">
        		<option value="0">自动</option>
        		<option value="1">供电</option>
        		<option value="2">网关</option>
        	</select>
        </li>
        <li>
        	<span>免打扰:</span>
        	<select name="mdr" id="mdr">
        		<option>是</option>
        		<option>否</option>
        	</select>
        </li>
        <li>
        	<span>用户级别 ：</span>
			<select name="cp" id="cp">
					<c:forEach items="${cpList}" var="cpList">
						<OPTION VALUE="${cpList.priority}">
							${cpList.name}
						</option>
					</c:forEach>
			</select>
        </li>
        <li>
        	<span class="desc-span">描述：</span>
        	<textarea  name="desc" id="desc"> </textarea>
        </li>
    </ul>
		
    <ul class="buttons">
        <div>
            <span id="button1" class="button">添加</span>
            <span id="button2" class="button" onclick="parent.jQuery.colorbox.close();">返回</span>
        </div>
    </ul>
	</div>
	<input type="hidden" value="${edit}" id="edit" />
</form>
</body>
</html>