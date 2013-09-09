<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>添加操作员</title>
  <%@ include file="/commons/taglibs.jsp"%>
  <%@ include file="/commons/meta.jsp"%>

<script type="text/javascript">

$(document).ready(function() {
	$("#TB_Title",window.parent.document).css({"background-image":"url(./images/icon/settings.png)"});
	$("#TB_ajaxWindowTitle",window.parent.document).css({"margin-top":"10px"});
	if ("${finish}" == "finish") {
		//alert('操作完成');

		//self.parent.reloadFrame();
		self.parent.tb_remove();
		window.parent.location.reload();
	}
	addValidate();
	
	
});

function changeUpdateModel() {
	if ("${requestScope.edit}" == "edit") {
		$("#form").attr("action", "${ctxPath}/system/updateAccessCode.do");//改变action
		$('#code').val("${accessCode.code}");
		$('#name').val("${accessCode.name}");
		$('#description').val("${accessCode.description}");
		$('#button1').val("修改");
		$('#button2').val("返回");
		$('#id').attr("name", "id");//增加一个属性
		//$("input[type='reset']").attr('type', 'button');//改变一个属性，但获取方式不一
		//$('#account').unbind("onblur",checkOperatorExist); //更新模式时移除onblur事件(失败)
		//$('#account').attr("readonly", "readonly");//将account文本框改为只读状态(readonly不能设置值吗?)
		$("input[name=code]").attr("readonly", "readonly")//将input元素设置为readonly
		//$('input[id=account]').removeAttr("readonly");//去除input元素的readonly属性
		//document.getElementById("account").readonly="readonly";

		$('input[type=reset]').each(function() {
			this.type = 'button'
		});//修改一个属性(一定放于最后)
	}
}
window.onload = changeUpdateModel;

var resCode = false;
var resName = false;
function addValidate() {
	/* 设置默认属性 */
	$.validator.setDefaults( {
		submitHandler : function(form) {
			form.submit();
		}
	});

	// 接入码验证   
	jQuery.validator.addMethod("isCode", function(value, element) {
		var length = value.length;
		//var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
			var tel = /^\d{3,8}$/;
			return this.optional(element) || (tel.test(value));

		}, "请正确填写3到8位的接入码");

if ("${requestScope.edit}" == "edit") {
		$("#form").validate({
		rules: {
			code: { isCode:true,
					
				required: true,
				maxlength: 20
			},
			name: {
				
				required: true,
			},
		},
		messages: {
			code: {
				required: "*接入码必须填写",
				maxlength:"长度必须小于20",
				
			},
			name: {
				required: "*名称必须填写",
				
			}
		}
	});
	} else {
		$("#form").validate({
		rules: {
			code: { isCode:true,
					remote:"${ctxPath}/system/isExistAccessCode.do",
				required: true,
				maxlength: 20
			},
			name: {
				remote:"${ctxPath}/system/isExistAccessCode.do",
				required: true,
			},
		},
		messages: {
			code: {
				required: "*接入码必须填写",
				maxlength:"长度必须小于20",
				remote:"接入码已经存在"
			},
			name: {
				required: "*名称必须填写",
				remote:"名称已经存在"
			}
		}
	});
	}
	// validate signup form on keyup and submit

	
}

function GetRemoteInfo(postUrl, data) {  
	
	alert(postUrl)
     var remote = {  

        type: "post",  

         async: false,  

        url: postUrl,  

         dataType: "json",  

         data: data,  

         dataFilter: function(dataXML) {  

    	 	alert(dataXML);
             var result = new Object();  

             result.Result = jQuery(dataXML).find("Result").text();  

             result.Msg = jQuery(dataXML).find("Msg").text();  

             if (result.Result == "-1") {  

                result.Result = false;  

                 return result;  

             }  

            else {  

                 result.Result = result.Result == "1" ? true : false;  

                return result;  

            }  

        }  

     };  

    return remote;  

 } 
 
function add_update(){
	$("#form").submit();
}
window.onunload = function (){
	self.parent.tb_remove();
}

</script>

<link rel="stylesheet" href="./styles/modify.css" type="text/css"></link>
<script type="text/javascript" src="./scripts/updatethickboxstyle.js"></script>
</head>

<body>
 <form id="form" action="${ctxPath}/system/addAccessCode.do" method="post">
	<ul>
        <li> <labelbefore for="code">接入码 :</labelbefore> <input name="code" id="code" tabindex="1" type="text"/></li>
        <li> <labelbefore for="name">名称 :</labelbefore> <input name="name" id="name" tabindex="2" type="text"/></li>
		<li> <labelbefore for="description">描述 :</labelbefore>
			<textarea name="description" id="description" tabindex="3"></textarea>
		</li>
		<li> <button id="button1" type="button" onclick="add_update()" >
											${edit != null ?'修改':'增加'}
											</button>
			 <button id="button2" type="reset" onclick="self.parent.tb_remove();" >返回</button>
			 <input type="hidden" value="${edit}" id="edit" />
		</li>
    </ul>		
 </form>


</body>


</html>
