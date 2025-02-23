<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/resources/css/studentSignUp.css'/>">
    <title>회원가입</title>
</head>
<body>
<center>
    <h2>회원가입</h2>
    <form action="/sign-up" method="post">
        <input type="hidden" name="studentNumber" value="${studentNumber}">
        <label for="student-number">학번</label><br>
        <input type="text" id="student-number" name="studentNumber" value="${studentNumber}" disabled required><br>

        <label for="password">비밀번호</label><br>
        <input type="password" id="password" name="password" required><br>
        <label for="confirm-password">비밀번호 확인</label><br>
        <input type="password" id="confirm-password" name="confirmPassword" required><br>
        <label class="error">${error}</label><br>
        <button type="submit" class="login-btn">회원가입</button>
        <br><br>
        <label>* 학생 전용 회원가입으로 교직원은 교학처로 문의 *</label>
    </form>
</center>
</body>
</html>
