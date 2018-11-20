<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:url var="R" value="/" />

<!DOCTYPE html>
<html>
<head>
<tiles:insertAttribute name="head" />
<style>
    div.alert.autoclose { float: right; min-width: 300px; margin-right: 20px; }
</style>
</head>
<body>

<tiles:insertAttribute name="menu" />

<div class="container" style="background-color: white;">
	<div style="min-height: 800px; margin-top: 20px;">
		<tiles:insertAttribute name="content" />
	</div>
	<tiles:insertAttribute name="footer" />
</div>

<c:if test="${ not empty errorMsg }">
  <div class="modal myMsg" id="errorModal" role="dialog" tabindex='-1'>
    <div class="modal-dialog modal-sm">
      <div class="modal-content"  style="background-color: #fff0f0">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h3 class="modal-title">작업실패</h3>
        </div>
        <div class="modal-body">
          <p>${ errorMsg }</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <script>$("#errorModal").modal("show");</script>
</c:if>

<c:if test="${ not empty successMsg }">
  <div class="modal" id="successModal" role="dialog" tabindex='-1'>
    <div class="modal-dialog modal-sm">
      <div class="modal-content"  style="background-color: #f0fff0">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h3 class="modal-title">작업성공</h3>
        </div>
        <div class="modal-body">
          <p>${ successMsg }</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success btn-sm" data-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <script>$("#successModal").modal("show");</script>
</c:if>

<div class="modal" id="waitingModal" role="dialog" tabindex='-1'>
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3 class="modal-title">작업중</h3>
      </div>
      <div class="modal-body" style="text-align: center">
        <p>작업중입니다. 잠시 기다려 주세요.</p>
        <img src="${R}res/images/wait.gif" />
      </div>
    </div>
  </div>
</div>

<script>
function closeMsgModal() {
  $("div.modal.myMsg").modal("hide");
  $(document).off("keydown", closeMsgModal);
}
$(document).keydown(closeMsgModal);
function showWaitMsg() {
 $("#waitingModal").modal("show");
}
</script>
    
<sec:authorize access="authenticated">
 <script>
 function resetTimeout() { 
     startTime = new Date(); 
   }
   function checkTimeout() { 
         var span = (new Date() - startTime) / 1000;
         if (span > 30 * 60) {
           location.href = '${R}home/logout.do';
           alert('30분 동안 작업이 없어서 자동 로그아웃되었습니다.');
         }
       }
   
   $("body").click(resetTimeout);
   $("body").keydown(resetTimeout);
   resetTimeout();     
   setInterval(checkTimeout, 5000);          
 </script>
</sec:authorize>
    
</body>
</html>
