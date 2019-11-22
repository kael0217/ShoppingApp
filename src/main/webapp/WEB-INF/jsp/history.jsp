<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
	<title>History</title>
	<spring:url value="/resources/images" var="images" />
	<spring:url value="/resources/js/jquery-3.1.0.min.js" var="resourceJquery" />
	<script src="${resourceJquery}"></script>
	<spring:url value="/resources/js/bootstrap.min.js" var="resourceBootstrapJs" />
	<script src="${resourceBootstrapJs}"></script>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div class="container">
		<c:if test="${(sessionScope.userRole=='admin' || sessionScope.userRole=='user' ) && not empty pastorders}">
			<c:forEach var="listValue" items="${pastorders}">
				<p>${listValue.getOrderId()}</p><br>
				<p>${listValue.getUsername()}</p><br>
				<p>${listValue.getDate().toString()}</p><br>
				<p>${listValue.getStatus().toString()}</p><br>
				<p>${listValue.getAmountDeducted()}</p>
				<br><br><br>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>