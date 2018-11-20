<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="fund.service.UserService,fund.service.C" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!
    static String menu(String title, String url, int menuId) {
        if (UserService.canAccess(menuId))
            return String.format("<li><a href='%s'>%s</a></li>", url, title);
        return "<li><a style='color:#aaa;'>" + title + "</a></li>";
    }
%>

<div class="wrapper">
  <nav class="navbar navbar-default" role="navigation" style="margin-bottom: 0px;" >
    <div class="container">
      <!-- Brand and toggle get grouped for better mobile display -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1">
          <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/funds2/">후원관리시스템</a>
      </div>

      <sec:authorize access="authenticated">
        <div class="navbar-collapse collapse" id="navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">기초정보관리<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("기관 관리", "/funds2/corporate/list.do", C.메뉴_기초정보관리)%>
                <%= menu("기부목적 관리", "/funds2/donationPurpose/list.do", C.메뉴_기초정보관리)%>
                <li class="divider"></li>
                <% if (UserService.canAccess(C.메뉴_기초정보관리)) { %>                
                  <c:forEach var="codeGroup" items="${ codeGroupList }">
                      <li><a href="/funds2/code/list.do?gid=${codeGroup.id}">${codeGroup.name} 관리</a></li>
                  </c:forEach>
                <% } %>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">회원관리<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("회원 관리", "/funds2/sponsor/list.do", C.메뉴_회원관리_회원관리)%>
                <%= menu("우편 발송", "/funds2/sponsor/dm.do", C.메뉴_회원관리_우편발송)%>
                <%= menu("예우 업로드", "/funds2/sponsor/event/upload.do", C.메뉴_회원관리_예우업로드)%>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">금융연동<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("EB13 생성", "/funds2/cms/eb13.do", C.메뉴_금융연동_EB13생성 )%>
                <%= menu("EB14 등록", "/funds2/cms/eb14.do", C.메뉴_금융연동_EB14등록 )%>
                <%= menu("EB13/14 결과조회", "/funds2/cms/eb14result.do", C.메뉴_금융연동_EB1314결과조회 )%>
                <li class="divider"></li>
                <%= menu("EB21 생성", "/funds2/cms/eb21.do", C.메뉴_금융연동_EB21생성 )%>
                <%= menu("EB22 등록", "/funds2/cms/eb22.do", C.메뉴_금융연동_EB22등록 )%>
                <%= menu("EB21/22 결과조회", "/funds2/cms/eb22result.do", C.메뉴_금융연동_EB2122결과조회 )%>
                <li class="divider"></li>
                <%= menu("자동이체 결과등록", "/funds2/cms/xfer.do", C.메뉴_금융연동_자동이체결과등록 )%>
                <%= menu("급여공제 결과등록", "/funds2/cms/sal.do", C.메뉴_금융연동_급여공제결과등록 )%>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">납입조회<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("납입 내역 조회", "/funds2/payment/srch1a.do", C.메뉴_납입조회_납입내역조회) %>
                <li class="divider"></li>
                <%= menu("회원별 납입 합계", "/funds2/payment/srch1b.do", C.메뉴_납입조회_회원별납입합계)%>
                <%= menu("기부목적별 납입 합계", "/funds2/payment/srch2/0.do", C.메뉴_납입조회_기부목적별납입합계)%>
                <%= menu("회원구분별 납입 합계", "/funds2/payment/srch2/1.do", C.메뉴_납입조회_회원구분별납입합계)%>
                <%= menu("소속교회별 납입 합계", "/funds2/payment/srch2/2.do", C.메뉴_납입조회_소속교회별납입합계)%>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">기부금영수증<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("기부금 영수증 발급대장", "/funds2/receipt/list.do", C.메뉴_영수증_기부금영수증발급대장)%>
                <%= menu("기부금 영수증 개별생성", "/funds2/receipt/create1.do", C.메뉴_영수증_영수증개별생성) %>
                <%= menu("기부금 영수증 일괄생성", "/funds2/receipt/create2.do", C.메뉴_영수증_영수증일괄생성) %>
                <%= menu("기부금 영수증 발급합계", "/funds2/receipt/sum.do", C.메뉴_영수증_기부금영수증발급합계) %>
                <li class="divider"></li>
                <%= menu("국세청 보고자료", "/funds2/receipt/taxData.do", C.메뉴_영수증_국세청보고자료) %>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">증서<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("장학증서", "/funds2/certificate/0/list.do", C.메뉴_증서_장학증서)%>
                <%= menu("기부증서", "/funds2/certificate/1/list.do", C.메뉴_증서_기부증서)%>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">기타<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("일정관리", "/funds2/todo/list.do", C.메뉴_기타_일정관리)%>
              </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">시스템관리<span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <%= menu("사용자 관리", "/funds2/user/list.do", C.메뉴_시스템관리)%>
                <%= menu("로그기록 관리", "/funds2/log/list.do", C.메뉴_시스템관리)%>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/funds2/user/myinfo.do"><sec:authentication property="user.name" /> 정보수정</a></li>
            <li><a href="/funds2/home/logout.do">로그아웃</a></li>
          </ul>
        </div>
      </sec:authorize>

      <sec:authorize access="not authenticated">
        <div class="navbar-collapse collapse" id="navbar-collapse-1">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/funds2/guest/login.do">로그인</a></li>
          </ul>
        </div>
      </sec:authorize>

    </div>
  </nav>

</div>
