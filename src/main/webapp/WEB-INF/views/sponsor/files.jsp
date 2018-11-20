<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 첨부파일
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab6" value="active" />
    <%@include file="_tab2.jsp" %>
    
    <my:fileUpload foreignType="sponsor" foreignId="${ sponsor.id }" returnUrl="${ url }" />    
    
  </div>
</div>    