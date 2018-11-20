<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="R" value="/" />

<div class="navigation-info">
  &gt; 회원관리 &gt; 예우 업로드
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>예우 업로드</h3> 
  </div>
  <div class="panel-body">

    <form method="post" enctype="multipart/form-data">
      <span>파일:</span> <input type="file" name="file" style="min-width: 400px; height: 2em;"/> <br />
       
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="upload">업로드</button>
      <a class="btn btn-default btn-sm" href="${R}res/upload1.xlsx">업로드 양식 다운로드</a>
      
    </form>
      
  </div>
</div>          
