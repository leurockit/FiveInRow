<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Player Login</title>
</head>
<body>
<form action="Login" method="post">
<div><%=request.getAttribute("errorMsg")==null?"":request.getAttribute("errorMsg")%></div>
Player Name: <input id="playerName" name="playerName" type="text" />
<input type="submit" value="OK">
</form>
</body>
</html>