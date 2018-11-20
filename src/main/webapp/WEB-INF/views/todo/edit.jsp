<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
  table#todo label { font-weight: normal; display: inline-block; margin-left: 5px; margin-right: 20px; }
</style>

<c:set var="mode" value="${ todo.id > 0 ? '수정' : '등록' }" />

<div class="navigation-info">
  &gt; 기타 &gt; <a href="list.do?${ pagination.queryString }">일정목록</a> &gt; 일정 ${ mode }
</div>

<form:form method="post" modelAttribute="todo">

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>일정 ${ mode }</h3> 
  </div>  
  <div class="panel-body">
    <table id="todo" class="table table-bordered lbw120 pd4">
      <tr>
        <td class="lb">일정</td>
        <td><form:input path="dueDate2" class="date" /></td>
        <td class="lb">알림</td>
        <td><form:input path="alertBefore" class="w50" /> 일전</td>
      </tr>
      <tr>
        <td class="lb">확인</td>
        <td>${ todo.confirmDate }
          <c:if test="${ todo.confirmDate ne null }">          
             <button type="submit" class="btn btn-info btn-sm" name="cmd" value="cancelConfirm" >취소</button>
          </c:if>
        </td>
        <td class="lb">반복</td>
        <td>
          <form:radiobutton path = "repeat" value = "0" label = "없음" />
          <form:radiobutton path = "repeat" value = "1" label = "매월" />
          <form:radiobutton path = "repeat" value = "2" label = "매년" />
        </td>
      </tr>
      <tr>
        <td class="lb">내용</td>
        <td colspan="3"><form:textarea path="message" rows="5" style="width:100%;" /></td>        
      </tr>
    </table>  

    <div class="">
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">일정 저장</button>
      <c:if test="${ todo.id > 0 }">
        <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>일정 삭제</button>
      </c:if>    
      <a href="list.do?${ pagination.queryString }" class="btn btn-gray btn-sm">일정 목록으로</a>
    </div>
  </div>
</div>

</form:form>
