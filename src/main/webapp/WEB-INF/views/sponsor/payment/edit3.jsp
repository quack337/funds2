<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="${R}res/js/vue_mine.js"></script>

<c:set var="mode" value="${ payment.id > 0 ? '수정' : '등록' }" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 현물납입 관리 &gt; <a href="list3.do?sid=${ sponsor.id }&${ pagination.queryString }">현물납입 목록</a>
  &gt; 현물납입 ${ mode } 
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab5" value="active" />
    <%@include file="../_tab2.jsp" %> 

    <div style="width: 800px">
      <table class="table table-bordered lbw120 mt10 pd4">
        <tr>
          <td class="lb">납입일</td>
          <td><input type="text" name="paymentDate" class="date" /></td>
          <td class="lb">납입방법</td>
          <td>현물</td>
        </tr>
        <tr>
          <td class="lb">납입액</td>
          <td><fmt:formatNumber value="${ payment.amount }" /></td>
          <td class="lb">기부목적</td>
          <td>
              <c:set var="paramObj" value="${ payment }" />
              <%@include file="../_donationPurposeInput.jsp" %>
          </td>
        </tr>
        <tr>  
          <td class="lb">비고</td>
          <td colspan="3"><form:textarea path="etc" class="w600 h100" /></td>
        </tr>
      </table>
      
      <input type="hidden" name="itemId" value="0" />
      <table class="table table-bordered lbw120 mt10 pd4">
        <thead>
          <tr>
            <th>품목</th><th>수량</th><th>단가</th><th>금액</th><th></th>
          <tr>
        </thead>
        <tbody>
          <c:forEach var="p" items="${ list }">
              <tr>
                <td>${ p.title }<td>
                <td class="right"><fmt:formatNumber value="${ p.quantity }" /><td>
                <td class="right"><fmt:formatNumber value="${ p.unitCost }" /><td>
                <td class="right"><fmt:formatNumber value="${ p.quantity * p.unitCost }" /><td>
                <td id="itemButtons" data-id="${ p.id }">
                  <button type="submit" class="btn btn-info btn-sm" name="cmd" value="editItem">수정</button>
                  <button type="submit" class="btn btn-info btn-sm" name="cmd" value="deleteItem">삭제</button>
                </td>
              </tr>
          </c:forEach>          
          <tr>            
            <td colspan="5">
              <button type="submit" class="btn btn-info btn-sm" name="cmd" value="addItem">현물 추가</button>
            </td>
          </tr>
        </tbody>
      </table>      
  
      <div>
        <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">현물납입 저장</button>
        <c:if test="${ payment.id > 0 }">
          <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>현물납입 삭제</button>
        </c:if>    
        <a href="list3.do?sid=${ sponsor.id }&${ pagination.queryString }" class="btn btn-gray btn-sm">현물납입 목록으로</a>
      </div>
    </div>
  </div>
</div>    

<%@include file="../_donationPurposeDialog.jsp" %>

<script>
  $("#itemButtons").click(function() {
      $("input#itemId").value($(this).attr("data-id"));
  });
</script>
