<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>입학처 상세정보</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/admissionOfficeInfo.css'/>">
    <script>
        function toggleSidebar() {
            document.querySelector('.sidebar').classList.toggle('closed');
        }
    </script>
</head>
<body>
<!-- 사이드바 열기 버튼 -->
<button class="open-btn" onclick="toggleSidebar()">&#9776;</button>

<div class="container">
    <!-- 사이드바 -->
    <div class="sidebar closed">
        <button class="close-btn" onclick="toggleSidebar()">×</button>
        <ul>
            <li><a href="/admission/main">메인 페이지</a></li>
            <li><a href="/admission/main/pass">합격자 조회</a></li>
            <li><a href="/admission/main/fail">불합격자 조회</a></li>
            <li><a href="/admission/office">지원자 추가</a></li>
        </ul>
    </div>

    <!-- 본문 -->
    <div class="content">
        <form id="myForm" action="/admission/main/admit" method="post">
            <input type="hidden" name="applicationNumber" value="${candidate.applicationNumber}">
            <input type="hidden" name="isAdmitted" id="isAdmitted" value="">
            <div class="student-info">
                <h1>입학처</h1>
                <table class="student-table">
                    <tr><th>이름</th><td>${candidate.name}</td></tr>
                    <tr><th>연락처</th><td>${candidate.phone}</td></tr>
                    <tr><th>주소</th><td>${candidate.address}</td></tr>
                    <tr><th>수험번호</th><td>${candidate.applicationNumber}</td></tr>
                    <tr><th>지원유형</th><td>${candidate.applicationType}</td></tr>
                    <tr><th>학부</th><td>${candidate.faculty}</td></tr>
                    <tr><th>학과</th><td>${candidate.department}</td></tr>
                </table>
                <button class="myBtn" id="fail" onclick="admit(false)">불합격 처리</button>
                <button class="myBtn" id="true" onclick="admit(true)">합격 처리</button>
                <button class="myBtn" id="payment" onclick="payment()">등록금 납부</button>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    document.getElementById("payment").style.display = "none";
    if ("${now}" != "main") {
        document.getElementById("fail").style.display = "none";
        document.getElementById("true").style.display = "none";
        if ("${now}" == "pass") {
            document.getElementById("payment").style.display = "";
        }
    }
    function admit(admit) {
        if ("${now}" == "main") {
            document.getElementById("isAdmitted").value = admit;
            document.getElementById("myForm").submit();
        }
    }
    function payment() {
        if ("${now}" == "pass") {
            document.getElementById("myForm").action = "/admission/main/payment";
            document.getElementById("myForm").submit();
        }
    }

</script>
</html>
