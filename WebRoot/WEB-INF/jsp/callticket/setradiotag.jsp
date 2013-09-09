<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
  
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<link rel="stylesheet" type="text/css" href="styles/dialogsPage.css" />
  	
  	<script type="text/javascript">
  		$(document).ready(function(){
			$("#button1").click(function(){
				var str = $("#radiotag").val();
				if(str == ""){
					$("#radiotag").val("标签不能为空");
				}
				else if(str.search("[:\/]") != -1){
					$("#radiotag").val("标签不能包含\":\"和\"\/\"");
				}
				else{
					self.parent.zipFile($("#radiotag").val());
				}

			});
  		});
  		
  		
  	</script>
  	
  </head>
  
  <body>
	<form id="form">
		<div class="dialog" style="width: 430px; height: 158px;">
		<ul>
			<li>
				<span>标签 :</span>
				<input type="text" name="radiotag" id="radiotag" />
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
