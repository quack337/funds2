<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="donationPurposeUpdateDialog" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>기부목적 일괄변경</h3>
      </div>
      <div class="modal-body">
        <table class="table table-bordered lbw150 mb4">
          <tr>
            <td class="lb">기간</td>
            <td><input type="text" name="startDate" class="startDt" /> ~ <input type="text"  name="endDate" class="endDt" /></td>
          </tr>
          <tr>
            <td class="lb">기부목적</td>
            <td><c:set var="paramObj" value="${ commitment }" />
              <%@include file="../_donationPurposeInput.jsp" %>
            </td>
          </tr>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary btn-sm" onclick="updateDonationPurpose()" data-dismiss="modal">변경사항 적용</button>
        <button class="btn btn-default btn-sm" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>  

