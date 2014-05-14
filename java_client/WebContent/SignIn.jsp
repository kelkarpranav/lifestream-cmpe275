<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>Message: ${msg}</p>
<form id="form4" method ="post" action="SignIn">

<div class="form-row"><span class="label">Username :</span> <input type="text" name="user" /></div>
<div class="form-row"><span class="label">Password :</span> <input type="password" name="pass" /></div>

<div class="form-row"><input class="submit" type="submit" value="submit"></div>
</form>
</body>
</html>