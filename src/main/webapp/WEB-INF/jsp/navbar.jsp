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
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${productListPageURL}">Shopper's Club</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            
                <ul class="nav navbar-nav">
                    <li>
                        <a href="${productListPageURL}">Product List</a>
                    </li>
                   
                    <li>
                        <a href="${shoppingCartPageURL}">Shopping Cart</a>
                    </li>
                </ul>
                
                <!-- search -->
		    
			        <form id="search-field-form" class="navbar-form navbar-left" role="search" action="<c:url value="products" />" method="GET">
				        <div class="input-group">
				        	<input id="search-field" type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
				            <div class="input-group-btn">
			                	<button id="search-field-btn" class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i>
			                	</button>
				            </div>
				        </div>
			        </form>
		      
		        <!-- search close-->
  
             <div  id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav">
            
				<c:if test="${pageContext.request.userPrincipal.name != null}">

					<!-- <div class="col-sm-3 col-rg-2 form-inline btn-group border pull-right" " role="group" aria-label="Basic example">		 -->				
						<li>

							<button class="btn btn-link border-secondary">
							
								<c:out value="Welcome! Dear ${sessionScope.userEntry.getUser().getNickname()}" />
								
							</button>
							<button class="btn btn-default border-secondary" onclick="javascript:location.href='/logout'">Logout</button>						
					<!-- </div> --></li>
				</c:if>
				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<!-- <div class="col-sm-3 col-rg-2 form-inline btn-group border pull-right" role="group" aria-label="Basic example"> -->
					<li>
					<c:if test="${pageContext.request.userPrincipal.name == null}">
						<button class="btn btn-default border-secondary" onclick="javascript:location.href='/login'">Login</button>
						</c:if>
						<c:if test="${pageContext.request.userPrincipal.name == null}">
						<button class="btn btn-default border-secondary" onclick="javascript:location.href='/register'">Register</button>
						</c:if>
					</li><!-- </div> -->
				</c:if>
			</ul>
			<!-- Login/Register Close-->
		</div>
        <!-- /.container -->
</div>
    </nav>
</body>
</html>