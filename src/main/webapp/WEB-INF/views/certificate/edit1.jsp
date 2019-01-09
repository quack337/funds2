<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 증서 &gt; <a href="list">기부증서 목록</a> &gt; 기부증서 등록
</div>

<form:form modelAttribute="certificate">

<div class="panel panel-default shadow w800">
  <div class="panel-heading">
    <h3>기부증서 등록</h3>
  </div>
  <div class="panel-body">  
    <div class="pull-right mb4">
      <button class="btn btn-primary btn-sm" type="submit">저장</button>
      <a class="btn btn-info btn-sm" href="list?${pagination.queryString}">목록으로</a> 
    </div>

    <table class="table table-bordered lbw120 pd4">  
      <tr>
        <td class="lb">증서번호</td>
        <td>${ certificate.certificateNo }</td>
        <td class="lb">발급일</td>
        <td><form:input path="createDate" class="date" /></td>
      </tr>
      <tr>
        <td class="lb">회원번호</td>
        <td><form:input path="personNo" /></td>
        <td class="lb">회원</td>
        <td><form:input path="personName" /></td>
      </tr>
      <tr>
        <td class="lb">금액</td>
        <td><form:input path="amount" class="money w100" /></td>
        <td class="lb">발급인</td>
        <td>${ certificate.userName }</td>
      </tr>
      <tr>
        <td class="lb">기부처</td>
        <td colspan="3">
          <c:if test="${ not empty corporates }">
            <form:select path="corporateId">
              <form:options itemValue="id" itemLabel="name" items="${ corporates }" />
            </form:select>
          </c:if>
          <c:if test="${ empty corporates }">
            <form:select path="corporateId">
              <form:option value="${ corporate.id }" label="${ corporate.name }"/>
            </form:select>
          </c:if>        
        </td>
      </tr>
      <tr>
        <td class="lb">내용</td>
        <td colspan="3"><form:input path="body" class="w600" /></td>
      </tr>
    </table>
  
  </div>
</div>  

</form:form>
