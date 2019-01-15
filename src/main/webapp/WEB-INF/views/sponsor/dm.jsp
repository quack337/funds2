<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
    .nowrap { white-space: nowrap;}
</style>

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
        <form:select path="et">
          <form:option value="0" label="우편 발송 동의" />
          <form:option value="1" label="우편 반송" />
          <form:option value="2" label="이메일 수신 동의" />
          <form:option value="3" label="SMS 수신 동의" />
        </form:select>
        <form:input type="text" class="w100" path="st" placeholder="이름" style="margin-left: 20px; margin-right: 20px;" />
        <button type="submit" class="btn btn-primary btn-sm">조회</button>
        <a href="dmx?${pagination.queryString}" class="btn btn-success btn-sm">엑셀 다운로드</a>
      </div>
    
      <table class="table table-bordered" id="table_s">
        <thead>
          <tr>
            <th class="nowrap">회원번호</th>
            <th class="nowrap">이름</th>
            <th class="nowrap">회원구분</th>
            <th class="nowrap">소속</th>
            <th>주소</th>
            <th class="nowrap">이메일</th>
            <th class="nowrap">휴대폰</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="s" items="${list}">
            <tr>
              <td class="nowrap">${s.sponsorNo}</td>
              <td class="nowrap">${s.name}</td>
              <td class="nowrap">${s.sponsorType2}</td>
              <td class="nowrap">${s.church}</td>              
              <c:if test="${ s.sponsorType1Id != CodeID_Corporation }">
                  <td>${s.postCode} ${s.address}</td>
              </c:if>
              <c:if test="${ s.sponsorType1Id == CodeID_Corporation }">
                  <td>${s.postCode} ${s.address} ${ s.department }/${ s.position }/${ s.liaison }</td>
              </c:if>
              <td class="nowrap">${s.email}</td>
              <td class="nowrap">${s.mobilePhone}</td>
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

    <div class="alert alert-info">
        <p><i class="glyphicon glyphicon-info-sign"></i> <b>회원/비회원</b></p>
        <p>회원의 경우, 조회 기간에 납입 건이 있는 회원만 조회됩니다.<br />
           납입 건수가 없는 사람은 비회원으로 분류되는데, 조회 기간에 등록된 비회원만 조회됩니다.
        </p>    
    </div>        
    
</form:form>


