<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>    
	<title>席间消息</title>
    <%@ include file="/commons/taglibs.jsp"%>
    <%@ include file="/commons/meta.jsp"%>

    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/notice_list.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/bbslist.css" />
    <link rel="stylesheet" type="text/css" href="${ctxPath}/styles/toolbar.css" />

    <!-- link-js -->
    <script src="${ctxPath}/scripts/jquery-1.7.js"></script>
	<script src="${ctxPath}/scripts/jquery.colorbox.js"></script>
    <script src="${ctxPath}/scripts/thickbox.js"></script>
    <script src="${ctxPath}/scripts/noticeList.js"></script>
    <script src="${ctxPath}/scripts/autosize.js"></script>
    <script src="${ctxPath}/scripts/dialogwarning.js"></script>
    

    <style type="text/css">
    html,body{
		margin: 0;
    	padding: 0;
    	overflow: hidden;
    	background: white;
    }
    </style>

    <script type="text/javascript">
    var megs = [];
    megs = ${bbsList};
	var AvailableRefreshFlag = 0;
	var CloseRefreshFlag = 0;
	var refreshTime = 3000;// 30秒轮训
	var timer, timer1, timer2;
	var sorttimect = 0, sortwct = 0, sorttct = 0;
	var maxlength = 30;
	var strtmp;
	var splitstr = "\n";
	var laststr = "";
	
	
$(document).ready(function() {

	init();
	BbsAvailableRender();
	
	$("#notice").colorbox({opacity: 0, closeButton: false, innerWidth: "600px", innerHeight: "290px"});
	
	$("#available").click(function(){
		AvailableRefreshFlag = 1;
		CloseRefreshFlag = 0;
		$(".megul").empty();
		BbsAvailableRender();
	});
	
	$("#closed").click(function(){
		AvailableRefreshFlag = 0;
		CloseRefreshFlag = 1;
		$(".megul").empty();
		BbsCloseRender();
	});
	
	$("#sortbytime").click(function(){
		sorttimect++;
		if(sorttimect > 2){
			sorttimect = 1;
		}
		
		(sorttimect == 1) ? megs.sort(createComparsionFunction("cDate", false)) : megs.sort(createComparsionFunction("cDate", true));		
		
		$(".megul").empty();
		(AvailableRefreshFlag == 1 && CloseRefreshFlag == 0)?BbsAvailableRender():BbsCloseRender();
	});
	
	$("#sortbywriter").click(function(){		
		sortwct++;
		if(sortwct > 2){
			sortwct = 1;
		}
		
		(sortwct == 1) ? megs.sort(createComparsionFunction("writer", false)) : megs.sort(createComparsionFunction("writer", true));
		$(".megul").empty();
		(AvailableRefreshFlag == 1 && CloseRefreshFlag == 0)?BbsAvailableRender():BbsCloseRender();
	});
	
	$("#sortbytype").click(function(){	
		sorttct++;
		if(sorttct > 2){
			sorttct = 1;
		}
		
		(sorttct == 1) ? megs.sort(createComparsionFunction("type", false)) : megs.sort(createComparsionFunction("type", true));
		$(".megul").empty();
		(AvailableRefreshFlag == 1 && CloseRefreshFlag == 0)?BbsAvailableRender():BbsCloseRender();
	});
	
	window.onresize = function() { resize_id("megs"); }
	
	resize_id("megs");

	timer = setInterval("refreshPage()", 30000);
});


function createComparsionFunction(propertyName, isAsc) 
{
	var sign = isAsc ? 1 : -1;
	return function(object1, object2)
			{
                var value1 = object1[propertyName];
                var value2 = object2[propertyName];
 				return (value1 == value2) ?  0 : ((value1 > value2 ? 1 : -1)) * sign;
            }
}

function refreshUl(){
	refreshData();
}

function refreshData(){
	$.ajax({
		url: "${ctxPath}/bbs/refreshBbsList.do",
		type: "POST",
		dataType: "json",
		success: function(bbslist){
			if(bbslist.length > 0){
				for(var i = 0; i < bbslist.length; i++){
					if(i > megs.length - 1){
						megs.push(bbslist[i]);
					}
					else{
						for(var j = 0; j < megs.length; j++){
							if(megs[j].id == bbslist[i].id){
								megs[j].deletedFlag = bbslist[i].deletedFlag;
								break;
							}
						}
					}
				}
				
				$(".megul").empty();
				BbsAllRender();
			}
		}
	});
}


function BbsAllRender(){
	if(megs.length > 0){
		for(var i = megs.length - 1; i >= 0; i--){
			$(".megul").append(megs[i].deletedFlag == 0?displaybbsmegAva(megs[i], i):displaybbsmegClosed(megs[i], i));
		}
	}
	else{
		$.colorbox({opacity: 0, closeButton: false, html: dialogerrorNoCause("无消息！")});
	}
}

function BbsAvailableRender(){
	if(megs.length > 0){
		for(var i = megs.length - 1; i >= 0; i--){
			if(megs[i].deletedFlag == 0){
				$(".megul").append(displaybbsmegAva(megs[i], i));
			}
		}
	}
	else{
		$.colorbox({opacity: 0, closeButton: false, html: dialogerrorNoCause("无可用消息！")});
	}
}

function BbsCloseRender(){
	if(megs.length > 0){
		for(var i = megs.length - 1; i >= 0; i--){
			if(megs[i].deletedFlag == 1){
				$(".megul").append(displaybbsmegClosed(megs[i], i));
			}
		}
	}
	else{
		$.colorbox({opacity: 0, closeButton: false, html: dialogerrorNoCause("无关闭消息！")});
	}
}

function bodyhander(body){
		var strarry = body.split(/\n/g);
		if(strarry.length == 1){
			if(strarry[0].length > parseInt(maxlength)){
				var sublen = Math.ceil(strarry[0].length / maxlength);
				for(var i = 1; i < sublen; i++){
					strtmp = strarry[0].substr((i - 1) * maxlength, maxlength);
					laststr += strtmp + splitstr;
				}
				laststr += strarry[0].substr((sublen - 1) * maxlength);
			}
			else{
				laststr += strarry[0];
			}
		}
		else {
			for(var i = 0; i < strarry.length; i++){
				if(strarry[i].length > parseInt(maxlength)){
					var sublen1 = Math.ceil(strarry[i].length / maxlength);
					for(var j = 1; j < sublen1; j++){
						strtmp = strarry[i].substr((j - 1) * maxlength, maxlength);
						laststr += strtmp + splitstr;
					}
					laststr += strarry[i].substr((sublen1 - 1) * maxlength) + splitstr;
				}
				else{
					laststr += strarry[i] + splitstr;
				}
			}
		}
		
		return laststr;
}

function addBbs(type, writer, body){
	if(type == "" && writer == ""){
		$.colorbox({opacity: 0, closeButton: false, innerWidth: "350px", innerHeight: "130px", html: dialogerrorNoCause("与服务端连接超时，请重新登录!")});
	}
	else{
		var newbody = bodyhander(body);
		laststr = "";
		var req = "type=" + type + "&writer=" + writer + "&body=" + newbody;
		$.ajax({
			url: "${ctxPath}/bbs/addBbs.do",
			type: "POST",
			dataType: "json",
			data: req,
			success: function(meg){
				if(meg[0].ok == "1" && meg[0].failure == "0"){
					$.colorbox({opacity: 0, closeButton: false, html: "<div style='width: 150px; height: 50px; margin-left: 75px; margin-top:30px;font:normal 1em Sans Serif, Verdana, 微软雅黑;'>操作成功！</div>"});
				}
				else{
					$.colorbox({opacity: 0, closeButton: false, html: "<div>操作失败！</div> <div>原因:" + meg[0].cause +"</div>"});
				}
			
				setTimeout("refreshPage()", 2000);
			}
		});
	}
}

function refreshPage() {
		$.ajax({
			url: "${ctxPath}/bbs/getBbsLastList.do",
			type: "POST",
			dataType: "json",
			success: function(new_megs){
						if(new_megs.length > 0){
							for(var i = 0; i < new_megs.length; i++){
								if($("li[avaid='"+ new_megs[i].id +"']").length == 0){
									var newlength = megs.push(new_megs[i]);
									$(".megul").prepend(displaybbsmegAva(new_megs[i], newlength - 1));
									newMessege();
									setTimeout(clearMess, 30000);
								}
							}
						}
			}
		});
}

function closemeg(id){
	$.get("${ctxPath}/bbs/doBbs/2/" + id + ".do?deletedBy=${sessionScope.user.account}");
	var index = $("li[avaid='"+ id +"']").attr("index");
	megs[index].deletedFlag = 1;
	$(".megul").empty();
	(AvailableRefreshFlag == 1 && CloseRefreshFlag == 0)?BbsAvailableRender():BbsCloseRender();
}

function openmeg(id){
	$.get("${ctxPath}/bbs/doBbs/1/" + id +".do");
	var index = $("li[closeid='"+ id +"']").attr("index");
	megs[index].deletedFlag = 0;
	$(".megul").empty();
	(AvailableRefreshFlag == 1 && CloseRefreshFlag == 0)?BbsAvailableRender():BbsCloseRender();
}

function delmeg(id){
	$.get("${ctxPath}/bbs/doBbs/3/"+ id +".do");
	var index = $("li[closeid='"+ id +"']").attr("index");
	megs.splice(index, 1);
	$(".megul").empty();
	(AvailableRefreshFlag == 1 && CloseRefreshFlag == 0)?BbsAvailableRender():BbsCloseRender();
}

function displaybbsmegAva(meg, index){
	
	var html = "<li type='" + meg.type + "' avaid='"+ meg.id +"' index='"+ index +"'>"
				+ "<div class='bbshead'>" 
					+ "<span class='datetime' style='margin-right: 10px;'> " + meg.cDate + "</span>"			
					+ "<span class='writer'>" + meg.writer + " </span>"
					+ "<span class='button' onclick='closemeg("+ meg.id +")' ><img style='width: 16px; height: 16px;' src='images/icon/unlock.png' />关闭</span>"
				+ "</div>"
				+ "<p class='bbsbody'>"
					+ "<span>" + meg.body.replace(/\n/g, "<br/>") + "</span>"
				+ "</p>" 
				+ "</li>";
	return html;  
}

function displaybbsmegClosed(meg, index){
	var html = "<li type='" + meg.type + "' closeid='"+ meg.id +"' index='"+ index +"'>"
				+ "<div class='bbshead'>" 
					+ "<span class='datetime' style='margin-right: 10px;'> " + meg.cDate + "</span>"			
					+ "<span class='writer'>" + meg.writer + " </span>"
					+ "<span class='button' onclick='delmeg("+ meg.id +")'><img style='width: 16px; height: 16px;' src='images/icon/delete.png' />删除</span>"
					+ "<span class='button' onclick='openmeg("+ meg.id +")' ><img style='width: 16px; height: 16px;' src='images/icon/refresh.png' />恢复</span>"
				+ "</div>"
				+ "<p class='bbsbody'>"
					+ "<span>" + meg.body.replace(/\n/g, "<br/>") + "</span>"
				+ "</p>" 
				+ "</li>";
	return html; 
}

function resetlevelsingle(level){
			var newlevel;
			switch(level){
				case 3:
				newlevel = "话务员";
				break;
					
				case 2:
				newlevel = "班长";
				break;
						
				case 1:
				newlevel = "管理员";
				break;
					
				default:
				newlevel = "未知";
			}
	
			return newlevel;
}

//消息提醒
var deg = 0;
function newMessege() {
        showMess();
}

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

function showMess()
{
	$('#newMess').show();
	$('#noMess').hide();
}

function clearMess()
{
	$('#newMess').hide();
	$('#noMess').show();
}

	</script>
    <style type="text/css">
    	.topstyle{
			background: url(${ctxPath}/images/texture/blue_grid.png);
    		height: 50px;
			width: 100%;
			border-bottom: 1px solid orange;
			border-bottom: 2px solid #FB9500;
		}
		
		#notice{
			position: relative;
			top: 2px;
		}
		
		.megbutton{
			margin-left: 200px;
		}
		
		 .message {
            background: url(../images/icon/messege.png) no-repeat 6px 0;
            background-size: 20px 20px;
         }
         .message.new {
            color: orange;
            text-decoration: blink;
         }
    	 
    </style>
</head>

<body>
	<div class="topstyle">
		<jsp:include page="../../../commons/top.jsp" />
	</div>

	<ul class="toolbar">
		<li id="notice"  class="toolbutton"  href="${ctxPath}/bbs/toAddBbs.do">发布</li>
		<li id="available" class="toolbutton">可用消息</li>
		<li id="closed" class="toolbutton">已关闭消息</li>
		<li id="sortbytime" class="toolbutton">时间排序</li>
		<li id="sortbywriter" class="toolbutton">发布者排序</li>
		<li id="sortbytype" class="toolbutton">类型排序</li>
		<li class="message new" id="newMess" style="display:none" style="display: none;">您有新消息</li>
    	<li class="message no" id="noMess">新消息</li>
	</ul>

	<ul id="megs" class="megul bbslist" style="width: 100%; overflow:auto; left:0px;"></ul>
	
</body>
</html>
