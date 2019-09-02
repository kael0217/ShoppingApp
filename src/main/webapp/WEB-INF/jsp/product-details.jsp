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



<title>Product Details Page</title>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet">
<spring:url value="/resources/css/4-col-portfolio.css" var="themeCSS" />
<link href="${themeCSS}" rel="stylesheet">
<spring:url value="/resources/css/custom.css" var="customCSS" />
<link href="${customCSS}" rel="stylesheet">
<style>
<spring:url value="/resources/fonts/glyphicons-halflings-regular.eot" var="file1"/>
<spring:url value="/resources/fonts/glyphicons-halflings-regular.svg" var="file2"/>
<spring:url value="/resources/fonts/glyphicons-halflings-regular.ttf" var="file3"/>
<spring:url value="/resources/fonts/glyphicons-halflings-regular.woff" var="file4"/>
<spring:url value="/resources/fonts/glyphicons-halflings-regular.woff2" var="file5"/>

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
<script>
	$(document).ready(function() {
		$(".add-to-basket-btn").click(function() {
			var id = $(this).attr('id');
			var quantity = $("#quantity-input").val();
		/* 	var imageUrl=document.getElementsByTagName("img")[0].src;
			var price= parseInt($("#price")[0].innerHTML);
			var productTotalPrice= parseInt(price)*parseInt(quantity);
			var prodName= $("#prodName")[0].innerHTML; */
			if(quantity == '') {
				$("#modal-body-msg").text('Please enter a quantity');
				
				return false;
			}		
			if(quantity > $("#qty")[0].innerHTML) {
				$("#modal-body-msg").text('Please enter a valid quantity. ');
				
				return false;
			}
		
			});
			
			var msg = quantity + ' items added to your cart.';
			$("#modal-body-msg").text(msg);
		});
		
		$("#ok-modal-button").click(function() {
			// refresh input
			$("#quantity-input").val('');
		});
		
	});
	
	</script>
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
					Shopping Cart <small>Product Details Page</small>
				</h1>
			</div>
		</div>
		<!-- /.row -->

		<hr>

<form action="services/addToCart">
		<!-- page content  -->
		<div class="row">
			<!-- left category  -->
			<div class="col-md-6">
				<img class="img-responsive" src="${product.getImageUrl()}" alt="">
			</div>
			
			<!-- right category  -->
			<div class="col-md-6">
				<div>
					<h3 id="prodName">${ product.getProductName() }</h3>
					<p id="price">${ product.getPrice() }TL</p>
					<div class="row">
						<div class="col-lg-6 col-md-6">
							<div class="input-group">
								<span class="input-group-addon">Quantity</span> <input
									id="quantity" name="quantity" type="text" class="form-control"
									placeholder="0" aria-describedby="quantity-input">
							</div>
						</div>
						<input type="hidden"  name="id" value="${product.id}"/>
						<input type="hidden"  name="imageUrl" value="${product.imageUrl}"/>
						<input type="hidden"  name="price" value="${product.price}"/>
						<input type="hidden"  name="prodName" value="${product.productName}"/>
						<input type="hidden" name="productTotalPrice" value="0"/>
						<div class="col-lg-3 col-md-3">
							<input class="add-to-basket-btn"
								id="${ product.id}" type="submit"
								class="btn btn-primary btn-md" data-toggle="modal"
								data-target="#success-modal" value="Add to basket">
						</div>
					</div>
					<br />
					<div class="row">
						<table class="table">
							<thead>
								<td>Product Details</td>
							</thead>
							<tbody>
							
								<tr>
									<td>Description:</td>
									<td>${ product.description }</td>
								</tr>
								<tr>
									<td>Manufacturer:</td>
									<td>${ product.manufacturer}</td>

								</tr>
								<tr>
									<td>Type:</td>
									<td>${ product.type }</td>
								</tr>
								<tr>
									<td>Price:</td>
									<td>${ product.price }</td>
								</tr>
								
								<tr>
									<td>UPC:</td>
									<td>${ product.upc }</td>
								</tr>
								<tr>
									<td>Quantity Available:</td>
									<td id="qty">${ product.inStore }</td>
								</tr>
								<tr>
									<td>Color:</td>
									<td>${ product.color }</td>
								</tr>
								<tr>
									<td>Shipping:</td>
									<td>${ product.shipping }</td>
								</tr>
								<tr>
									<td>Model:</td>
									<td>${ product.model }</td>
								</tr>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</form>
		<hr>

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

	<!-- Modal -->
	<div class="modal fade" id="success-modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Thank You</h4>
				</div>
				<div class="modal-body">
					<p id="modal-body-msg">items added to your cart.</p>
				</div>
				<div class="modal-footer">
					<button id="ok-modal-button" type="button" class="btn btn-default"
						data-dismiss="modal">OK</button>
					<a href="products" type="button" class="btn btn-default">Back
						To Products</a>
				</div>
			</div>
		</div>
	</div>

</body>

</html>