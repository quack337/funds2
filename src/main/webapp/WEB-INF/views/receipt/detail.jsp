<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 영수증 &gt; <a href="list.do">영수증 목록</a> &gt; 영수증
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>영수증</h3> 
  </div>
  <div class="panel-body">

    <div class="pull-right mb4">
      <a class="btn btn-primary btn-sm" href="report1.do?rid=${ receipt.id }">영수증 다운로드1</a>
      <a class="btn btn-primary  btn-sm" href="report2.do?rid=${ receipt.id }">다운로드2</a>
      <a class="btn btn-danger btn-sm" href="delete.do?id=${ receipt.id }&${ pagination.queryString }" data-confirm-delete>영수증 삭제</a>
      <a class="btn btn-info btn-sm btn-sm" href="list.do?${ pagination.queryString }">영수증 목록으로</a>
    </div>
    
    <table class="table table-bordered lbw120">  
      <tr>
        <td class="lb">영수증 번호</td>
        <td>${ receipt.no }</td>
        <td class="lb">발급일</td>
        <td>${ receipt.createDate }</td>
      </tr>
      <tr>
        <td class="lb">회원명</td>
        <td>${ sponsor.name }</td>
        <td class="lb">주민번호</td>
        <td>${ sponsor.juminNo }</td>
      </tr>
      <tr>
        <td class="lb">회원 주소</td>
        <td colspan="3">${ sponsor.homePostCode } ${ sponsor.homeRoadAddress } ${ sponsor.homeDetailAddress }</td>
      </tr>
      <tr>
        <td class="lb">기관</td>
        <td>${ corporate.name } ${ corporate.corporateNo }</td>
        <td class="lb">대표</td>
        <td>${ corporate.representative }</td>
      </tr>  
      <tr>
        <td class="lb">기관 주소</td>
        <td colspan="3">${ corporate.postCode } ${ corporate.roadAddress } ${ corporate.detailAddress }</td>
      </tr>
      <tr>
        <td class="lb">기부기간</td>
        <td>${ paymentSum.startDate } ~ ${ paymentSum.endDate }</td>
        <td class="lb">기부금액</td>
        <td class="right"><fmt:formatNumber value="${ paymentSum.amount }" /></td>
      </tr>  
    </table>
    
    <table class="table table-bordered mt10 w300">
      <thead>
        <tr>
          <th>기부일</th>
          <th class="right">기부금액</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="p" items="${ list }">
          <tr>
            <td>${ p.paymentDate }</td>
            <td class="right"><fmt:formatNumber value="${ p.amount }" /></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>    