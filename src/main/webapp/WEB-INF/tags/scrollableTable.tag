<%@ tag description="pagination" pageEncoding="UTF-8"%>
<%@ tag import="java.util.ArrayList" %>
<%@ attribute name="tagId" required="true" %>
<%@ attribute name="header" required="true" fragment="true" %>
<%@ attribute name="body" required="true" fragment="true" %>

<style>
  #scroll1 th { white-space: normal; }
  #scroll2 td { white-space: nowrap; }
</style>

<div id="${ tagId }">
	<div id='scroll1' style='overflow: hidden; border: 1px solid #eee; width:100%;'>
	  <table id='head' class='table table-bordered' style='white-space: nowrap; margin-bottom:0px; table-layout: fixed;'>
	    <thead style='white-space: nowrap;'>
	      <jsp:invoke fragment="header" />
	    </thead>
	  </table>
	</div>
	<div id='scroll2'  style='overflow-y: scroll; overflow-x: auto; max-height: 600px; border: 1px solid #eee; margin-bottom: 10px; width:100%;'>    
	  <table id="srch1a" class="table table-bordered mt4">
	    <tbody>
	      <jsp:invoke fragment="body" />
	    </tbody>
	  </table>
	</div>
</div>

<script>
function initScrollableTable(tagId) {
  var root = $("div#" + tagId);
  var scroll1 = root.find("#scroll1");
  var scroll2 = root.find("#scroll2");
  var table1 = scroll1.find("table");
  var table2 = scroll2.find("table");
  
  var th = table1.find("th");
  var td = table2.find("tr:nth-child(1) td");
  
  if (td.length > 0) {
      scroll1.width( scroll2.width() - 18 );
      table1.width( table2.width() );
  
  	for (var i = 0; i < th.length; ++i) {
  	    $(th.get(i)).width( $(td.get(i)).width() );
  	}
  	
  	scroll2.on('scroll', function () {
  	    scroll1.scrollLeft(scroll2.scrollLeft());
          console.log(scroll2.scrollLeft() + " " + scroll1.scrollLeft());
  	});
  }
}
initScrollableTable("${ tagId }")
</script>
