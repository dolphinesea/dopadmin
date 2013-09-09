<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head id=Head1>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <style type=text/css>
        .logo {
            display: inline-block;
            width: 88px;
            margin: -2px 20px;
            -moz-transition: all 1s linear; /* ease-in-out; */
            }
        .logo:hover {
            -moz-transform: scale(1.05);
            }
        .toptitle {
            display: inline-block;
            color: orange;
            font: normal bold 2em Georgia, serif;
            text-shadow: 1px 2px 3px #222;
            vertical-align: top;
            margin-top: 6px;
            letter-spacing: 0.1em;
        }
        .toptitle .dop {
            color: lightblue;
            font-size: 200%;
            margin-right: 10px;
            text-shadow: 1px 2px 7px black;
            -moz-transition: all 0.4s ease-in-out;
        }
        .toptitle .dop:hover {
            -moz-transform: scale(1.1);
        }
        .widget-container {
            background: -moz-linear-gradient(center top, #fff, #ccc);
            display: inline-block;
            float: right;
            border: 1px solid #bbb;
            border-radius: 0 0 0 15px;
            box-shadow: -1px 1px 1px #666;
            padding: 4px 10px 4px 30px;
            margin-top: 0;
            opacity: 0.6;
        }
        .widget-container:hover {
            padding-top: 6px;
            box-shadow: -2px 2px 5px #666;
            opacity: 1;
        }
        .widget-container a {
            display:inline-block;
            padding-left: 30px;
            padding-right: 10px;
            font: 10pt "微软雅黑";
            border-left: 1px solid #eee;
            border-right: 1px solid #ccc;
            margin-left: -8px;
            text-decoration: none;
            color: #999;
        }
        .widget-container a:hover {
            font-size: 11pt;
            font-weight: bold;
            color: #686868;
            text-shadow: 1px 1px 1px #bbb;
        }
        .widget-container .current-user {
            background: url(${ctxPath}/images/icon/user.png) no-repeat 0px 0;
            background-size: 20px 20px;
            padding-left: 24px;
            border-left: 0px none;
            font-size: 10pt;
        }
        .widget-container .change-password {
            background: url(${ctxPath}/images/icon/key.png) no-repeat 6px 0;
            background-size: 20px 20px;
         }
        .widget-container .message {
            background: url(${ctxPath}/images/icon/messege.png) no-repeat 6px 0;
            background-size: 20px 20px;
         }
        .widget-container .message.new {
            color: orange;
            text-decoration: blink;
         }
        .widget-container .help {
            background: url(${ctxPath}/images/icon/consulting.png) no-repeat 6px 0;
            background-size: 20px 20px;
         }
        .widget-container .logout {
            background: url(${ctxPath}/images/icon/logout.png) no-repeat 6px 1px;
            background-size: 18px 18px;
            border-right: 0px none;
            padding-right: 0px;
         }
    </style>

		<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
		<%@ include file="/commons/taglibs.jsp"%>


		<!-- link-js -->
		<script language="JavaScript" type="text/javascript">
var refreshTime = 3000;// 30秒轮训
var timer;
var winfeatures = "Width=" + screen.width + ", Height=" + screen.height + ", resizable=yes, status=no, scrollbars=yes, location=no";
var toptype = "${toptype}";
$(document).ready(function() {
    		if(toptype == "nomegfun"){
    			$("#noMess").hide();
    			$("#newMess").hide();
    			$("#logout")
    				.attr("href", "javascript:")
    				.click(function(){
    					closeWindow();
    				});
    		}
    		else if(toptype == "megfun"){
    			init();
    			clearInterval(timer);
    			timer = setInterval("newMessege()", refreshTime);
    			$("#newMess").click(function(){
    				window.open('${ctxPath}/bbs/getBbsList.do', ' ', winfeatures);
    				getMess();
    				$(this).hide();
    				$('#noMess').show();
    			});
    			
    			$("#logout")
    				.attr("href", "javascript:")
    				.click(function(){
    					closeWindow();
    				});
    		}
    		else{
    			init();
    			clearInterval(timer);
    			timer = setInterval("newMessege()", refreshTime);
    			$("#newMess").click(function(){
    				window.open('${ctxPath}/bbs/getBbsList.do', ' ', winfeatures);
    				getMess();
    				$(this).hide();
    				$('#noMess').show();
    			});
    		}
    				
			

	
	$("#help").click(function(){
		var winfeatures = 'Width=600, Height=500, resizable=yes, status=no, scrollbars=yes, location=no';
		window.open('${ctxPath}/commons/help.jsp', '', winfeatures);
	});

});
function init() {
    if (${empty sessionScope.lastDate}) {
        $('#newMess').show();
        $('#noMess').hide();
    }
    else {
        $('#newMess').hide();
        $('#noMess').show();
    }
}
var deg = 0;
function newMessege() {
    deg+=360;
    $('.logo').css('-moz-transform', 'rotate('+deg+'deg)');
    if (!${empty sessionScope.lastDate}) {
            clearInterval(timer);  
            //alert(${sessionScope.lastDate});
            $.post("${ctxPath}/bbs/isNewBbs.do",{lastDate:'${sessionScope.lastDate}'},  
                        function(data){                                                                               
                            //alert(data)  ;
                            if(data==0)
                            {timer = setInterval("newMessege()", 5000);}
                            if(data>'${sessionScope.lastDate}')
                            {
                            showMess();
                            }
                        });  
        } 
}

function showMess()
{
	$('#newMess').show();
	$('#noMess').hide();
}

function getMess()
{
	clearInterval(timer);
	//self.location.reload();	
}

function closeWindow() 
{  
    window.opener = null;  
    window.open('', '_top', '');  
    window.parent.close();  
}

</script>
</head>

<body>
    <p class="title"><span class="dop">DOP</span>话务管理平台</p>
    <p class="widget-container">
        <a class="current-user">${sessionScope.user.account}</a>
        <a class="change-password"
           onclick="javascript:self.parent.tb_show('修改密码', '${ctxPath}/user/getOperator/${sessionScope.user.id}.do?keepThis=true&TB_iframe=true&height=350&width=510')"
            href="javascript:" target="_self">修改密码</a>
        <a id="help" class="help" href="javascript:" target="_self">帮助</a>
        <a class="message new" id="newMess" style="display:none" href="javascript:"
           style="color: red;" target="_self">您有新消息</a>
        <a class="message no" id="noMess">新消息</a>
        <a id="logout" class="logout" href="../login/logout.do" target="_top">退出</a>
    </p>
</body>
</html>
