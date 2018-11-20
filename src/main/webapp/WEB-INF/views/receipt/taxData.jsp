<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 영수증 &gt; 국세청 보고자료
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>국세청 보고자료</h3> 
  </div>
  <div class="panel-body" style="height: 200px;">

    <form:form method="post" modelAttribute="wrapper">
    
    <span>납입기간:</span>
    <form:input path="map[startDate]" class="startDt" placeholder="필수" /> ~ 
    <form:input path="map[endDate]" class="endDt" placeholder="필수" />
    
    <span>기부기관:</span>
    <form:select path="map[corporateId]" itemValue="id" itemLabel="name" items="${ corporates }" />
    <button type="submit" class="btn btn-primary btn-sm">다운로드</button>
    
    </form:form>
  </div>
</div>    