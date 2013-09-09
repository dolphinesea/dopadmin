<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@ include file="/commons/meta.jsp"%>
  </head>  
  <body>
      <table width="60%" border="1" cellpadding="0" align="center">
			<thead>
				<tr>
					<th style="cursor: hand;" title="按姓名进行排序" onclick="sortPage('account')" valign="top">
						account<font color='red'>${page.sortName eq "account" ? page.sortInfo : page.defaultInfo}</font>
					</th>
					<th style="cursor: hand;" title="按年龄进行排序" onclick="sortPage('password')" valign="top">
						password<font color='red'>${page.sortName eq "password" ? page.sortInfo : page.defaultInfo}</font>
					</th>
					<th style="cursor: hand;" title="按性别进行排序" onclick="sortPage('level')" valign="top">
						level<font color='red'>${page.sortName eq "level" ? page.sortInfo : page.defaultInfo}</font>
					</th>
					<th style="cursor: hand;" title="按地址进行排序" onclick="sortPage('name')" valign="top">
						name<font color='red'>${page.sortName eq "name" ? page.sortInfo : page.defaultInfo}</font>
					</th>
					<th style="cursor: hand;" >
						操作
					</th>
				</tr>
			</thead>
			<tbody>			

				<c:forEach items="${userList}" var="user">
					<tr align="center">
						<td>
							${user.account}
						</td>
						<td>
							${user.password}
						</td>
						<td>
							${user.level eq 1 ? "男" : user.level eq 2 ? "女" : "未知"}
						</td>
						<td>
							${user.name}
						</td>
						<td>
							<a
								href="${ctxPath}/user/toAddUser.do">添加</a>
							|
							<a
								href="${ctxPath}/user/getUser/${user.id}.do">编辑</a>
							|
							<a
								href="${ctxPath}/user/delUser/${user.id}.do">删除</a>
						</td>
					</tr>
				</c:forEach>
				<jsp:include page="/page/page.jsp">
					<jsp:param name="url" value="user/userList.do" />					
				</jsp:include>
				
			</tbody>
		</table>
		<br>
		<a href="${ctxPath}/index.jsp">返回</a><br>		
  </body>
</html>
