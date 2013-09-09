<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/widgets/My97DatePicker/WdatePicker.js">
</script>
		<script language="javascript">
var se, m = 0, h = 0, s = 0, ss = 1;
function second() {

	if ((ss % 100) == 0) {
		s += 1;
		ss = 1;
	}
	if (s > 0 && (s % 60) == 0) {
		m += 1;
		s = 0;
	}
	if (m > 0 && (m % 60) == 0) {
		h += 1;
		m = 0;
	}
	t = h + "时" + m + "分" + s + "秒" + ss + "毫秒";
	document.getElementById("showtime").value = t;
	ss += 1;

}

function startclock() {
	clearInterval(se);
	se = setInterval("second()", 1);
} //这句已经过更新
function pauseclock() {
	clearInterval(se);
}
function stopclock() {
	clearInterval(se);
	ss = 1;
	m = h = s = 0;
}
</script>


		<script type="text/javascript">
$(document)
		.ready(
				function() {
					$("#ta tr").mouseover(function() {
						$(this).addClass("over");
					});
					$("#ta tr").mouseout(function() {
						$(this).removeClass("over");
					});

					$("#ta tr:even").addClass("double");

					$("#demo4")
							.click(
									function() {
										$
												.post(
														"${pageContext.request.contextPath}/callrecord/getCallRecordRetrunJson/1.do",
														null,
														function(map) {
															var json = eval(map);
															alert(json);
														});
									});
				});
</script>
		<style type="text/css">
th {
	background: gray;
	color: white;
}

table {
	width: 30em;
	height: 10em;
}

td {
	border-bottom: 1px solid #95bce2;
	text-align: center;
}

tr.over td {
	background: #D6E8F8;
	font-weight: bold;
}

tr.double td {
	background: #DAF3F1;
}
</style>
	</head>
	<body>

		<input name="demo4" id="demo4" type="button" value="demo4" />

		<input name="endTime"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"
			size="24" />


		<input name="s" type="button" value="开始计时" onclick="startclock()">
		<input name="s" type="button" value="暂停计时" onclick="pauseclock()">
		<input name="s" type="button" value="停止计时" onclick="stopclock()">
		<input name="showtime" style="color: #ff0000; width: 200px;"
			id="showtime" type="text" value="0时0分0秒">



		<table id="ta" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<th>
					姓名
				</th>
				<th>
					年龄
				</th>
				<th>
					籍贯
				</th>
				<th>
					手机
				</th>
			</tr>
			<tr>
				<td>
					alp
				</td>
				<td>
					zweizig
				</td>
				<td>
					上海
				</td>
				<td>
					13088888888
				</td>
			</tr>
			<tr>
				<td>
					alp
				</td>
				<td>
					zweizig
				</td>
				<td>
					上海
				</td>
				<td>
					13088888888
				</td>
			</tr>

		</table>
	</body>
</html>
