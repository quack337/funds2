<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 정기납입 관리 &gt; 정기납입 목록
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab3" value="active" />
    <%@include file="../_tab2.jsp" %> 
    
    <div class="mt4">
      <span>약정번호:</span>
      <select name="commitmentId">
        <option value="0">전체</option>
        <c:forEach var="c" items="${ commitments }">
          <option value="${c.id}">${ c.commitmentNo }</option>
        </c:forEach>
      </select> 
      <button type="button" class="btn btn-primary btn-sm" onclick="searchPayment()">조회</button>
      <a id="btnChange" href="#donationPurposeUpdateDialog" class="btn btn-info btn-sm" data-toggle="modal">기부목적 일괄변경</a>
      <a id="btnSearch" href="#" class="btn btn-info btn-sm">납입내역 상세 조회</a>
    </div>  

    <div id="searchResult">
    </div>
    
    <div id="sum"></div>
    
  </div>
</div>  

<%@include file="_donationPurposeUpdateDialog.jsp" %>
<%@include file="../_donationPurposeDialog.jsp" %>

<script>
$("select[name=commitmentId]").change(function() {
    if ($(this).val() == 0) {
        $('#btnChange').hide();
        $('#btnSearch').hide();
    }
    else {
        $('#btnChange').show();
        var commitmentNo = $("select[name=commitmentId] option:selected").text();
        $('#btnSearch').attr("href", "${R}payment/srch1a.do?commitmentNo=" + commitmentNo);        
        $('#btnSearch').show();        
    }
});

function searchPayment() {
    var params = { commitmentId: $("select[name=commitmentId]").val(),
                   sid: ${ sponsor.id } };
    $("#searchResult").load("list1ajax.do", params);
}

function updateDonationPurpose() {
    var params = { id: $("select[name=commitmentId]").val(),
                   startDate: $("input[name=startDate]").val(),
                   endDate: $("input[name=endDate]").val(),
                   donationPurposeId: $("input[name=donationPurposeId]").val(), 
                   sid: ${ sponsor.id } };
    $("#searchResult").load("list1updateajax.do", params);
}

$('#btnChange').hide();
$('#btnSearch').hide();
</script>
