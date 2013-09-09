<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>编辑用户信息</title>
</head>
<body>
<form action="${ctxPath}/user/updateUser.do" method="post">
<br>
  <table>
      <tr>       
        <td>
        <input name="id" type="hidden" value="${user.id}">
          account: <input name="account" value="${user.account}">                 
         </td>
      </tr>
      <tr>       
        <td>
          password: <input name="password" value="${user.password}">      
        </td>
      </tr>
      <tr>        
        <td>
          level: <select name="level">
          	<option value="1" ${user.level eq "1"  ? "selected" : ""}>男</option>
          	<option value="2" ${user.level eq "2"  ? "selected" : ""}>女</option>
          </select>
        </td>
      </tr>
      <tr>        
        <td>
           name: <input name="name" value="${user.name}">      
        </td> 
      </tr>
      <tr>
        <td align="right">
         <input type="submit" vlaue="保存"/>
        </td>
      </tr>
    </table>
</form>
<br>
<a href="${ctxPath}/index.jsp">返回主页</a><br>
<a href="${ctxPath}/user/userList.do">返回集合显示</a>
</body>
</html>
