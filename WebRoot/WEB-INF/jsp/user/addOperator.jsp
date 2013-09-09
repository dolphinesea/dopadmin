<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script src="${pageContext.request.contextPath}/scripts/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jquery.setCursorPosition.js"></script>
<link rel="stylesheet" type="text/css" href="styles/dialogsPage.css" />

<script type="text/javascript">
$(document).ready(function(){
	changeUpdateModel();
	
	addValidate();
		
	$("#button1").click(function(){
		add_update();
	});
	
	$("#account").focusEnd();
	
});

function changeUpdateModel() {
	if ("${requestScope.edit}" == "edit") {
		//$(".dialogtitle img").attr("src", "images/icon/settings.png");
		$(".dialogtitle span").text("修改话务员账号");
		$('#account').val("${requestScope.user.account}");
		$('#password').val("${requestScope.user.password}");
		$('#confirm_password').val("${requestScope.user.password}");
		$('#name').val("${requestScope.user.name}");
		$('#level').val("${requestScope.user.level}");
		$('#button1').text("修改");
		$('#button2').text("返回");
		$('#id').attr("name", "id");//增加一个属性
		$("input[name=account]").attr("readonly", "readonly")//将input元素设置为readonly

		$('input[type=reset]').each(function() {
			this.type = 'button'
		});//修改一个属性(一定放于最后)
	}
}

var res = false;
function addValidate() {
	jQuery.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	}, "*只允许英文字母、数字");

if ("${requestScope.edit}" == "edit") {
	
		// validate signup form on keyup and submit
$("#form").validate({
		rules: {
			account: { stringCheck:true,
				required: true,
				maxlength: 20
			},
			password: { required: true },
			name: { required: true },
			confirm_password: {
				required: true,
				equalTo: "#password"
			}
		},
		messages: {
			account: {
				required: "*工号必须填写",
				maxlength:"*长度必须小于20"
			},
			password: {
				required: "*密码必须填写",
			},
			confirm_password: {
				required: "*密码必须填写",
				equalTo: "*两次输入的密码不一致"
			},
			name: {
				required: "*姓名必须填写",
			}
		}
	});
		
}
else{
	
  // validate signup form on keyup and submit
 $("#form").validate({
		rules: {
			account: { stringCheck:true,
					remote:"${pageContext.request.contextPath}/user/isExistOperator.do",
				required: true,
				maxlength: 20
			},
			password: {
				required: true,
			},
			name: {
				required: true,
			},
			confirm_password: {
				required: true,
				equalTo: "#password"
			}
		},
		messages: {
			account: {
				remote:"*工号已存在",
				required: "*工号必须填写",
				maxlength:"长度必须小于20"
			},
			password: {
				required: "*密码必须填写",
			},
			name: {
				required: "*姓名必须填写",
			},
			confirm_password: {
				required: "*密码必须填写",
				equalTo: "*两次输入的密码不一致"
			},
		}
	});
	}
}


function add_update(){
	if ("${requestScope.edit}" == "edit"){
		self.parent.updateUser(trim($("#account").val()),
								 $("#password").val(), trim($("#name").val()), $("#level").val(), $("#id").val(), $("#confirm_password").val());
	}
	else{
		self.parent.createUser(trim($("#account").val()),
								$("#password").val(), trim($("#name").val()), $("#level").val(), $("#confirm_password").val());
	}
}

function trim(str){
	return str.replace(/^ +/, "").replace(/ +$/, "");
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
<div class="dialog" style="width: 600px;">
    <ul>
    	<li><img src="images/icon/user.png" style="position:relative;top: -8px;left: 150px;"/><span style="width: 200px;position:relative;top: -12px;left: 155px; font-size: 1.5em;">创建话务员账号</span></li>
        <li><span>工号 :</span> <input type="text" name="account" id="account" autocomplete="off"/></li>
        <li><span>密码 :</span> <input name="password" id="password" type="password" autocomplete="off" /></li>
        <li><span>确认密码 :</span> <input name="confirm_password" id="confirm_password" type="password" autocomplete="off" /></li>
        <li><span>姓名 :</span> <input type="text" name="name" id="name" autocomplete="off" /></li>
        <li>
        	<span>级别 :</span>
        	<select name="level" id="level" tabindex="5">
				<option value="1">话务员</option>
				<option value="2">班长</option>
				<option value="3">管理员</option>
			</select>
        </li>
    </ul>

    <ul class="buttons">
        <div>
            <span id="button1" class="button">添加</span>
            <span id="button2" class="button" onclick="parent.jQuery.colorbox.close();">返回</span>
        </div>
    </ul>
</div>

<input id="id" type="hidden" value="${user.id}">
<input id="isAble" type="hidden" value="true">
<input type="hidden" value="${edit}" id="edit" />
</form>
</body>
</html>
