<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 영수증 &gt; 영수증 일괄생성
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>영수증 일괄생성</h3> 
  </div>
  <div class="panel-body" style="height: 200px;">

    <form:form method="post" modelAttribute="wrapper">
    
    <span>납입기간:</span>
    <form:input path="map[startDate]" class="startDt" placeholder="필수" /> ~ 
    <form:input path="map[endDate]" class="endDt" placeholder="필수" />
    <span>발급일:</span>
    <form:input path="map[createDate]" class="date" placeholder="필수" />
    
    <button type="submit" class="btn btn-primary btn-sm" onclick="showWaitMsg()">영수증 일괄 발급</button>
    
    </form:form>
    
  </div>
</div>    
