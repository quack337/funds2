<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="R" value="/" />

<ul class="nav nav-tabs mb4">
  <li class="${ tab1 }"><a href="${R}sponsor/edit?id=${sponsor.id}&${pagination.queryString}">회원정보</a></li>
  <c:if test="${ sponsor.id != 0 }">
    <li class="${ tab2 }"><a href="${R}sponsor/commitment/list?sid=${sponsor.id}&${pagination.queryString}">약정관리</a></li>
    <li class="${ tab3 }"><a href="${R}sponsor/payment/list1?sid=${sponsor.id}&${pagination.queryString}">정기납입관리</a></li>
    <li class="${ tab4 }"><a href="${R}sponsor/payment/list2?sid=${sponsor.id}&${pagination.queryString}">비정기납입관리</a></li>
    <li class="${ tab5 }"><a href="${R}sponsor/payment/list3?sid=${sponsor.id}&${pagination.queryString}">현물납입관리</a></li>
    <li class="${ tab6 }"><a href="${R}sponsor/files?id=${sponsor.id}&${pagination.queryString}">첨부파일(${fileCount})</a></li>
    <li class="${ tab7 }"><a href="${R}sponsor/log/list?id=${sponsor.id}&${pagination.queryString}">변경이력</a></li>
    <li class="${ tab8 }"><a href="${R}sponsor/event/list?sid=${sponsor.id}&${pagination.queryString}">예우관리</a></li>
  </c:if>
</ul>