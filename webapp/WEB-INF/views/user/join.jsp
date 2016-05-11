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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#btn-checkemail").click(function(){
		var id = $("#blog-id").val();
		
		//아이디가 아예 입력되지 않았으면 함수 끝냄
		if(id == "") return;
		
		console.log("join.jsp -> 들어온 아이디는 " + id + " 입니다.");
		
		$.ajax({
			url : "${pageContext.request.contextPath}/user/checkid?id=" + id,
			method : "get",
			dataType : "json",
			success : function(response){
				console.log("join.jsp -> " + response);
				if(response.result != "success"){
					return;
				}
				
				if(response.data == false){
					alert("이미 존재하는 아이디입니다. 다른 아이디를 사용해 주세요.");
					$("#blog-id").val("").focus();
					return;
				}
				
				$("#btn-checkemail").hide();
				$("#img-checkemail").show();
			},
			error : function(jqXHR, status, error){
				console.error(status + " : " + error)
			}
		});
	});
	
	$("#blog-id").change(function(){
		$("#btn-checkemail").show();
		$("#img-checkemail").hide();
	});
	
	//가입하기 전에 필수요소 체크
	$("#join-form").submit(function(){
		//1.이름
		if($("#name").val() == ""){
			alert("이름을 입력해주세요.")
			$("#name").focus();
			return false;
		}
		
		//2.아이디
		if($("#blog-id").val() == ""){
			alert("아이디를 입력해주세요.")
			$("#blog-id").focus();
			return false;
		}
		
		//3.중복체크
		if($("#img-checkemail").is(":visible") == false){
			alert("아이디 중복 체크를 해야 합니다.")
			return false;
		}
		
		//4.패스워드
		if($("#password").val() == ""){
			alert("패스워드를 입력해주세요.")
			$("#password").focus();
			return false;
		}
		
		//5.약관체크
		if($("#agree-prov").is(":checked") == false){
			alert("서비스 약관을 동의해야 합니다.")
			return false;
		}
	})
})
</script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url="/WEB-INF/views/include/user_menu.jsp"></c:import>
		<form class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name">이름</label>
			<input id="name"name="name" type="text" value="">
			
			<spring:hasBindErrors name="blogUserVo">
				<c:if test="${errors.hasFieldErrors('name') }">
					<c:set var="errorName" value="${errors.getFieldError('name').codes[0] }"/>
					<br>
					<strong style="color:red">
						<spring:message
							code="${errorName}"
							text="${errors.getFieldError('name').defaultMessage}"
						/>
					</strong>
				</c:if>
			</spring:hasBindErrors>
			
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text"> 
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			
			<spring:hasBindErrors name="blogUserVo">
				<c:if test="${errors.hasFieldErrors('id') }">
					<c:set var="errorName" value="${errors.getFieldError('id').codes[0] }"/>
					<br>
					<strong style="color:red">
						<spring:message
							code="${errorName}"
							text="${errors.getFieldError('id').defaultMessage}"
						/>
					</strong>
				</c:if>
			</spring:hasBindErrors>

			<label class="block-label" for="passwd">패스워드</label>
			<input id="password" name="passwd" type="password" />
			
			<spring:hasBindErrors name="blogUserVo">
				<c:if test="${errors.hasFieldErrors('passwd') }">
					<c:set var="errorName" value="${errors.getFieldError('passwd').codes[0] }"/>
					<br>
					<strong style="color:red">
						<spring:message
							code="${errorName}"
							text="${errors.getFieldError('passwd').defaultMessage}"
						/>
					</strong>
				</c:if>
			</spring:hasBindErrors>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>
</body>
</html>
