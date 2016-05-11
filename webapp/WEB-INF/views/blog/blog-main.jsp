<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("newLine", "\r\n");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
console.log("${categoryList}")
console.log("${postList}")
console.log("${blogInfo}")
console.log("${id}")
console.log("${post}")
console.log("${category}")
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1><a href="${pageContext.request.contextPath}/blog/${id}">${blogInfo.title}</a></h1>
			<c:import url="/WEB-INF/views/include/blog_menu.jsp"></c:import>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${post.title}</h4>
					<p>
						${fn:replace(post.content, newLine, '<br>')}
					<p>
					<c:if test="${empty post}">
						<h4 style="text-align:center">작성된 글이 없습니다.</h4>
						<p style="text-align:center"><a href="${pageContext.request.contextPath}/blog/${id}/post-write-form">글 작성하러 가기</a></p>
					</c:if>
				</div>
				<c:if test="${not empty sessionScope.authUser}">
					<c:if test="${sessionScope.authUser.id == id }">
						<span><a href="${pageContext.request.contextPath}/blog/${id}/deletePost?no=${post.postId}">삭제</a></span>
					</c:if>
				</c:if>
				<ul class="blog-list">
					<c:forEach var="item" items="${postList}">
						<c:choose>
							<c:when test="${item.postId == post.postId }">
								<li><strong>${item.title}</strong> <span>${item.regDate}</span>	</li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/blog/${id}/${category.name}?no=${item.postId}">${item.title}</a> <span>${item.regDate}</span>	</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/assets/user_image/${blogInfo.logo}">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach var="item" items="${categoryList}">
					<c:choose>
						<c:when test="${item.name == category.name}">
							<li>${item.name}(${item.postCount})</li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageContext.request.contextPath}/blog/${id}/${item.name}">${item.name}(${item.postCount})</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogInfo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>