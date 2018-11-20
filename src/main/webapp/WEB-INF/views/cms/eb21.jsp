<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<div class="navigation-info">
  &gt; 금융연동 &gt; EB21 생성
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>EB21 생성</h3> 
  </div>
  <div class="panel-body">

      <form:form class="mb4" modelAttribute="wrapper">
        <span>출금일:</span>
        <form:input path="map[paymentDay]" class="w50" />
        <button type="submit" class="btn btn-info btn-sm" name="cmd" value="search">조회</button>
        
        <span class="block ml100">출금일:</span>
        <form:input path="map[paymentDate]" class="date" />
        <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="create">EB21 파일 생성</button>  
      </form:form>
          
      <c:set var="sum" value="${ 0 }" />
      
      <my:scrollableTable tagId="eb21">
        <jsp:attribute name="header">
           <tr>
            <th>회원번호</th>
            <th>회원</th>
            <th>약정번호</th>
            <th>약정일</th>
            <th>금액</th>
            <th>계좌</th>
            <th>생년월일</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
          <c:forEach var="c" items="${ list }">
            <tr class="${ c.valid ? '' : 'my-error' }">
              <td>${ c.sponsorNo }</td>
              <td>${ c.name }</td>
              <td>${ c.commitmentNo }</td>
              <td>${ c.commitmentDate }</td>
              <td class="right"><fmt:formatNumber value="${ c.amountPerMonth}" /></td>
              <td>${ c.bankName } ${ c.accountNo } ${ c.accountHolder }</td>
              <td>${ c.birthDate }</td>
            </tr>
            <c:set var="sum" value="${ sum + c.amountPerMonth }" />
          </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>
    
    <div class="sum">
      합계: <fmt:formatNumber value="${ sum }" /> 원 / ${ list.size() } 건
    </div>

    </div>
</div>          
   
