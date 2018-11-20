<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 기타 &gt; 일정관리 &gt; 일정목록 
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>일정목록</h3> 
  </div>
  <div class="panel-body">

      <form:form id="todo" modelAttribute="pagination" method="get">
      
      <input type="hidden" name="pg" value="1" />
            
      <div class="pull-right mb4">
        <a href="create.do?${pagination.queryString}" class="btn btn-primary btn-sm">일정 등록</a>
      </div>      
      
      <span>정렬순서:</span>
      <form:select path="od" data-auto-submit="true">
        <form:option value="0" label="일정 순서" />
        <form:option value="1" label="등록 순서" />
      </form:select>
      
      <span class="block ml10">기간:</span>
      <form:input path="sd" class="startDt" /> ~
      <form:input path="ed" class="endDt" />
          
      <button type="submit" class="btn btn-default ml10 btn-sm">조회</button>
      <c:if test="${ not empty pagination.st || not empty pagination.sd }">
         <a href="list.do" class="btn btn-default btn-sm">조회조건 취소</a>
      </c:if>
      
      <table id="todo" class="table table-bordered mt4 pd6">
        <thead>
          <tr>
            <th class="nowrap">작성자</th>
            <th class="nowrap">등록일</th>
            <th class="nowrap">일정</th>
            <th class="nowrap">확인</th>
            <th class="right nowrap">알림</th>
            <th class="nowrap">반복</th>
            <th class="nowrap">내용</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="todo" items="${ list }">
            <tr data-url="edit.do?id=${todo.id}&${pagination.queryString}">
              <td class="nowrap">${ todo.userName }</td>
              <td class="nowrap">${ todo.createDate }</td>
              <td class="nowrap">${ todo.dueDate2 }</td>
              <td class="nowrap">${ todo.confirmDate }</td>
              <td class="nowrap right">${ todo.alertBefore }일전</td>
              <td class="nowrap">${ todo.repeat == 2 ? "매년" : todo.repeat == 1 ? "매월" : "" }</td>
              <td>${ todo.message }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      
      <form:select path="sz" data-auto-submit="true">
        <form:option value="10" />
        <form:option value="15" />
        <form:option value="30" />
        <form:option value="100" />
      </form:select>
      
      <ul class="pagination">
        <c:forEach var="page" items="${ pagination.pageList }">
          <li class='${ page.cssClass }'><a data-page="${ page.number }">${ page.label }</a></li>
        </c:forEach>
      </ul>
      
      </form:form>

  </div>
</div>
