<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <title>loginError</title>
  <head>
     <%@ include file="/commons/meta.jsp"%>
  </head>  
  <body>
      登录错误:ID号: ${user.name},密码: ${user.password},<font color='red'>${msg}</font><br>
     <a href="${ctxPath}/index.jsp">返回</a>
  </body>
</html>
