<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<style>
    div.modal-dialog { width: 400px; }
    div#scroll { height: 500px; width: 100%; overflow-y: scroll; }
    #scroll tbody tr:hover { background-color: #ffe; cursor: pointer; }
</style>

<div id="churchDialog" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>교회 조회</h3>
      </div>
      <div class="modal-body">
        <div> 
          <input type="text" id="srchText" onkeydown="if (event.keyCode == 13) filterChurch()" />
          <button type="button" class="btn btn-primary btn-sm" onclick="filterChurch()">조회</button>
        </div>
        
	    <my:scrollableTable tagId="churchScrollTable">
	        <jsp:attribute name="header">
	           <tr>
	           </tr>        
	        </jsp:attribute>
	        <jsp:attribute name="body">
	            <c:forEach var="church" items="${ churchList }">
	              <tr class="hover">
	                  <td>${ church.id }</td>
	                  <td>${ church.codeName }</td>
	              </tr>
	            </c:forEach>
	        </jsp:attribute>
	    </my:scrollableTable>        
              
      </div>
      <div class="modal-footer">
        <button onclick="cancelChurch()" class="btn btn-danger btn-sm" data-dismiss="modal">소속교회 없음</button>
        <button class="btn btn-default btn-sm" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>

<script>
    $(document).ready(function() {
        $("#churchDialog tbody tr").click(function() {
            var tr = $(this);
            var churchId = tr.find("td:nth-child(1)").text();
            console.log(churchId);
            var churchName = tr.find("td:nth-child(2)").text();
            $("span#churchName").text(churchName);
            $("input[name*=churchName]").val(churchName);
            $("input[name*=churchId]").val(churchId);
            $("#churchDialog").modal('toggle');
        })
    });
    function cancelChurch() {
        $("span#churchName").text('');
        $("input[name*=churchName]").val('');
        $("input[name=churchId]").val(0);
    }
    function filterChurch() {
        var s = $("#churchDialog #srchText").val().trim();
        var list = $("#churchDialog tbody tr");
        list.show();
        if (s.length > 0) {
            list.each(function() {
               var text = $(this).find("td:nth-child(2)").text();
               if (text.indexOf(s) < 0) $(this).hide();
            });
        }
    }
</script>
