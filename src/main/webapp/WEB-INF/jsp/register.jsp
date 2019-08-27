<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
</head>
<body>
${msg}
<form action="/addUser" method="post">
Username: <input type="text" name="username"/> <br/>
Password: <input type="password" name="password"/> <br/>
Mobile: <input type="text" name="mobile"/> <br/>
Gender: <input type="radio" name="gender" value="MALE"> Male <input type="radio" name="gender" value="FEMALE"/> Female <br/>
Address 1: <input type="text" name="address"/><br/>
Address 2: <input type="text" name="address"/><br/>
<input type="submit" value="Regist"/>
</form>
<form action="/addUser" method="post">
<input type="text" name="username"/>
<input type="submit" value="get"/>
</form>
</body>
</html>