<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url var="R" value="/" />

<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>
<script src="${R}res/js/vue_mine.js"></script>

<c:set var="mode" value="${ payment.id > 0 ? '수정' : '등록' }" />

<div class="navigation-info">
  &gt; 회원 관리 &gt; <a href="${R}sponsor/list?${ pagination.queryString }">회원 목록</a>  
  &gt; 현물납입 관리 &gt; <a href="list3?sid=${ sponsor.id }&${ pagination.queryString }">현물납입 목록</a>
  &gt; 현물납입 ${ mode } 
</div>

<div id="app" class="panel panel-default shadow">
  <div class="panel-heading">
    <%@include file="../_title.jsp" %> 
  </div>
  <div class="panel-body">  
    <c:set var="tab5" value="active" />
    <%@include file="../_tab2.jsp" %> 

    <div style="width: 800px">
      <table class="table table-bordered lbw120 mt10 pd4">
        <tr>
          <td class="lb">납입일</td>
          <td><input type="text" id="paymentDate" vlaue="${ payment.paymentDate }" class="date" /></td>
          <td class="lb">납입방법</td>
          <td>현물</td>
        </tr>
        <tr>
          <td class="lb">납입액</td>
          <td>{{ sum() | number }}</td>
          <td class="lb">기부목적</td>
          <td>
              <c:set var="paramObj" value="${ payment }" />
              <%@include file="../_donationPurposeInput.jsp" %>
          </td>
        </tr>
        <tr>  
          <td class="lb">비고</td>
          <td colspan="3"><textarea id="etc" class="w600 h100">${ payment.etc }</textarea></td>
        </tr>
      </table>

      <table class="table table-bordered lbw120 mt10 pd4">
        <thead>
          <tr>
            <th>품목</th><th>수량</th><th>단가</th><th>금액</th><th></th>
          <tr>
        </thead>
        <tbody>
          <tr v-for="(p, index) in items">
              <td>{{ p.title }}</td>
              <td class="right">{{ p.quantity | number }}</td>
              <td class="right">{{ p.unitCost | number }}</td>              
              <td class="right">{{ p.quantity * p.unitCost | number }}</td>
              <td style="width:150px">
                <button type="button" class="btn btn-success btn-sm" v-on:click="editItem(index)">수정</button>
                <button type="button" class="btn btn-warning btn-sm" v-on:click="deleteItem(index)">삭제</button>
              </td>
          </tr>
          <tr>            
            <td colspan="5">
              <button type="button" class="btn btn-info btn-sm" v-on:click="createItem">현물 추가</button>
            </td>
          </tr>
        </tbody>
      </table>      
  
      <div>
        <button type="button" class="btn btn-primary btn-sm" v-on:click="saveAll()">현물납입 저장</button>
        <c:if test="${ payment.id > 0 }">
          <button type="submit" class="btn btn-danger btn-sm" data-confirm-delete>현물납입 삭제</button>
        </c:if>    
        <a href="list3?sid=${ sponsor.id }&${ pagination.queryString }" class="btn btn-gray btn-sm">현물납입 목록으로</a>
      </div>
    </div>
  </div>
  
  <div class="modal fade" id="editModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">현물</h4>
        </div>
        <div class="modal-body">
          <table class="table table-bordered lbw120 mt10 pd4">
            <tr>
              <td class="lb">품목</td>
              <td><input type="text" v-model="item.title" style="width: 300px;" id="title" /></td>
            </tr>
            <tr>
              <td class="lb">수량</td>
              <td><input type="text" v-model="item.quantity" /></td>
            </tr>
            <tr>
              <td class="lb">단가</td>
              <td><input type="text" v-model="item.unitCost" /></td>
            </tr>
            <tr>
              <td class="lb">금액</td>
              <td style="padding:8px;">{{ (item.quantity * item.unitCost ? item.quantity * item.unitCost : 0) | number }}</td>
            </tr>
          </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal" v-on:click="saveItem">저장</button>
          <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
  
</div>

<%@include file="../_donationPurposeDialog.jsp" %>

<script type="text/javascript">
var app = new Vue({
  el: '#app',
  data: {
      payment: { },
      items: [],
      item: {},
      selectedIndex: -1
  },
  methods: {
      createItem: function() {
          this.selectedIndex = -1;
          this.item = {};
          $("#editModal").modal("show");
          $("input#title").focus();
      },
      editItem: function(index) {
          this.selectedIndex = index;
          this.item = Object.assign({}, this.items[index]);
          $("#editModal").modal("show");
          $("input#title").focus();
      },
      deleteItem: function(index) {
          if (confirm("삭제하시겠습니까?"))
              this.items.splice(index, 1);
      },
      saveItem: function() {
          if (this.selectedIndex >= 0) {
              this.items[this.selectedIndex] = this.item;
          } else
              this.items.push(this.item);
      },
      sum: function() {
          let s = 0;
          this.items.forEach(function(item) {
              let t = item.quantity * item.unitCost;
              if (t) s += t;
          });
          return s;
      },
      saveAll: function() {
          this.payment.paymentDate = $("#paymentDate").val();
          this.payment.donationPurposeId = $("#donationPurposeId").val();
          this.payment.etc = $("#etc").val();
          this.payment.amount = this.sum();
          this.payment.items = this.items;          
          console.log(this.payment);
          axios.post(location.href, this.payment).then(function (response) {
              console.log(response);
            })
            .catch(function (error) {
              console.log(error);
            });
      }
  }
})

$('#editModal').on('shown.bs.modal', function () {
    $('input#title').focus();
})    
</script>
