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

#change{
font:bold;
font-style:italic;
padding-bottom:10px;
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

<c:if test="${not empty sessionScope.priceChange}">
<c:forEach items="${sessionScope.priceChange}" var="priceChangedProduct" > 

<div id="change">
Price of ${priceChangedProduct.key} changed from ${priceChangedProduct.value}. 
</div>
</c:forEach>
</c:if>
		<div>

			<div class="row">
			<c:set var="count" value="0"></c:set>
				<!-- left category  -->
				<c:if test="${not empty sessionScope.shoppingCartMap.cartItems}">
					<form action="./make_payment" method="get">
						<c:set var="totalPrice" value="0" />
						<c:forEach var="cartEntry" items="${sessionScope.shoppingCartMap.cartItems.values()}">

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
								<c:set  var="count" value="${count+ cartEntry.getQuantity()}"></c:set>
								<div class="col-lg-3 col-md-3">
								
									
									<c:set value="${totalPrice+ cartEntry.getProductTotalPrice()*cartEntry.getQuantity()}"
										var="totalPrice"></c:set>
											Total Price:<c:out value="${cartEntry.getProductTotalPrice()*cartEntry.getQuantity()}"></c:out>
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
				<c:out value="${count} items on your basket." />
			</h4>

			<input type="hidden" value="${totalPrice+taxPrice }" name="sum">
			<input type="submit" value="Make Payment">


			</form>
			</c:if>
			<c:if test="${ empty sessionScope.shoppingCartMap.cartItems}">
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