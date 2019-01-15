<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" modelAttribute="user">

<div class="navigation-info">
  &gt; 시스템 관리 &gt; 사용자 관리 &gt; <a href="list">사용자 목록</a> &gt; 사용자 등록
</div>

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>사용자 등록</h3>
  </div>
  <div class="panel-body">
    <div class="right mt10 mb10">
      <button class="btn btn-primary btn-sm" type="submit">사용자 저장</button>
      <a class="btn btn-gray btn-sm" href="list">목록으로 나가기</a>
    </div>

    <table class="table table-bordered lbw150 pd4">
      <tr>
        <td class="lb">로그인</td>
        <td><form:input path="loginName" /></td>
        <td class="lb">이름</td>
        <td><form:input path="name" /></td>
      </tr>
      <tr>
        <td class="lb">이메일</td>
        <td><form:input path="email" /></td>
        <td class="lb">사용자유형</td>
        <td><form:select path="userType">
            <form:option value="사용자" />
            <form:option value="관리자" />
            </form:select>
        </td>        
      </tr>
      <tr>
        <td class="lb">비밀번호</td>
        <td><form:password path="password1" /></td>
        <td class="lb">비밀번호확인</td>
        <td><form:password path="password2" /></td>
      </tr>
      <tr>
        <td  class="lb">소속</td>
        <td>
            <form:select path="corporateId">
                <form:option value="" label="" />
                <form:options items="${ corporates }" itemLabel="name" itemValue="id" />
            </form:select>
        <td class="lb">활성화</td>
        <td><form:checkbox path="enabled" /></td>
      </tr>
    </table>
    <div class="alert alert-info">
        <p><i class="glyphicon glyphicon-info-sign"></i> <b>비밀번호 작성 규칙</b></p>
        <p>정보보안 및 개인정보 보호 규정에 의하여 비밀번호는 7문자 이상이어야 하고,<br/>
           숫자, 소문자, 대문자를 모두 포함해야 합니다.</p>    
    </div>
  </div>
</div>

</form:form>