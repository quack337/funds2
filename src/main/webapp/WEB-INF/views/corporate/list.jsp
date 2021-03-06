<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; 기부처 관리 &gt; 기부처 목록
</div>

<div class="panel panel-default shadow w1000">
  <div class="panel-heading">
    <h3>기부처</h3>
  </div>
  <div class="panel-body">
    <div class="right mb4">
      <a class="btn btn-primary btn-sm" href="create">기부처 등록</a>
    </div>

    <table class="table table-bordered mt4">
      <thead>
        <tr>
          <th>기부처명</th>
          <th>약칭</th>
          <th>사업자등록번호</th>
          <th>대표자명</th>
          <th>주소</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="corporate" items="${ list }">
          <tr data-url="edit?id=${corporate.id}">
            <td>${ corporate.name }</td>
            <td>${ corporate.shortName }</td>
            <td>${ corporate.corporateNo }</td>
            <td>${ corporate.representative }</td>
            <td>${ corporate.postCode } ${ corporate.roadAddress } ${ corporate.detailAddress }</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

  </div>
</div>  
