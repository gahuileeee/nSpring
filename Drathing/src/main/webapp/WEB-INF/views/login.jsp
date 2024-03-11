<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href='/css/login01.css'>
    <title>DraThing</title>

</head>

<body>

<c:if test="${msg != null}">
    <script>
        alert("${msg}");
    </script>
</c:if>
    <!-- 로고 이미지 -->
    <div id="img">
        <h1><span class="pink-text">Dra</span><span class="green-text">Thing</span></h1>
    </div>

    <!-- 로그인 폼 -->
    <div class="login-container">
        <form class="login-form" id ="form" action="/login"   method="post">
            <div class="form-group">
                <label for='uid'>ID(email):</label>
                <input type="text" id="uid" name="uid">
            </div>
            <div class="form-group">
                <label for="upassword">password:</label>
                <input type="password" id="upassword" name="upassword">
            </div>
            <div class="button-container">
                <button type="submit" class="button">Login</button>
            </div>
        </form>
    </div>
    
	<div>
	<form action="/register" method="post">
	<button type="submit" class="guest-button" id='button2'>Do not have an account?</button>
	</form>
	</div>

</body>

</html>