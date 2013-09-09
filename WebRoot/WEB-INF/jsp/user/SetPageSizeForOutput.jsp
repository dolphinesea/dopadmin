<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
  
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<script src="${ctxPath}/scripts/jquery-1.7.js"></script>
	<link rel="stylesheet" type="text/css" href="styles/dialogsPage.css" />
  	
  	<script type="text/javascript">
  		$(document).ready(function(){
			$("#button1").click(function(){
				self.parent.pagesize = $("#pagesize").val();
				parent.jQuery.colorbox.close();
			});
  		});
  	</script>
  	
  </head>
  
  <body>
	<form id="form">
		<div class="dialog" style="width: 430px;">
		<ul>
			<li>
				<span>分割大小 :</span>
				<input type="text" name="pagesize" id="pagesize" />
			</li>
			<li><br></li>
		</ul>
    	<ul class="buttons">
       	 	<div>
            	<span id="button1" class="button">确定</span>
            	<span id="button2" class="button" onclick="parent.jQuery.colorbox.close();">返回</span>
        	</div>
    	</ul>
    	</div>
	</form>
  </body>
</html>
