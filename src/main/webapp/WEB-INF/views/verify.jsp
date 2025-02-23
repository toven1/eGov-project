<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/resources/css/verify.css'/>">
    <title>회원가입</title>
</head>
<body>
<center>
    <h2>회원가입</h2>
    <h4>본인인증</h4>
    <form action="/auth/verify/" method="post">
        <label for="name">이름</label><br>
        <input type="text" id="name" name="name" required><br>

        <label for="rrn">주민등록번호</label><br>
        <input type="text" id="rrn" name="rrn" required><br>
        <label for="phone">연락처</label><br>
        <input type="text" id="phone" name="phone" required><br>
        <label for="studentNumber">학번</label><br>
        <input type="text" id="studentNumber" name="studentNumber" required><br>
        <label class="error">${error}</label><br>
        <button type="submit" class="login-btn">본인인증</button>

    </form>
</center>
</body>
</html>
