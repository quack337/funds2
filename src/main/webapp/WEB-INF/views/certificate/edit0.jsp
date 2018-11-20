<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>장학증서</h1>
<hr />

<form:form modelAttribute="certificate">

<div class="pull-right mb4">
  <button class="btn btn-primary" type="submit">저장</button>
  <a class="btn btn-info" href="list.do?${pagination.queryString}">취소</a>
</div>

<table class="table table-bordered lbw150">  
  <tr>
    <td class="lb">증서번호</td>
    <td>${ certificate.certificateNo }</td>
    <td class="lb">발급일</td>
    <td><form:input path="createDate" class="date" /></td>
  </tr>
  <tr>
    <td class="lb">학과</td>
    <td><form:input path="department" class="w300" /></td>
    <td class="lb">학번</td>
    <td><form:input path="personNo" /></td>
  </tr>
  <tr>
    <td class="lb">이름</td>
    <td><form:input path="personName" /></td>
    <td class="lb">발급인</td>
    <td>${ certificate.userName }</td>
  </tr>
  <tr>
    <td class="lb">내용</td>
    <td colspan="3"><form:input path="body" class="w600" /></td>
  </tr>
</table>

</form:form>
