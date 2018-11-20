<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<c:set var="pg" value="${ pagination.queryString }" />
<c:set var="sid" value="${ sponsor.id }" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 예우 관리
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab8" value="active" />
    <%@include file="../_tab2.jsp" %> 
     
    <table class="table table-bordered mt10">
      <thead>
        <tr>
          <th>날짜</th>
          <th>실행인</th>
          <th>내용</th>
          <th>비고</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="event" items="${ list }">
          <tr data-url="edit.do?id=${event.id}&sid=${sid}&${pg}">
            <td><fmt:formatDate value="${ event.eventDate }" type="date" pattern="yyyy-MM-dd"/></td>
            <td>${ event.userName }</td>
            <td>${ event.description }</td>
            <td>${ event.etc }</td>
          </tr>
        </c:forEach>
        <c:if test="${ list.size() == 0 }">
            <tr><td colspan="4">자료가 없습니다.</td></tr>
        </c:if>        
      </tbody>
    </table>
    
    <div class="">
      <a href="create.do?sid=${sid}&${pg}" class="btn btn-primary btn-sm">예우 등록</a>
    </div>
    
  </div>
</div>    