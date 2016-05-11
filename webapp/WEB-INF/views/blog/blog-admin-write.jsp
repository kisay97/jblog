<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
console.log("${id}");
console.log("${blogInfo}");
console.log("${categoryList}");
console.log("${error}")

$(function(){
	$("form").submit(function(event){
		var title = $("input[name='title']").val();
		var category = $("select").val();
		var content = $("textarea").val();
		var trimContent = $.trim($("textarea").val());
		
		console.log(title)
		console.log(category)
		console.log(content)
		
		if(title == ""){
			alert("제목을 입력하세요");
			event.preventDefault();
			return;
		}
		if(category == ""){
			alert("카테고리를 입력하세요");
			event.preventDefault();
			return;
		}
		if(trimContent == ""){
			alert("내용을 입력하세요");
			event.preventDefault();
			return;
		}
	})
})
</script>
<body>
	<div id="container">
		<div id="header">
			<h1><a href="${pageContext.request.contextPath}/blog/${id}">${blogInfo.title}</a></h1>
			<c:import url="/WEB-INF/views/include/blog_menu.jsp"></c:import>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/blog/${id}/blog-admin-basic">기본설정</a></li>
					<li><a href="${pageContext.request.contextPath}/blog/${id}/blog-admin-category">카테고리</a></li>
					<li class="selected">글작성</li>
				</ul>
				<spring:hasBindErrors name="postVo">
					<c:if test="${errors.hasFieldErrors('title') }">
						<script type="text/javascript">
							alert("제목을 입력하세요");
						</script>
					</c:if>
					<c:if test="${errors.hasFieldErrors('content') }">
						<script type="text/javascript">
							alert("내용을 입력하세요");
						</script>
					</c:if>
				</spring:hasBindErrors>
				<form action="${pageContext.request.contextPath}/blog/${id}/postWrite" method="post">
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<input type="text" size="60" name="title">
				      			<select name="categoryId">
				      				<c:forEach var="item" items="${categoryList}">
				      					<option value="${item.no}">${item.name}</option>
				      				</c:forEach>
				      			</select>
				      		</td>
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td><textarea name="content"></textarea></td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="submit" value="포스트하기"></td>
			      		</tr>
			      	</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>${blogInfo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>