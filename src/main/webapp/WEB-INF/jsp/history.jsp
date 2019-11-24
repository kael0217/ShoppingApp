<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" 
	import = "java.time.format.DateTimeFormatter"
%>
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
		<% if(request.getAttribute("message") != null) {%>
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
        <%			request.setAttribute("message", null);
           } %>
        <% if(request.getAttribute("errMessage") != null) {%>
            <div class="alert alert-danger" role="alert">
                ${message}
            </div>
        <%			request.setAttribute("errMessage", null);
           } %>
		<c:if test="${(sessionScope.userRole=='admin' || sessionScope.userRole=='user' ) && not empty pastorders}">
			<c:forEach var="listValue" items="${pastorders}">
				<div class="row col-md-12">
					<div class="col-md-6">
						Order status: 
						<c:if test="${listValue.getStatus() == 'PAYMENT_SUCCESS'}">
							complete
						</c:if>
						<c:if test="${listValue.getStatus() != 'PAYMENT_SUCCESS'}">
							pending
						</c:if><br>
						Placed date: ${listValue.getDate()}
					</div>
					<div class="col-md-1 col-md-offset-5">
						<a class="btn btn-danger btn-md" href="/history/${listValue.getOrderId()}">delete</a>
					</div>
				</div>
				<c:set var="totalPrice" value="0" />
				<c:forEach var="cartEntry" items="${listValue.getProductsPlaced()}">
					<div class="row col-md-12">
						<div class="col-lg-4">
							<a href="<c:url value='product-details-${cartEntry.id}'/>">
		                        <img class="img-responsive" src="${cartEntry.getImageUrl()}" alt="" width="250px" height="250px">
		                    </a>
						</div>
						<div class="col-lg-8">
							<h4><small>Product name:</small> <a href="<c:url value='product-details-${cartEntry.id}'/>">${cartEntry.getProductName()}</a></h4>
							<h4><small>Product price:</small> ${cartEntry.getPrice()}</h4>
							<h4><small>Purchased quantity:</small> ${cartEntry.getQuantity()}</h4>
							<h4><small>Total price:</small> ${cartEntry.getProductTotalPrice()}</h4>
							<c:set value="${totalPrice+cartEntry.getProductTotalPrice() }" var="totalPrice"></c:set>
						</div>
					</div>
					<br>
				</c:forEach>
				<p>Order price: ${totalPrice}</p>
				<c:if test="${listValue.status != 'PAYMENT_SUCCESS'}">
					<a href="#" class="btn btn-success btn-lg btn-block">Finish payment</a>
				</c:if>
				
				<br><br><br>
			</c:forEach>
			<div class="col-md-4 col-md-offset-3">
				<ul class="pagination">
					<c:if test="${page>0 }">
						<li class="page-item">
							<a class="page-link" href="./history?page=${page-1}&limit=6"
								aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
									<span class="sr-only">Previous</span>
							</a>
						</li>
					</c:if>
					<li class="page-item"><a class="page-link" href="#">${page+1}</a></li>
						<c:if test="${pastorders.size()==6}">
							<li class="page-item">
								<a class="page-link" href="./history?page=${page+1}&limit=6"
									aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
									<span class="sr-only">Next</span>
								</a>
							</li>
						</c:if>
					</ul>
				</div>
			</c:if>
		<c:if test="${empty pastorders}">
			<p>You don't have any history order, start ordering today!</p>
		</c:if>
	</div>
</body>
</html>