<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 증서 &gt; <a href="list.do">장학증서 목록</a> &gt; 장학증서 등록
</div>

<form:form modelAttribute="certificate">

<div class="panel panel-default shadow w800">
  <div class="panel-heading">
    <h3>장학증서 등록</h3>
  </div>
  <div class="panel-body">  
    <table class="table table-bordered lbw120 pd4">  
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

    <div class="pull-right mb4">
      <button class="btn btn-primary btn-sm" type="submit">저장</button>
      <a class="btn btn-info btn-sm" href="list.do?${pagination.queryString}">목록으로</a>
    </div>
  </div>
</div>  

</form:form>
