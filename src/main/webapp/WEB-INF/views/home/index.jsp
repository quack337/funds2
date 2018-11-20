<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:url var="R" value="/" />

<sec:authorize access="authenticated">

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>일정목록</h3> 
  </div>
  <div class="panel-body">

	  <table id="todo" class="table table-bordered mt4 pd6">
	    <thead>
	      <tr>
	        <th>작성자</th>
	        <th>일정</th>
	        <th>내용</th>
	        <th></th>
	      </tr>
	    </thead>
	    <tbody>
	      <c:forEach var="todo" items="${ todos }">
	        <tr data-url="edit.do?id=${todo.id}&${pagination.queryString}">
	          <td class="nowrap">${ todo.userName }</td>
	          <td class="nowrap">${ todo.dueDate2 }</td>
	          <td>${ todo.message }</td>
	          <td><a href="${R}home/confirm.do?id=${todo.id}" class="btn btn-default btn-sm">확인</a>
	        </tr>
	      </c:forEach>
	      <c:if test="${ todos.size() <= 1 }">
	        <tr>
	          <td colspan="4">일정이 없습니다</td>
	        </tr>
	      </c:if>
	    </tbody>
	  </table>
	  <a class="btn btn-primary" href="${R}todo/list.do">일정관리</a>
  </div>
</div>

</sec:authorize>
