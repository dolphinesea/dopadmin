<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.RandomStringUtils"%>
<%
String id = RandomStringUtils.randomAlphanumeric(32);
 %>
<html>
	<head>
		<title>用户信息</title>
		
	</head>
	<body>
		<form action="${ctxPath}/user/addUser.do"
			method="post">
			<br>
			<table>
				<tr>
					<td>
						
						id:
						<input name="id">
					</td>
				</tr>
				<tr>
					<td>
						account:
						<input name="account">
					</td>
				</tr>
				<tr>
					<td>
						password:
						<input name="password">
					</td>
				</tr>
				<tr>
					<td>
						name:
						<input name="name">
					</td>
				</tr>
				<tr>
					<td>
						level:
						<input name="level" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" vlaue="保存" />
					</td>
				</tr>
			</table>
		</form>
		<br>
		<a href="${ctxPath}/index.jsp">返回主页</a>
		<br>
		<a href="${ctxPath}/user/userList.do">返回显示</a>
	</body>
</html>
