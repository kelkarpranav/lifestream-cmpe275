<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign - Up</title>
<style type = "text/css">
body
{
	text-align: centre;
}
</style>


</head>
	
<body>
<p>Message: ${msg}</p>
<div> <b>Sign-Up for New Account </b> </div> <br>

<form id="form2" method="post" onSubmit="do()" action="SignUp">

<div class="form-row"><span class="label">Name :</span> <input type="text" name="name" /></div>
<div class="form-row"><span class="label">Email id :</span> <input type="text" name="email" /></div>

<div class="form-row"><span class="label">Username :</span> <input type="text" name="user" /></div>
<div class="form-row"><span class="label">Password :</span> <input type="password" name="pass" /></div>

<div class="form-row"><input class="submit" type="submit" value="submit"></div>
</form>

</body>
</html>