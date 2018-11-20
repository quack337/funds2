$(function() {
    $("tr[data-url]").click(function() {
        location.href = $(this).attr("data-url");
    });

    $("ul.pagination a").click(function() {
        $("input[name=pg]").val($(this).attr("data-page"));
        $("form").submit();
    });
    
    $("[data-auto-submit=true]").change(function() {
        $(this).parents("form").submit();
    });
    
    $("[data-confirm]").click(function() {
    	var msg = $(this).attr("data-confirm");
    	return confirm(msg);
    })
    
    $("[data-confirm-delete]").click(function() {
    	return confirm("삭제하시겠습니까?");
    })    

    function stop_propagation_handler(e) {
      e.stopPropagation();
    }
    $("td input:checkbox").each(function(c) {
    	$(this).parent().click(stop_propagation_handler);
    })    

    $("thead input:checkbox").click(function() {
    	$("tbody input:checkbox").each(function() {
    		$(this).prop("checked", !$(this).prop("checked"));
    	});
    })
    	   
});

$(function() {                          
	$(".money").mask("00,000,000,000",{reverse: true});

	$("form").submit(function() {
		$(".money").unmask();
	});
});

$(function() {
    // 기간 설정 타입 1 
    // start Date 설정시 end Date의 min Date 지정
    $( ".startDt" ).datepicker({
        dateFormat: "yy-mm-dd",
        dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
        monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        numberOfMonths: 1,
        changeMonth: true,
        showMonthAfterYear: true ,
        changeYear: true,
        onClose: function( selectedDate ) {
            $( "#endDt" ).datepicker( "option", "minDate", selectedDate );
        }
    }); 
     // end Date 설정시 start Date max Date 지정
    $( ".endDt" ).datepicker({
        dateFormat: "yy-mm-dd",
        dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
        monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        numberOfMonths: 1,
        changeMonth: true,
        showMonthAfterYear: true ,
        changeYear: true,
        onClose: function( selectedDate ) {
            $( "#startDt" ).datepicker( "option", "maxDate", selectedDate );
        }
    });

    // 기간 설정 타입 2 
    // start Date 설정시 end Date 가 start Date보다 작을 경우 end Date를 start Date와 같게 설정
    $(".startDt").datepicker({
        dateFormat: "yy-mm-dd",
        defaultDate: "+1w",
        numberOfMonths: 1,
        changeMonth: true,
        showMonthAfterYear: true ,
        changeYear: true,
        onClose: function( selectedDate ) {
            if ($( "#endDt" ).val() < selectedDate) $( "#endDt" ).val(selectedDate);
        }
    }); 
    // end Date 설정시 end Date 가 start Date 보다 작을 경우 start Date를  end Date와 같게 설정
    $( ".endDt" ).datepicker({
        dateFormat: "yy-mm-dd",
        defaultDate: "+1w",
        numberOfMonths: 1,
        changeMonth: true,
        showMonthAfterYear: true ,
        changeYear: true,
        onClose: function( selectedDate ) {
            if ($("#startDt" ).val() > selectedDate)
                $("#startDt" ).val(selectedDate);
        }
    });
    //날짜
    $( ".date" ).datepicker({
        changeMonth: true ,
        changeYear: true ,
        showMonthAfterYear: true ,
        dateFormat: "yy-mm-dd",
        dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
        monthNames: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
        numberOfMonths: 1
    }); 
}); 

$(function () {
  $('[data-toggle="popover"]').popover()
});

function cancelSearch() { // 납입 내역 조회 화면에서...
  var url = location.href;
  url = url.replace(/\?.+$/, '');
  location.href = url;
}

function tableScroll() {
    var thead = $("table#body thead tr");
    if (thead.length > 0) {
      $("table#head").width( $("table#body").width() );
      $("table#body th, table#body tr:nth-child(1) td").each( function() {
          $(this).width($(this).width());
      });
      thead.appendTo( $("table#head thead") );
    }
}

var tableHVScroll2_unique_id = 0;

function tableHVScroll2(table) {    
  if (table.hasClass("tableHVScroll2")) return;
  table.addClass("tableHVScroll2");
  table.css("white-space", "nowrap");
  id = "tableHVScroll2" + ++tableHVScroll2_unique_id;
  var template =
      "<div id='" + id + "'>" +
        "<div id='scroll1' style='margin-top: 10px;  overflow: hidden !important; border: 1px solid #eee;'>" +
          "<table id='head' class='table table-bordered' style='white-space: nowrap; margin-bottom:0px; table-layout: fixed;'>" +
            "<thead style='white-space: nowrap;'>" +
            "</thead>" +
          "</table>" +
        "</div>" +
        "<div id='scroll2'  style='overflow: auto; max-height: 600px; border: 1px solid #eee; margin-bottom: 10px;'>" +
        "</div>" +
      "</div>";
      
    $(template).insertAfter(table);
    var root = $("#" + id);
    table.appendTo( root.find("#scroll2") );

    var thead = table.find("thead tr");
    root.find("#scroll1").width( root.find("#scroll2").prop("clientWidth") );
    root.find("table#head").width( root.find("#scroll2 table").width() );
    root.find("table#head").css({ "min-width": root.find("#scroll2 table").width() });
    root.find("#scroll2 table th, #scroll2 table tr:nth-child(1) td").each( function() {
        $(this).width($(this).width());
    });
    thead.appendTo( root.find("table#head thead") );

    root.find("#scroll2").on('scroll', function () {
        root.find("#scroll1").scrollLeft($(this).scrollLeft());
    });
}
