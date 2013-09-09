<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@ include file="/commons/meta.jsp"%>
  </head>  
  <body>
      欢迎你: ${user.account}<br>
      测试参数: ${p1},${p2}
     <a href="${ctxPath}/user/userList.do">用户集合</a><br>
     <a href="${ctxPath}/index.jsp">返回</a>
  </body>
</html>
