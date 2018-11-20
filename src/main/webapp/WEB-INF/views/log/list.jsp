<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="navigation-info">
  &gt; 시스템 관리 &gt; 로그기록 관리 &gt; 로그기록 목록
</div>

<form:form method="get" modelAttribute="pagination" class="mt4">
  <input type="hidden" name="pg" value="1" />

<div class="panel panel-default shadow w1000">
  <div class="panel-heading">
    <h3>로그기록 목록</h3>
  </div>
  <div class="panel-body">  
    <div class="pull-right">
      <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>선택항목 삭제</button>
    </div>    
 
    
      <div class="form-inline">
        <form:select path="od"  data-auto-submit="true">
          <form:option value="0" label="정렬순서" />
          <form:option value="1" label="IP" />
          <form:option value="2" label="URL" />
          <form:option value="3" label="카테고리" />
        </form:select>
    
        <form:select path="ss" class="ml50">
          <form:option value="0" label="조회조건" />
          <form:option value="1" label="IP" />
          <form:option value="2" label="URL" />
          <form:option value="3" label="카테고리" />
          <form:option value="4" label="내용" />
        </form:select>
        <form:input path="st" />
        <button type="submit" class="btn btn-sm btn-info">조회</button>
        <c:if test="${ pagination.ss != 0 }">
          <a href="list.do" class="btn btn-small btn-default">조회조건 취소</a>
        </c:if>
      </div>
    
      <table class="table table-bordered mt4 pd4">
        <thead>
          <tr>
            <th><input type="checkbox" /></th>
            <th>사용자</th>
            <th>날짜</th>
            <th>IP</th>
            <th>category</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="p" items="${ list }">
            <tr data-url="detail.do?id=${p.id}&${pagination.queryString}">
              <td><input type="checkbox" name="id" value="${ p.id }" /></td>
              <td>${ p.currentUser }</td>
              <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ p.writeTime }" /></td>
              <td>${ p.ip }</td>
              <td>${ p.category }</td>
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
      
  </div>
</div>
      
 </form:form>
 
 