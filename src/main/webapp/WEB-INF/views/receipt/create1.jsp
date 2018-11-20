<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<style>
body table#receiptCreaet1 tbody tr td { height: 1.7em; line-height: 1.7em; vertical-align: middle; padding: 4px; }
body table#receiptCreaet1 td:nth-child(1) { text-align: center; }
</style>

<div class="navigation-info">
  &gt; 영수증 &gt; 영수증 개별생성
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>영수증 개별생성</h3> 
  </div>
  <div class="panel-body">

    <form:form method="post" modelAttribute="wrapper">
    
    <span>납입기간:</span>
    <form:input path="map[startDate]" class="startDt" placeholder="필수" /> ~ 
    <form:input path="map[endDate]" class="endDt" placeholder="필수" />
    <span>회원명:</span>
    <form:input path="map[srchText]" placeholder="필수" />
    <span>기부기관:</span>
    <form:select path="map[corporateId]" itemValue="id" itemLabel="name" items="${ corporates }" />
    <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="search">납입내역 조회</button>
    
    <my:scrollableTable tagId="receiptCreaet1">
      <jsp:attribute name="header">
         <tr>
          <th style="text-align:center;"><input type="checkbox" onclick="setTimeout(toggleCreateButton, 100)" /></th>
          <th>영수증번호</th>
          <th>회원번호</th>
          <th>회원명</th>
          <th>정기/비정기</th>
          <th>납입일</th>
          <th class="right">금액</th>
         </tr>        
      </jsp:attribute>
      <jsp:attribute name="body">
        <c:forEach var="p" items="${ list }">
          <tr>
            <td><c:if test="${ p.receiptId == null }">
                  <input type="checkbox" name="pid" value="${ p.id }" onclick="toggleCreateButton()" />
                </c:if>
            </td>
            <td>${ p.receiptNo }</td>          
            <td>${ p.sponsorNo }</td>
            <td>${ p.name }</td>
            <td>${ p.commitmentId != 0 ? '정기' : '비정기' }</td>
            <td>${ p.paymentDate }</td>
            <td class="right"><fmt:formatNumber value="${ p.amount }" /></td>
          </tr>
        </c:forEach>
      </jsp:attribute>
    </my:scrollableTable>
        
     <c:if test="${ list.size() <= 0 }">
       <div>조회 결과가 없습니다.</div>
     </c:if>    
    
    <c:if test="${ list.size() > 0 }">
      <div class="createReceipt">
        <span id="sum">금액 합계: 0</span>
        <span class="block ml30">영수증 발급일:</span>
        <form:input class="date" path="map[createDate]" />
        <button type="submit" class="btn btn-info btn-sm" name="cmd" value="createReceipt">선택항목 영수증 발급</button>
      </div>
    </c:if>
    
    </form:form>
  </div>
</div>    

<script>
function toggleCreateButton() {
  var sum = 0;
  $("input[name=pid]:checked").each(function(i, c) {
	  sum += (1 * $(c).parents("tr").find("td.right").text().replace(/,/g, ""));
  });
  $("span#sum").text("금액 합계: " + sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
  if (sum > 0) $("button[value=createReceipt]").show();
  else $("button[value=createReceipt]").hide();
}
$("button[value=createReceipt]").hide();
</script>