<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head id=Head1>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    
    <style type=text/css>
        .title {
            display: inline-block;
            color: orange;
            font: normal bold 1.5em Georgia, serif;
            text-shadow: 1px 2px 3px #222;
            vertical-align: top;
            letter-spacing: 0.1em;
            margin-left:5%;
            margin-top:-5px;
            -moz-user-select: none;
        }
        .title .dop {
            color: lightblue;
            font-size: 200%;
            margin-right: 10px;
            text-shadow: 1px 2px 7px black;
            -moz-transition: all 0.4s ease-in-out;
        }
        
        .title .stitle{
        	font: bold 0.9em "verdana";
        	text-shadow: 1px 1px 2px black;
        	vertical-align: middle;
        	letter-spacing: 0.1em;
        	position: absolute;
        	left: 80%;
        	top: 18px;
        	-moz-user-select: none;
        }
        
        .title .closewin{
			-moz-user-select: none;
		}
		
		.closewin img{
			width: 22px;
			height: 22px;
			position: relative;
			top: -5px;
			cursor: pointer;
		}
        
    </style>

		<META content="MSHTML 6.00.2900.5848" name=GENERATOR>

		<%@ include file="/commons/taglibs.jsp"%>
		
<script type="text/javascript">
$(function(){
	$(".closewin").click(function(){
		closeWindow();
	}).offset({left: $(".stitle").offset().left + 173, top: $(".stitle").offset().top + 10});
	
	$(window).resize(function(){
		$(".closewin").offset({left: $(".stitle").offset().left + 173, top: $(".stitle").offset().top + 10});
	});
});

function closeWindow() 
{ 
    window.opener = null;  
    window.open('', '_top', '');  
    window.parent.close();  
}
</script>
</head>

<body>
    <table border="0px" cellpadding="0" cellspacing="0" width="100%" align="center">
    		<tr class="title">
    			<td>
    				<p style="margin-top: 20px;">党政专网话务管理系统</p>
    			</td>
    			<td class="stitle">${smalltitle}</td>
    			<td><span class="closewin"><img src="${ctxPath}/images/icon/logout.png" /></span></td>
    		</tr>
    </table>
</body>
</html>
