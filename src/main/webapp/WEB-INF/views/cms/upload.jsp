<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navigation-info">
  &gt; 금융연동 &gt; ${ title }
</div>

<div class="panel panel-default shadow">
  <div class="panel-heading">
    <h3>${ title }</h3> 
  </div>
  <div class="panel-body">

    <form method="post" enctype="multipart/form-data">
      <span>파일:</span> <input type="file" name="data" style="min-width: 400px; height: 2em;"/> <br />
       
      <button type="submit" class="btn btn-primary btn-sm" name="cmd" value="upload">업로드</button>
    </form>

      
    </div>
</div>          
   


