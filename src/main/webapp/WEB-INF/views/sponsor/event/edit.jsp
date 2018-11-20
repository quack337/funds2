<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list.do?${ pagination.queryString }">회원 목록</a>  
  &gt; 예우 관리 &gt; <a href="${R}sponsor/event/list.do?sid=${sponsor.id}&${ pagination.queryString }">예우 목록</a>  
  &gt; 예우 ${ sponsorEvent.id == 0 ? '등록' : '수정' }  
</div>

<form:form method="post" modelAttribute="sponsorEvent">

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab8" value="active" />
    <%@include file="../_tab2.jsp" %> 

    <div style="width: 800px">
      <table class="table table-bordered lbw120 mt10 pd4">
        <tr>
          <td class="lb">예우 내용</td>
          <td><form:input path="description" class="w600" /></td>
        </tr>
        <tr>
          <td class="lb">날짜</td>
          <td><form:input path="eventDate" class="date" /></td>
        </tr>
        <tr>
          <td class="lb">비고</td>
          <td><form:textarea path="etc" class="w600 h100" /></td>
        </tr>
      </table>  
  
      <div>
        <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">예우 저장</button>
        <c:if test="${ sponsorEvent.id > 0 }">
          <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>예우 삭제</button>
        </c:if>    
        <a href="list.do?sid=${ sponsor.id }&${ pagination.queryString }" class="btn btn-gray btn-sm">예우 목록으로</a>
      </div>
    </div>
  </div>
</div>    
    
</form:form>
