<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<form:form method="get" modelAttribute="pagination">

<div class="navigation-info">
  &gt; 회원 관리 &gt; 우편 발송 
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>우편 발송</h3> 
  </div>
  <div class="panel-body">
      <input type="hidden" name="pg" value="1" />
    
      <div class="form-inline mb4">
        <form:input tyle="text" class="startDt w100" path="sd" placeholder="시작일" />
        ~
        <form:input type="text" class="endDt w100" path="ed" placeholder="종료일" />
        <form:select path="od" style="margin-left: 20px; margin-right: 20px;">
          <form:option value="0" label="회원구분" />
          <form:options itemValue="id" itemLabel="codeName" items="${ sponsorType2List }" /> 
        </form:select>
        <form:select path="ss">
          <form:option value="0" label="회원/비회원" />
          <form:option value="1" label="회원" />
          <form:option value="2" label="비회원" />
        </form:select>
        <form:input type="text" class="w100" path="st" placeholder="이름" style="margin-left: 20px; margin-right: 20px;" />
        <button type="submit" class="btn btn-primary btn-sm">조회</button>
        <a href="dmx.do?${pagination.queryString}" class="btn btn-success btn-sm">엑셀 다운로드</a>
      </div>
    
      <table class="table table-bordered" id="table_s">
        <thead>
          <tr>
            <th>회원번호</th>
            <th>이름</th>
            <th>회원구분</th>
            <th>소속교회</th>
            <th>우편번호</th>
            <th>주소</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="s" items="${list}">
            <tr>
              <td>${s.sponsorNo}</td>
              <td>${s.name}</td>
              <td>${s.sponsorType2}</td>
              <td>${s.church}</td>
              <td>${s.postCode}</td>
              <td>${s.address}</td>
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
    
      <ul class="pagination mt0">
        <c:forEach var="page" items="${ pagination.pageList }">
          <li class='${ page.cssClass }'><a data-page="${ page.number }">${ page.label }</a></li>
        </c:forEach>
      </ul>
      
    </div>
</div>          
    
</form:form>


