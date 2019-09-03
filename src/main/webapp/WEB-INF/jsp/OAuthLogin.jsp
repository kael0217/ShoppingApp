<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Third Party Login</title>
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

	<div >
		<a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
			<div> Log in with Google</div>
		</a>
		<a className="btn btn-block social-btn facebook" href={FACEBOOK_AUTH_URL}>
			<div> Log in with Facebook</div>
		</a>
		<a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
			<div> Log in with Github</div>
		</a>
	</div>

</body>
</html>