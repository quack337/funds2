<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; 기부목적 관리 &gt; 기부목적 목록
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>기부목적</h3>
  </div>
  <div class="panel-body">
    <div class="pull-right">
      <a class="btn btn-primary btn-sm" href="create.do">기부목적 등록</a>
    </div>    
  
    <form:form method="get" modelAttribute="pagination" class="mt4">
      <input type="hidden" name="pg" value="1" />
    
      <div class="form-inline">
        <span>상태:</span>
        <form:select path="od"  data-auto-submit="true" style="width:90px">
          <form:option value="0" label="전체" />
          <form:option value="1" label="사용" />
          <form:option value="2" label="사용안함" />
        </form:select>
              
        <form:select path="ss" style="width: 120px">
          <form:option value="1" label="기부목적" />
          <form:option value="2" label="기부금단체코드" />
        </form:select>
        <form:input path="st" />
        <button type="submit" class="btn btn-sm btn-info btn-sm">조회</button>
        <c:if test="${ pagination.ss != 0 }">
          <a href="list.do" class="btn btn-small btn-gray btn-sm">조회조건 취소</a>
        </c:if>
      </div>
  
    <table class="table table-bordered mt4">
      <thead>
          <tr>
              <th>기부처</th>
              <th>기금구분</th>
              <th>기부목적</th>
              <th>기부금단체코드</th>
              <th>구분</th>
              <th>상태</th>
          </tr>
      </thead>
       <tbody>
          <c:forEach var="donationPurpose" items="${ list }">
              <tr data-url="edit.do?id=${donationPurpose.id}" >
                  <td>${ donationPurpose.corporateName }</td>
                  <td>${ donationPurpose.organizationName }</td>
                  <td>${ donationPurpose.name }</td>
                  <td>${ donationPurpose.code }</td>
                  <td>${ donationPurpose.gubun }</td>
                  <td>${ donationPurpose.closed ? '사용안함' : '사용' }</td>
              </tr>
          </c:forEach>
      </tbody>
    </table>
    
      <form:select path="sz" data-auto-submit="true">
        <form:option value="10" />
        <form:option value="15" />
        <form:option value="30" />
        <form:option value="100" />
      </form:select>
    
      <ul class="pagination">
        <c:forEach var="page" items="${ pagination.pageList }">
          <li class='${ page.cssClass }'><a data-page="${ page.number }">${ page.label }</a></li>
        </c:forEach>
      </ul>

    </form:form>          

  </div>
</div>  
