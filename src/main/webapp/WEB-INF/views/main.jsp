<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.entity.student.StudentActive" %>
<%
  // 세션에서 로그인한 학생 정보 가져오기
  StudentActive student = (StudentActive) session.getAttribute("student");
  if (student == null) {
    response.sendRedirect("login.jsp"); // 로그인되지 않은 경우 로그인 페이지로 이동
    return;
  }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>메인 페이지</title>
  <link rel="stylesheet" href="main.css">
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
      <li><a href="#">학적상태</a></li>
      <li><a href="#">수강과목/시간표</a></li>
      <li><a href="#">성적조회</a></li>
      <li><a href="#">강의평가</a></li>
      <li><a href="#">등록금고지서 출력</a></li>
      <li><a href="#">교육비 납입 증명서</a></li>
      <li><a href="#">제증명서 발급</a></li>
      <li><a href="#">로그인 암호 변경</a></li>
      <li><a href="logout.jsp">로그아웃</a></li>
    </ul>
  </div>

  <!-- 본문 -->
  <div class="content">
    <div class="student-info">
      <h1>학생 정보</h1>
      <table class="student-table">
        <tr><th>이름</th><td><%= student.getName() %></td></tr>
        <tr><th>학번</th><td><%= student.getStudentNumber() %></td></tr>
        <tr><th>학년</th><td><%= student.getAcademicYear() %></td></tr>
        <tr><th>학기</th><td><%= student.getSemester() %></td></tr>
        <tr><th>학부</th><td><%= student.getFaculty() %></td></tr>
        <tr><th>학과</th><td><%= student.getDepartment() %></td></tr>
        <tr><th>소속 클래스</th><td><%= student.getClassGroup() %></td></tr>
      </table>
    </div>
  </div>
</div>
</body>
</html>
