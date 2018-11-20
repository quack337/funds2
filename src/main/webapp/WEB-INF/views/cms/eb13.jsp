<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<div class="navigation-info">
  &gt; 금융연동 &gt; EB13 생성
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>EB13 생성</h3> 
  </div>
  <div class="panel-body">

      <form class="mb4" method="post">
        <span>EB13신청일:</span>
        <input name="date" type="text" class="date" value="${ today }" />
        <button type="submit" class="btn btn-primary btn-sm" >EB13 파일 생성</button>
      </form>
          
      <my:scrollableTable tagId="eb13">
        <jsp:attribute name="header">
           <tr>
            <th>회원번호</th>
            <th>회원</th>
            <th>약정번호</th>
            <th>약정일</th>
            <th>계좌</th>
            <th>생년월일</th>
           </tr>        
        </jsp:attribute>
        <jsp:attribute name="body">
          <c:forEach var="c" items="${ list }">
            <tr class="${ c.valid ? '' : 'my-error' }">
              <td>${ c.sponsorNo }</td>
              <td>${ c.name }</td>
              <td>${ c.commitmentNo }</td>
              <td>${ c.commitmentDate }</td>
              <td>${ c.bankName } ${ c.accountNo } ${ c.accountHolder }</td>
              <td>${ c.birthDate }</td>
            </tr>
          </c:forEach>
        </jsp:attribute>
    </my:scrollableTable>
    
    <c:if test="${ list.size() <= 0 }">
        <div>대상자 없음</div>      
    </c:if>
    

    </div>
</div>          
