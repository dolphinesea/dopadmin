<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>呼叫话单管理</title>
		<%@ include file="/commons/taglibs.jsp"%>
		<%@ include file="/commons/meta.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/slick.grid.css" />
		<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/examples.css" />
		<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/mainframe.css" />
		<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/colorbox.css" />
		
		<script src="${ctxPath}/scripts/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./scripts/dateformat.js"></script>
		<script src="${ctxPath}/scripts/jquery.event.drag-2.0.min.js">
</script>
		<script src="${ctxPath}/scripts/jquery.event.drop-2.0.min.js">
</script>
		<script src="${ctxPath}/scripts/jquery.json-2.3.min.js">
</script>


		<script src="${ctxPath}/scripts/slick-bak/slick.core.js">
</script>
		<script
			src="${ctxPath}/scripts/slick-bak/plugins/slick.cellrangeselector.js">
</script>
		<script
			src="${ctxPath}/scripts/slick-bak/plugins/slick.cellselectionmodel.js">
</script>
		<script
			src="${ctxPath}/scripts/slick-bak/plugins/slick.rowselectionmodel.js">
</script>
		<script src="${ctxPath}/scripts/slick-bak/plugins/slick.rowmovemanager.js">
</script>
		<script src="${ctxPath}/scripts/slick-bak/slick.formatters.js">
</script>
		<script src="${ctxPath}/scripts/slick-bak/slick.editors.js">
</script>
		<script src="${ctxPath}/scripts/slick-bak/slick.grid.js">
</script>

		<script src="${ctxPath}/scripts/recordplayer.js">
</script>
<script src="${ctxPath}/scripts/sprintf.js"></script>

<script src="${ctxPath}/scripts/dialogwarning.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/styles/thickbox.css" />

<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	overflow: hidden;
}

ul>* {
	font-family: 宋体;
}

.cell-title {
	zfont-weight: bold;
}

.cell-effort-driven {
	text-align: center;
}

.hidden col {
	display: none;
}

.cell-reorder {
	cursor: move;
	background: url("../images/drag-handle.png") no-repeat center center;
}

.cell-selection {
	border-right-color: silver;
	border-right-style: solid;
	background: #f5f5f5;
	color: gray;
	text-align: right;
	font-size: 10px;
}

.slick-row.selected .cell-selection {
	background-color: transparent;
	/* show default selected row background */
}

.recycle-bin {
	width: 120px;
	border: 1px solid gray;
	background: beige;
	padding: 4px;
	font-size: 12pt;
	font-weight: bold;
	color: black;
	text-align: center;
	-moz-border-radius: 10px;
}

form * {
	font: normal 14px 'verdana', 微软雅黑;
}

<!--
edit css-->.operateview {
	width: 1000px;
}

.operate {
	
}

.title-form {
	background-color: #3f8ecf;
	font-weight: 800;
	color: #FFFFFF;
	font-size: 1.09em; //
	border-radius: 3px 3px 0 0;
	height: 22px;
	margin-bottom: 4px;
	text-shadow: gray;
	line-height: 30px;
}

.title-form>span {
	position: relative;
	top: -4px;
}

.title-form>a {
	cursor: pointer;
	font-weight: normal;
	color: #808080;
	float: right;
	height: 16px;
	line-height: 18px;
	margin-right: 10px;
	margin-top: 2px;
	outline: medium none;
	padding-left: 18px;
	position: relative;
	background-image: url("./images/icon/up.png");
	background-repeat: no-repeat;
	background-size: 15px 15px;
}

.choice-hidden {
	display: none;
}

.choice-block {
	
}

form>ul { //
	border-bottom: 1px solid #ccc; //
	border-left: 1px solid #ccc; //
	border-right: 1px solid #ccc; //
	border-radius: 1px 1px 4px 4px;
	width: 100%;
}

#form>ul>li {
	padding-top: 3px;
}

#form>ul>li>* {
	display: inline-block;
}
/*#form>ul>li > input[type="text"]:hover{
	font-weight: 800;
	width: 140px;
}*/
#form select {
	width: 140px;
}

#form>ul>li>label {
	margin-right: 12px;
}

#form>ul>li>input[type="text"] {
	border-width: 1px;
	border-style: solid;
	border-color: #ccc;
	height: 25px;
	width: 140px;
	border-radius: 4px 4px 4px 4px;
	font-family: Consolas;
	-moz-user-select: text;
}

#form>ul>li>labeltext {
	width: 80px;
	font-weight: 400;
}

form>ul>li>button {
	background-color: #F5F5F5;
	border: 1px solid rgba(0, 0, 0, 0.1);
	border-radius: 2px 2px 2px 2px;
	color: #666666;
	font-family: arial, sans-serif;
	font-size: 15px;
	height: 29px;
	padding: 0 8px;
	margin: 4px 0 8px 0;
}

form>ul>li>button[type="button"] {
	font-weight: bold;
	width: 90px;
}

form>ul>li>button[type="submit"] {
	font-weight: bold;
	width: 110px;
}

form>ul>li>button[id="resetButton"] {
	font-weight: bold;
	width: 110px;
}

form>ul>li>button:hover {
	border-color: #4d90fe;
	color: #333333;
	box-shadow: 0 1px #222 rgba(0, 0, 0, 0.1);
}

form>ul>li>button:hover {
	border-color: #4d90fe;
	color: #333333;
	box-shadow: 0 1px solid rgba(0, 0, 0, 0.1);
}

#form1>ul {
	margin-top: 10px;
}

.error {
	color: red;
	background-color: #FFB9B9;
	border-color: red;
}

.mangersound {
	border-bottom: 1px solid #ccc;
	box-shadow: 0px 2px 0px #888;
	cursor: pointer;
}

.clock {
	display: inline-block;
	vertical-align: top;
	position: relative;
	top: 5px; //
	left: 50px;
	width: 135px;
	height: 25px;
	border: 1px solid rgba(0, 0, 0, .9);
	border-radius: 5px;
	box-shadow: inset 0 0 5px rgba(0, 0, 0, .9);
	background: #bca;
	font: bold 21px 'Transponder AOE', 'Digital-7';
	color: rgba(0, 0, 0, .7);
	letter-spacing: 0.01em;
	text-align: center;
	text-shadow: 3px 2px 1px rgba(0, 0, 0, .2);
	cursor: pointer;
	-moz-user-select: none;
}

#startTiming { //
	width: 30px; //
	height: 30px;
	vertical-align: bottom; //
	border-radius: 40px 40px 40px 40px;
	cursor: pointer; //
	z-index: 19;
	-moz-user-select: none;
	height: 30px;
	width: 50px;
	font-size: 0.7em;
	position: relative;
	top: 10px;
}

.startTimingclick {
	border-width: 1px;
	border-style: solid;
	border-color: #CDCDD1;
	box-shadow: 1px 1px 1px #111;
}

.pointer {
	display: inline-block;
	position: absolute; //
	z-index: 20;
	width: 30px;
	height: 30px;
	left: 1004px;
	top: 392px;
	cursor: pointer;
}

#form1 li {
	margin-top: 3px;
}

#form1 label {
	font-size: 9pt;
}

#form1 .before {
	font-size: 14px;
	font-weight: 400;
}

.slick-column-name {
	font: 11px "verdana", 微软雅黑;
	//text-align: center;
	color: black;
}

.operatortable {
	width: 100%;
	cellspacing: 0;
	cellpadding: 0;
}

.topstyle {
	background: url(${ctxPath}/images/texture/blue_grid.png);
	height: 50px;
	width: 100%;
	border-bottom: 1px solid orange;
	border-bottom: 2px solid #FB9500;
}

#divPlayer {
	position: relative;
	top: -20px;
}

#myGrid {
	font: 0.7em "verdana", 微软雅黑;
	min-height: 550px;
	//text-align: left;
	position: relative;
	top: -18px;
	z-index: 0;
}

#contextMenu {
	background: #cee4f5;
	border: 1px solid gray;
	padding: 2px;
	display: inline-block;
	min-width: 100px;
	-moz-box-shadow: 2px 2px 2px silver;
	-webkit-box-shadow: 2px 2px 2px silver;
	z-index: 99999;
}

#contextMenu li {
	padding: 4px 4px 4px 14px;
	list-style: none;
	cursor: pointer;
}

#contextMenu li:hover {
	background-color: white;
}

label[for=showTime] {
	position: relative;
	top: 10px;
}

.detailbutton {
	display: inline-block;
	height: 25px;
	width: 100px;
	margin-right: 10px;
	font-size: 0.7em;
}

.flipx {
	-moz-transform: matrix(1, 0, 0, -1, 0, 0);
}

#displaytime {
	margin-left: 28px;
}

#operatorName1 {
	margin-left: 28px;
	margin-top: 6px;
}

#importance1 {
	margin-left: 28px;
}

#timeout {
	margin-left: 28px;
}

#mistake {
	margin-left: 28px;
}

#non-basic {
	margin-left: 7px;
}

#dealbad {
	margin-left: 19px;
}

#servicebad {
	margin-left: 19px;
}

#importance2 {
	margin-left: 4px;
}

.toolbutton {
	display: inline-block;
	margin: 0;
	width: 150px;
	height: 100%;
	line-height: 30px;
	border: 0;
	border-left: 1px solid #fff;
	border-right: 1px solid #666;
	border-radius: 0;
	list-style: none;
	text-align: center;
	vertical-align: super;
	font-family: "Sans Serif", "微软雅黑";
	text-shadow: 1px 1px 0px rgba(255, 255, 255, 1);
	margin-left: -8px;
	background: #eee;
	cursor: pointer;
	-moz-user-select: none;
}

.toolbutton:first-child {
	border-left: 0;
}

.toolbutton.last {
	float: right;
	border: 0
}

.toolbutton:hover {
	background: -moz-linear-gradient(top, rgba(100, 105, 250, 0.8), rgba(90, 100, 240, 0.8) );
}

.toolbutton:active {
	box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.5);
}
.gridheader{
	text-align: center;
}
.gridheader1{
	text-align: left;
}
</style>

<script language="javascript">
		
$(document).ready(function(){
			//$("#startTimingPointer").offset({top: $("#startTiming").offset().top + 1, left: $("#startTiming").offset().left + 3});
			$(window).resize(function(){
				//$("#startTimingPointer").offset({top: $("#startTiming").offset().top + 1, left: $("#startTiming").offset().left + 3});
			});
		          //alert($("#startTiming").offset().top+"--"+$("#startTiming").offset().left);
			addValidate();
			
<%--			$("#startstartTime, #endstartTime").calendar();--%>
			
		    /*$("#startstartTime").datepicker({
		        changeMonth: true,
		        changeYear: true,
		        
		        time24h: true,
		        showTime: true
		    });
		   
		    
		    $("#endstartTime").datepicker({
		        changeMonth: true,
		        changeYear: true,
		        
		        time24h: true,
		        showTime: true
		    });*/
		    
		    if(${callTicketsJdbcQuery == null}){
		    	var today=getNowFormatDate();
		    	$('#startstartTime').val(today);
		    	$('#endstartTime').val(today);
		    }
		    else
		    {
		    	if(${callTicketsJdbcQuery.ischecked})
	    		{
	    			$("#ischecked").attr("checked", true);
	    		}
		    	if(${callTicketsJdbcQuery.istypeone})
	    		{
	    			$("#istypeone").attr("checked", true);
	    		}
		    	if(${callTicketsJdbcQuery.istypetwo})
	    		{
	    			$("#istypetwo").attr("checked", true);
	    		}
		    }
		    
		    $("#startstartTime").onchange
	    
});
		
var se, m = 0, h = 0, s = 0, ss = 1, flag = 0, ms = 0, mss = 0;
// start datetime
//start datetime of millisecond
var startMillisecond = new Date().getTime();
	
function second() {

	var currenMillisecond = new Date().getTime();
	//var currentDatetime = new Date();
	var milliseconds = currenMillisecond - startMillisecond;
	s = parseInt(milliseconds/1000); 
	ss = milliseconds % 1000;
	  
	if( s > 60 ){
		 m = parseInt(s / 60);
		 s = s % 60;
	 }
	if ( m > 60){
		h = parseInt(m / 60);
		m = m % 60;
	}
	//var datetime = (h < 10 ? ("0"+h) : h) + ": " + (m < 10 ? ("0"+m) : m) + ": " +(s < 10 ? ("0"+s) : s);
	var datetime = (m < 10 ? ("0"+m) : m) + ":" +(s < 10 ? ("0"+s) : s);
	$("#showTime").val(datetime);
}
var secondInterval;
function startclock() {
	startMillisecond = new Date().getTime();
	flag = (flag == 1) ? 2 : (flag == 2) ? 0 : 1;
	
	if (flag == 1) {
		//clearInterval(secondInterval);
		//clearInterval(se);
		secondInterval = setInterval("runsecond()",1000);
		se = setInterval("second()", 300);
		//alert($("#startTiming").val());
		$("#startTiming").attr("title","结束");
		$("#startTiming").attr("class","startTimingclick");
		$(".pointer").css("transform","rotate(-90deg)");
		seconddeg = -90;
	} 
	else if(flag == 2) {
		clearInterval(se);
		clearInterval(secondInterval);
		ss = 1;
		m = h = s = 0;
		$("#startTiming").attr("title","重新开始");
		$("#startTiming").removeAttr("class");
		$("#startTiming").val("00: 00");
	}
	else if(flag == 0){
		$("#showTime").val("00: 00");
	}
} //这句已经过更新
var seconddeg = -90;
function runsecond(){
	//alert(seconddeg);
	seconddeg += 30
	if (seconddeg == 360)
		seconddeg = 0;
	$(".pointer").css("transform","rotate("+seconddeg+"deg)");
	
}

var t0;
function handleTimer100ms(){
  var currtime = new Date().getTime();
  cl("currtime:" + currtime + " t0:" + t0);
  var s = parseFloat((currtime - t0)/1000);
  cl("s:" + s);
  $("#displaytime").text(sprintf("%2.1f", s));
  $("#showTime").val($("#displaytime").text());
}

function mousedownclock(){
	flag = (flag == 1) ? 2 : 1;
	
	if(flag == 1){
		t0 = new Date().getTime();
		$("#displaytime").text("0.0");
		$("#showTime").val("0.0");
		se = setInterval("handleTimer100ms()", 100);
	}
	else if(flag == 2){
		clearInterval(se);
	}
}

/*function second1(){
	ms++;
	
	if(ms == 100){
		mss++;
		ms = 0;
	}
	
	if(mss == 10){
		s++;
		mss = 0;
	}
	
	$("#showTime").val(s + "." + mss);
}

function countse(){
	flag = (flag == 1) ? 2 : 1;
	
	if(flag == 1){
		s = mss = ms = 0;
		$("#showTime").val("00.00");
		se = setInterval("second1()", 100);
		$("#startTiming").text("停止");
	}
	else if(flag == 2){
		clearInterval(se);
		$("#startTiming").text("开始");
	}
}*/

function convertTotimeformat(sectime){
	var mm, ss;
	var strmm, strss;
	var float = parseFloat(sectime);
	var val = Math.round(float);
	mm = parseInt(val / 60);
	ss = val % 60;
	
	if(mm > 9){
		strmm = mm; 
	}
	else{
		strmm = "0" + mm;
	}
	
	if(ss > 9){
		strss = ss;
	}
	else{
		strss = "0" + ss;
	}
	
	return strmm + ":" + strss;
}

function backupradio(){
	$.colorbox({opacity: 0, closeButton: false, innerWidth: "442px", innerHeight: "171px", href: "callticket/tosetradiotag.do"});
}

function zipFile(tag) {
	if(selectTickets==null||selectTickets.length<1){
		return;
	}
	var saveFileString="";	
	var numarr = "";
	for (var i = 0; i <selectTickets.length; i++) {
		
		saveFileString+= data[selectTickets[i]].savefilename;
		numarr += data[selectTickets[i]].sequenceNumber;
	    if(i!=selectTickets.length-1){
	    	saveFileString+=",";
	    	numarr +=",";
	    }
		  
	}
	cl(saveFileString);
	
	if(saveFileString == "" || saveFileString == null || saveFileString.length == 0){
		$.colorbox({opacity: 0, closeButton: false, innerWidth: "450px", innerHeight: "130px", html: dialogerrorNoCause("该话单没有录音，请选择其他话单进行归档！")});
	}
	else{
		var req1 = "saveFileNameArr=" + saveFileString;
		$.ajax({
			url: "${ctxPath}/callticket/iswavExist.do",
			type: "POST",
			dataType: "json",
			data: req1,
			success: function(meg){
				if(meg[0].ok == "1" && meg[0].failure == "0"){
					var req = "sequenceNumberArr=" + numarr + "&saveFileNameArr=" + saveFileString + "&tag=" + tag;
					$.ajax({
						url: "${ctxPath}/callticket/downloadbackupaudio.do",
						type: "POST",
						dataType: "json",
						data: req,
						success: function(meg){
							if(meg[0].ok == "1" && meg[0].failure == "0"){
								$.colorbox({opacity: 0,closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
							}
							else if(meg[0].ok == "0" && meg[0].failure == "0"){
								$.colorbox({opacity: 0,closeButton: false,  html: dialogerror("操作失败！", meg[0].cause)});
							}
							else{
								$.colorbox({opacity: 0, closeButton: false, html: dialogerrorNoCause("操作失败！")});
							}
						}
					});				
				}
				else{
					$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
				}
			}
		});
	}
	
}


function removeassessment()
{
	if($("#ticketid").val()!=""){
		deleteUrl="${ctxPath}/callticket/deleteReview/"+$("#ticketid").val()+".do";	
			jQuery.ajax( {     
	          type : 'get',
	          url : deleteUrl,    
	          dataType : 'json',
	          success : function(res) {
	        	  		var tid=$("#currHide").val();
	        	  		getReview(tid);  
	                   data[tid].result="";
	                   data[tid].important="";
	                   grid.setData(data);
					   grid.render();
	          }, 
	          error : function(data) {     
	            	getReview(currTicket);  
	          }     
	        });
		
		
	}
	
}

function updateTicketOperator(){
		if($("#operatorName1").val()!="-1"&&currTicket!=-1){
		deleteUrl="${ctxPath}/callticket/updateTicketOperator/"+$("#operatorName1").val()+"/"+data[currTicket].sequenceNumber+".do";
		data[currTicket].operatorName=$("#operatorName1").val();

			jQuery.ajax( {     
	          type : 'get',
	          url : deleteUrl,    
	          dataType : 'json',
	          success : function(res) {
				
					   //data[currTicket].operatorName=$("#operatorName1").val();
	                   //cl(data[currTicket].operatorName);
	                   grid.setData(data);
					   grid.render();
	                   getReview(currTicket);
	                   
	          }, 
	          error : function(data) {     
	            	getReview(selectTickets[0]);   
	          }     
	        });
		
		
	}
}

function updateSelectTicketOperator(){
	if(selectTickets==null||selectTickets.length<1){
		return;
	}
	ticketidArr="";
	for (var i = 0; i <selectTickets.length; i++) {
		data[selectTickets[i]].operatorName=$("#operatorName1").val();
	    ticketidArr+=  data[selectTickets[i]].sequenceNumber;	
	    if(ticketidArr!=selectTickets.length-1){
	    	 ticketidArr+=",";
	    }
	}
	cl(ticketidArr);
	if($("#operatorName1").val()!="-1"){
		deleteUrl="${ctxPath}/callticket/updateTicketOperator/"+$("#operatorName1").val()+"/"+ticketidArr+".do";

			jQuery.ajax( {     
	          type : 'get',
	          url : deleteUrl,    
	          dataType : 'json',
	          success : function(res) {
				
					   //data[currTicket].operatorName=$("#operatorName1").val();
	                   //cl(data[currTicket].operatorName);
	                   grid.setData(data);
					   grid.render();
	                   getReview(selectTickets[0]);
	                   
	          }, 
	          error : function(data) {     
	            	getReview(selectTickets[0]);   
	          }     
	        });

		
		
	}
}

function selectAll(){
	if(data!=null&&data.length>0){
		var arrData=new Array();
		for(i=0;i<data.length;i++){
			
			arrData.push(i);
		}
		grid.setSelectedRows(arrData);
	}
	
}

/*播放器状态改变事件*/
function OnDSPlayStateChangeEvt(NewState) {
	cl("in OnDSPlayStateChangeEvt");
	if (isPlayAll) {
		// 自动播放
		//alert(NewState);
		//判断是否 有录音文件
		if (NewState == 8 || NewState == 10) {
			if (playIndex < playList.length-1) {
				playIndex++;
				cl("playIndex:" + playIndex);
				cl("playList[playIndex][1]:" + playList[playIndex][1] + " " + "playList[playIndex][0]:" + playList[playIndex][0]);
				playrecord(playList[playIndex][1],playList[playIndex][0]);
		   		getReview(playList[playIndex][0]);
				//$("input[name='song'][value=" + playList[playIndex] + "]")
						//.dblclick();
			} else {
				showPlayer("divPlayer", "");
			}
		}

	} else {
		// 单选播放
		
		if (preState==9 && NewState == 10) {
			// 
				
				$("input[name='song'][value=" + currPlaySong + "]").parent().parent().removeClass("over");
				$("input[name='song'][value=" + currPlaySong + "]").parent().parent().addClass("nofile");

		}
		if (NewState == 8 || NewState == 10) {
			showPlayer("divPlayer", "");
		}
	}
	if (NewState==1) {
			//Stop();
			//playList = new Array();
		   	//playIndex=0;
			 
				//$("input[name='song'][value=" + playList[playIndex] + "]").parent().parent().removeClass("over");
				//$("input[name='song'][value=" + playList[playIndex] + "]").parent().parent().addClass("nofile");

	}
	preState=NewState;
}

function printdiv(){
	var headstr = "<html><head><title></title></head><body>";  
	var footstr = "</body>";  
	var newstr = document.getElementById('div_print').innerHTML;  
	w=window.open("","_blank","heigth=300, width=400");
	w.document.write(headstr+newstr+footstr);  
	w.print();
	w.close();
}

</script>
	</head>

	<body>
		<div class="topstyle">
			<jsp:include page="../../../commons/top.jsp" />
		</div>

		<div >
<!-- edit html-->			
			<table class="operatortable">
				<tr>
					<td class="operateview"  valign="top"  align="left" >
						<div id="divPlayer"></div>
						<div id="myGrid">
						</div>
					</td>
					<td  class="operate" valign="top" align="left" width="270px">
						<form id="form" method="post"
							  action="./callticket/getCallTicketListForm.do">
								<ul>
									<li class="title-form" ><span>查询条件</span><a id="title-choice" ></a></li>
									<li class="choice-block"> <labeltext for="operatorName">工号:</labeltext>
										 <select id="operatorName" name="operatorName">
												<option value = "">--未选择--</option>
											<c:forEach items="${operatorList}" var="operator">
											   <option value="${operator.account}" 
											   	<c:if test="${param.operatorName == operator.account}">
											   		selected="selected"
											   	</c:if>
											   >
												 ${operator.account}
											   </option>
											</c:forEach>
										 </select>
									</li>
									<li class="choice-block"> <labeltext for="startTime">开始时间 :</labeltext>
										 <input id="startstartTime" name="startstartTime" type="text" autocomplete="off" 
												value="${callTicketsJdbcQuery.startstartTime}" /></li>
									<li class="choice-block"> <labeltext for="stopTime">结束时间 :</labeltext>
											<input id="endstartTime" name="endstartTime" maxlength="50" type="text" autocomplete="off"
												value="${callTicketsJdbcQuery.endstartTime}" />
									
									</li class="choice-block">
									<li class="choice-block"> <labeltext for="desk">坐席号:</labeltext>
											<input id="desk" name="desk" maxlength="50" type="text" autocomplete="off"
												value="${callTicketsJdbcQuery.desk}" />
									</li>
									<li class="choice-block"> <labeltext for="callingNumber">主叫号码 :</labeltext>
											<input id="callingNumber" name="callingNumber" maxlength="50" type="text" autocomplete="off"
												value="${callTicketsJdbcQuery.callingNumber}" />
									</li>
									<li class="choice-block"> <labeltext for="calledNumber">被叫号码 :</labeltext>
										 <input id="calledNumber" name="calledNumber" maxlength="50" type="text" autocomplete="off"
												value="${callTicketsJdbcQuery.calledNumber}" />
									</li>
									<li class="choice-block"> <input type="checkbox" id="ischecked" name="ischecked" /> <label for="ischecked">已评估录音</label>
										 <input type="checkbox" id="istypeone" name="istypeone" /><label for="istypeone">重要1</label>
										 <input type="checkbox" id="istypetwo" name="istypetwo" /><label for="istypetwo">重要2</label>
									</li>
									<li class="choice-block"> 
										 <button id="searchButton" type="submit" > 查询 </button>
										 <button id="reload" type="button" style="display:none;" > reload </button>
										 <button id="resetButton" type="button" onclick="clearForm(document.forms[0])">重置</button>
									</li>
								</ul>
							<input id="saveFileNameArr" name="saveFileNameArr" type="hidden"/>
						</form>
						<input name="currHide" type="hidden" id="currHide"/>
<!-- edit html-->
						<form id="form1" action="${ctxPath}/callticket/addView.do" method="post">
							<ul>
									<li class="title-form">
										<span>评审</span>
									</li>
									<li class="mangersound"> 
										<span class="toolbutton" onclick="playSelect()" style="display: inline-block; width: 65px; height: 30px;"><img src="./images/icon/newplay.png" style="width: 16px; height: 16px; position: relative; top: 1px; left: -4px;"/>播放</span>
										<span class="toolbutton" onclick="Stop()" style="display: inline-block; width: 65px; height: 30px;"><img src="./images/icon/stop.png" style="width: 16px; height: 16px; position: relative; top: 1px; left: -4px;"/>停止</span>
										<span id="zip" class="toolbutton" onclick="backupradio()" style="display: inline-block; width: 65px; height: 30px;"><img src="./images/icon/floppy.png" style="width: 16px; height: 16px; position: relative; top: 1px; left: -4px;">归档</span>
										<span class="toolbutton" onclick="selectAll()" style="display: inline-block; width: 65px; height: 30px;"><img src="./images/icon/checkall.png" style="width: 16px; height: 16px; position: relative; top: 1px; left: -4px;"/>全选</span>
									</li>
									<li> <label class="before" for="showTime"> 计时：</label>
										<!--  <input class="clock" name="showTime" style="color: #ff0000;  width: 100px; position: relative; top: 16px;"
												id="showTime" type="text"  value="0.0">-->
											<span id="displaytime" class="clock" style="color: #ff0000;" onclick="mousedownclock()">0.0</span>
										  <!--  <span>
											  <img id="startTiming" src="./images/icon/clockborder.png" title="开始" onclick="startclock()"/>
											  <img id="startTimingPointer" src="./images/icon/pointer.png" class="pointer" onclick="startclock()"/>
										  </span>
										<button id="startTiming" type="button" class='button' onclick="mousedownclock()" >开始</button>-->
									</li>
									<li> <label class="before">工号：</label>
										 <select id="operatorName1" style="text-align:center;width:80px;line-height:-10px;">
						                   <option value="-1">-选择-</option>
										   <c:forEach items="${operatorList}" var="operatorList">
											<option value="${operatorList.account}">
											   ${operatorList.account}</option>
										   </c:forEach>
										 </select>
										 <img style="opacity:0.5;width:20px;height:20px;padding: 0 3px;border:0px;vertical-align: bottom;cursor:pointer;" title="修改工号" src="./images/icon/operator.png" onclick="updateTicketOperator()"/>
										 <img title="批量修改工号" src="./images/icon/operator.png" style="width:20px;height:20px;padding: 0 3px;vertical-align:bottom;cursor:pointer;" onclick="updateSelectTicketOperator()"/>
									</li>
									<li> <label class="before">类型：</label>
										<!--<input type="radio" name="type" value="0" checked="checked" id="normal"><label for="normal">普通</label>-->
										<input type="radio" name="type" value="1" id="importance1"><label for="importance1">重要1</label>
										<input type="radio" name="type" value="2" id="importance2"><label for="importance2">重要2</label>
									</li>
									<li> <label for="answer" class="before">应答：</label>
										 <!--<input type="radio" name="answer" value="0" checked="checked" id="onTime"><label for="onTime">按时</label>-->
										 <input type="radio" name="answer" value="1" id="timeout"> <label for="timeout">超时</label>
									
									</li>
									<li> <label for="operatorName" class="before"> 号码类型： </label>
										 <!--<input type="radio" style="margin-left:-12px;" name="numbertype" value="0" checked="checked" id="unknown"><label for="unknown">未知</label>-->
										 <input type="radio" name="numbertype" value="1" id="basic"> <label for="basic">基本</label>
										 <input type="radio" name="numbertype" value="2" id="non-basic"> <label for="non-basic">非基本</label>
									</li>
									<li><label for="checknumber" class="before">查号时长：</label>
										<!--<input type="radio" style="margin-left:-12px;" name="checknumber" value="0" checked="checked" id="non-timeout"> <label for="non-timeout">未超时</label>-->
										<input type="radio" name="checknumber" value="1" id="maxtimeout"><label for="maxtimeout">大超时</label>
										<input type="radio" name="checknumber" value="2" id="mintimeout"><label for="mintimeout">小超时</label>
									</li>
									<li> <label for="dealproblem" class="before">处理问题：</label>
									 	<!-- <input type="radio" style="margin-left:-12px;" name="dealproblem" value="0" checked="checked" id="dealnormal"> <label for="dealnormal">正常</label>-->
										 <input type="radio" name="dealproblem" value="1" id="dealgood"> <label for="dealgood">好</label>
										 <input type="radio" name="dealproblem" value="2" id="dealbad"> <label for="dealbad">坏</label>
									</li>
									<li> <label for="serviceterms" class="before"> 服务用语：</label>
										<!-- <input type="radio" style="margin-left:-12px;" name="serviceterms" value="0" checked="checked" id="servicenormal"> <label for="servicenormal">正常</label>-->
										 <input type="radio" name="serviceterms" value="1" id="servicegood"> <label for="servicegood">好</label>
										 <input type="radio" name="serviceterms" value="2" id="servicebad"><label for="servicebad">坏</label>
									</li>								
									<li> <label for="isbad" class="before"> 差错：</label>
										 <!-- <input type="radio" name="isbad" value="0" checked="checked" id="nonmistake"> <label for="nonmistake">无</label> -->
										 <input type="radio" name="isbad" value="1" id="mistake"> <label for="mistake">有</label>
									</li>
									<li> <button  type="button" onclick="writerassessment()">写入评估</button>
										 <button  type="button" onclick="removeassessment()" >清除评估</button>
									</li>
								</ul>
							<input name="ticketid" type="hidden" value="${review.ticketid}" id="ticketid" />
							<input id="reviewer" name="reviewer" type="hidden" value="${sessionScope.user.account}" />
							<input id="showTime" name="showTime" type="hidden" />
						</form>
					</td>
				</tr>
			</table>

		</div>

<ul id="contextMenu" style="display:none; position:absolute;">
	<li class="info">详单</li>
</ul>
<input id="rlinput" type="hidden" value="${param.rl}"/>
	</body>
	
<script>

showPlayer("divPlayer", "");
<%--var dvWidth= document.getElementById("divPlayer").offsetWidth--%>
<%--alert(dvWidth)--%>
<%--//document.getElementById("myGrid").offsetWidth=dvWidth--%>
<%--$("myGrid").width(dvWidth);--%>
var dvWidth= document.getElementById("divPlayer").offsetWidth;

var grid;
var data = [];

// 录音播放变量
var recordPath = '${ctxPath}/${recordPath}'
var isPlayAll = false; // 是否播放全部
var isAutoPlay = false; // 是否自动全部
var isPlaying = false; // 是否正在播放
var playIndex = -1; // 录音列表游标 
var playList = new Array();// 录音列表
//var currPlaySong = -1; // 当前录音
var recordList = new Array();//话单列表
var currTicket = -1//当前话单
var currPlay = -1//当前播放
var preState = 0;//播放器状态
var selectTickets;// 选中区域
var reqprint;



var columns = [ {id : "status",name : "",field : "status",width : 3,cannotTriggerInsert : true,formatter : Slick.Formatters.Recordmark}, 
                {id : "operatorDesk",name : "席号",field : "operatorDesk",width : 10,cssClass : "tablesytle1", headerCssClass: "gridheader", sortable : true}, 				
                {id : "operatorName",name : "工号",field : "operatorName",width : 10,cssClass : "tablesytle2", headerCssClass: "gridheader1",sortable : true}, 
                {id : "callType",name : "类型",field : "callType",width : 11,cssClass : "tablesytle2", headerCssClass: "gridheader1", sortable : true},
                {id : "callingNumber",name : "主叫",field : "callingNumber",width : 13,cssClass : "tablestyle2", headerCssClass: "gridheader1", sortable : true},
                {id : "calledNumber",name : "被叫",field : "calledNumber",width : 13,cssClass : "tablestyle2", headerCssClass: "gridheader1", sortable : true},
                {id : "startTime",name : "开始",field : "startTime",width : 22,cssClass : "tablesytle1", headerCssClass: "gridheader", sortable : true, formatter : Slick.Formatters.startTimemark},
                {id : "answerLength",name : "应答",field : "answerLength",width : 10,cssClass : "tablesytle1",sortable : true, headerCssClass: "gridheader", formatter : Slick.Formatters.Answermark}, 				
                {id : "establishTime",name : "通话",field : "establishTime",width : 15,cssClass : "tablesytle1",sortable : true, headerCssClass: "gridheader", formatter : Slick.Formatters.Timemark},
                {id : "stopTime",name : "结束",field : "stopTime",width : 15,cssClass : "tablesytle1",sortable : true, headerCssClass: "gridheader", formatter : Slick.Formatters.Timemark},
                {id : "result",name : "评估",field : "result",width : 12,cssClass : "tablestyle2", headerCssClass: "gridheader", formatter : Slick.Formatters.Resultmark},
                {id : "important",name : "重要",field : "important",width : 6,cssClass:"tablestyle3", headerCssClass: "gridheader"}
              ];

var options = {
                 enableAddRow : true,
	             enableRowReordering : false,
	             enableCellNavigation : true,
	             forceFitColumns : true,
	             multiColumnSort : true,
	             autoWidth:true
               };

function requiredFieldValidator(value) {
	if (value == null || value == undefined || !value.length) {
		return {
			valid : false,
			msg : "This is a required field"
		};
	} else {
		return {
			valid : true,
			msg : null
		};
	}
}


function filterData()
{
	var tmp = new Array();
	for(k=0;k<data.length;k++){
		if(recordList.length>0){
			data[k].savefilename=getFileName(data[k].sequenceNumber);
		}
	
		if(data[k].result.length > 0){
			tmp = data[k].result.split(",");
			data[k].important = (tmp[1] == "1") ? "!" : (tmp[1] == "2") ? "!!" : "";
			data[k].result = tmp[0] + "," + tmp[2] + "," + tmp[3] + "," + tmp[4] + "," + tmp[5] + "," + tmp[6] + "," + tmp[7] + "," + tmp[8];
		}
		
		data[k].detailbutton = k;
	}
}


function getFileName(sequenceNumber)
{
	if(recordList==null||recordList.length==0){
		return "";
	}
	for(i=0;i<recordList.length;i++){
		if(sequenceNumber==recordList[i].ticketid){
			savefilename=recordList[i].savefilename;
			//recordList.splice(i);
			return savefilename;
		}
	}
	return "";
}

function printdetailinfo(req){
	$.ajax({
		url: "${ctxPath}/callticket/exportandprint.do",
		type: "POST",
		dataType: "json",
		data: reqprint,
		success: function(meg){
			if(meg[0].ok == "1" && meg[0].failure == "0"){
				$.colorbox({opacity: 0, closeButton: false, innerWidth: "180px", innerHeight: "130px", html: dialogsuccess("操作成功！")});
			}
			else{
				$.colorbox({opacity: 0, closeButton: false, html: dialogerror("操作失败！", meg[0].cause)});
			}
		}
	});
}

function getdetailcontext(dd, current_row){

	var answerLength = (dd[current_row].answerLength == null || dd[current_row].answerLength =="")?"0000":dd[current_row].answerLength.split(":")[1] + dd[current_row].answerLength.split(":")[2];
	reqprint = "operatorName=" + dd[current_row].operatorName + "&callType=" + dd[current_row].callType
			+ "&callingNumber=" + dd[current_row].callingNumber + "&calledNumber=" + dd[current_row].calledNumber
			+ "&answerLength=" + getsec(answerLength) + "(秒)&detail=" + dd[current_row].detail;

	return "<div id='div_print' style='overflow: auto; height: 405px; min-width: 300px; margin-left: 10px; font:normal 0.7em Sans Serif, Verdana, 微软雅黑; -moz-user-select: none;'>"
			+ "<p>=======================</p>"
			+ "<p>呼叫处理流程</p>"
			+ "<p>=======================</p>"
			+ "<p><span>工号	</span><span>"+ dd[current_row].operatorName +"</span></p>"
			+ "<p><span>呼叫类别	</span><span>"+ dd[current_row].callType +"</span></p>"
			+ "<p><span>主叫号码	</span><span>"+ dd[current_row].callingNumber +"</span></p>"
			+ "<p><span>被叫号码	</span><span>"+ dd[current_row].calledNumber +"</span></p>"
			+ "<p><span>应答时限	</span><span>"+ getsec(answerLength) +"</span>(秒)</p> <br/>"
			+ "<p>" + dd[current_row].detail.replace(/\n/g, "<br/>") + "</p>"
			+ "</div>"
			+ "<div style='position:absolute;top:410px;left: 10px'><span class='button detailbutton' onclick='printdiv()'>打印</span><span class='button detailbutton' onclick='parent.jQuery.colorbox.close();'>关闭</span></div>";
			
}

$(function () {
  var rl_ = $("#rlinput").val();
   
  initSilckgrid(rl_);
  
});

function initSilckgrid(mark){
		var callTicketsQueryLimit=${callTicketsQueryLimit}
		var recordcount = ${recordcount}

		if(callTicketsQueryLimit>0 && mark == '')
		{
			$.colorbox({opacity: 0, closeButton: false, innerWidth: "250px", innerHeight: "70px", html: "<div style='margin-left: 30px;font:normal 1em Sans Serif, Verdana, 微软雅黑;'>本次查询共"+ recordcount +"条记录</div><div style='margin-left: 30px; margin-top:10px;font:normal 1em Sans Serif, Verdana, 微软雅黑;'>最多只显示"+callTicketsQueryLimit+"行记录</div>"});
		}
		else if(recordcount > 0){
			$.colorbox({opacity: 0, closeButton: false, innerWidth: "250px", innerHeight: "50px", html: "<div style='margin-left: 50px; margin-top:10px;font:normal 1em Sans Serif, Verdana, 微软雅黑;'>本次查询共"+ recordcount +"条记录</div>"});
		}
		
		data =${data}
		
		recordList=${recordList}
		
		
		filterData();
		
		enableButton('searchButton',true);
		
	  	grid = new Slick.Grid("#myGrid", data, columns, options);
	
	  	grid.setSelectionModel(new Slick.RowSelectionModel());
	  	
		var moveRowsPlugin = new Slick.RowMoveManager();
	  
	  	moveRowsPlugin.onBeforeMoveRows.subscribe(function (e, data) {
	     for (var i = 0; i < data.rows.length; i++) {
	       // no point in moving before or after itself
	       if (data.rows[i] == data.insertBefore || data.rows[i] == data.insertBefore - 1) {
	         e.stopPropagation();
	         return false;
	       }
	      }
	      return true;
	     });
		
	  
	  grid.onDblClick.subscribe(function (e, args) {
		  	cl("in onDblClick");
		   	var selectedRows = grid.getSelectedRows();
		   	playList = new Array();
		   	playIndex=0;
		   
<%--			   	for (var i = 0; i <selectedRows.length; i++) {--%>
<%--			   		setRecordUrl(data[selectedRows[i]].sequenceNumber,data[selectedRows[i]].savefilename,selectedRows[i]);--%>
<%--			    }--%>
			   	for (var i = selectedRows[0]; i <data.length; i++) {
			   		
			   		setRecordUrl(i,data[i].savefilename)
			   	}
			   	cl("playList[0][1]:" + playList[0][1] + " " + "playList[0][0]:" + playList[0][0]);
			   	playrecord(playList[0][1],playList[0][0]);
			   	getReview(playList[0][0]);
      			
		   		//cl(e);
		   	
   	  });
	  
	  
	  
	  grid.onSelectedRowsChanged.subscribe(function (e, args) {
	  		cl("in onSelectedRowsChanged");
		   	var rowindex = grid.getSelectedRows();
		   	$("#currHide").val(rowindex);
		   	$("#showTime").val("0.0");
		   	$("#displaytime").text("0.0");
		   		var selectedRows = grid.getSelectedRows();
		   		cl("selectedRows:" + selectedRows);
		   		playList = new Array();
		   		playIndex=0;
		   		isPlayAll=true;
		   		selectedRows = selectedRows.sort(
	             	function(x,y)
	             	{
	                 	return (x - y);
	             	}
         		);
		   		selectTickets=selectedRows;
		   		showPlayer("divPlayer", "");
		   		try{
<%--		   		for (var i = 0; i <selectedRows.length; i++) {--%>
<%--		   			setRecordUrl(data[selectedRows[i]].sequenceNumber,data[selectedRows[i]].savefilename,selectedRows[i]);--%>
<%--		   		--%>
<%--		   			cl("selectedRows[i]"+selectedRows[i])--%>
<%--		    	}--%>
		   		for (var i = selectedRows[0]; i <data.length; i++) {
		   			//alert(recordList['4633'].savefilename)
			   		setRecordUrl(i,data[i].savefilename)
			   	}
		   		
		   		
		   		currTicket=selectedRows[0];

		   		getReview(selectedRows[0]);
		   	}catch(e){
		   		cl(e);
		   	}
		   	
		   	
      		
   	  });
  
      grid.onSort.subscribe(function (e, args) {
          var cols = args.sortCols;

	      data.sort(function (dataRow1, dataRow2) {
	        for (var i = 0, l = cols.length; i < l; i++) {
	          var field = cols[i].sortCol.field;
	          var sign = cols[i].sortAsc ? 1 : -1;
	          var value1 = dataRow1[field], value2 = dataRow2[field];
	          var result = (value1 == value2 ? 0 : (value1 > value2 ? 1 : -1)) * sign;
	          if (result != 0) {
	            return result;
	          }
	        }
	        return 0;
	      });
	      
	      grid.invalidate();
	      grid.render();
	    });
      
 
	
      grid.setSelectedRows([0]);
  	  currTicket=0;// 选中第一条话单为当前话单
  	  currPlay=0;
	  
  	  grid.onContextMenu.subscribe(function(e){
  	  	e.preventDefault();
  	  	var cell = grid.getCellFromEvent(e);
  	  	grid.setActiveCell(cell.row, 0);
  	  	var dd = grid.getData();
  	  	var html = getdetailcontext(dd, cell.row);
  	  	$.colorbox({opacity: 0, closeButton: false, innerWidth: "310px", innerHeight: "450px", html: html});
  	  	
  	  	$("#printbutton").click(function(){
  	  		printdiv('div_print');
  	  	});
  	  	/*$("#contextMenu")
          .data("row", cell.row)
          .css("top", e.pageY)
          .css("left", e.pageX)
          .show();
          
        $("body").one("click", function () {
        	$("#contextMenu").hide();
      	});*/
  	  });
  	  
  	 $("#contextMenu").click(function (e) {
    	if (!$(e.target).is("li")) {
      		return;
    	}
    	else if($(e.target).is("li[class=info]")){
    		var dd = grid.getData();
    		var row = $(this).data("row");
			var html = getdetailcontext(dd, row);
			$.colorbox({opacity: 0, closeButton: false, innerWidth: "500px", innerHeight: "400px", html: html});
    	}
    	
  	 });

}

function writerassessment(){
	if($("#ticketid").val()=="") return;
         var jsonuserinfo1 = $.toJSON($('#form1').serializeObject());
         cl(jsonuserinfo1);
          //jsonuserinfo=$.toJSON({"ticketid":"1","showTime":"0分0秒","reviewer":"dop"})
          jsonuserinfo = eval( "(" + jsonuserinfo1 + ")" );
          //$("#showtime").val()
         var value=$("#operatorName1").val()+","+defaultvalue(jsonuserinfo1.type)+","+
             defaultvalue(jsonuserinfo.answer)+
             ",00:"+defaultvalue(s)+":00,"+defaultvalue(jsonuserinfo.checknumber)+","+
             defaultvalue(jsonuserinfo.numbertype)+","+defaultvalue(jsonuserinfo.dealproblem)+
             ","+defaultvalue(jsonuserinfo.serviceterms)+","+defaultvalue(jsonuserinfo.isbad);
          
          var value1 = $("#operatorName1").val()+","+$("input[name=answer]:checked").val()+
          				",00:"+ convertTotimeformat($("#displaytime").text())
          				+","+defaultvalue(jsonuserinfo.checknumber)+","+
             			defaultvalue(jsonuserinfo.numbertype)+","+defaultvalue(jsonuserinfo.dealproblem)+
             			","+defaultvalue(jsonuserinfo.serviceterms)+","+defaultvalue(jsonuserinfo.isbad);
          cl("value:" + value1);
          
          jQuery.ajax( {     
            type : 'POST',     
            contentType : 'application/json',     
            url : '${ctxPath}/callticket/addAjaxView.do',     
            data : jsonuserinfo1,     
            dataType : 'json',     
            success : function(res) {
          	  	var tid=$("#currHide").val();
                    data[tid].result=value1;
                    data[tid].important = ($("input[name=type]:checked").val() == "1") ? "!" : ($("input[name=type]:checked").val() == "2") ? "!!" : "";
                     
                    grid.setData(data);
  					grid.render();
            },     
            error : function(res) {     
              alert("error")     
            }     
          });   
}
 
//write result update default is zero.
function defaultvalue(val){
	if (val == undefined )
	    return 0;
	else
		return val;
}
//设置播放列表
function setRecordUrl(selectIndex,fileNames) {
	
	if(fileNames==null||fileNames=="")
	{
		playList.push([selectIndex,""]);
		return;
	}
	var arr=fileNames.split(',');
	for (var i = 0, l = arr.length; i < l; i++) {
		cl("selectIndex:" + selectIndex + " " + "arr[" + i + "]:" + arr[i]);
		//alert(recordPath+"/"+arr[i].substring(4, 6)+"/"+arr[i]);
		playList.push([selectIndex,arr[i]]);
		//alert(arr[i])
	}
	
	
}

function playrecord(url,selectIndex) {
	
	isPlaying=true;
	ticketid=data[selectIndex].sequenceNumber;
	$("#ticketid").val(ticketid);
	$("#currHide").val(selectIndex)
	
	
	if(currPlay>-1){
		
		data[currPlay].status=false;
	}
	
	data[selectIndex].status=true;
	


	
	grid.setData(data);
	grid.render();
	grid.setActiveCell(selectIndex, 0);
	grid.scrollRowIntoView(selectIndex, false);
	deletePlayer();
	url=recordPath+"/"+url.substring(4, 6)+"/"+url;
	
	currPlay=selectIndex;
	
	currTicket==selectIndex;
	cl(url)
	showPlayer("divPlayer", url);	
}

function getReview(selectIndex)
{
	ticketid=data[selectIndex].sequenceNumber;
	$("#ticketid").val(ticketid);
	//getReview(playList[0][0],playList[0][2]);

	if(ticketid==""){
  		return;
  	}
	;
	$("#operatorName1").val(data[selectIndex].operatorName);
	cl("currentrow:" + selectIndex);
                           
  				     
        jQuery.ajax( {     
          type : 'get',
          url : '${ctxPath}/callticket/getReviewJson/'+ticketid+'.do',    
          dataType : 'json',
          success : function(res) {
      	  	res = eval( "(" + res.data + ")" );
      	  	if(res==null){
      	  			$("#form1 input[type=radio]").removeAttr("checked");
			}else{
       	  		$("input[name='type'][value='"+res.type+"']").attr("checked", true);
				$("input[name='answer'][value='"+res.answer+"']").attr("checked", true);
				$("input[name='numbertype'][value='"+res.numbertype+"']").attr("checked", true);
				$("input[name='checknumber'][value='"+res.checknumber+"']").attr("checked", true);
				$("input[name='dealproblem'][value='"+res.dealproblem+"']").attr("checked", true);
				$("input[name='serviceterms'][value='"+res.serviceterms+"']").attr("checked", true);
				$("input[name='isbad'][value='"+res.isbad+"']").attr("checked", true);
			}
          }, 
          error : function(data) {     
            alert("error")     
          }     
        });
}
//second click the radio button uncheckd
$("#form1 input[type=radio]").mousedown(function(){
            if ($(this).attr("checked") == "checked" || $(this).attr("checked")){
             $(this).click(function(){
                $(this).removeAttr("checked");
                $(this).unbind("click");
         });
         }

   });



function playSelect()
{
	if(playList.length>0){
		playIndex=0;
		playrecord(playList[0][1],playList[0][0]);
		getReview(playList[0][0]);
	}
}
function Stop(){
	isPlaying=false;
	showPlayer("divPlayer", "");
<%--	if(data!=null&&data.length>0){--%>
<%--		if(isPlaying){--%>
<%--			isPlaying=false;--%>
<%--			showPlayer("divPlayer", "");--%>
<%--			$("#stopPlay").val("恢复播放");--%>
<%--			--%>
<%--		}else{--%>
<%--			isPlaying=true;--%>
<%--			$("#stopPlay").val("停止播放");--%>
<%--			playrecord(playList[playIndex][1],playList[playIndex][0]);--%>
<%--			getReview(playList[playIndex][0]);--%>
<%--		}--%>
<%--	}--%>
			//playList = new Array();
		   	//playIndex=0;
}

/** 
* add error of style.
* while user input error data that would this element change as error style
*/
function adderrorstyle(tagsName,$context){
		var tag = $("#"+tagsName);
		tag.attr("title",$context);
}
/**
* remove error of style.
* while user would error change as right that error style would been removed. 
*/
function removeerrorstyle(tagsName){
		var $element = $("#"+tagsName);
		if($element.attr("class") == "error"){
			$element.removeAttr("title");
			$element.css({"border-color":"green","background-color":"#FFFFFF"});
		}
}
function addValidate() {
	/* 设置默认属性 */
	
	$.validator.setDefaults( {
		submitHandler : function(form) {
			enableButton('searchButton',false);
			form.submit();
		}
	});
	$.validator.addMethod("stringCheck", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
	}, "只允许英文字母、数字");
	
	$.validator.addMethod("dateformate", function(value, element) {
		//return this.optional(element) || /^\d{4}-\d{1,2}-\d{1,2}\s{1}\d{1,2}:\d{1,2}$/.test(value);
		/*if (this.optional(element) || /^(2\d\d\d)[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])(?:[\/\- ](0?[0-9]|1[0-9]|2[0-3])(?:[\/\-:](0?[0-9]|[1-5][0-9])(?:[\/\-:](0?[0-9]|[1-5][0-9]))?)?)?$/.test(value)) {
			var a = value.split(/[\.\/: \-]/g);
			$(element).val(sprintf("%04s-%02s-%02s %02s:%02s", a[0],a[1],a[2],
								   a[3]==null?"00":a[3],
								   a[4]==null?"00":a[4]));
			return true;
		} else {
			return false;
		}*/
		return this.optional(element) || /^(2\d\d\d)[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])(?:[\/\- ](0?[0-9]|1[0-9]|2[0-3])(?:[\/\-:](0?[0-9]|[1-5][0-9])(?:[\/\-:](0?[0-9]|[1-5][0-9]))?)?)?$/.test(value);
	}, "");
	
	// 联系电话(手机/电话皆可)验证   
	$.validator.addMethod("isPhone", function(value, element) {
		var length = value.length;
		//var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
			var tel = /^\d{1,19}$/;
			return this.optional(element) || (tel.test(value));

		}, function(){
			adderrorstyle("callingNumber","请输入1-19位的数字");
		});
		
	$.validator.addMethod("desk",function(value,element){
		removeerrorstyle("desk");
		var validator_desk = /^\d$/;
		return this.optional(element) || (validator_desk.test(value));
	},function(){
		adderrorstyle("desk","请输入数字");
		});

		
//自定义验证方法
    jQuery.validator.addMethod("endDate",
    function(value, element) {
        var startDate = $('#startstartTime').val();
        var endDate=$('#endstartTime').val();
        if(startDate==""||endDate=="")
        {
        	
        	return true;
        }
       // return new Date(Date.parse(startDate.replace("-", "/"))) < = new Date(Date.parse(value.replace("-", "/")));
         return ((new Date(startDate.replace(/-/g,"\/")))<= (new Date(endDate.replace(/-/g,"\/")))); 
    },
    "结束日期大于开始日期");

	// validate signup form on keyup and submit
$("#form").validate({
		//onfocusout: false,
		rules: {
			callingNumber: { isPhone:true,				
			},
			calledNumber: { isPhone:true,				
			},
			startstartTime: {
				dateformate:true			
			},
			endstartTime: {
				dateformate:true	
			},
			desk:{desk:true}
		},
		messages: {
			startstartTime: {			
			},
			endstartTime: {	
			},
			desk:{
				digits:"座位号必须是数字"
			}
		}
	});
	
}

$("#title-choice").click(function(){
	
	if ( $($(".choice-block").get(0)).attr("class") == "choice-block" ){
		$(".choice-block").attr("class","choice-hidden");	
		$(this).addClass("flipx");
	}else{
		$(".choice-hidden").attr("class","choice-block");
		$(this).removeClass("flipx");
	}
	
	//$("#startTimingPointer").offset({top: $("#startTiming").offset().top + 1, left: $("#startTiming").offset().left + 3});
});

$("#reload").click(function(){
	document.forms[0].action = "${ctxPath}/callticket/getCallTicketListForm.do?rl=1";	//document.forms[]
	document.forms[0].submit();
  //location.reload(false);
});
</script>
</html>
