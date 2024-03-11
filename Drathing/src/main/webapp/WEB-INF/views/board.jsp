<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<c:set var="loginOutLink" value="${sessionScope.user==null ? '/login?pg=1' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.user==null ? 'Login' : 'Logout'}"/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" href='/css/board01.css'>
    <title>DraThing</title>
   
</head>

<body>
    <div id="title">
        <h1><span class="pink-text">Dra</span><span class="green-text">Thing</span></h1>
    </div>

    <div id="nav">
        <form action="/index"  >
            <button type="submit" class="button" id='button2'>
                <h3>home</h3>
            </button>
        </form>
        <form action="${loginOutLink}" method="get">
            <button type="submit" class="button" id='button2'>
                <h3>${loginOut}</h3>
            </button>
        </form>
    </div>
    
   <div id="context">
    <!-- 이미지를 표시할 공간 -->
       <c:forEach var="print" items="${prints}">
        <div class="image-container"  >
            <img src="data:image/png;base64,${print.printing}" alt="Print Image"/>
            <form action="/bulletin"  >
                <input type ="hidden" name="number" value="${print.number}">
                <button type = "sumbit" class ="button" id="button1" >
                    <h5 >${print.name}</h5>
                </button>
            </form>
        </div>
       </c:forEach>
        </div>


</body>
</html>