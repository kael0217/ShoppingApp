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
spring












:url






 






value












="/
resources
/fonts/glyphicons-halflings-regular












.eot












"
var












="
file1










"
/
>
<
spring












:url






 






value












="/
resources
/fonts/glyphicons-halflings-regular












.svg












"
var












="
file2










"
/
>
<
spring












:url






 






value












="/
resources
/fonts/glyphicons-halflings-regular












.ttf












"
var












="
file3










"
/
>
<
spring












:url






 






value












="/
resources
/fonts/glyphicons-halflings-regular












.woff












"
var












="
file4










"
/
>
<
spring












:url






 






value












="/
resources
/fonts/glyphicons-halflings-regular












.woff2












"
var












="
file5










"
/
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

		<jsp:useBean id="order" class="com.levent.pcd.model.Order"
			scope="request"></jsp:useBean>
		<jsp:setProperty property="totalPrice" name="order"
			value="${totalPrice}" />
		<jsp:setProperty property="totalProducts" name="order"
			value="${totalProducts}" />

		<div>

			<div class="row">
				<!-- left category  -->
				<c:if test="${not empty shoppingCartEnries}">
					<form action="./make_payment" method="get">
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

								</div>
							</div>
							<hr>

						</c:forEach>

						<input type="hidden" value="${totalPrice+taxPrice }" name="sum">
						<input type="submit" value="Make Payment">

					</form>
				</c:if>
			</div>
			<div class="row">
				<h4>${shoppingItemSize}items in your bucket!.</h4>
			</div>

		</div>
		<div class="row">
			<span class="pull-right">Total: ${ totalPrice }</span>
			<c:set var="total" value="${totalPrice}"></c:set>
		</div>
		<div class="row">
			<span class="pull-right">Tax: ${ taxPrice }</span>
		</div>


		<%-- 
				<form action="https://www.sandbox.paypal.com/cgi-bin/webscr"
					method="POST">
					<input type="hidden" name="cmd" value="_cart" /> <input
						type="hidden" name="upload" value="1" />
					<c:set var="itemCount" value="1" />
					<c:forEach items="${shoppingCartEnries}" var="entry">
						<input type="hidden" name="item_name_${itemCount}"
							value="${entry.productName}" />
						<input type="hidden" name="quantity_${itemCount}"
							value="${entry.quantity}" />
						<input type="hidden" name="amount_${itemCount}"
							value="${entry.productTotalPrice}" />
					</c:forEach>
					<input type="hidden"	name="USER" value="${paypal.username}" /> 
					<input type="hidden"	name="PWD" value="${paypal.password}" /> 
					<input type="hidden"	name="SIGNATURE" value="${paypal.signature}" /> 
				 <input type="hidden"	name="currency_code" value="INR" /> 
				 <input type="hidden"
						name="business" value="payal-facilitator@rjtcompuquest.com" /> <input
						type="hidden" name="return"
						value="http://localhost:9000/payment_success" /> <input
						type="hidden" name="cancel"
						value="payal-facilitator@rjtcompuquest.com" /> <input
						type="image" name="submit"
						src="https://www.paypalobjects.com/en_US/i/btn/btn_buynow_LG.gif"
						alt="Paypal-Safe and easier way to pay online" />
				</form> --%>


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