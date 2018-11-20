<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<div class="navigation-info">
  &gt; 납입조회 &gt; 회원별 납입 합계 조회
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>회원별 납입 합계 조회</h3> 
  </div>
  <div class="panel-body">

      <form:form modelAttribute="wrapper">
      
        <div class="pull-right mb4">
          <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="search" onclick="showWaitMsg()">납입 합계 조회</button>
          <button type="button" class="btn btn-default btn-sm" onclick="cancelSearch()">조회조건 취소</button>    
          <button type="submit" class="btn btn-success btn-sm" name="cmd" value="excel">엑셀 다운로드</button>
        </div>
      
        <div>
          <span>정렬순서:</span>
          <form:select path="map[orderBy]">
            <form:option value="" labe="" />
            <form:options itemValue="value" itemLabel="label" items="${ report1aOrderBy }" />
          </form:select>
        </div>
      
        <table class="table table-bordered lbw120 pd4">
          <tr>
            <td class="lb">이름</td>
            <td><form:input path="map[sponsorName]" />    
            <td class="lb">회원번호</td>
            <td><form:input path="map[sponsorNo]" />    
          </tr>
          <tr>
            <td class="lb">회원구분</td>
            <td><form:select path="map[sponsorType2Id]">
                <form:option value="" label="" />
                <form:options itemValue="id" itemLabel="codeName" items="${ sponsorType2List }" />
                </form:select>
            </td>            
            <td class="lb">소속교회</td>
            <td>
              <form:input path="map[churchName]" readonly="true" /> 
              <form:hidden path="map[churchId]" /> 
              <a href="#churchDialog" class="btn btn-xs btn-flat" data-toggle="modal">소속교회 조회</a>
            </td>
          </tr>
          <tr>      
            <td class="lb">기부처</td>
            <td>
                <form:select path="map[corporateId]">
                <form:option value="" labe="" />
                <form:options itemValue="id" itemLabel="name" items="${ corporates }" />
                </form:select>
            </td>
            <td class="lb">정기/비정기</td>
            <td><form:select path="map[regular]">
                <form:option value="-1" label="" />
                <form:option value="1" label="정기" />
                <form:option value="0" label="비정기" />
              </form:select>
            </td>
          </tr>
          <tr>
            <td class="lb">납입일</td>
            <td><form:input path="map[startDate]" class="startDt" /> ~ <form:input path="map[endDate]" class="endDt" /></td>
            <td class="lb">납입방법</td>
            <td><form:select path="map[paymentMethodId]">
                <form:option value="" labe="" />
                <form:options itemValue="id" itemLabel="codeName" items="${ paymentMethods }" />
                </form:select>
            </td>
          </tr>      
          <tr>      
            <td class="lb">기부목적</td>
            <td colspan="3">
              <form:input path="map[donationPurposeName]" readonly="true" class="w600" />
              <form:hidden path="map[donationPurposeId]" />
              <a href="#donationPurposeDialog" class="btn btn-xs btn-flat" data-toggle="modal">기부목적 조회</a>
            </td>
          </tr>
        </table>
      </form:form>
      
      <c:set var="sum" value="${ 0 }" />
      <c:set var="count" value="${ 0 }" />

    <my:scrollableTable tagId="srch1b">
        <jsp:attribute name="header">
           <tr>
            <th>회원번호</th>
            <th>이름</th>
            <th>회원구분</th>
            <th>소속교회</th>
            <th class="right">납입액</th>
            <th class="right">납입건수</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
           <c:forEach var="p" items="${list}">
             <tr>
               <td>${p.sponsorNo}</td>
               <td>${p.name}</td>
               <td>${p.sponsorType2Name}</td>
               <td>${p.churchName}</td>
               <td class="right"><fmt:formatNumber value="${p.amount}" /></td>
               <td class="right" style="width: 70px;"><fmt:formatNumber value="${p.count}" /></td>
             </tr>
             <c:set var="sum" value="${ sum + p.amount }" />
             <c:set var="count" value="${ count + p.count }" />
           </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>

    <div>
      합계: <fmt:formatNumber value="${ sum }" /> 원 / <fmt:formatNumber value="${ count }" /> 건
    </div>    
    
  </div>
</div>    

<%@include file="../sponsor/_churchDialog.jsp"%>
<%@include file="../sponsor/_donationPurposeDialog.jsp"%>
