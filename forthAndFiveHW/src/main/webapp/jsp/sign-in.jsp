<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>${optional}</h1>
<form method="post" action="/sign-in">
    <input name="login" type="text"
           placeholder="Enter name">
    <input name="password" type="password"
           placeholder="Enter password">
    <input type="submit" value="Sign In">
</form>
</body>
</html>