<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">    
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- link-css -->
<!--
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/theme4.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/style2.css" />
-->
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/jquery-ui.css" />

<%--<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/jquery-calendar.css" />--%>
<!-- link-js -->
<script src="${ctxPath}/scripts/jquery.js"></script>
<script src="${ctxPath}/scripts/jquery.validate.min.js"></script>
<script src="${ctxPath}/scripts/jquery-ui.min.js"></script>
<script src="${ctxPath}/scripts/common.js"></script>
<script src="${ctxPath}/scripts/timepicker.js"></script>
<%--<script src="${ctxPath}/scripts/jquery-calendar.js"></script>--%>
<%--<script type="text/javascript" src="${ctxPath}/widgets/My97DatePicker/WdatePicker.js"></script>--%>

<!-- ie-browser -->
<!--[if IE]>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/ie-sucks.css" />
<![endif]-->

<script language="javascript">
if(${empty sessionScope.user})
{
	window.parent.location.href = "login.jsp";
}
</script>
