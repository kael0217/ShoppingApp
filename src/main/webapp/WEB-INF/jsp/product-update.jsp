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
.input-group .input-group-addon{
width:140px;
}
.form-control{
width:250px;
}
</style>

<spring:url value="/resources/js/jquery-3.1.0.min.js"
	var="resourceJquery" />
<script src="${resourceJquery}"></script>
<spring:url value="/resources/js/bootstrap.min.js"
	var="resourceBootstrapJs" />
<script src="${resourceBootstrapJs}"></script>
<script>
	function validateData(){
		
	
			var productName = $("#productName").val();
			var description = $("#description").val();
			var manufacturer = $("#manufacturer").val();
			var type = $("#type").val();
			var price = $("#price").val();
			var upc = $("#upc").val();
			var inStore = $("#inStore").val();
			var color = $("#color").val();
			var shipping = $("#shipping").val();
			var color = $("#model").val();
			if(productName == '' || description == '' || manufacturer == '' || type == '' 
					|| price == '' || upc == '' || color == '' || shipping == '' || model == '') {
				return false;
			}		
			
		
			return true;
	};
			
		
		
	
	
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
					Product Management <small>Product Update Page</small>
				</h1>
			</div>
		</div>
		<!-- /.row -->

		<hr>
<form action="/updateProduct" id="updateProductForm" method="post" >

		<!-- page content  -->
		<div class="row">
			<!-- left category  -->
			<div class="col-md-6">
				<img class="img-responsive" src="${product.getImageUrl()}" alt="">
			</div>
			
			<!-- right category  -->
			<div class="col-md-6">
				<div>
					<div class="input-group">
								<span class="input-group-addon">Product name</span> 
									<input id="productName" name="productName" type="text" class="form-control"
									value="${ product.productName }" aria-describedby="productName-input"
									style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Description</span> 
								<textarea rows="3"  
									id="description" name="description" class="form-control"
									 aria-describedby="description-input" style="width:300px;"  
									 required="required">${ product.description }</textarea>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Manufacturer</span> 
								<input id="manufacturer" name="manufacturer" type="text" class="form-control"
									value="${ product.manufacturer }" aria-describedby="manufacturer-input"
									style="width:300px;"  required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Type</span> 
									<input id="type" name="type" type="text" class="form-control"
									value="${ product.type }" aria-describedby="type-input" style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Price</span> 
									<input id="price" name="price" type="text" class="form-control"
									value="${ product.price }" aria-describedby="price-input" style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">UPC</span> 
									<input id="upc" name="upc" type="text" class="form-control"
									value="${ product.upc }" aria-describedby="upc-input" style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Quantity Available</span> 
									<input id="inStore" name="inStore" type="text" class="form-control"
									value="${ product.inStore }" aria-describedby="inStore-input" style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Color</span> 
									<input id="color" name="color" type="text" class="form-control"
									value="${ product.color }" aria-describedby="color-input" style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Shipping</span> 
									<input id="shipping" name="shipping" type="text" class="form-control"
									value="${ product.shipping }" aria-describedby="shipping-input" style="width:300px;" required="required"/>
					</div><br>
					<div class="input-group">
								<span class="input-group-addon">Model</span> 
									<input id="model" name="model" type="text" class="form-control"
									value="${ product.model }" aria-describedby="model-input" style="width:300px;" required="required"/>
					</div><br>
					
					<div class="row">
					
						<input type="hidden"  name="id" value="${product.id}"/>
						<input type="hidden"  name="imageUrl" value="${product.imageUrl}"/>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					</div>
					<br />
					<div class="row">
					
						<div class="col-lg-3 col-md-3">
							<input 
								id="${ product.id}" type="submit"
								 value="Update Product" onclick="return validateData()"/>
						</div>
					</div>
					
			<!-- { "_id" : ObjectId("5dc6473f6c4caf2674ad29d6"), "sku" : 185230, "productName" : "Duracell - C Batteries (4-Pack)",
			 "type" : "HardGood", "price" : 8.99, "upc" : "041333440019", "category" : [ { "id" : "pcmcat312300050015", "productName" : "Connected Home & Housewares" },
			  { "id" : "pcmcat248700050021", "productName" : "Housewares" }, { "id" : "pcmcat303600050001", "productName" : "Household Batteries" },
			   { "id" : "abcat0208002", "productName" : "Alkaline Batteries" } ], "shipping" : 5.49, 
			   "description" : "Compatible with select electronic devices; C size; DURALOCK Power Preserve technology; 4-pack", 
			   "manufacturer" : "Duracell", "model" : "MN1400R4Z", "url" : "http://www.bestbuy.com/site/duracell-c-batteries-4-pack/185230.p?id=1051384046486&skuId=185230&cmp=RMXCC",
			    "imageUrl" : "http://img.bbystatic.com/BestBuy_US/images/products/1852/185230_sa.jpg", "inStore" : 1 } -->
		
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