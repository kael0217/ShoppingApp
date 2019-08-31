<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet">
<spring:url value="/resources/css/4-col-portfolio.css" var="themeCSS" />
<link href="${themeCSS}" rel="stylesheet">
<spring:url value="/resources/css/custom.css" var="customCSS" />
<link href="${customCSS}" rel="stylesheet">
<spring:url value="/resources/js/jquery-3.1.0.min.js" var="resourceJquery" />
<script src="${resourceJquery}"></script>
<spring:url value="/resources/js/bootstrap.min.js" var="resourceBootstrapJs" />
<script src="${resourceBootstrapJs}"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>


<style>
body {
	padding-top: 50px;
}

h1 {
	color: #fff;
}

#content {
	width: 100%;
	min-height: 600px;
	background: #444 url(footer.png);
	padding-top: 20px;
	color: #FFF;
}

.register {
	padding: 20px;
	font-weight: 700;
	border: #FFF solid 1px;
	-moz-border-radius: 10px; /* Gecko browsers */
	-webkit-border-radius: 10px; /* Webkit browsers */
	border-radius: 10px; /* W3C syntax */
}
.black-background {background-color:#000000;}
.white {color:#ffffff;}

@font-face {
	font-family: 'Glyphicons Halflings';
	src: url('${file1}');
	src: url('${file1}?#iefix') format('embedded-opentype'), url('${file4}')
		format('woff'), url('${file3}') format('truetype'),
		url('${file2}#glyphicons_halflingsregular') format('svg');
}
</style>
</head>
<body>
	<!-- Nav-bar -->
	<jsp:include page="navbar.jsp" />
	<!-- /.Nav-bar -->	
	<!-- validator -->
	<script>
	$().ready(function() {

		
		$('#registerForm').validate({			
			highlight:function(element) {
		        $(element).removeClass("badge badge-warning");
		    },
			
			rules : {
				username:{
					required : true,
					minlength : 5,
					maxlength : 30,
					email: true
				},
				nickname:{
					required : true,
					minlength : 5,
					maxlength : 12
				},
				addresses:{
					required : true
				},				
				password:{
					required : true,
					minlength : 5
				},
				confirm_password:{
					required : true,
					minlength : 5,
					equalTo : '#password'

				},
				mobile:{
					minlength : 5,
					number : true

				}
			},
			messages:{
				confirm_password:{
					equalTo : "Password does not match"
				}
			},	   	    
    	    submitHandler:function(form){
    	    	form.submit();
    	    }  
		});
	});
	</script>
	<!-- /.validator -->	
	<div class="container">				
		<div class="form row">
			<form action="/addUser" id="registerForm" method="post" class="form-horizontal">
				
				<div class="col-md-6 col-md-offset-3 col-xs-10 col-xs-offset-1 register">
					<div class="row text-center"><c:out value="${msg}"></c:out></div>
					<div class="form-group">
						<label class="control-label">Email:</label>
						<input type="text" name="username" class="form-control col-sm-8" placeholder="Will also be your account name" />
						<br />
					</div>
					<div class="form-group">
						<label class="control-label">Password:</label>
						<input type="password" class="form-control col-sm-8" name="password" id="password" placeholder="Input your Password"/>
						<br />
					</div>
					<div class="form-group">
						<label class="control-label">Confirm password:</label>
						<input type="password" class="form-control col-sm-8" name="confirm_password" placeholder="Input your Password"/>
						<br />
					</div>
					<div class="form-group">
						<label>User Name:</label>
						<input type="text" class="form-control col-sm-8" name="nickname" placeholder="Replicable Name" />
						<br />
					</div>
					<div class="form-group">
						<label class="control-label">Mobile:</label>
						<input type="text" class="form-control col-sm-8" name="mobile" placeholder="Input your phone number"/>
						<br />
					</div>
					<div class="form-group ">
						<label class="control-label">Gender:</label>
						<div class ="input-group">
						<input type="radio" name="gender" class="radio-inline" value="MALE" checked>
						Male
						<input type="radio" name="gender" class="radio-inline" value="FEMALE" />
						Female
						<br />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label">Address 1:</label>
						<input type="text" class="form-control col-sm-8" name="addresses" placeholder="Primay Address"/>
						<br />
					</div>
					<div class="form-group">
						<label class="control-label">Address 2:</label>
						<input type="text" class="form-control col-sm-8" name="addresses" placeholder="Secondary Address"/>
						<br />
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="submit" class="btn btn-block black-background white" value="Register" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>