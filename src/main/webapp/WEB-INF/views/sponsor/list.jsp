<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="navigation-info">
  &gt; 회원 관리 &gt; 회원 목록
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>회원 목록</h3>
  </div>
  <div class="panel-body">
    
    <form:form method="get" modelAttribute="pagination" class="mt4">
      <input type="hidden" name="pg" value="1" />
      
      <div class="pull-right mb4">
        <button type="submit" class="btn btn-info btn-sm">회원 조회</button>
        <c:if test="${ pagination.notEmpty() }">
          <a href="list.do" class="btn btn-gray btn-sm">조회조건 취소</a>
        </c:if>
        <a href="excel.do" class="btn btn-success btn-sm">엑셀 다운로드</a>
        <a href="create.do?${pagination.queryString}" class="btn btn-primary btn-sm">회원 등록</a>
      </div>      

      <div>
        <span>정렬순서:</span>
        <form:select path="od" data-auto-submit="true">
          <form:option value="0" label="회원번호 순서" />
          <form:option value="1" label="이름 순서" />
          <form:option value="2" label="등록 순서" />
        </form:select>
      </div>
      
      <table class="table table-bordered lbw120 pd4">
        <tr>
          <td class="lb">회원번호/회원명</td>
          <td><form:input path="st" placeholder="회원번호나 회원명" /></td>    
          <td class="lb">가입구분</td>
          <td>
            <form:select path="st1" class="ml5">
              <form:option value="0" label="가입구분 " />
              <form:options itemValue="id" itemLabel="codeName" items="${ sponsorType1Codes }" />
            </form:select>
          </td>    
          <td class="lb">회원구분</td>
          <td>
	        <form:select path="st2" class="ml5 mr5">
	          <form:option value="0" label="회원구분 " />
	          <form:options itemValue="id" itemLabel="codeName" items="${ sponsorType2Codes }" />
	        </form:select>
          </td>    
        </tr>
        <tr>
          <td class="lb">가입일</td>
          <td colspan="3"><form:input path="startDate" class="startDt" /> ~ <form:input path="endDate" class="endDt" /></td>        
          <td class="lb">회원/비회원</td>
          <td>
            <form:select path="st3" class="ml5 mr5">
              <form:option value="0" label="회원/비회원 " />
              <form:option value="1" label="회원 " />
              <form:option value="2" label="비회원 " />
            </form:select>
          </td>    
        </tr>
      </table>      
      
      <table class="table table-bordered mt4">
        <thead>
          <tr>
            <th>회원번호</th>
            <th>이름</th>
            <th>가입구분</th>
            <th>회원구분</th>
            <th>소속교회</th>
            <th>가입일</th>
            <th>연락처</th>
            <th>이메일</th>
    
          </tr>
        </thead>
        <tbody>
          <c:forEach var="sponsor" items="${list}">
            <tr data-url="edit.do?id=${sponsor.id}&${pagination.queryString}">
              <td>${sponsor.sponsorNo}</td>
              <td>${sponsor.name}</td>
              <td>${sponsor.sponsorType1}</td>
              <td>${sponsor.sponsorType2}</td>
              <td>${sponsor.church}</td>
              <td>${sponsor.signUpDate}</td>
              <td>${sponsor.mobilePhone}</td>
              <td>${sponsor.email}</td>
            </tr>
          </c:forEach>
          <c:if test="${ list.size() == 0 }">
              <tr>
                  <td colspan="8">조회 결과가 없습니다.</td>
              </tr>
          </c:if>          
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