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

var or_accessCode = "${callTypeIdentification.accessCode}";
var or_name = "${callTypeIdentification.calltypename}";

function changeUpdateModel() {
	if ("${requestScope.edit}" == "edit") {
		$(".dialogtitle span").text("修改呼叫类型配置");
		$('#accessCode').val("${callTypeIdentification.accessCode}");
		$('#name').val("${callTypeIdentification.calltypename}");	
		$('#button1').text("修改");
		$('#button2').text("返回");
		$("input[name=accessCode]").attr("readonly", "readonly")//将input元素设置为readonly
		$('input[type=reset]').each(function() {
			this.type = 'button'
		});//修改一个属性(一定放于最后)
	}
}

var res = false;
function addValidate() {

	jQuery.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	}, "只允许英文字母、数字");

	// validate signup form on keyup and submit

	if ("${requestScope.edit}" == "edit") {
	$("#form").validate( {
			rules : {
				name : {
					required : true,
					
				},
				
				accessCode : {
					required : true,
					
				},

				
			},
			messages : {
				name : {
					required : "*呼叫类型必须填写",
				},
				accessCode : {
					required : "*接入码必须选择",
					
				},
			}
		});
	}
	else
	{
		$("#form").validate( {
			rules : {
				name : {
					required : true,
					
				},
				
				accessCode : {
					required : true,
					remote:{
							url: "${pageContext.request.contextPath}/system/isExistCallTypeIdentification.do",
							type: "post",
							cache: false,
							dataType:"json",
							data: {
								accessCode : function() {
									return $("#accessCode").val();
								},						
							}
						},
					
				}
				
			},
			messages : {
				name : {
					required : "*呼叫类型必须填写"
				},
				accessCode : {
					required : "*接入码必须选择",
					remote: "*接入码已配置呼叫类型"
				}
			}
		});
	}
}

function add_update(){
	if ("${requestScope.edit}" == "edit"){
		self.parent.update(trim($("#name").val()), trim($("#accessCode").val()), or_name, or_accessCode);
	}
	else{
		self.parent.create(trim($("#name").val()), trim($("#accessCode").val()));
	}
}

function trim(str){
	return str.replace(/^ +/, "").replace(/ +$/, "");
}
</script>
	
</head>
<body>
	<form id="form">
		<div class="dialog" style="width: 600px;">
			<ul>
				<li class="dialogtitle"><img src="images/icon/settings.png" style="position:relative;top: -8px;left: 150px; -moz-user-select: none;"/><span style="width: 200px;position:relative;top: -12px;left: 155px; font-size: 1.5em;">创建呼叫类型配置</span></li>
				<li><span>接入码：</span> <input type="text" name="accessCode" id="accessCode"  autocomplete="off" /></li>
        		<li><span>呼叫类型：</span> <input type="text" name="name" id="name" autocomplete="off" /></li>
    		</ul>
    	    <ul class="buttons">
        		<div>
            		<span id="button1" class="button">添加</span>
            		<span id="button2" class="button" onclick="parent.jQuery.colorbox.close();">返回</span>
        		</div>
    		</ul>
		</div>
		<input type="hidden" value="${edit}" id="edit" />
		<input type="hidden" id="callType" name="callType" />
	</form>
</body>	
</html>
