<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<c:set var="pg" value="${ pagination.queryString }" />
<c:set var="sid" value="${ sponsor.id }" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 약정 관리 &gt; 약정 목록
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab2" value="active" />
    <%@include file="../_tab2.jsp" %> 
     
    <table class="table table-bordered mt10">
      <thead>
        <tr>
          <th>약정번호</th>
          <th>기부처</th>
          <th>기부목적</th>
          <th>납입방법</th>
          <th>약정일</th>
          <th>기간</th>
          <th>약정상태</th>
          <th class="right">월납입액</th>
          <th class="right">납입일</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="commitment" items="${ list }">
          <tr data-url="edit.do?id=${commitment.id}&sid=${sid}&${pg}">
            <td>${ commitment.commitmentNo }</td>
            <td>${ commitment.corporateName }</td>
            <td>${ commitment.donationPurposeName }</td>
            <td>${ commitment.paymentMethodName }</td>
            <td>${ commitment.commitmentDate }</td>
            <td>${ commitment.startDate } ~ ${ commitment.endDate } (${ commitment.months } 개월)
            </td>
            <td>${ commitment.state }</td>
            <td class="right"><fmt:formatNumber value="${ commitment.amountPerMonth }" type="number" /></td>
            <td class="right">${ commitment.paymentDay }</td>
          </tr>
        </c:forEach>
        <c:if test="${ list.size() == 0 }">
            <tr><td colspan="8">약정이 없습니다.</td></tr>
        </c:if>        
      </tbody>
    </table>
    
    <div class="">
      <a href="create.do?sid=${sid}&${pg}" class="btn btn-primary btn-sm">약정 등록</a>
    </div>
    
  </div>
</div>    