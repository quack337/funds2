<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="navigation-info">
  &gt; 내정보 수정
</div>

<form:form method="post" modelAttribute="user">

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>내 정보</h3>
  </div>
  <div class="panel-body">    
    <table class="table table-bordered table-condensed lbw120 pd4">
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
        <td>${ user.userType }</td>
      </tr>
      <tr>
        <td class="lb">활성화</td>
        <td>${ user.enabled }</td>
        <td  class="lb"></td>
        <td></td>    
      </tr>
    </table>

    <div>
      <button class="btn btn-primary btn-sm" type="submit" name="cmd" value="saveInfo">내 정보 저장</button>
    </div>    
  </div>
</div>  
  
<hr />

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>비밀번호 변경</h3>
  </div>
  <div class="panel-body">    
    <table class="table table-bordered lbw120 pd4">
      <tr>
        <td class="lb">비밀번호</td>
        <td><form:password path="password1" />
        <td class="lb">비밀번호확인</td>
        <td><form:password path="password2" />
      </tr>
      <tr>
        <td colspan="4" style="background-color: #eee; padding: 10px;">
          비밀번호는 7자 이상이어야 하고, 숫자, 영어소문자, 영어대문자 중 3가지 이상을 조합해야 합니다.</td>
      </tr>
    </table>
    <div class="">
      <button class="btn btn-primary btn-sm" type="submit" name="cmd" value="savePassword">비밀번호 저장</button>
    </div>
  </div>
</div>  

</form:form>