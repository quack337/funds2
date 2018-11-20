<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="R" value="/" />

<c:set var="mode" value="${ donationPurpose.id > 0 ? '수정' : '등록' }" />

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; 기부목적 관리 &gt; <a href="list.do">기부목적 목록</a> &gt; 기부목적 ${ mode }
</div>

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>기부목적 ${ mode }</h3>
  </div>
  <div class="panel-body">
  
    <form:form method="post" modelAttribute="donationPurpose">     
	    <table class="table table-bordered lbw120 pd4" style="margin-bottom: 10px;">
	      <tr>
	        <td class="lb">기부처</td>
	        <td><form:select path="corporateId" itemValue="id" itemLabel="name" items="${corporateList}" /></td>
	        <td class="lb">기금구분</td>
	        <td><form:select path="organizationId" itemValue="id" itemLabel="codeName" items="${organizationList}" /></td>
	      </tr>
	      <tr>
	        <td class="lb">기부목적</td>
	        <td><form:input path="name" class="w400" /></td>
	        <td class="lb">구분</td>
	        <td><form:input path="gubun" /></td>
	      </tr>
	      <tr>
	        <td class="lb">상태</td>
	        <td><form:select path="closed">
	              <form:option value="0">사용</form:option>
	              <form:option value="1">사용안함</form:option>
	            </form:select>
	        </td>
	        <td class="lb">기부금단체코드</td>
	        <td><form:input path="code" /></td>
	      </tr>
	    </table>
	    <table class="table table-bordered pd4">
	      <tr>
	        <td class="lb" colspan="4">비고</td>
	      <tr>
	      <tr>
	        <td colspan="4"><form:textarea path="etc" style="width:100%; height: 200px" /></td>
	      </tr>
	    </table>  
	
	    <div class="">
	      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">기부목적 저장</button>
	      <c:if test="${ donationPurpose.id > 0 }">
	        <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>기부목적 삭제</button>
	      </c:if>    
	      <a href="list.do" class="btn btn-gray btn-sm">기무복적 목록으로</a>
	    </div>    
    </form:form>    
    
    <hr />
    <my:fileUpload foreignType="donationPurpose" foreignId="${ donationPurpose.id }" 
                   returnUrl="/donationPurpose/edit.do?id=${ donationPurpose.id }" />
  </div>
</div>      

