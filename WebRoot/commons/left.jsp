<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>无标题页</title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <%@ include file="/commons/taglibs.jsp"%>

    <script src="${ctxPath}/scripts/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $(".menu-item").click(function() {
                $(".menu-item").removeClass('selected');
                $(this).addClass('selected');
            });

            $(".menu-item:first").addClass('selected');
        });

    </script>

	<style type=text/css>

body {
    font: normal 12pt "微软雅黑";
    overflow:hidden;
}

.sidebar {
    overflow: hidden;
    position: relative;
    color: #EDEDED;
    float: left;
    width: 200px;
    background: none repeat scroll 0 0 #272727;
    background: -moz-linear-gradient(center top , #e7e7d7 0pt, #d0d0c3 100%) repeat scroll 0 0 #d8d8d8;
    height:100%;
    padding-left: 0;
}

.sidebar li {
    background: url(../images/texture/lightgray.png);
}
.menu-item {
    background: -moz-linear-gradient(center top , #e7e7d7 0pt, #d8d8ce 100%) repeat scroll 0 0 #272727;
    border-top: 1px solid #eeeeee;
    border-bottom: 1px solid #bebebe;
    border-right: 4px solid transparent;
    color: #666666;
    cursor: pointer;
    display: block;
    margin: 0;
    height: 32px;
    padding: 10px 0 0 20px;
    position: relative;
    font-size: 11pt;
    text-decoration: none;
    letter-spacing: 0.03em;
}
.menu-item .thumb {
    width: 25px;
    height: 25px;
    vertical-align: middle;
}
.menu-item .name {
    display: inline-block;
    line-height: 1.2em;
    padding-left: 20px;
}
.menu-item:hover {
    background: -moz-linear-gradient(center top , #5959b9 0pt, #384888 100%);
    border-right-color: #cc882b;
    font-size: 12pt;
    color: #eee;
}
.menu-item.selected, .menu-item.selected:hover {
    border-right-color: #C4302B;
}
.menu-item.selected, .menu-item:focus {
    background: none;
    color: #555;
}
    </style>

</head>

<body>
    <ul class="sidebar">
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/user/operatorList.do?p_orderBy=account&p_orderDir=asc')" target="mainFrame">
            <img class="thumb" src="../images/icon/users.png"/>
            <span class="name">话务员账号</span>
        </a></li>
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/user/subscriberList.do?p_orderBy=number&p_orderDir=asc')" target="mainFrame">
            <img class="thumb" src="../images/icon/phone.png"/>
            <span class="name">电话用户管理</span>
        </a></li>
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/callticket/getCallTicketListForm.do?p_orderBy=sequenceNumber&p_orderDir=desc')" target="mainFrame">
            <img class="thumb" src="../images/icon/check.png"/>
            <span class="name">话单管理</span>
        </a></li>
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/report/getOperatorRecordStatistics.do')" target="mainFrame">
            <img class="thumb" src="../images/icon/chart.png"/>
            <span class="name">统计报表</span>
        </a></li>
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/report/getOperatorRecordDetail.do')" target="mainFrame">
            <img class="thumb" src="../images/icon/statistics.png"/>
            <span class="name">详细报表</span>
        </a></li>
        <!-- <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/maintain/getCallRecordList.do?p_orderBy=sequenceNumber&p_orderDir=desc')" target="mainFrame">
            <img class="thumb" src="../images/icon/floppy.png"/>
            <span class="name">录音维护</span>
        </a></li>
         -->
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/bbs/getBbsList.do')" target="mainFrame">
            <img class="thumb" src="../images/icon/messege.png"/>
            <span class="name">席间消息</span>
        </a></li>
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/system/accessCodeManager.do')" target="mainFrame">
            <img class="thumb" src="../images/icon/settings.png"/>
            <span class="name">接入码配置</span>
        </a></li>
        <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/system/callTypeManager.do')" target="mainFrame">
            <img class="thumb" src="../images/icon/gear.png"/>
            <span class="name">呼叫类型配置</span>
        </a></li>
         <li><a class="menu-item" href="javascript:self.parent.changeFrame('${ctxPath}/backupRecord/readBackUpfile.do')" target="mainFrame">
            <img class="thumb" src="../images/icon/gear.png"/>
            <span class="name">录音文件的备份</span>
         </a></li>
       
    </ul>
</body>
</html>
