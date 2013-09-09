<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>党政专网话务管理系统</title>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>

<script src="${ctxPath}/scripts/jquery.js"></script>
<script src="${ctxPath}/scripts/thickbox.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctxPath}/styles/thickbox.css" />

<script type="text/javascript">        
function layout() {
            var $topFrame = $("#topFrame");
            var topHeight = $topFrame.contents().find("body").height();
            $topFrame.height(topHeight);

            $("#frame-container").css('top', topHeight+1);

            var mainWidth = $("#frame-container").width() - $("#leftFrame").width();
            //$("#mainFrame").width(mainWidth);
        }

        $(function() {
            $(window).resize(function() { layout();});
            $("#topFrame").load(function() {layout(); });
        });
		
        
        var isdisplay=true;
		
        function switchSysBar(){ 
        	
        	if(!isdisplay)
        	{
        		document.getElementById("frmTitle").style.display="";
        		isdisplay=!isdisplay;
        	}
        	else
        	{
        			
        		    document.getElementById("frmTitle").style.display="none";
        			isdisplay=!isdisplay;
        	}
        	
        	try{
        		
        		$("#mainFrame").contents().find("#reload").click(); 
        		
        	}catch(e){
        		alert(e)
        	}
        	
        	
        	
<%--        var locate=location.href.replace('center.html','');--%>
<%--        //var ssrc=document.all("img1").src.replace(locate,'');--%>
<%--        if (ssrc=="images/main_41.gif")--%>
<%--        { --%>
<%--        //document.all("img1").src="images/main_41_1.gif";--%>
<%--        document.all("leftFrame").style.display="none" --%>
<%--        } --%>
<%--        else--%>
<%--        { --%>
<%--        //document.all("img1").src="images/main_41.gif";--%>
<%--        document.all("leftFrame").style.display="" --%>
<%--        } --%> 	 	
        } 

    </script>

<script type="text/javascript">

$(document).ready(function() {
    //保存正在浏览的页面，防止F5刷新跳到初始页面
    document.documentElement.mozRequestFullScreen();
    if(!${empty sessionScope.url})
    {
        url="${sessionScope.url}";
        //alert(url);
        url=url.split("$")
        //alert(url[2]+"/"+url[3]);
        $('#mainFrame').attr("src",url[2]+"/"+url[3]+".do");
    }
    
    
   	var winfeatures = "Width=" + screen.availWidth + ", Height=" + screen.availHeight + ", resizable=yes, status=no, scrollbars=yes, location=no, z-look=yes";
    $(".hwy").click(function(){
    	var p = window.open('${ctxPath}/user/operatorList.do?p_orderBy=account&p_orderDir=asc', ' ', winfeatures);
    	p.focus();
    });
    
    $(".dhyh").click(function(){
    	var p = window.open('${ctxPath}/user/subscriberList.do?p_orderBy=number&p_orderDir=asc', ' ', winfeatures);
    	p.focus();
    });
    
    $(".hdgl").click(function(){
    	var p = window.open('${ctxPath}/callticket/getCallTicketListForm.do?p_orderBy=sequenceNumber&p_orderDir=desc', ' ', winfeatures);
    	p.focus();
    });
    
    $(".tjbb").click(function(){
    	var p = window.open('${ctxPath}/report/getOperatorRecordStatistics.do', ' ', winfeatures);
    	p.focus();
    });
    
    $(".xxbb").click(function(){
    	var p = window.open('${ctxPath}/report/getOperatorRecordDetail.do', ' ', winfeatures);
    	p.focus();
    });
    
    $(".xjxx").click(function(){
    	var p = window.open('${ctxPath}/bbs/getBbsList.do', ' ', winfeatures);
    	p.focus();
    });
    
    $(".hjlxpz").click(function(){
    	var p = window.open('${ctxPath}/system/callTypeManager.do', ' ', winfeatures);
    	p.focus();
    });
    
    $(".bflygl").click(function(){
    	var p = window.open('${ctxPath}/backupRecord/readBackUpfile.do', ' ', winfeatures);
    	p.focus();
    });
    
});

function dialog(url) {
    alert(url);

    $("#remotehref").attr("href", url);
    //alert($("#remotehref").val());
    $("#remotehref").click();
}

function dialog(url, title) {
    //alert(url);
    $("#remotehref").attr("href", url);
    $("#remotehref").attr("title", title);
    //alert($("#remotehref").val());
    $("#remotehref").click();
}

function reloadFrame() {
	//通过随机数来防止 弹出重新发送订单的窗口
	var randomnumber = Math.floor(Math.random() * 100000)
	url=$('#currUrl').val()
	if(url.indexOf("?")<0){
		url=url+"?randomnumber=" + randomnumber;
	}
	
	cl(url);
	//alert($('#mainFrame').attr("src"))
	mainFrame.location.href=url;
}

function changeFrame(url) {
	//$('#mainFrame').attr("src",url);
	mainFrame.location.href=url;
	$('#currUrl').val(url);
	url=url.replace(/\//g,"$")+"$";
	//alert($('#currUrl').val());
	$.post("${ctxPath}/login/setCurrUrl/" + url + ".do", null, null);
}

</script>

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background: #22739E;
	-moz-user-select: -moz-none;
}

#topFrame {
	display: block;
	height:20px;
	width: 100%;
	overflow-y: hidden;
	border-bottom: 1px solid orange;
	border-bottom: 2px solid #FB9500;
	box-shadow: 2px 0 5px black;
	position: relative;
	z-index: 10;
}

#leftFrame {
	float: left;
	width: 200px;
	overflow: hidden;
	display: block;
	margin-top: 2px;
	box-shadow: 2px 0 3px gray;
	height: 100%;
	position: relative;
	z-index: 5;
}

.itemtable1{
	width:80%;
	position: relative;
	top: 50px;
	left: 150px;
}

.itemstyle1{
	display:inline-block;
	width:150; 
	height:250;
	margin: 0.5px;
	border-bottom: 5px solid rgba(0,0,0,0);
	cursor: pointer;
	-moz-user-select: none;
}

.itemstyle1:hover {
	border-color: orange;
}

.itemstyle2{
	display:inline-block;
	width:310px;
	height:200px;
	margin: 0.5px;
	color: #EEEEEE;
	border-bottom: 5px solid rgba(0,0,0,0);
	cursor: pointer;
	-moz-user-select: none;
}

.itemstyle2:hover{
	border-bottom: 5px solid orange;
}

.itemstyle3{
	display:inline-block;
	width:150px;
	height:461px;
	margin: 0.5px;
	color: #EEEEEE;
	border-bottom: 5px solid rgba(0,0,0,0);
	cursor: pointer;
	-moz-user-select: none;
}

.itemstyle3:hover{
	border-bottom: 5px solid orange;
}

.itemstyle4{
	display:inline-block;
	width:150; 
	height:200;
	margin: 0.5px;
	border-bottom: 5px solid rgba(0,0,0,0);
	cursor: pointer;
	-moz-user-select: none;
}

.itemstyle4:hover {
	border-color: orange;
}

.item-color-green{
	background:#5DA81A;
}

.item-color-purple{
	background:#5D3AB7;
}

.item-color-blue{
	background:#2877ED;
}

.item-color-red{
	background:#DB532B;
}

.item-color-hardred{
	background:#BE1C4B;
}

.item-color-hardgreen{
	background:#009F1B;
}

.hwy{
}

.hwy img{
	width: 40px;
	margin-left: 55px;
	margin-top: 20px;
}

.hwy ul{
	margin-left: 36px;
	margin-top: 20px;
	color: #EEEEEE;
	font:normal 1em "Microsoft YaHei",微软雅黑;
}

.hwy p{
	margin-left: 10px;
    margin-top: 46%;
	color: #EEEEEE;
	font:normal 1.4em "Microsoft YaHei",微软雅黑;
}

.dhyh{
}

.dhyh img{
	width: 40px;
	margin-left: 55px;
	margin-top: 20px;
}

.dhyh ul{
	margin-left: 30px;
	margin-top: 20px;
	color: #EEEEEE;
	font:normal 1em "Microsoft YaHei",微软雅黑;
}

.dhyh p{
	margin-left: 10px;
    margin-top: 46%;
    color: #EEEEEE;
	font:normal 1.4em "Microsoft YaHei",微软雅黑;
}

.hdgl{
}

.hdgl img{
	width: 40px;
	margin-left: 55px;
	margin-top: 20px;
}

.hdgl ul{
	margin-left: 40px;
	margin-top: 20px;
	color: #EEEEEE;
	font:normal 1em "Microsoft YaHei",微软雅黑;
}

.hdgl p{
	margin-left: 10px;
    margin-top: 31%;
	color: #EEEEEE;
	font:normal 1.4em "Microsoft YaHei",微软雅黑;
}

.tjbb{
	margin-top: 6px;
}

.tjbb img{
	width: 40px;
	margin-top: 20px;
	margin-left: 40%;
}

.tjbb ul{
	margin-left: 18%;
    margin-top: 20px;
    color: #EEEEEE;
    font:normal 1em "Microsoft YaHei",微软雅黑;
}

.tjbb p{
	margin-left: 15px; 
	margin-top: 13%; 
	color: #EEEEEE;
	font:normal 1.4em 'Microsoft YaHei',微软雅黑;
}

.xxbb{
	margin-top: 4px;
}

.xxbb img{
	width: 40px;
	margin-top: 20px;
	margin-left: 40%;
}

.xxbb ul{
	margin-left: 18%;
    margin-top: 20px;
    color: #EEEEEE;
    font:normal 1em "Microsoft YaHei",微软雅黑;
}

.xxbb p{
	margin-left: 15px; 
	margin-top: 13%; 
	color: #EEEEEE;
	font:normal 1.4em 'Microsoft YaHei',微软雅黑;
}


.xjxx{
}

.xjxx img{
	width: 40px;
	margin-left: 55px;
	margin-top: 20px;
}

.xjxx ul{
	margin-left: 45px;
	margin-top: 20px;
	color: #EEEEEE;
	font:normal 1em "Microsoft YaHei",微软雅黑;
}

.xjxx p{
	margin-left: 10px;
    margin-top: 60%;
	color: #EEEEEE;
	font:normal 1.4em "Microsoft YaHei",微软雅黑;
}

.hjlxpz{
}

.hjlxpz img{
	width: 40px;
	margin-left: 55px;
	margin-top: 20px;
}

.hjlxpz ul{
	margin-left: 34px;
	margin-top: 20px;
	color: #EEEEEE;
	font:normal 1em "Microsoft YaHei",微软雅黑;
}

.hjlxpz p{
	margin-left: 10px;
    margin-top: 46%;
	color: #EEEEEE;
	font:normal 1.4em "Microsoft YaHei",微软雅黑;
}

.bflygl{
	margin-top: 4px;
}

.bflygl img{
	width: 40px;
	margin-left: 55px;
	margin-top: 20px;
}

.bflygl ul{
	margin-left: 32px;
	margin-top: 20px;
	color: #EEEEEE;
	font:normal 1em "Microsoft YaHei",微软雅黑;
}

.bflygl p{
	margin-left: 10px;
    margin-top: 26%;
	color: #EEEEEE;
	font:normal 1.5em "Microsoft YaHei",微软雅黑;
}

.title {
	display: inline-block;
    color: orange;
    font: normal bold 2em Georgia, serif;
    text-shadow: 1px 2px 3px #222;
    vertical-align: top;
    margin-top: 22px;
    letter-spacing: 0.1em;
    position: relative;
	left: 80px;
}
.title .dop {
	color: lightblue;
    font-size: 150%;
    margin-right: 10px;
    text-shadow: 1px 2px 7px black;
    -moz-transition: all 0.4s ease-in-out;
}
.title .dop:hover {
	-moz-transform: scale(1.1);
}
.widget-container {
	display: inline-block;
    font:normal bold 1.5em Georgia, serif;
    position: relative;
	left: 500px;
	top: 30px;
}
.usericon{
	width: 25px;
	position: relative;
	left: 500px;
	top: 34px;
}
</style>
</head>

<body>
    <table border="0px" cellpadding="0" cellspacing="0" width="100%" align="center">
    		<tr>
    			<td style="-moz-user-select: none;">
    				<p class="title">党政专网话务管理系统</p>
    				<img class="usericon" src="images/icon/user.png" />
    				<p class="widget-container"><span>${sessionScope.user.account}</span></p>
    			</td>
    		</tr>
    </table>
    
    				<div style="width: 790px; margin-left: auto; margin-right: auto; margin-top: 50px">
    					<div class="hwy itemstyle1 item-color-green">
							<img src="images/icon/user.png"/>
							<ul>
								<li>新增话务员</li>
								<li>删除话务员</li>
								<li>修改话务员</li>
							</ul>
							<p>话务员账号</p>
						</div>
		
						<div class="dhyh itemstyle1 item-color-purple">
							<img src="images/icon/phone.png"/>
							<ul>
								<li>新增电话用户</li>
								<li>删除电话用户</li>
								<li>修改电话用户</li>
							</ul>
							<p>电话用户</p>
						</div>
						
						<div class="xjxx itemstyle1 item-color-hardred">
							<img src="images/icon/messege.png"/>
							<ul>
								<li>发布消息</li>
								<li>查看消息</li>
							</ul>
							<p>席间消息</p>
						</div>
						
						<div class="hdgl itemstyle1 item-color-blue">
							<img src="images/icon/check.png"/>
							<ul>
								<li>话单查询</li>
								<li>话单评审</li>
								<li>录音归档</li>
								<li>录音播放</li>
							</ul>
							<p>话单管理</p>
						</div>
						
						<div class="hjlxpz itemstyle1 item-color-red">
							<img src="images/icon/gear.png"/>
							<ul>
								<li>添加呼叫类型</li>
								<li>删除呼叫类型</li>
								<li>修改呼叫类型</li>
							</ul>
							<p>呼叫类型配置</p>
						</div>
		
						<div class="tjbb itemstyle2 item-color-blue">
							<img src="images/icon/chart.png"/>
							<ul>
								<li>话务服务综合统计查询</li>
								<li>导出话务服务综合统计报表</li>
							</ul>
							<p>统计报表</p>
						</div>
						
						<div class="bflygl itemstyle4 item-color-purple">
							<img src="images/icon/floppy.png"/>
							<ul>
								<li>浏览归档录音</li>
								<li>播放归档录音</li>
							</ul>
							<p>备份录音</p>
						</div>
						
						<div class="xxbb itemstyle2 item-color-hardgreen">
							<img src="images/icon/statistics.png"/>
							<ul>
								<li>话务服务明细统计查询</li>
								<li>导出话务服务明细统计报表</li>
							</ul>
							<p>详细报表</p>
						</div>
    				</div>
</body>
</html>
