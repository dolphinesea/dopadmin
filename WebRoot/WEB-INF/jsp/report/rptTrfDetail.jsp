<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="/commons/taglibs.jsp"%>
    <%@ include file="/commons/meta.jsp"%>
	
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/toolbar.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />

    <style type="text/css">
        .table th,
        .table td {
            border: 1px solid gray;
        }
        .table thead {
            background: none;
            border-top: none;
        }
        .table tbody {
            border-bottom: none;
        }
        
		.topstyle{
			background: url(${ctxPath}/images/texture/blue_grid.png);
    		height: 50px;
			width: 100%;
			border-bottom: 1px solid orange;
			border-bottom: 2px solid #FB9500;
		}
		
    </style>
    
	<script src="${ctxPath}/scripts/dialogwarning.js"></script>
    <script src="${ctxPath}/scripts/jquery.colorbox.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />

    <script>
var operators = [];
var gonghao;
$(document).ready(function() {
	operators=${operatorList};
	gonghao = $("input[name=operatorName]").val();
	
	fillallOperator();
	
	if('${reporttooMax}'== '1')
		alert("查询结果超过500条,请确认查询条件");
		
	$("#exportExcel").click( function() {
										if ($.trim($("input[name=operatorName]").eq(0).val()).length == 0) {
											alert("工号不能为空");
											return;
										}
										
										var req = "operatorName=" + gonghao + "&startTime=" + $("input[name=startTime]").val() + "&endTime=" + $("input[name=endTime]").val() + "&exporttype=" + $("#exporttype").val();
										
										$.ajax({
											url: "${ctxPath}/report/exportReportExcel.do",
											type: "POST",
											dataType: "json",
											data: req,
											success: function(meg){
												if(meg[0].ok == "1" && meg[0].failure == "0"){
													$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
												}
												else{
													$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
												}
											}
										});
										/*document.forms[0].action = "${ctxPath}/report/exportReportExcel.do";
										document.forms[0].submit();
										document.forms[0].action = "${ctxPath}/report/getOperatorRecordDetail.do";
										$.colorbox({closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("导出成功！")});*/
									});
					addValidate();

					var tabs = $("#tabs").tabs();

					$('#startTime').datepicker( {
						changeMonth : true,
						changeYear : true,
						dateFormat : "yy-mm-dd"
					});
					$('#endTime').datepicker( {
						changeMonth : true,
						changeYear : true,
						dateFormat : "yy-mm-dd"
					});
					
	$("#setoperator").colorbox({opacity: 0, closeButton: false, innerWidth: "412px", innerHeight: "183px", href: "${ctxPath}/report/toAddgnum.do"});
					
});

function fillallOperator(){
	var str ="";
	for(var i = 0; i < operators.length; i++){
		(str == "")?str = operators[i].account : str += "," + operators[i].account;
	}
	
	$("input[name=operatorName]").val(str);
}

function addValidate() {
	/* 设置默认属性 */
	$.validator.setDefaults( {
		submitHandler : function(form) {
			form.submit();
		}
	});
	jQuery.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9,]+$/.test(value);
	}, "只允许英文字母、数字、逗号");

	//自定义验证方法
	jQuery.validator.addMethod("endDate", function(value, element) {
		var startDate = $('#startTime').val();
		var endDate = $('#endTime').val();
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
		operatorName: { 
			stringCheck:true
			},
			startTime: {
				date:true,
				endDate:true,				
			},
			endTime: {date:true,
				endDate:true,				
			},
		},
		messages: {
			startTime: {
				date:"输入正确日期 如:2011-01-01"
							
			},
			endTime: {
						date:"输入正确日期 如:2011-01-01"
			},
		}
	});
	
}
</script>
<link rel="stylesheet" href="./styles/searchlist.css" type="text/css"></link>
</head>

<body>
	<div class="topstyle">
		<jsp:include page="../../../commons/top.jsp" />
	</div>
	
    <form id="form" action="${ctxPath}/report/getOperatorRecordDetail.do" method="post">
    	<ul id="searchform" class="toolbar" style="margin-top: 3px;">
    		<li style="margin-left: 10px; margin-right: 10px; margin-top: -3px;">
    			<a id="setoperator" href="javascript:"><span class="label">工号</span></a>
    			<input type="text" name="operatorName" autocomplete="off" value="${reportJdbcQuery.operatorName}" />
    		</li>
    		<li style="margin-right: 10px; margin-top: -3px;">
    			<span class="label">开始时间</span>
    			<input type="text" name="startTime" id="startTime" autocomplete="off" value="${reportJdbcQuery.startTime}" />
    		</li>
    		<li style="margin-right: 10px; margin-top: -3px;">
    			<span class="label">结束时间</span>
    			<input type="text" name="endTime" id="endTime" autocomplete="off" value="${reportJdbcQuery.endTime}" />
    		</li>
    		<li style="margin-right: 10px; margin-top: -3px;">
    			<input id="searchButton" type="submit" value="确定" class="button" style="height: 27px; width: 100px;" />
    			<input type="hidden" name="exporttype" id="exporttype" value="detail">
    		</li>
    		<li style="margin-top: 3px;">(请点击工号标签来选择要查询的工号)</li>
    	</ul>
    </form>

    <c:if test="${reportDetailList == null || fn:length(reportDetailList) < 1}">
        <p>没有符合条件的记录</p>
    </c:if>

    <c:if test="${reportDetailList != null && fn:length(reportDetailList) > 0}">
    
        <div class="button" href="javascript:" id="exportExcel" style="margin-bottom: 2px;"><img src="images/icon/statistics.png" style="width: 16px; height: 16px; position: relative; left: -4px;"/>导出表</div>

        <div id="tabs">
            <ul>
                <c:forEach items="${reportDetailList}" var="callRecordList"
                    varStatus="status"  >
                    <c:forEach items="${callRecordList}" var="callRecord"
                        varStatus="status">
                        <c:if test="${status.index==0}">
                            <li>
                                <a href="#${callRecord.operatorName}">${callRecord.operatorName}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </ul>

            <c:forEach items="${reportDetailList}" var="callRecordList"
                varStatus="status">
                <div
                    id="<c:forEach items="${callRecordList}" var="callRecord"
                    varStatus="status"><c:if test="${status.index==0}">${callRecord.operatorName}</c:if></c:forEach>">
                    <!-- <table id="tab" align="center" border="0" width="100%"> -->
                    <table class="table">
                        <thead>
                            <tr>
                                <th>
                                    序号
                                </th>
                                <th>
                                    坐席
                                </th>
                                <th>
                                    开始日期
                                </th>
                                <th>
                                    开始时间
                                </th>
                                <th>
                                    类型
                                </th>
                                <th>
                                    应答
                                </th>
                                <th colspan="2">
                                    号码类型
                                </th>
                                <th colspan="3">
                                    查号处理时长
                                </th>
                                <th colspan="2">
                                    处理问题
                                </th>
                                <th>
                                    服务用语
                                </th>
                                <th>
                                    差错
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${callRecordList}" var="callRecord"
                                varStatus="status">
                                <tr>
                                    <td>
                                        ${status.index+1}&nbsp;
                                    </td>
                                    <td>
                                        ${callRecord.operatorDesk}&nbsp;
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${callRecord.startdate }"
                                            pattern="yyyy-MM-dd" />
                                        &nbsp;
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${callRecord.starttime }"
                                            pattern="hh:mm:ss" />
                                        &nbsp;
                                    </td>
                                    <td width="55px">
                                        <c:if test='${callRecord.type ==1}'>基本</c:if>
                                        <c:if test='${callRecord.type ==2}'>非基本</c:if>
                                    </td>
                                    <td width="55px">
                                        ${callRecord.answer==0?"按时":"超时" }&nbsp;
                                    </td>
                                    <td width="55px">
                                        ${callRecord.numbertype==1?"基本":"" }&nbsp;
                                    </td>
                                    <td width="55px">
                                        ${callRecord.numbertype==2?"非基本":"" }&nbsp;
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${callRecord.checknumbertime }"
                                            pattern="hh:mm:ss" />
                                    </td>
                                    <td width="55px">
                                        ${callRecord.checknumber==1?"大超时":"" }&nbsp;
                                    </td>
                                    <td width="55px">
                                        ${callRecord.checknumber==2?"小超时":"" }&nbsp;
                                    </td>

                                    <td width="55px">
                                        ${callRecord.dealproblem==1?"处理好":"" }&nbsp;
                                    </td>
                                    <td width="55px">
                                        ${callRecord.dealproblem==2?"处理坏":"" }&nbsp;
                                    </td>

                                    <td>
                                        <c:if test='${callRecord.serviceterms ==2}'>用语不佳</c:if>
                                    </td>
                                    <td>
                                        ${callRecord.isbad==0?"":"差" }&nbsp;
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <div>
</body>
</html>
