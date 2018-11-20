<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" modelAttribute="user">

<div class="navigation-info">
  &gt; 시스템 관리 &gt; 사용자 관리 &gt; <a href="list.do">사용자 목록</a> &gt; 사용자 등록
</div>

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>사용자 등록</h3>
  </div>
  <div class="panel-body">
    <table class="table table-bordered lbw150">
      <tr>
        <td class="lb">로그인</td>
        <td><form:input path="loginName" />
        <td class="lb">이름</td>
        <td><form:input path="name" />
      </tr>
      <tr>
        <td class="lb">이메일</td>
        <td><form:input path="email" />
        <td class="lb">사용자유형</td>
        <td><form:select path="userType">
            <form:option value="직원" />
            <form:option value="관리자" />
            </form:select>
        </td>        
      </tr>
      <tr>
        <td class="lb">비밀번호</td>
        <td><form:password path="password1" />
        <td class="lb">비밀번호확인</td>
        <td><form:password path="password2" />
      </tr>
    </table>
    <div class="">
      <button class="btn btn-primary" type="submit">사용자 저장</button>
      <a class="btn btn-gray" href="list.do">목록으로 나가기</a>
    </div>
  </div>
</div>

</form:form>