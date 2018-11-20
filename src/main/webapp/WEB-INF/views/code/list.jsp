<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="R" value="/" />

<style>
  table#head { margin-bottom: 0px; }
  div#scroll { 
    height: 500px; overflow-y: scroll; overflow-x: hidden;
    border-bottom: 1px solid #ddd; margin-bottom: 10px;  
  }
</style>

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; ${ codeGroup.name } 관리 &gt; ${ codeGroup.name } 목록
</div>

<div class="panel panel-default shadow w700">
  <div class="panel-heading">
    <h3>${ codeGroup.name }</h3>
  </div>
  <div class="panel-body">
  
      <my:scrollableTable tagId="srch1a">
        <jsp:attribute name="header">
           <tr>
	          <th>구분</th>
	          <th>상태</th>
	          <th>${ codeGroup.etc1 }</th>
	          <th>${ codeGroup.etc2 }</th>
	          <th>${ codeGroup.etc3 }</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
	        <c:forEach var="code" items="${list}">
	          <tr data-url="edit.do?id=${code.id}&gid=${codeGroup.id}">
	            <td>${ code.codeName }</td>
	            <td>${ code.state ? '사용' : '사용안함' }</td>
	            <td style="min-width:30px;">${ code.etc1 }</td>
	            <td style="min-width:30px;">${ code.etc2 }</td>
	            <td style="min-width:30px;">${ code.etc3 }</td>
	          </tr>
	        </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>
    
    <div class="">
      <a class="btn btn-primary btn-sm" href="create.do?gid=${ codeGroup.id }">${ codeGroup.name } 등록</a>
    </div>
  </div>
</div>  
