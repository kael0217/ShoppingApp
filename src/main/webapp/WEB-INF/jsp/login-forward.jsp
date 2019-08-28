<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    ${msg}
	<c:if test="${!sessionScope.userEntry.isLogin}">
		<div class="col-sm-3 col-rg-2 form-inline btn-group" role="group" aria-label="Basic example">
			<button class="nav navbar-nav btn btn-secondary my-2 my-sm-0 form-inline" onclick="javascript:location.href='/login'">Try Again</button>
			<button class="nav navbar-nav btn btn-secondary my-2 my-sm-0 form-inline" onclick="javascript:location.href='/register'">To Register</button>
		</div>
	</c:if>
</body>
</html>