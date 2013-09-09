<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="/commons/taglibs.jsp"%>
    <%@ include file="/commons/meta.jsp"%>

    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/toolbar.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />

    <style type="text/css">
        .table>thead th {
            border-bottom: 1px solid #aaa;
        }
        .table>thead th:first-child,
        .table>thead th:last-child {
            border-bottom: none;
        }
        .table>thead tr:last-child,
        .table>tbody td {
            text-align: right;
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
	
	$('#startTime').datepicker( {
        changeMonth : true,
        changeYear : true,
        dateFormat : "yy-mm-dd",
    });
    $('#endTime').datepicker( {
        changeMonth : true,
        changeYear : true,
        dateFormat : "yy-mm-dd",
    });
    
    $("#setoperator").colorbox({opacity: 0, closeButton: false, innerWidth: "412px", innerHeight: "183px", href: "${ctxPath}/report/toAddgnum.do"});
    
    $("#exportExcel").click(function() {
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
    });

    addValidate();
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
        operatorName: { stringCheck:true},
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
	
	<form id="form" action="${ctxPath}/report/getOperatorRecordStatistics.do" method="post">
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
    			<input type="hidden" name="exporttype" id="exporttype" value="statistics">
    		</li>
    		<li style="margin-top: 3px;">(请点击工号标签来选择要查询的工号)</li>
    	</ul>
    </form>

    <c:if test="${recordStatisticsList == null}">
        <p> 没有符合条件的记录 </p>
    </c:if>

    <c:if test="${recordStatisticsList != null}">
        
    <div class="button" href="javascript:" id="exportExcel" style="margin-bottom: 2px;"><img src="images/icon/statistics.png" style="width: 16px; height: 16px; position: relative; left: -4px;"/>导出表</div>

  <!-- <table border="0" align="center"> -->
  <table class="table" border="1">
    <thead>
     <tr> <th>&nbsp;</th> <th>&nbsp;</th> <th colspan="3"> 基本号</th> <th colspan="3"> 非基本号 </th>
          <th colspan="5"> 处理情况</th> <th></th>
     </tr>
     <tr>
        <td>工号</td>      <td> 应答超时</td>   <td>&lt;小超时</td> <td>大超时</td>  
        <td>总数&gt;</td>  <td>&lt;小超时</td>  <td>大超时</td>    <td>总数&gt;</td>  
        <td>&lt;处理好</td> <td>处理坏</td>      <td>用语不佳</td>  <td>出差错</td>
        <td>总数&gt;</td>  <td>话务量</td>
     </tr>
    </thead>
    <tbody>
    <c:set value="0" var="smallTimeoutNoBase_"></c:set>
           <c:forEach items="${recordStatisticsList}" var="recordStatistics" varStatus="i">
               <tr>
                   <td>
                     ${recordStatistics.operatorName }
                   </td>
                   <td>
                       ${recordStatistics.answerTimeout}
                   </td>

                   <td>
                       ${recordStatistics.smallTimeoutBase}
                   </td>

                   <td>
                       ${recordStatistics.largeTimeoutBase}
                   </td>

                   <td>
                       ${recordStatistics.timeoutBaseCount}
                   </td>

                   <td>
                        ${recordStatistics.smallTimeoutNoBase}
                   </td>

                   <td>
                   <!-- input large -->
	                   	${recordStatistics.largeTimeoutNoBase}
                   </td>
                   <td>
		               ${recordStatistics.timeoutNoBaseCount}
                   </td>

                   <td>
                       ${recordStatistics.dealGood}
                   </td>

                   <td>
                       ${recordStatistics.dealBad}
                   </td>

                   <td>
                       ${recordStatistics.badWords}
                   </td>

                   <td>
                       ${recordStatistics.mistakes}
                   </td>

                   <td>
                       0<!-- ${recordStatistics.dealCount} -->
                   </td>

                   <td>
                       ${recordStatistics.ticketNum}
                   </td>
               </tr>
       </c:forEach>

    </tbody>
   </table>
 </c:if>
</div>
</body>
</html>
