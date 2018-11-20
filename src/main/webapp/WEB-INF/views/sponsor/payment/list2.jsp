<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="R" value="/" />

<c:set var="pg" value="${ pagination.queryString }" />
<c:set var="sid" value="${ sponsor.id }" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 비정기납입 관리 &gt; 비정기납입 목록
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab4" value="active" />
    <%@include file="../_tab2.jsp" %> 

    <c:set var="sum" value="${ 0 }" />

    <my:scrollableTable tagId="srch1a">
        <jsp:attribute name="header">
           <tr>
	          <th>납입방법</th>
	          <th class="right">납입금액</th>
	          <th>납입일</th>
	          <th>기부목적</th>
	          <th>비고</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
            <c:forEach var="payment" items="${list}">
	          <tr data-url="edit2.do?id=${payment.id}&sid=${sid}&${pg}">
	            <td>${ payment.paymentMethodName }</td>
	            <td class="right"><fmt:formatNumber value="${ payment.amount}" /></td>
	            <td>${ payment.paymentDate}</td>
	            <td>${ payment.corporateName } / ${ payment.organizationName } / ${ payment.donationPurposeName }</td>
	            <td>${ payment.etc }</td>
	          </tr>
	          <c:set var="sum" value="${ sum + payment.amount }" />
            </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>

    <div id="sum" class="mb10">
      합계: <fmt:formatNumber value="${ sum }" />
    </div>

    <div class="">
      <a href="create2.do?sid=${sid}&${pg}" class="btn btn-primary btn-sm">비정기납입 등록</a>
      <a id="btnChange" href="#donationPurposeUpdateDialog" class="btn btn-info btn-sm" data-toggle="modal">기부목적 일괄변경</a>
      <a id="btnSearch" href="${R}payment/srch1a.do?sponsorNo=${sponsor.sponsorNo}&regular=0" class="btn btn-info btn-sm">납입내역 상세 조회</a>
    </div>
    
  </div>
</div>    

<form id="updateDonationPurpose" method="post">
  <input type="hidden" name="sponsorId" value="${ sponsor.id }" />
  <%@include file="_donationPurposeUpdateDialog.jsp" %>
</form>

<%@include file="../_donationPurposeDialog.jsp" %>

<script>
function updateDonationPurpose() {
    $("form#updateDonationPurpose").submit();
}
</script>