<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="R" value="/" />

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="${R}res/js/daum_postcode.js"></script>

<c:set var="mode" value="${ corporate.id > 0 ? '수정' : '등록' }" />

<div class="navigation-info">
  &gt; 기초정보 관리 &gt; 기관 관리 &gt; <a href="list.do">기관 목록</a> &gt; 기관 ${ mode }
</div>

<form:form method="post" modelAttribute="corporate">

<div class="panel panel-default shadow w900">
  <div class="panel-heading">
    <h3>기관 ${ mode }</h3>
  </div>
  <div class="panel-body">    
    <table class="table table-bordered lbw120 pd4">
      <tr>
        <td class="lb">기관명</td>
        <td><form:input path="name" tabindex="1" class="w200" /></td>
        <td class="lb">우편번호</td>
        <td >
            <form:input path="postCode" tabindex="2" />
            <button type="button" class="btn btn-flat btn-xs" onclick="postCodeSearch('postCode', 'roadAddress')">우편번호 찾기</button>
        </td>
      </tr>
      <tr>
        <td class="lb">사업자등록번호</td>
        <td><form:input path="corporateNo" tabindex="1" /></td>
        <td class="lb">도로명주소</td>
        <td><form:input path="roadAddress" class="w300" tabindex="2" /></td>
      </tr>
      <tr>
        <td class="lb">대표자명</td>
        <td><form:input path="representative" tabindex="1" /></td>
        <td class="lb">상세주소</td>
        <td><form:input path="detailAddress" class="w300" tabindex="2" /></td>
      </tr>
      <tr>
        <td class="lb">약칭</td>
        <td><form:input path="shortName" tabindex="1" /></td>
        <td class="lb"></td>
        <td></td>
      </tr>
    </table>  

    <div class="">
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="save">기관 저장</button>
      <c:if test="${ corporate.id > 0 }">
        <button type="submit" class="btn btn-danger btn-sm" name="cmd" value="delete" data-confirm-delete>기관 삭제</button>
      </c:if>    
      <a href="list.do" class="btn btn-gray btn-sm">기관 목록으로</a>
    </div>
  </div>
</div>

</form:form>
