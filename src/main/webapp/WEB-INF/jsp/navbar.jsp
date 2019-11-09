<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"

import="com.levent.pcd.model.UserEntry"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NavBar</title>
    <spring:url value="/products" var="productListPageURL" />
	<spring:url value="/shopping-cart" var="shoppingCartPageURL" />	
	<spring:url value="/resources/images" var="images" />	
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/addproduct" var="addProductPageURL" />	
    
    <link href="${bootstrapCss}" rel="stylesheet">
    <spring:url value="/resources/css/4-col-portfolio.css" var="themeCSS" />
    <link href="${themeCSS}" rel="stylesheet">
    <spring:url value="/resources/css/custom.css" var="customCSS" />
    <link href="${customCSS}" rel="stylesheet">
</head>
<body>
    <!-- Navigatione -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${productListPageURL}">Shopper's Club</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            
            <ul class="nav navbar-nav collapse navbar-collapse bs-example-navbar-collapse-1">
                    <li>
                        <a href="${productListPageURL}">Product List</a>
                    </li>
                   
                    <li>
                        <a href="${shoppingCartPageURL}">Shopping Cart</a>
                    </li>
                    <li>
                    	<c:if test="${sessionScope.userRole=='admin'}">
                    		<a href="${addProductPageURL}">Add Product</a> 
                    	</c:if>
                    </li>
            </ul>
            <ul class="nav navbar-nav collapse navbar-collapse navbar-right bs-example-navbar-collapse-1">
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<!-- <div class="col-sm-3 col-rg-2 form-inline btn-group border pull-right" " role="group" aria-label="Basic example">		 -->				
					<li>
						<a href="/history">History</a>
					</li>
					<li>
						<a>
							<c:out value="Welcome! ${sessionScope.userEntry.getUser().getNickname()}" />	
						</a>
					</li>
					<li>
						<a href="/logout">Logout</a>						<!-- </div> -->
					</li>
				</c:if>
				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<!-- <div class="col-sm-3 col-rg-2 form-inline btn-group border pull-right" role="group" aria-label="Basic example"> -->
					<li>
						<a href="/login">Login</a>
					</li>
					<li>
						<a href="/register">Register</a>
					</li><!-- </div> -->
				</c:if>
			</ul>
			<!-- Login/Register Close-->
			<!-- search -->
			<form id="search-field-form" class="navbar-form navbar-right" role="search" action="<c:url value="products" />" method="GET">
				<div class="input-group">
				    <input id="search-field" type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
				    <div class="input-group-btn">
			            <button id="search-field-btn" class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
				    </div>
				</div>
			</form>
		    <!-- search close-->
        <!-- /.container -->
		</div>
   	</nav>
</body>
</html>