<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="navigation-info">
  &gt; 시스템 관리 &gt; 로그기록 관리 &gt; <a href="list.do">로그기록 목록</a> &gt; 로그기록 상세
</div>

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>로그기록 상세</h3>
  </div>
  <div class="panel-body">    
    <table class="table table-bordered lbw150">
      <tr>
        <td class="lb">카테고리</td>
        <td>${ log.category }</td>    
      </tr>
      <tr>
        <td class="lb">사용자</td>
        <td>${ log.currentUser }</td>    
      </tr>
      <tr>
        <td class="lb">IP</td>
        <td>${ log.ip }</td>    
      </tr>
      <tr>
        <td class="lb">시각</td>
        <td><fmt:formatDate value="${ log.writeTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>    
      </tr>
      <tr>
        <td class="lb">URL</td>
        <td>${ log.url }</td>    
      </tr>
      <tr>
        <td colspan="2"><pre style="white-space: pre-wrap;">${ log.body }</pre></td>
      </tr>
    </table>
    <c:set var="q" value="${ pagination.queryString }" />
    <div class="">
      <a class="btn btn-danger btn-sm" href="delete.do?id=${log.id}&${q}" data-confirm-delete>삭제</a>
      <a class="btn btn-gray btn-sm" href="list.do">목록으로 나가기</a>
    </div>
 </div>
</div>

