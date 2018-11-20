<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<c:set var="sum" value="${ 0 }" />

<my:scrollableTable tagId="list1ajax_scrollTable">
    <jsp:attribute name="header">
	    <tr>
	      <th>약정번호</th>
	      <th>납입<br/>방법</th>
	      <th class="right">납입금액</th>
	      <th>납입일</th>
	      <!--
	      <th>계좌번호</th>
	      <th>은행</th>
	      <th>예금주</th>
	      -->
	      <th>기부목적</th>
	      <th>비고</th>
	    </tr>
    </jsp:attribute>
    <jsp:attribute name="body">
	    <c:forEach var="p" items="${ list }">
	      <tr>
	        <td>${ p.commitmentNo }</td>
	        <td>${ p.paymentMethodName }</td>
	        <td class="right"><fmt:formatNumber value="${ p.amount}" /></td>
	        <td>${ p.paymentDate}</td>
	        <!--
	        <td>${ p.accountNo }</td>
	        <td>${ p.bankName }</td>
	        <td>${ p.accountHolder }</td>
	        -->
	        <td>${ p.corporateName } / ${ p.organizationName } / ${ p.donationPurposeName }</td>
	        <td>${ fn:substring(p.etc, 0, 80) }</td>
	        <c:set var="sum" value="${ sum + p.amount }" />
	      </tr>
	    </c:forEach>      
    </jsp:attribute>
</my:scrollableTable>

<div class="sum">
합계 : <fmt:formatNumber value="${ sum }" />
</div>
