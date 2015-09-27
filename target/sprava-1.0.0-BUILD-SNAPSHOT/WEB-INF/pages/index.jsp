<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spilna Sprava | Welcom Page</title>

<!-- style setting buttons type="submit" -->
<style>
input[type="submit"] {
	height: 200px;
	width: 400px;
	cursor: pointer;
	color: teal;
	font-size: 35px;
	font-family: Verdana;
	font-weight: bold;
}
</style>
</head>
<body>
	<center>
		<br>
		<br>
		<br>
		<br>
		<div style="color: teal; font-size: 30px">Spilna Sprava | Welcome Page</div>
		<br>
		<br>
		<!-- Button  is redirected /signin -->
		<c:url var="faceConnection" value="signin.html" />
		<form:form id="messageForm" method="get" action="${faceConnection}">
	
							<input type="submit" value="Facebook Connect" />

		</form:form>

	</center>
</body>
</html>