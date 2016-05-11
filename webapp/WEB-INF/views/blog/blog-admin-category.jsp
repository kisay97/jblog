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
<script type="text/javascript">
$(function(){
	var renderHTML = function(vo, count){
		var html = 	"<tr id='list-" + count + "'>"
				+		"<td>0</td>"
				+		"<td>" + vo.name + "</td>"
				+		"<td>" + vo.postCount + "</td>"
				+		"<td>" + vo.discription + "</td>"
				+		"<td>"
				+		"<img src='${pageContext.request.contextPath}/assets/images/delete.jpg' onclick='deleteButtonClick(" + vo.no + ", " + "0" + ")'>"
				+		"</td>"
				+	"</tr>";
		return html;
	}
	
	$("#insertForm").submit(function(event){
		event.preventDefault();
		console.log(this);
		
		var name = $("input[name='name']").val();
		var disc = $("input[name='desc']").val();
		if(name == ""){
			alert("이름을 입력하세요");
			return;
		}
		if(disc == ""){
			alert("설명을 입력하세요");
			return;
		}
		$("input[name='name']").val("");
		$("input[name='desc']").val("");
		
		$.ajax({
			url : "${pageContext.request.contextPath}/blog/${id}/Ajax-insertCategory?name="+name+"&disc="+disc,
			type : "get",
			dataType : "json",
			success : function(res){
				console.log(res.data);
				
				//html 붙임
				$before_tds = $(".admin-cat tr td:first-child");
				var before_count = $before_tds.length;
				$(".admin-cat tbody").append(renderHTML(res.data, before_count+1))
				
				//인덱싱 해줌
				$after_tds = $(".admin-cat tr td:first-child");
				var after_count = $after_tds.length;
				$after_tds.each(function(index, td){
					var $td = $(td);
					$td.text(after_count - index);
				});
				
				alert("추가되었습니다.");
			},
			error : function(xhr, status, error){
				console.error(status + " : " + error);
			}
		})
	})
})

var deleteButtonClick = function(no, listNo){
	$.ajax({
		url : "${pageContext.request.contextPath}/blog/${id}/Ajax-deleteCategory?categoryNo="+no,
		type : "get",
		dataType : "json",
		success : function(response){
			if(response.result == -2){
				alert("포스트가 남아있는 카테고리는 지울 수 없습니다.");
				return;
			}
			
			if(response.result == -1){
				alert("알 수 없는 이유로 카테고리를 삭제할 수 없습니다.")
				return;
			}
			
			if(response.result == 1){
				$("#list-"+listNo).remove();
				
				$tds = $( ".admin-cat tr td:first-child" );
				var count = $tds.length;

				$tds.each(function(index, td){
					var $td = $(td);
					$td.text( count - index );
				});
				
				alert("삭제되었습니다.")
			}
		},
		error : function(xhr, status, error){
			console.error(status + " : " + error);
		}
	})
};

$()
</script>
</head>
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
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/blog/${id}/post-write-form">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		<c:set var="count" value="${fn:length(categoryList)}" />
					<c:forEach var="item" items="${categoryList}" varStatus="status">
		      			<tr id="list-${ count - status.count }">
		      				<td>${ count - status.index }</td>
		      				<td>${ item.name }</td>
		      				<td>${ item.postCount }</td>
		      				<td>${ item.discription }</td>
		      				<c:choose>
		      					<c:when test="${item.postCount > 0}">
		      						<td>&nbsp;</td>
		      					</c:when>
		      					<c:otherwise>
		      						<td><img src="${pageContext.request.contextPath}/assets/images/delete.jpg" onclick="deleteButtonClick(${item.no}, ${ count - status.count })"></td>
		      					</c:otherwise>
		      				</c:choose>
		      			</tr>
		      		</c:forEach>				  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form id = "insertForm">
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" name="desc"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
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