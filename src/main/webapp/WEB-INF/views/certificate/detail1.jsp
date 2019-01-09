<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 증서 &gt; <a href="list.do">기부증서 목록</a> &gt; 기부증서
</div>

<div class="panel panel-default shadow w800">
  <div class="panel-heading">
    <h3>장학증서</h3>
  </div>
  <div class="panel-body">  
    <table class="table table-bordered lbw120 pd4">  
      <tr>
        <td class="lb">증서번호</td>
        <td>${ certificate.certificateNo }</td>
        <td class="lb">발급일</td>
        <td>${ certificate.createDate }</td>
      </tr>
      <tr>
        <td class="lb">회원번호</td>
        <td>${ certificate.personNo }</td>
        <td class="lb">회원</td>
        <td>${ certificate.personName }</td>
      </tr>
      <tr>
        <td class="lb">금액</td>
        <td>${ certificate.personName }</td>
        <td class="lb">발급인</td>
        <td>${ certificate.userName }</td>
      </tr>
      <tr>
        <td class="lb">기부처</td>
        <td colspan="3">${ certificate.corporateName }</td>
      </tr>
      <tr>
        <td class="lb">내용</td>
        <td colspan="3">${ certificate.body }</td>
      </tr>
    </table>

    <div class="pull-right mb4">
      <a class="btn btn-primary btn-sm" href="report.do?id=${ certificate.id }">증서 다운로드</a>
      <a class="btn btn-info btn-sm" href="list.do?${ pagination.queryString }">목록으로</a>
      <a class="btn btn-danger btn-sm" href="delete.do?id=${ certificate.id }&${ pagination.queryString }" data-confirm-delete>증서 삭제</a>
    </div>
  </div>    
</div>

