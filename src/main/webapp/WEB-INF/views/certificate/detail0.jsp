<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 증서 &gt; <a href="list">장학증서 목록</a> &gt; 장학증서
</div>

<div class="panel panel-default shadow w800">
  <div class="panel-heading">
    <h3>장학증서</h3>
  </div>
  <div class="panel-body">  
    <div class="pull-right mb4">
      <a class="btn btn-primary btn-sm" href="report?id=${ certificate.id }">증서 다운로드</a>
      <a class="btn btn-info btn-sm" href="list?${ pagination.queryString }">목록으로</a>
      <a class="btn btn-danger btn-sm" href="delete?id=${ certificate.id }&${ pagination.queryString }" data-confirm-delete>증서 삭제</a>
    </div>

    <table class="table table-bordered lbw120 pd8">  
      <tr>
        <td class="lb">증서번호</td>
        <td>${ certificate.certificateNo }</td>
        <td class="lb">발급일</td>
        <td>${ certificate.createDate }</td>
      </tr>
      <tr>
        <td class="lb">학과</td>
        <td>${ certificate.department }</td>
        <td class="lb">학번</td>
        <td>${ certificate.personNo }</td>
      </tr>
      <tr>
        <td class="lb">이름</td>
        <td>${ certificate.personName }</td>
        <td class="lb">발급인</td>
        <td>${ certificate.userName }</td>
      </tr>
      <tr>
        <td class="lb">내용</td>
        <td colspan="3">${ certificate.body }</td>
      </tr>
    </table>

  </div>
</div>  <div class="pull-right mb4">
</div>
