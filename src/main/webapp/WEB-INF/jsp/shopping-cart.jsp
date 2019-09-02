<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">



<title>Shopping Cart Page</title>
<spring:url value="/resources/images" var="images" />

<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet">
<spring:url value="/resources/css/4-col-portfolio.css" var="themeCSS" />
<link href="${themeCSS}" rel="stylesheet">
<spring:url value="/resources/css/custom.css" var="customCSS" />
<link href="${customCSS}" rel="stylesheet">
<style>
<
spring:url value ="/resources/css/bootstrap.min.css " var
	="bootstrapCss" /> <link href ="${bootstrapCss
	
}

"
rel ="stylesheet"> <spring:url value ="/resources/css/4-col-portfolio.css
	" var ="themeCSS" /> <link href ="${themeCSS
	
}

"
rel ="stylesheet"> <spring:url value ="/resources/css/custom.css " var
	="customCSS" /> <link href ="${customCSS
	
}

"
rel ="stylesheet"> <spring:url value ="/resources/js/jquery-3.1.0.min.js
	" var ="resourceJquery" /> <script src ="${resourceJquery
	
}

"></
script> <spring:url value ="/resources/js/bootstrap.min.js " var
	="resourceBootstrapJs" /> <script src ="${resourceBootstrapJs
	
}

"></
script
>
@font-face {
	font-family: 'Glyphicons Halflings';
	src: url('${file1}');
	src: url('${file1}?#iefix') format('embedded-opentype'), url('${file4}')
		format('woff'), url('${file3}') format('truetype'),
		url('${file2}#glyphicons_halflingsregular') format('svg');
}
</style>

<spring:url value="/resources/js/jquery-3.1.0.min.js"
	var="resourceJquery" />
<script src="${resourceJquery}"></script>
<spring:url value="/resources/js/bootstrap.min.js"
	var="resourceBootstrapJs" />
<script src="${resourceBootstrapJs}"></script>

</head>

<body>



	<!-- Nav-bar -->
	<jsp:include page="navbar.jsp" />
	<!-- /.Nav-bar -->
	<!-- Page Content -->
	<div class="container">

		<!-- Page Heading -->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					Shopping Cart <small>Item List</small>
				</h1>
			</div>
		</div>
		<!-- /.row -->



		<div>

			<div class="row">
				<!-- left category  -->
				<c:if test="${not empty shoppingCartEnries}">
					<form action="./make_payment" method="get">
						<c:set var="totalPrice" value="0" />
						<c:forEach var="cartEntry" items="${shoppingCartEnries}">

							<div class="row">
								<div class="col-lg-3 col-md-3">
									<img class="img-responsive" src="${cartEntry.getImageUrl()}"
										alt="">
								</div>
								<div class="col-lg-3 col-md-3">
									<p>${cartEntry.getProductName()},${cartEntry.getPrice()}</p>
								</div>
								<div class="col-lg-3 col-md-3">
									<p>Quantity: ${cartEntry.getQuantity()}</p>
								</div>
								<div class="col-lg-3 col-md-3">
									Total Price:
									<p id="totalPrice">${cartEntry.getProductTotalPrice()}</p>
									<c:set value="${totalPrice+ cartEntry.getProductTotalPrice()}"
										var="totalPrice"></c:set>
								</div>
								<div class="col-lg-3 col-md-3">
									Status
									<p id="status" style="color: red">${cartEntry.status}</p>

								</div>
							</div>
							<hr>

						</c:forEach>
			</div>
		</div>
		<hr>



		<div class="row">
			<h4>
				<c:out value="${shoppingItemSize} items on your basket." />
			</h4>

			<input type="hidden" value="${totalPrice+taxPrice }" name="sum">
			<input type="submit" value="Make Payment">


			</form>
			</c:if>
			<c:if test="${ empty shoppingCartEnries}">
				<c:out value="Your shopping Cart is empty!"></c:out>
			</c:if>
		</div>


	</div>
	<div class="row">
		<span class="pull-right">Total: ${ totalPrice }</span>
		<c:set var="total" value="${totalPrice}"></c:set>
	</div>
	<div class="row">
		<span class="pull-right">Tax: ${ taxPrice }</span>
	</div>



	</div>
	<!-- Footer -->
	<footer>
		<div class="row">
			<div class="col-lg-12">
				<p>Copyright &copy; Divilioglu LTD. 2016</p>
			</div>
		</div>
		<!-- /.row -->
	</footer>

	</div>
	<!-- /.container -->

</body>

</html>