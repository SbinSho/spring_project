<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p><%= request.getAttribute("abc") %></p>
	<p><%= request.getAttribute("RSAModulus") %></p>
	<p><%= request.getAttribute("RSAExponent") %></p>
</body>
</html>