<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:set var="page" value="${sessionScope.page}" />
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="url" value="${param.url}" />
<c:set var="urlParams" value="${param.urlParams}" />
<c:set var="pathurl" value="${path}/${url}" />
<table width="100%">


 <tr>
	<td align="center">
		共${page.totalCount}条记录&nbsp;&nbsp;共${page.totalPage}页&nbsp;&nbsp;每页显示${page.everyPage}条&nbsp;&nbsp;
		当前第${page.currentPage}页&nbsp;&nbsp;&nbsp;
		<c:choose>
			<c:when test="${page.hasPrePage eq false}">
 			    &lt&lt首页&nbsp;&lt上页&nbsp;
 			</c:when>
			<c:otherwise>
				<a href="${pathurl}?&pageAction=first${urlParams}">&lt&lt首页</a>&nbsp;&nbsp;
				<a href="${pathurl}?pageAction=previous${urlParams}" />&lt上一页</a>
			</c:otherwise>
		</c:choose>
		&nbsp;||&nbsp;
		<c:choose>
			<c:when test="${page.hasNextPage eq false}">
 			    &nbsp;下页&gt&nbsp;尾页&gt&gt
 			</c:when>
			<c:otherwise>
				<a href="${pathurl}?&pageAction=next${urlParams}">下一页&gt</a>&nbsp;&nbsp;
				<a href="${pathurl}?pageAction=last${urlParams}" />末页&gt&gt</a>
			</c:otherwise>
		</c:choose>
		&nbsp;&nbsp;&nbsp;
<%--		<SELECT name="indexChange" id="indexChange"--%>
<%--			onchange="getCurrentPage(this.value);">--%>
<%--			<c:forEach var="index" begin="1" end="${page.totalPage}" step="1">--%>
<%--				<option value="${index}" ${page.currentPage eq index ? "selected" : ""}>--%>
<%--					第${index}页--%>
<%--				</option>--%>
<%--			</c:forEach>--%>
<%--		</SELECT>--%>
		&nbsp;&nbsp;&nbsp;
<%--		每页显示:<select name="everyPage" id="everyPage" onchange="setEveryPage(this.value);">--%>
<%--			       <c:forEach var="pageCount" begin="5" end="${page.totalCount}" step="5">			       	    --%>
<%--						<option value="${pageCount}" ${page.everyPage eq pageCount ? "selected" : ""}>--%>
<%--							${pageCount}条--%>
<%--						</option>--%>
<%--					</c:forEach>--%>
<%--		       </select>--%>
		       
		       		每页显示:<select name="everyPage" id="everyPage" onchange="setEveryPage(this.value);">
			       <c:forEach var="pageCount" begin="5" end="20" step="5">			       	    
						<option value="${pageCount}" ${page.everyPage eq pageCount ? "selected" : ""}>
							${pageCount}条
						</option>
					</c:forEach>
		       </select>
	</td>
</tr>
<div style='display: none'>
	<a class=listlink id="indexPageHref" href='#'></a>
</div>

</table>
<!--
<div id="pager">
                    	Page <a href="${pathurl}?pageAction=previous${urlParams}"><img src="${pageContext.request.contextPath}/images/icons/arrow_left.gif" width="16" height="16" /></a> 
                    	<input size="1" value="1" type="text" name="page" id="page" /> 
                    	<a href="${pathurl}?&pageAction=next${urlParams}"><img src="${pageContext.request.contextPath}/images/icons/arrow_right.gif" width="16" height="16" /></a>of ${page.totalPage}
                    pages | View <select name="everyPage" id="everyPage" onchange="setEveryPage(this.value);">
			       					<c:forEach var="pageCount" begin="5" end="${page.totalCount}" step="5">			       	    
										<option value="${pageCount}" ${page.everyPage eq pageCount ? "selected" : ""}>
											${pageCount}条
										</option>
									</c:forEach>
		       					 </select>
                    per page | Total <strong>${page.totalCount}</strong> records found
                    </div>
 -->

<script>
function getCurrentPage(index){	
	var a = document.getElementById("indexPageHref");	
	a.href = '${pathurl}?pageAction=gopage&pageKey='+index+'${urlParams}';        
    a.setAttribute("onclick",'');          
    a.click("return false");   
}
function setEveryPage(everyPage){	
	var a = document.getElementById("indexPageHref");
	var currentPage = document.getElementById('indexChange').value;
	a.href = '${pathurl}?pageAction=setpage&pageKey='+everyPage+'${urlParams}';       
    a.setAttribute("onclick",'');          
    a.click("return false");   
}
function sortPage(sortName){
	var a = document.getElementById("indexPageHref");	
	a.href = '${pathurl}?pageAction=sort&pageKey='+sortName+'${urlParams}';      
    a.setAttribute("onclick",'');      
    a.click("return false");   
}
</script>