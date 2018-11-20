<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a> 
  &gt; 약정 관리 &gt; <a href="list.do?sid=${ sponsor.id }&${ pagination.queryString }">약정 목록</a> &gt; 약정 등록
</div>

<form:form method="post" modelAttribute="commitment">

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">
  
    <c:set var="tab2" value="active" />
    <%@include file="../_tab2.jsp" %> 

    <table class="table table-bordered lbw120 pd4 mt10">
      <tr>
        <td class="lb">약정번호</td>
        <td>${ commitment.commitmentNo }</td>
        <td class="lb">납입방법</td>
        <td><form:select path="paymentMethodId" itemValue="id" itemLabel="codeName" items="${ paymentMethods }" /></td>
      </tr>
      <tr>
        <td class="lb">약정일</td>
        <td><form:input path="commitmentDate" class="date" /></td>
        <td class="lb">월납입액</td>
        <td><form:input path="amountPerMonth" class="money" /></td>
      </tr>
      <tr>
        <td class="lb">시작일</td>
        <td><form:input path="startDate" class="startDt" /></td>
        <td class="lb">은행</td>
        <td><form:select path="bankId" itemValue="id" itemLabel="codeName" items="${ banks }" /></td>
      </tr>
      <tr>
        <td class="lb">종료일</td>
        <td><form:input path="endDate" class="endDt" /></td>
        <td class="lb">계좌번호</td>
        <td><form:input path="accountNo" /></td>
      </tr>
      <tr>
        <td class="lb">월납입일</td>
        <td><form:input path="paymentDay" /></td>
        <td class="lb">예금주 / 생년월일</td>
        <td><form:input path="accountHolder" /> 
            <form:input path="birthDate" placeholder="YYMMDD 6자리입력" /></td>
      </tr>
      <tr>
        <td class="lb">기부목적</td>
        <td colspan="3">
          <c:set var="paramObj" value="${ commitment }" />
          <%@include file="../_donationPurposeInput.jsp" %>
        </td>
      </tr>
      <tr>  
        <td class="lb">비고</td>
        <td colspan="3"><form:textarea path="etc" class="w600 h100" /></td>
      </tr>
    </table>  
    
    <div class="">
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">약정 저장</button>
      <a href="list.do?sid=${ sponsor.id }&${ pagination.queryString }" class="btn btn-gray btn-sm">약정 목록으로</a>
    </div>
  </div>
</div>    

</form:form>

<%@include file="../_donationPurposeDialog.jsp" %>