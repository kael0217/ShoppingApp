<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Add Product</title>
</head>
<body>

<form action="/services/addProductWithS3" method="POST" enctype="multipart/form-data">
    <input type="file" name="file"><br /> 
    <input type="submit" name="submit" value="TESTPOST">
</form>
<form action="/services/getProducts" method="get"> 
    <input type="submit" name="TestService" value="TEST">
</form>

</body>
</html>