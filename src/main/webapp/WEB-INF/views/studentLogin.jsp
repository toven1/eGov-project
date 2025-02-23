<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/login.css'/>"> <!-- CSS 파일 연결 -->
    <title>로그인</title>
</head>
<body>
<center>
    <h2>로그인</h2>
    <form action="${pageContext.request.contextPath}/studentLogin" method="post">
        <label for="studentNumber">아이디</label><br>
        <input type="text" id="studentNumber" name="studentNumber" required><br>

        <label for="password">비밀번호</label><br>
        <input type="password" id="password" name="password" required><br>

        <button type="submit" class="login-btn">로그인</button>
        <button type="button" class="signup-btn" onclick="location.href='admissionOfficeInfo.jsp'">회원가입</button>

        <div class="role-buttons">
            <button type="button">학생</button> <!--기본적으로는 학생 로그인이 디폴트로 뜨고 버튼을 누르면 교수인지 직원인지 분리할수 있도록 한다. -->
            <button type="button">교수</button>
            <button type="button">직원</button>
        </div>
    </form>
</center>
</body>
</html>