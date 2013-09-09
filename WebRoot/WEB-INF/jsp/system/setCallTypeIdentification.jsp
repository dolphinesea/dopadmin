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
	if ("${finish}" == "finish") {
		alert('操作完成');

		self.parent.reloadFrame();
		self.parent.tb_remove();
	}
	addValidate();
});
var res = false;
function addValidate() {
	/* 设置默认属性 */
	$.validator.setDefaults( {
		submitHandler : function(form) {
			form.submit();
		}
	});

	jQuery.validator.addMethod("checkExist", function(value, element) {
		var callingClass = $("#callingClass").val();
		var accessCode = $("#accessCode").val();
		if ($.trim(accessCode) != "" && $.trim(callingClass) != "") {
			$.post("${ctxPath}/system/checkCallTypeIdentificationExist/"
					+ callingClass + "/" + accessCode + ".do", {
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
	}, "该项设置已存在");

	// validate signup form on keyup and submit
$("#form").validate({
		rules: {
			callingClass: { 
					checkExist:true,
			},
			callType: {
				required: true,
			},
		},
		messages: {
			callType: {
				required: "*呼叫类型必须选",
			}
		}
	});
	
}
</script>
	</head>
	<body style="background: none;">
		<div id="container" style="margin-top: 0px;">

			<div id="content"
				style="margin-top: 0px; width: 500px; padding: 0px;">

				<div id="box">

					<form id="form"
						action="${ctxPath}/system/setCallTypeIdentification.do"
						method="post" onsubmit="return addValidate()">
						<fieldset id="personal">
							<legend>
								接入码
							</legend>
							<p>
								<label for="accessCode">
									接入码 :
								</label>
								<input name="accessCode" id="accessCode" tabindex="1"
									readonly="readonly" value="${code}" />
							</p>
							<p>
								<label for="code">
									呼叫类别 :
								</label>
								<SELECT name="callingClass" id="callingClass">
									<option VALUE="0">
										保留
									</option>
									<option VALUE="1">
										本地通话
									</option>
									<option VALUE="2">
										国内长途
									</option>
									<option VALUE="3">
										国际长途
									</option>
								</SELECT>
							</p>
							<p>
								<label for="callType">
									呼叫类型 :
								</label>
								<SELECT MULTIPLE SIZE="5" name="callType" id="callType">
									<c:forEach items="${callTypeList}" var="callTypeList">
										<OPTION VALUE="${callTypeList.id}">
											${callTypeList.name}-${callTypeList.description}
										</option>
									</c:forEach>
								</SELECT>
							</p>
						</fieldset>
						<div align="center">
							<input id="button1" type="submit" value="添加" />
							<input id="button2" type="reset" value="返回"
								onclick="self.parent.tb_remove();" />
						</div>
					</form>
				</div>
			</div>
		</div>

	</body>
</html>
