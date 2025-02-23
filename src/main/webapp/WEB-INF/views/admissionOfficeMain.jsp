<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>입학처 메인 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/admissionOfficeMain.css'/>">
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
            <li id="main"><a href="/admission/main">메인 페이지</a></li>
            <li id="pass"><a href="/admission/main/pass">합격자 조회</a></li>
            <li id="fail"><a href="/admission/main/fail">불합격자 조회</a></li>
            <li><a href="/admission/office">지원자 추가</a></li>
        </ul>
    </div>

    <!-- 본문 -->

    <div class="content">
        <form id="form" action="/admission/main/search" method="post">
            <input type="hidden" name="applicationNumber" id="selectedApplicationNumber">
            <input type="hidden" name="now" value="${now}">
                <div class="info">
                    <h1>입학처</h1>
                    <h2><label id="now"></label></h2>
                    <div class="search-area">
                        <label for="search-type">
                            <select name="search-type" id="search-type" class="search-type">
                                <option value="name">이름</option>
                                <option value="phone">연락처</option>
                                <option value="address">주소</option>
                                <option value="applicationNumber">수험번호</option>
                                <option value="applicationType">지원유형</option>
                                <option value="faculty">학부</option>
                                <option value="department">학과</option>
                            </select>
                        </label>

                        <label>
                            <input type="text" name="search-text" value="${searchText}">
                        </label>
                        <button type="submit">검색</button>
                    </div>
                    <table class="table">
                        <tr><th>이름</th><th>연락처</th><th>주소</th><th>수험번호</th><th>지원유형</th><th>학부</th><th>학과</th></tr>
                        <c:forEach var="candidate" items="${candidates}">
                            <tr onclick="goInfo('${candidate.applicationNumber}')"><td>${candidate.name}</td><td>${candidate.phone}</td><td>${candidate.address}</td><td>${candidate.applicationNumber}</td>
                                <td>${candidate.applicationType}</td><td>${candidate.faculty}</td><td>${candidate.department}</td></tr>
                        </c:forEach>
                    </table>
                </div>
        </form>
    </div>

</div>
</body>
<script>
    function goInfo(applicationNumber) {
        document.getElementById("selectedApplicationNumber").value = applicationNumber;
        document.getElementById("form").action = "/admission/main/info";
        document.getElementById("form").submit();
    }
    let now;
    if ("${now}" == "main") {
        now = "지원자 목록";
        document.getElementById("main").style.display = "none";
    } else if ("${now}" == "pass") {
        now = "합격자 목록";
        document.getElementById("pass").style.display = "none";
    } else if ("${now}" == "fail") {
        now = "불합격자 목록";
        document.getElementById("fail").style.display = "none";
    }
    document.getElementById("now").innerHTML = now;

</script>
</html>

