<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 비정기납입 관리 &gt; <a href="list2.do?sid=${ sponsor.id }&${ pagination.queryString }">비정기납입 목록</a>
  &gt; 비정기납입 등록 
</div>

<form:form method="post" modelAttribute="payment">

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab4" value="active" />
    <%@include file="../_tab2.jsp" %> 

    <div style="width: 800px">
      <table class="table table-bordered lbw120 mt10 pd4">
        <tr>
          <td class="lb">납입일</td>
          <td><form:input path="paymentDate" class="date" /></td>
          <td class="lb">납입방법</td>
          <td><form:select path="paymentMethodId" itemValue="id" itemLabel="codeName" items="${ paymentMethods }" /></td>
        </tr>
        <tr>
          <td class="lb">납입액</td>
          <td><form:input path="amount" class="money" /></td>
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
  
      <div>
        <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">비정기납입 저장</button>
        <c:if test="${ payment.id > 0 }">
          <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>비정기납입 삭제</button>
        </c:if>    
        <a href="list2.do?sid=${ sponsor.id }&${ pagination.queryString }" class="btn btn-gray btn-sm">비정기납입 목록으로</a>
      </div>
    </div>
  </div>
</div>    
    
</form:form>

<%@include file="../_donationPurposeDialog.jsp" %>
