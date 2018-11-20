<%@ tag description="pagination" pageEncoding="UTF-8"%>
<%@ attribute name="returnUrl" required="true" %>
<%@ attribute name="foreignType" required="true" %>
<%@ attribute name="foreignId" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="R" value="/" />
        
<table class="table table-bordered mt10 w700">
  <thead>
    <tr>
      <th>파일명</th>
      <th class="right">파일크기</th>
      <th>날짜</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="file" items="${ files }">
      <tr>
        <td>${ file.fileName }</td>
        <td class="right"><fmt:formatNumber value="${ file.fileSize }" groupingUsed="true"/></td>
        <td><fmt:formatDate value="${ file.createDate }" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
        <td>
            <a class="btn btn-success btn-xs" href="${R}dataFile/download.do?id=${file.id}"> 다운로드</a> 
            <a class="btn btn-danger btn-xs" href="${R}dataFile/delete.do?id=${file.id}&returnUrl=${ returnUrl }" data-confirm-delete> 삭제</a>            
        </td>
      </tr>
    </c:forEach>
     <tr>
       <td colspan="4">
           <form method="post" action="${R}dataFile/upload.do" enctype="multipart/form-data">
             <input type="hidden" name="foreignType" value="${ foreignType }" />
             <input type="hidden" name="foreignId" value="${ foreignId }" />
             <input type="hidden" name="returnUrl" value="${returnUrl }" />
             <span>파일:</span> <input type="file" name="data" style="min-width: 400px; height: 2em; display:inline-block;"/> 
             <button type="submit" class="btn btn-primary btn-sm"><i class="icon-ok icon-white"></i> 첨부파일 업로드</button>
           </form>  
       </td>
     </tr>
    <c:if test="${ list.size() == 0 }">
        <tr><td colspan="4">첨부파일이 없습니다.</td></tr>
    </c:if>        
  </tbody>
</table>

