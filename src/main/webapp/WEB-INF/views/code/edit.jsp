<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mode" value="${ code.id > 0 ? '수정' : '등록' }" />

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; ${ codeGroup.name } 관리 &gt; <a href="list.do?gid=${codeGroup.id}">${ codeGroup.name } 목록</a> &gt; ${ codeGroup.name } ${ mode }
</div>

<form:form method="post" modelAttribute="code">

<div class="panel panel-default shadow w800">
  <div class="panel-heading">
    <h3>${ codeGroup.name } ${ mode }</h3>
  </div>
  <div class="panel-body">    
    <form:hidden path="codeGroupId" />

    <table class="table table-bordered lbw120 pd4">
      <tr>
        <td class="lb">코드명</td>
        <td><form:input path="CodeName" /></td>
        <td class="lb">${ codeGroup.etc1 }</td>
        <td><form:input path="etc1" class="w200" /></td>
      </tr>
      <tr>
        <td class="lb">상태</td>
        <td><form:select path="state">
              <form:option value="1">사용</form:option>
              <form:option value="0">사용안함</form:option>
            </form:select>
        </td>
        <td class="lb">${ codeGroup.etc2 }</td>
        <td><form:input path="etc2" class="w200" /></td>
      </tr>
      <tr>
        <td class="lb"></td>
        <td></td>
        <td class="lb">${ codeGroup.etc3 }</td>
        <td><form:input path="etc3" class="w200" /></td>
      </tr>
    </table>  

    <div class="">
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">저장</button>
      <c:if test="${ code.id > 0 }">
        <button type="submit" class="btn btn-danger  btn-sm" name="cmd" value="delete" data-confirm-delete>삭제</button>
      </c:if>    
      <a href="list.do?gid=${codeGroup.id}" class="btn btn-gray  btn-sm">${codeGroup.name} 목록으로</a>
    </div>
  </div>
</div>

</form:form>
