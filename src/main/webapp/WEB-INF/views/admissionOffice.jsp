<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="<c:url value='/resources/css/admissionOffice.css'/>">
  <title>입학처 지원자 명단 추가</title>
</head>
<body>
<div class="main_container">
  <h1>입학처</h1>
  <h2>지원자 명단 추가</h2>
  <form action="/admission/office/new" method="post">
    <div class="container">
      <div class="container1">
        <div>
          이름
        </div>
        <div>
          주민등록번호
        </div>
        <div>
          연락처
        </div>
        <div>
          주소
        </div>
        <div>
          수험번호
        </div>
        <div>
          유형
        </div>
        <div>
          학부
        </div>
        <div>
          학과
        </div>
      </div>
      <div class="container2">
        <label for="name">
          <input type="text" name="name" id="name">
        </label><br>
        <label for="rrn">
          <input type="text" name="rrn" id="rrn">
        </label><br>
        <label for="phone">
          <input type="text" name="phone" id="phone">
        </label><br>
        <label for="address">
          <input type="text" name="address" id="address">
        </label><br>
        <label for="applicationNumber">
          <input type="text" name="applicationNumber" id="applicationNumber">
        </label><br>
        <label for="applicationType">
          <select name="applicationType" id="applicationType" class="select-box">
            <option value="수시">수시</option>
            <option value="정시">정시</option>
            <option value="특별전형">특별전형</option>
          </select>
        </label><br>
        <label for="faculty">
          <select name="faculty" id="faculty" class="select-box">
            <option value="IT 융합">IT 융합</option>
          </select>
        </label><br>
        <label for="department">
          <select name="department" id="department" class="select-box">
            <option value="정보보안학과">정보보안학과</option>
            <option value="인공지능학과">인공지능학과</option>
            <option value="컴퓨터공학과">컴퓨터공학과</option>
          </select>
        </label><br>
      </div>
    </div>
  <br>
  <button type="submit" name="button" >추가</button>
  <br>
  <button type="button" name="button" onclick="location.href='/admission/main'">메인으로</button>
  </form>
</div>
</body>
</html>
