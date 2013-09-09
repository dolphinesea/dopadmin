<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>呼叫话单管理</title>
    <%@ include file="/commons/taglibs.jsp"%>
    <%@ include file="/commons/meta.jsp"%>

    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" /></script>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" /></script>

    <style type="text/css">
        .table>thead * {
            font-size: 11pt;
        }
        .table>tbody td {
            font-size: 10.5pt;
        }
        tr.over td {
            background: #D6E8F8;
            font-weight: bold;
        }
        .ischecked td {
            background: #74C69B;
            font-weight: bold;
        }
    </style>

    <!-- link-js -->
    <script src="${ctxPath}/scripts/thickbox.js"></script>
    <script type="text/javascript">
$(document).ready( function() {
    $("#ta tr").mouseover(function() {
        $(this).addClass("over");
    });
    $("#ta tr").mouseout(function() {
        $(this).removeClass("over");
    });
    //$("#ta tr:even").addClass("double");
    
    addValidate();
    
    $('#startstartTime').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd"
    });
    $('#endstartTime').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd"
    });
});

function viewDetail(url) {
	openFullScreenWindow(url,"");
}

function checkRecord(url) {
	url=url+"?keepThis=true&TB_iframe=true&height="+(screen.height-200)+"&width=1150";

	self.parent.dialog(url,'录音评审');
	//self.parent.dialog('${ctxPath}/callrecord/callrecordlist/${callTicket.sequenceNumber}.do?keepThis=true&TB_iframe=true&height=500&width=1150','话务员服务录音')"
}



function addValidate() {
	/* 设置默认属性 */
	$.validator.setDefaults( {
		submitHandler : function(form) {
			form.submit();
		}
	});
		jQuery.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	}, "只允许英文字母、数字");
	
	// 联系电话(手机/电话皆可)验证   
	jQuery.validator.addMethod("isPhone", function(value, element) {
		var length = value.length;
		//var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
			var tel = /^\d{1,19}$/;
			return this.optional(element) || (tel.test(value));

		}, "输入1到19位的数字");
	
	

		
//自定义验证方法
    jQuery.validator.addMethod("endDate",
    function(value, element) {
        var startDate = $('#startstartTime').val();
        var endDate=$('#endstartTime').val();
        if(startDate==""||endDate=="")
        {
        	
        	return true;
        }
       // return new Date(Date.parse(startDate.replace("-", "/"))) < = new Date(Date.parse(value.replace("-", "/")));
         return ((new Date(startDate.replace(/-/g,"\/")))<= (new Date(endDate.replace(/-/g,"\/")))); 
    },
    "结束日期大于开始日期");

	// validate signup form on keyup and submit
$("#form").validate({
		rules: {
			operatorName: { stringCheck:true,
			maxlength: 20				
			},
			callingNumber: { isPhone:true,				
			},
			calledNumber: { isPhone:true,				
			},
			startstartTime: {endDate:true,date:true,				
			},
			endstartTime: {endDate:true,date:true,				
			},
		},
		messages: {
			operatorName: {
				maxlength:"长度必须小于20"
			},
			startstartTime: {date:"输入正确日期 如:2011-01-01",			
			},
			endstartTime: {date:"输入正确日期 如:2011-01-01",		
			},
		}
	});
	
}
</script>
</head>

<body>
    <h2 class="title">呼叫话单管理</h2>

    <div class="content-container">
    <form:form commandName="form" method="POST"
        action="${ctxPath}/callticket/getCallTicketList.do">
        <form:errors path="*"></form:errors>
        <fieldset id="personal">
            <legend>
                查询条件
            </legend>
            <table class="searchtable">
                <tr>
                    <td width="50%"
                        style="text-align: left; padding-left: 15px; border: 0px none;"
                        valign="top">
                        <label for="operatorName">
                            操作员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
                        </label>
                        <form:input path="operatorName" id="operatorName"
                            maxlength="50" />
                        <br />
                        <label for="callingNumber">
                            主叫号码 :
                        </label>
                        <form:input path="callingNumber" id="callingNumber"
                            maxlength="50" />
                        <br />
                        <label for="calledNumber">
                            被叫号码 :
                        </label>
                        <form:input path="calledNumber" id="calledNumber"
                            maxlength="50" />
                        <br />
                        <input id="searchButton" type="submit" value="查询" 
                            width="150px" />
                        <input id="resetButton" type="button"
                            onclick="clearForm(document.forms[0])" value="重置"
                            width="150px" />

                    </td>
                    <td
                        style="text-align: left; padding-left: 15px; border: 0px none;"
                        valign="top">
                        <label for="startTime">
                            开始时间 :
                        </label>
                        <form:input path="startstartTime" id="startstartTime" />
                        <br />
                        <label for="stopTime">
                            结束时间 :
                        </label>
                        <form:input path="endstartTime" id="endstartTime"
                            maxlength="50" />
                        <br />
                        <form:checkbox path="ischecked" id="ischecked" />
                        只显示已评估录音
                        <br />
                        <form:checkbox path="istypeone" id="istypeone" />
                        显示重要1
                        <form:checkbox path="istypetwo" id="istypetwo" />
                        显示重要2

                    </td>
                </tr>
            </table>
        </fieldset>
    </form:form>

    <c:if test="${callTicketList == null}">
        <p> 没有符合条件的记录</p>
    </c:if>

    <c:if test="${callTicketList != null}">
        <table class="table">
            <thead>
                <tr>
                    <th width="33">
                        <a href="javascript:">评审</a>
                    </th>
<%--                    <th width="33">--%>
<%--                        <a href="javascript:">数量</a>--%>
<%--                    </th>--%>
                    <th title="按工号进行排序" valign="middle">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="sequenceNumber">编号</paging:hdivPagehead>
                    </th>
                    <th title="按工号进行排序" valign="middle">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="operatorName">操作员</paging:hdivPagehead>

                    </th>
                    <th title="按工号进行排序" valign="middle">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="type">类型</paging:hdivPagehead>

                    </th>
                    <th title="按主叫号码进行排序" valign="middle">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="callingNumber">主叫号码</paging:hdivPagehead>

                    </th>
                    <th title="按被叫号码进行排序" valign="middle">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="calledNumber">被叫号码</paging:hdivPagehead>

                    </th>
                    <th title="按开始时间进行排序" valign="middle" width="140">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="startTime">开始时间</paging:hdivPagehead>

                    </th>
                    <th title="按结束时间进行排序" valign="middle" width="140">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="answerTime">应答时间</paging:hdivPagehead>

                    </th>
                    <th title="按结束时间进行排序" valign="middle" width="140">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="establishTime">通话时间</paging:hdivPagehead>

                    </th>
                    <th title="按结束时间进行排序" valign="middle" width="140">
                        <paging:hdivPagehead url="/callticket/getCallTicketList.do"
                            orderBy="stopTime">结束时间</paging:hdivPagehead>
                    </th>
                </tr>
            </thead>
            <tbody id="ta">
                <c:forEach var="callTicket" items="${callTicketList}" varStatus="status">
                    <tr
                        <c:if test="${callTicket.checktime !=null}"> class="ischecked" </c:if>>
                        <td>
                            <a href="javascript:" onclick="checkRecord('${ctxPath}/callrecord/callrecordlist/${callTicket.sequenceNumber}.do')" title="话单详情">
                                <img class="operate" src="${ctxPath}/images/icon/check.png" title="查听录音"/>
                            </a>
                        </td>
<%--                        <td>--%>
<%--                            ${callTicket.recordCount}--%>
<%--                        </td>--%>
                        <td>
                            ${callTicket.sequenceNumber}
                        </td>
                        <td>
                            ${callTicket.operatorName}
                        </td>
                        <td>
                            ${callTicket.type eq 1?"重要1":callTicket.type eq 2?"重要2":""}
                        </td>
                        <td>
                            ${callTicket.callingNumber} </td> <td>
                            ${callTicket.calledNumber}
                        </td>
                        <td>
                            <fmt:formatDate value="${callTicket.startTime}"
                                pattern="yy-MM-dd hh:mm:ss" />

                        </td>
                        <td>
                            <fmt:formatDate value="${callTicket.answerTime}"
                                pattern="yy-MM-dd hh:mm:ss" />
                        </td>
                        <td>
                            <fmt:formatDate value="${callTicket.establishTime}"
                                pattern="yy-MM-dd hh:mm:ss" />
                        </td>
                        <td>
                            <fmt:formatDate value="${callTicket.stopTime}"
                                pattern="yy-MM-dd hh:mm:ss" />
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
        <paging:hdivPagebar url="/callticket/getCallTicketList.do" />
    </c:if>
    </div>
</body>
</html>
