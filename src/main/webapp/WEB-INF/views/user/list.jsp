<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="navigation-info">
  &gt; 시스템 관리 &gt; 사용자 관리 &gt; 사용자 목록
</div>

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>사용자 목록</h3>
  </div>
  <div class="panel-body">
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>로그인</th>
          <th>이름</th>
          <th>이메일</th>
          <th>사용자유형</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="u" items="${ list }">
          <tr data-url="edit.do?id=${u.id}">
            <td>${ u.loginName }</td>
            <td>${ u.name }</td>
            <td>${ u.email }</td>
            <td>${ u.userType }</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="">
      <a class="btn btn-primary btn-sm" href="create.do">사용자 등록</a>
    </div>
  </div>
</div>  
