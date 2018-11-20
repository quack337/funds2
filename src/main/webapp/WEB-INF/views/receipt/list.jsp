<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
function report1() {
  location.href = "report1.do?" + $("form#receipts").serialize(); 
}
function report2() {
  location.href = "report2.do?" + $("form#receipts").serialize(); 
}
</script>

<div class="navigation-info">
  &gt; 영수증 &gt; 기부금 영수증 발급대장
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>기부금 영수증 발급 대장</h3> 
  </div>
  <div class="panel-body">

      <form:form id="receipts" modelAttribute="pagination" method="get">
      
      <input type="hidden" name="pg" value="1" />
      
      <span>회원명:</span>
      <form:input path="st" />
      
      <span class="block ml10">발급기간:</span>
      <form:input path="sd" class="startDt" /> ~
      <form:input path="ed" class="endDt" />
      
      <span class="block ml10">기부처:</span>
      <form:select path="corporateId">
        <form:option value="0" label="전체" />
        <form:options itemValue="id" itemLabel="name" items="${ corporates }" />
      </form:select>      
      
      <button type="submit" class="btn btn-primary ml10 btn-sm">조회</button>
      <c:if test="${ not empty pagination.st || not empty pagination.sd }">
        <a href="list.do" class="btn btn-default btn-sm">조회조건 취소</a>
      </c:if>
      
      <table class="table table-bordered mt4 pd6">
        <thead>
          <tr>
            <th><input type="checkbox"></th>
            <th>영수증번호</th>
            <th>회원 이름</th>
            <th>회원번호</th>
            <th>연락처</th>
            <th class="right">기부금액</th>
            <th>영수증발급일자</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="receipt" items="${ list }">
            <tr data-url="detail.do?id=${receipt.id}&${pagination.queryString}">
              <td><input type="checkbox" name="rid" value="${ receipt.id  }"></td>
              <td>${ receipt.no }</td>
              <td>${ receipt.name }</td>
              <td>${ receipt.sponsorNo }</td>
              <td>${ receipt.mobilePhone }</td>
              <td class="right"><fmt:formatNumber value="${ receipt.amount }" /></td>
              <td>${ receipt.createDate }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      
      <form:select path="sz" data-auto-submit="true">
        <form:option value="10" />
        <form:option value="15" />
        <form:option value="30" />
        <form:option value="100" />
      </form:select>
      
      <ul class="pagination">
        <c:forEach var="page" items="${pagination.pageList }">
          <li class='${ page.cssClass }'><a data-page="${ page.number }">${ page.label }</a></li>
        </c:forEach>
      </ul>
      
      </form:form>

      <div id="buttons" class="mt4">
        <button class="btn btn-info btn-sm" type="button" onclick="report1()">선택한 영수증(건별) 다운로드</button>
        <button class="btn btn-info btn-sm" type="button" onclick="report2()">선택한 영수증(합산) 다운로드</button>
        <button class="btn btn-danger btn-sm" type="submit" name="cmd" value="delete" data-confirm-delete >선택한 영수증 삭제</button>
      </div>

  </div>
</div>

<script>
  $("#buttons button").prop("disabled", true);
  
  $("input[type=checkbox]").change(function() {
      var count = $("tbody input[type=checkbox]:checked").length;
      $("#buttons button").prop("disabled", count == 0);
  });
</script>        