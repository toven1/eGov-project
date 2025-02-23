<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="login.css">
    <title>로그인</title>
</head>
<body>
<center>
    <h2>로그인</h2>
    <form action="loginProcess.jsp" method="post"> <%-- JSP에서 로그인 처리 파일을 지정 --%>
        <label for="userId">아이디</label><br>
        <input type="text" id="userId" name="userId" required><br>

        <label for="password">비밀번호</label><br>
        <input type="password" id="password" name="password" required><br>

        <button type="submit" class="login-btn">로그인</button>
        <button type="button" class="signup-btn" onclick="location.href='admissionOffice.jsp'">회원가입</button>

        <div class="role-buttons">
            <button type="button" onclick="setUserRole('student')">학생</button>
            <button type="button" onclick="setUserRole('professor')">교수</button>
            <button type="button" onclick="setUserRole('staff')">직원</button>
        </div>

        <%-- Hidden input을 추가하여 역할(role) 정보를 보냄 --%>
        <input type="hidden" id="userRole" name="userRole" value="student">
    </form>
</center>

<script>
    function setUserRole(role) {
        document.getElementById('userRole').value = role;
    }
</script>

</body>
</html>
