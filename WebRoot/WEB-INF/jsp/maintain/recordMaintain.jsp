<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>呼叫录音维护</title>

    <%@ include file="/commons/taglibs.jsp"%>
    <%@ include file="/commons/meta.jsp"%>

    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
    <script src="${ctxPath}/scripts/thickbox.js"></script>

    <style type="text/css">
        tr.over td {
            background: #D6E8F8;
            font-weight: bold;
        }
    </style>

    <script type="text/javascript">
$(document).ready(function() {
	addValidate();

	$('#startstartTime').datepicker( {
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy-mm-dd",
		time24h: true,
		        showTime: true
	});
	$('#endstartTime').datepicker( {
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy-mm-dd",
		time24h: true,
		showTime: true
	});

	$("#ta tr").mouseover(function() {
		$(this).addClass("over");
	});
	$("#ta tr").mouseout(function() {
		$(this).removeClass("over");
	});

	$("#ta tr:even").addClass("double");

	$("#chkAll").click(function() {
		var selectItem = $("input[name='chkRecord']")
		//alert(selectItem.length);

			for ( var i = 0; i < selectItem.length; i++) {
				selectItem[i].checked = true;
				if (selectItem[i].checked == true) {
					//alert(selectItem[i].value);
					//addmedia(selectItem[i].value);
				}
				if ($("#chkAll").attr("checked") == true) {
					selectItem[i].checked = true;

				} else {
					selectItem[i].checked = false;
				}
			}
		})
});
function viewDetail(url) {
	openFullScreenWindow(url, "");
}
function checkRecord(url) {
	openWindow(url, 500, screen.height, "no");
}

function deleteAllConfirm() {
	var all = "";
	var selectItem = $("input[name='chkRecord']")
	//alert(selectItem.length);
	for ( var i = 0; i < selectItem.length; i++) {
		if (selectItem[i].checked == true) {
			all = all + selectItem[i].value;

			if (i != selectItem.length - 1) {
				all = all + ",";
			}
		}
	}
	if (all == "") {
		alert("请选择要删除的录音!");
		return;
	}
	if (confirm('确认删除所选吗?')) {
		$("#id").val(all);
		$("#form").attr("action", "${ctxPath}/maintain/delRecord.do");//改变action
		form.submit();

		//location.href = "${ctxPath}/maintain/delRecord/" + all + ".do";
	}
}

function deleteConfirm(id) {
	if (confirm('确认删除所选吗?')) {
		$("#id").val(id);
		$("#form").attr("action", "${ctxPath}/maintain/delRecord.do");//改变action
		form.submit();
		//location.href = "${ctxPath}/maintain/delRecord/" + all + ".do";
	}
}

function deleteAllResult() {
	if (confirm('确认删除所选吗?')) {
		$("#form").attr("action", "${ctxPath}/maintain/delRecordResult.do");//改变action
		form.submit();
	}
}

function addValidate() {
	/* 设置默认属性 */
	$.validator.setDefaults( {
		submitHandler : function(form) {
			enableButton('searchButton',false);
			form.submit();
		}
	});
	jQuery.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	}, "只允许英文字母、数字、逗号");

	//自定义验证方法
	jQuery.validator.addMethod("endDate", function(value, element) {
		var startDate = $('#startstartTime').val();
		var endDate = $('#endstartTime').val();
		if (startDate == "" || endDate == "") {

			return true;
		}
		// return new Date(Date.parse(startDate.replace("-", "/"))) < = new Date(Date.parse(value.replace("-", "/")));
			return ((new Date(startDate.replace(/-/g, "\/"))) <= (new Date(
					endDate.replace(/-/g, "\/"))));
		}, "结束日期大于开始日期");

	// validate signup form on keyup and submit
$("#form").validate({
		rules: {
		operatorName: { stringCheck:true
			,maxlength: 20},
			startstartTime: {endDate:true					
			},
			endstartTime: {endDate:true	
			},
		},
		messages: {
			operatorName: {
				maxlength:"长度必须小于20"
			}
			,startstartTime: {
				
							
			},
			endstartTime: {
			},
		}
	});
	
}


    </script>
<link rel="stylesheet" href="./styles/searchlist.css" type="text/css"></link>
</head>

<body>
    <h2 class="title"> 呼叫录音维护 </h2>

    <div class="content-container">
    <form:form commandName="form" method="POST" >
    <form:errors path="*"></form:errors>
    <ul class="search">
    	<li> 
    		<label>操作员：</label>
    		<form:input path="operatorName" id="operatorName" maxlength="50" />
    	</li>
    	<li><label>时间段：</label>
    		<form:input path="startstartTime" id="startstartTime" />
    		- <form:input path="endstartTime" id="endstartTime" />
    	</li>
    	<span style="margin-left: 25px;margin-top:10px;">
    		<form:checkbox path="ischecked" id="ischecked" /> <label for="ischecked">已评估录音</label>
            <form:checkbox path="istypeone" id="istypeone" /><label for="istypeone">重要1</label>
            <form:checkbox path="istypetwo" id="istypetwo" /><label for="istypetwo">重要2</label>
    	</span>
    </ul>   
    	
       <div class="search-button">
       	 <input id="searchButton" disabled="disabled" type="submit" value="查询" width="150px" />
       </div>
        <input type="hidden" id="id" name="id">
    </form:form>

    <c:if test="${callRecordList == null}">
        <p> 没有符合条件的记录 </p>
    </c:if>

    <c:if test="${callRecordList != null}">
        <input type="button" id="deleteAll" name="deleteAll"
            onclick="deleteAllConfirm()" value="批量删除">

        <input id="deleteAllResult" type="button" value="删除所有查询到的录音"
            width="120px" onclick="deleteAllResult()" />

        <table class="table">
            <thead>
                <tr>
                    <th width="60">
                        <input type="checkbox" name="chkAll" id="chkAll" />
                        <a href="javascript:">全选</a>
                    </th>
                    <th width="33">
                        <a href="javascript:">操作</a>
                    </th>
                    <th title="按工号进行排序" valign="middle" width="70px">
                        <paging:hdivPagehead url="/maintain/getCallRecordList.do"
                            orderBy="sequenceNumber">话单号</paging:hdivPagehead>
                    </th>
                    <th title="按工号进行排序" valign="middle" width="70px">
                        <paging:hdivPagehead url="/maintain/getCallRecordList.do"
                            orderBy="operatorName">工号</paging:hdivPagehead>

                    </th>
                    <th title="按工号进行排序" valign="middle" width="70px">
                        <paging:hdivPagehead url="/maintain/getCallRecordList.do"
                            orderBy="type">类型</paging:hdivPagehead>

                    </th>
                    <th title="按被叫号码进行排序" valign="middle" width="160px">
                        <paging:hdivPagehead url="/maintain/getCallRecordList.do"
                            orderBy="recordstarttime">录音时间</paging:hdivPagehead>

                    </th>
                    <th title="按开始时间进行排序" valign="middle" width="70px">
                        <paging:hdivPagehead url="/maintain/getCallRecordList.do"
                            orderBy="recordlength">录音时长</paging:hdivPagehead>

                    </th>


                    <th title="按结束时间进行排序" valign="middle" width="80px">
                        <a href="javascript:">文件名</a>
                    </th>

                </tr>
            </thead>
            <tbody id="ta">
                <c:forEach var="callRecord" items="${callRecordList}"
                    varStatus="status">
                    <tr class="a-center">
                        <td>
                            <input type="checkbox" name="chkRecord" id="chkRecord"
                                value="${callRecord.recordid}" />
                        </td>
                        <td>

                            <a href="javascript:deleteConfirm('${callRecord.recordid}')"><img
                                    src="${ctxPath}/images/icons/user_delete.png" title="删除录音"
                                    width="16" height="16" /> </a>

                        </td>
                        <td>
                            ${callRecord.ticketid}
                        </td>
                        <td>
                            ${callRecord.operatorName}
                        </td>
                        <td>
                            ${callRecord.type eq 1?"重要1":callRecord.type eq 2?"重要2":""}
                        </td>
                        <td>
                            <fmt:formatDate value="${callRecord.recordstarttime}"
                                pattern="yyyy-MM-dd HH:MM:SS" />
                        </td>
                        <td>
                            ${callRecord.recordlength}
                         </td>
                         <td>
                         
                            ${callRecord.savefilename}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <paging:hdivPagebar url="/maintain/getCallRecordList.do" />
    </c:if>
    <script>
                enableButton("searchButton",true);
    </script>
</body>
</html>
