<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="navigation-info">
  &gt; 영수증 &gt; 기부금 영수증 발급합계
</div>

<div class="panel panel-default shadow w1000">
  <div class="panel-heading">
    <h3>기부금 영수증 발급 합계</h3>
  </div>
  <div class="panel-body">
  
    <form:form id="sum" modelAttribute="pagination" method="post">
      
      <span>발급기간:</span>
      <form:input path="sd" class="startDt" /> ~
      <form:input path="ed" class="endDt" />
      
      <span class="block ml10">기부처:</span>
      <c:if test="${ not empty corporates }">
        <form:select path="corporateId">
          <form:option value="0" label="전체" />
          <form:options itemValue="id" itemLabel="name" items="${ corporates }" />
        </form:select>
      </c:if>
      <c:if test="${ empty corporates }">
        <form:select path="corporateId">
          <form:option value="${ corporate.id }" label="${ corporate.name }"/>
        </form:select>
      </c:if>
      
      <button type="submit" class="btn btn-primary ml10 btn-sm">조회</button>
  
      <table class="table table-bordered mt4">
	    <thead>
	      <tr>
	        <th>기부처</th>
	        <th>구분</th>
	        <th>건수</th>
	        <th>금액</th>
	      </tr>
	    </thead>
	    <tbody>
	      <c:forEach var="r" items="${ list }">
	        <tr>
	          <td>${ r.name }</td>
              <td>${ r.gubun }</td>
              <td class="right"><fmt:formatNumber value="${ r.count }" /></td>
              <td class="right"><fmt:formatNumber value="${ r.amount }" /></td>	        </tr>
	      </c:forEach>
	      <c:if test="${ list eq null || list.size() < 0 }">
	        <tr>
	          <td colspan="4">자료가 없습니다</td>
	        </tr>
	      </c:if>
	    </tbody>
	  </table>

    </form:form>
  </div>
</div>  
