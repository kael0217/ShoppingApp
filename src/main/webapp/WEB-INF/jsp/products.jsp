<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	
    <title>Product List Page</title>
    <spring:url value="/resources/images" var="images" />

    <style>
    
    <spring:url value="/resources/fonts/glyphicons-halflings-regular.eot"	var="file1" />
    <spring:url value="/resources/fonts/glyphicons-halflings-regular.svg"	var="file2" />
    <spring:url value="/resources/fonts/glyphicons-halflings-regular.ttf"	var="file3" />
    <spring:url value="/resources/fonts/glyphicons-halflings-regular.woff"	var="file4" />
    <spring:url value="/resources/fonts/glyphicons-halflings-regular.woff2"	var="file5" />
    
	@font-face {
    font-family: 'Glyphicons Halflings';

    src: url('${file1}');
    src: url('${file1}?#iefix') format('embedded-opentype'), url('${file4}') format('woff'), url('${file3}') format('truetype'), url('${file2}#glyphicons_halflingsregular') format('svg');
	}
	.adminEdit {
	  display: flex;
    justify-content: space-around;
	}
	
    </style>
    
	<spring:url value="/resources/js/jquery-3.1.0.min.js" var="resourceJquery" />
	<script src="${resourceJquery}"></script>
	<spring:url value="/resources/js/bootstrap.min.js" var="resourceBootstrapJs" />
	<script src="${resourceBootstrapJs}"></script>
	
</head>

<body>
	<!-- Nav-bar -->
	<jsp:include page="navbar.jsp"/>
	<!-- /.Nav-bar -->
${message}
    <!-- Page Content -->
    <div class="container">
    
        <div class="row">
            <div class="col-lg-12">
                <h1>History Order:</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
				<c:if test="${not empty productList}">
					<div class="row">
					    <c:forEach items="${productList}" var="listValue" begin="0" end="5">
			                <div class="col-md-2 col-sm-4 col-xs-12 portfolio-item">
			                    <a href="<c:url value='product-details-${listValue.id}' />">
			                        <img class="img-responsive" src="${listValue.getImageUrl()}" alt="" width="250px;" height="250px;">
			                    </a>
                                <p>${listValue.getProductName()}</p>
                                <c:if test="${sessionScope.userRole=='admin'}">
                                    <p class="adminEdit">
                                        <a href="<c:url value='product-update-${listValue.id}' />">Update Product</a> 
                                        <a href="<c:url value='product-delete-${listValue.id}' />">Delete Product</a> 
                                    </p>
                                </c:if>
				            </div>
				        </c:forEach>
				    </div>
				</c:if>
			</div>
        </div>
    
        <!-- Page Heading -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Products:
                    <small>Select categories or search using search box</small>
                </h1>
            </div>
        </div>
        <!-- /.row -->
		
        <div class="row">
			<div class="row">
				<!-- left category  -->
	            <div class="col-md-3">
	                <p class="lead">Top Categories</p>
	                <div id="category-options" class="list-group">
						<c:if test="${not empty categoryList}">
							<c:forEach items="${categoryList}" var="list" begin="0" end="9">
								<a href="<c:url value='products-by-category-${list}' />" class="list-group-item category-select-option" data-category-name="${list}">${list}</a>
							</c:forEach>
      						<h4 class="list-group-item panel-title">
        						<a data-toggle="collapse" href="#collap" aria-haspopup="true" aria-expanded="false">display all</a>
      						</h4>
    						<div id="collap" class="panel-collapse collapse">
    							<c:forEach items="${categoryList}" var="list" begin="10" >
									<a href="<c:url value='products-by-category-${list}' />" class="list-group-item category-select-option" data-category-name="${list}">${list}</a>
								</c:forEach>
    						</div>
						</c:if>
						
	                </div>
	            </div>
				
				<div class="col-md-9">
					<c:if test="${not empty productList}">
						<c:set var="divCount" scope="page" value="0"/>
						<div class="row">
						<c:forEach var="listValue" items="${productList}">
				            <div class="col-md-4 col-sm-12 col-xs-12 portfolio-item">
				                <a href="<c:url value='product-details-${listValue.id}' />">
				                    <img class="img-responsive" src="${listValue.getImageUrl()}" alt="" width="250px;" height="250px;">
				                </a>
    							<p >${listValue.getProductName()}</p>
    							<c:if test="${sessionScope.userRole=='admin'}">
    								<p class="adminEdit">
                    				<a href="<c:url value='product-update-${listValue.id}' />">Update Product</a> 
                    				<a href="<c:url value='product-delete-${listValue.id}' />">Delete Product</a> 
                    				</p>
                    			</c:if>
    							
				            </div>
				            <c:set var="divCount" value="${divCount + 1}" />
				            <c:if test="${divCount % 3 == 0}">
		            	</div>		            	
		            	<div class="row">
				            </c:if>
						</c:forEach>
					<div class="col-md-4 col-md-offset-3">
						<nav aria-label="Page navigation example">
							<ul class="pagination">
							<c:if test="${page>0 }">
								<li class="page-item">
									<a class="page-link" href="./products?page=${page-1}&limit=6"
									aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										<span class="sr-only">Previous</span>
								</a></li>
								</c:if>
														
								<li class="page-item"><a class="page-link" href="#">${page+1}</a></li>
								<c:if test="${productList.size()==6}">
								<li class="page-item">
									<a class="page-link" href="./products?page=${page+1 }&limit=6"
									aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										<span class="sr-only">Next</span>
								</a></li>
								</c:if>
							</ul>
						</nav>
					</div>
				</div>
					</c:if>
				</div>
				
			</div>
		</div>
	
	<hr>	

        <!-- Footer -->
        <footer>
       <!--  <img class="img-responsive" src="https://bucket-image-productcatagory.s3.us-east-2.amazonaws.com/01_men_one.jpg" alt=""> -->
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