<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list?${ pagination.queryString }">회원 목록</a> 
  &gt; 약정 관리 &gt; <a href="list?sid=${ sponsor.id }&${ pagination.queryString }">약정 목록</a> &gt; 약정 수정
</div>

<form:form method="post" modelAttribute="commitment">

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">
  
    <c:set var="tab2" value="active" />
    <%@include file="../_tab2.jsp" %> 
    
    <div class="right mt10 mb10">
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">약정 저장</button>
      <c:if test="${ commitment.eb13State eq null || commitment.eb13State eq '에러' }">
        <button type="submit" class="btn btn-danger  btn-sm" name="cmd" value="delete" data-confirm-delete>삭제</button>
      </c:if>
      <a href="list?sid=${ sponsor.id }&${ pagination.queryString }" class="btn btn-gray btn-sm">약정 목록으로</a>
      <a href="${R}payment/srch1a?commitmentNo=${commitment.commitmentNo}" class="btn btn-info btn-sm">납입내역 상세 조회</a>
    </div>

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
        <td>${ commitment.bankName }</td>
      </tr>
      <tr>
        <td class="lb">종료일</td>
        <td><form:input path="endDate" class="endDt" />
            <c:if test="${ empty commitment.endDate }">
                <button type="submit" class="btn btn-xs btn-flat" name="cmd" value="close" data-confirm="종료하시겠습니까?">종료하기</button>
            </c:if>
        </td>
        <td class="lb">계좌번호</td>
        <td>${ commitment.accountNo }</td>
      </tr>
      <tr>
        <td class="lb">월납입일</td>
        <td><form:input path="paymentDay" class="w100" /></td>
        <td class="lb">예금주 / 생년월일</td>
        <td>${ commitment.accountHolder } / ${ commitment.birthDate }</td>
      </tr>
      <tr>
        <td class="lb">약정상태</td>
        <td>${ commitment.state }</td>
        <td class="lb">EB13상태</td>
        <td>${ commitment.eb13State }
            <c:if test="${ commitment.eb13State == '종료' }">
                <button type="submit" class="btn btn-xs btn-flat" name="cmd" value="open" data-confirm="종료 취소하시겠습니까?">종료 취소하기</button>
            </c:if>
        </td>
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

    </div>
</div>

</form:form>

<%@include file="../_donationPurposeDialog.jsp" %>
