<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<div class="navigation-info">
  &gt; 납입조회 &gt; ${ title }별 납입 합계
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>${ title }별 납입 합계</h3> 
  </div>
  <div class="panel-body">
  
      <form:form modelAttribute="wrapper">
        <div class="pull-right mb4">
          <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="search">납입 합계 조회</button>
          <button type="button" class="btn btn-default btn-sm" onclick="cancelSearch()">조회조건 취소</button>    
          <button type="submit" class="btn btn-success btn-sm" name="cmd" value="excel">엑셀 다운로드</button>
        </div>
      
        <div>
          <span>기간:</span>
          <form:input path="map[startDate]" class="startDt" /> ~ 
          <form:input path="map[endDate]" class="endDt" />
          &nbsp;
          <span>기부처:</span>
          <form:select path="map[corporateId]">
              <form:option value="0" label="전체" />
              <form:options itemLabel="name" itemValue="id" items="${ corporates }" />
          </form:select>
        </div>  
      </form:form>
      
    <my:scrollableTable tagId="srch2">
        <jsp:attribute name="header">
          <tr>
            <th>${ title }</th>
            <th class="right">회원수</th>
            <th class="right">납입수</th>
            <th class="right">금액</th>
            <th class="right">비율</th>            
          </tr>
        </jsp:attribute>
        <jsp:attribute name="body">
          <c:forEach var="p" items="${list}">
            <tr>
              <td>${p.name} </td>
              <td class="right"><fmt:formatNumber value="${p.sponsorCount}" /></td>
              <td class="right"><fmt:formatNumber value="${p.paymentCount}" /></td>
              <td class="right"><fmt:formatNumber value="${p.amount}" /></td>
              <td class="right"><fmt:formatNumber value="${p.ratio }" pattern="##0.00"/></td>
            </tr>
          </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>

    </div>
</div>  
