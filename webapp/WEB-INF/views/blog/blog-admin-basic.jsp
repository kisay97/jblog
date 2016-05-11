<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("input[type='file']").change(function(){
			$file = $(this).val();
			if($file == null || $.isEmptyObject($file)) return;
			
			var formData = new FormData(document.getElementById('uploadForm'));
			
			$.ajax({
				url : "${pageContext.request.contextPath}/blog/${id}/ajax-fileUpload",
				data : formData,
				processData : false,
				contentType : false,
				type : "POST",
				success : function(response){
					console.log(response)
					$("#logoImage").attr("src", "${pageContext.request.contextPath}" + response.data );
				},
				error : function(xhr, status, error) {
					console.error(status + " : " + error);
				}
			})
		})
		
		$("#uploadForm").submit(function(event){
			event.preventDefault();
			console.log(this)
			
			var blog_title = $("input[name=title]").val();
			console.log(blog_title);
			if(blog_title == null || blog_title == ""){
				alert("블로그 제목을 입력해주세요.")
				$("input[name=title]").focus();
				return;
			}
			
			$.ajax({
				url : "${pageContext.request.contextPath}/blog/${id}/admin-basic-submit",
				data : "title="+blog_title,
				type : "post",
				dataType : "json",
				success : function(response){
					var title = response.title;
					$("#title").text(title);
					$("#footerTitle").text(title);
					alert("수정되었습니다.")
				},
				error : function(xhr, status, error) {
					console.error(status + " : " + error);
				}
			})
		})
	})
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1><a href="${pageContext.request.contextPath}/blog/${id}" id="title">${blogInfo.title }</a></h1>
			<c:import url="/WEB-INF/views/include/blog_menu.jsp"></c:import>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li class="selected">기본설정</li>
					<li><a href="${pageContext.request.contextPath}/blog/${id}/blog-admin-category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath}/blog/${id}/post-write-form">글작성</a></li>
				</ul>
				<form id = "uploadForm" enctype = "multipart/form-data" action="${pageContext.request.contextPath}/blog/${id}/admin-basic-submit" method="post">
	 		      	<table class="admin-config">
			      		<tr>
			      			<td class="t">블로그 제목</td>
			      			<td><input type="text" size="40" name="title" value="${blogInfo.title}"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">로고이미지</td>
			      			<td><img src="${pageContext.request.contextPath}/assets/user_image/${blogInfo.logo}" alt="로고" id="logoImage"></td>      			
			      		</tr>      		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td><input type="file" name="logo-file"></td>      			
			      		</tr>           		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td class="s"><input type="submit" value="기본설정 변경"></td>      			
			      		</tr>           		
			      	</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong id="footerTitle">${blogInfo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>