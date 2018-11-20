<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<div class="navigation-info">
  &gt; 금융연동 &gt; EB13/EB14 결과
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>EB13/EB14 결과</h3> 
  </div>
  <div class="panel-body">

      <form:form class="mb4" modelAttribute="wrapper">
        <span>기간:</span>
        <form:input path="map[startDt]" class="startDt" /> ~
        <form:input path="map[endDt]" class="endDt" />
        <span>상태:</span>
        <form:select path="map[state]">
          <form:option value="all" label=" 전체" />
          <form:option value="null" label="신청전" />
          <form:option value="신청" label="신청" />
          <form:option value="에러" label="에러" />
          <form:option value="성공" label="정상" />
        </form:select>
        <button type="submit" class="btn btn-primary btn-sm">조회</button>
      </form:form>
          
      <my:scrollableTable tagId="eb14result">
        <jsp:attribute name="header">
           <tr>
            <th>회원번호</th>
            <th>회원</th>
            <th>약정번호</th>
            <th>계좌</th>
            <th>주민번호</th>
            <th>EB13생성일</th>
            <th>상태</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
          <c:forEach var="c" items="${ list }">
            <tr class="${ c.eb13State=='에러' ? 'my-error' : 'my-success' }">
              <td>${ c.sponsorNo }</td>
              <td>${ c.name }</td>
              <td>${ c.commitmentNo }</td>
              <td>${ c.bankName } ${ c.accountNo } ${ c.accountHolder }</td>
              <td>${ c.juminNo }</td>
              <td><fmt:formatDate pattern="yyyy-MM-dd" value="${ c.eb13Date }" /></td>
              <td>${ c.eb13State }</td>
            </tr>
            <c:if test="${ c.eb13State == '에러' }">
              <tr class="my-error"><td colspan="7">${ c.eb13ErrorCodeMsg }</td></tr>
            </c:if>            
          </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>
                  
    </div>
</div>          
