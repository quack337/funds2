<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="R" value="/" />

<style>
table#sponsorLog td:nth-child(1) { white-space: nowrap; }
table#sponsorLog td:nth-child(2) { white-space: nowrap; }
table#sponsorLog td:nth-child(3) { white-space: nowrap; }
</style>

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 변경 이력
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab7" value="active" />
    <%@include file="../_tab2.jsp" %> 
     
    <my:scrollableTable tagId="sponsorLog">
        <jsp:attribute name="header">
           <tr>
	          <th>변경일</th>
	          <th>실행인</th>
	          <th>항목</th>
	          <th>변경전</th>
	          <th>변경후</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
	        <c:set var="prev" value="x" />
	        <c:forEach var="log" items="${ list }">
	          <tr>
	            <c:if test="${ prev eq log.writeTime }">
	              <td style="border-top: none;"></td><td style="border-top: none;"></td>
	            </c:if>
	            <c:if test="${ prev ne log.writeTime }">
	              <td><fmt:formatDate value="${ log.writeTime }" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
	              <td>${ log.userName }</td>
	            </c:if>
	            <td>${ log.field }</td>
	            <td>${ log.value1 }</td>
	            <td>${ log.value2 }</td>
	          </tr>
	          <c:set var="prev" value="${ log.writeTime }" />
	        </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>
         
    <c:if test="${ list.size() == 0 }">
        <div>변경 이력이 없습니다.</div>
    </c:if>  

  </div>
</div>    
