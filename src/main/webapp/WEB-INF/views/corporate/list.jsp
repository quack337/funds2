<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; 기관 관리 &gt; 기관 목록
</div>

<div class="panel panel-default shadow w1000">
  <div class="panel-heading">
    <h3>기관</h3>
  </div>
  <div class="panel-body">
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>기관명</th>
          <th>약칭</th>
          <th>기관번호</th>
          <th>대표자명</th>
          <th>주소</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="corporate" items="${ list }">
          <tr data-url="edit.do?id=${corporate.id}">
            <td>${ corporate.name }</td>
            <td>${ corporate.shortName }</td>
            <td>${ corporate.corporateNo }</td>
            <td>${ corporate.representative }</td>
            <td>${ corporate.roadAddress } ${ corporate.detailAddress } ${ corporate.postCode }</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <div class="">
      <a class="btn btn-primary btn-sm" href="create.do">기관 등록</a>
    </div>
  </div>
</div>  
