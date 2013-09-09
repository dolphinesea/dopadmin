<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<script src="${ctxPath}/scripts/jquery-1.7.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#delinfo").text(self.parent.deleteinfo);
			$("#button1").click(function(){
				self.parent.delUser();
			});
		});
	</script>
	<style type="text/css">
	.buttons {
        margin: 0;
        padding: 10px 0 0 0;
        height: 40px;
    }

    .buttons>div {

    }
    .buttons .button {
    	display: inline-block;
        margin: 0 0 0 20px;
        width: 100px;
    }
	</style>
</head>
<body>
	<div id="delinfo" style="margin-top: 20px; margin-left: 20px; font-size:1em; font-family: Sans Serif, Verdana, 微软雅黑;"></div>
	<div class="buttons">
			<span id="button1" class="button">确定</span>
			<span id="button2" class="button" onclick="parent.jQuery.colorbox.close();">返回</span>
	</div>
</body>
</html>