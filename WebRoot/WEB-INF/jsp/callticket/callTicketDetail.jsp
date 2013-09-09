<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Call - Tickets Detail</title>
		<%@ include file="/commons/taglibs.jsp"%>

		<%@ include file="/commons/meta.jsp"%>

		<style type="text/css">
.a-center td {
	text-align: left;;
}
</style>
	</head>
	<body style="background: #ffffff;">
		<div id="container" style="width: 100%; margin: 0px;">

			<div id="content" style="margin-top: 0px; width: 100%; padding: 0px;">
				<div id="box">
					<form id="form" action="" method="post">
						<fieldset id="personal">
							<legend>
								话单详情
							</legend>
							<c:if test="${callTicket == null}">
								<div style="text-align: center;">
									没有记录
								</div>
							</c:if>

							<c:if test="${callTicket != null}">
								<table>
									<tbody>
										<tr class="a-center">
											<td>
												流水号 : ${callTicket.sequenceNumber }
											</td>
											<td>
												发起方 : ${callTicket.originator }
											</td>
											<td>
												开始时间 :
												<fmt:formatDate value="${callTicket.startTime}"
													pattern="yyyy-MM-dd hh:mm:ss" />
											</td>
										</tr>
										<tr class="a-center">
											<td>
												工号 : ${callTicket.operatorName }
											</td>
											<td>
												座席 : ${callTicket.operatorDesk }
											</td>
											<td>
												结束时间 :
												<fmt:formatDate value="${callTicket.stopTime}"
													pattern="yyyy-MM-dd hh:mm:ss" />
											</td>
										</tr>
										<tr class="a-center">
											<td>
												主叫号码 : ${callTicket.callingNumber }

											</td>
											<td>
												呼叫级别 : ${callTicket.callPriority }
											</td>
											<td>
												应答时间 :
												<fmt:formatDate value="${callTicket.answerTime }"
													pattern="yyyy-MM-dd hh:mm:ss" />
											</td>
										</tr>
										<tr class="a-center">
											<td>
												被叫号码 : ${callTicket.calledNumber }

											</td>
											<td>
												呼叫类型 : ${callTicket.callType }
											</td>
											<td>
												通话时间 :
												<fmt:formatDate value="${callTicket.establishTime }"
													pattern="yyyy-MM-dd hh:mm:ss" />
											</td>
										</tr>
										<tr class="a-center">
											<td colspan="3">
												详单 :
												<br />
												${callTicket.detail}

											</td>
										</tr>
									</tbody>
								</table>
							</c:if>
						</fieldset>
					</form>
				</div>


			</div>

		</div>
	</body>
</html>
