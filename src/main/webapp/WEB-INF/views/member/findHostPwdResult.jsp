<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Host Password Reset</title>
<link href="CSS/subpage.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.2.min.js" integrity="sha256-2krYZKh//PcchRtd+H+VyyQoZ/e3EcrkxhM8ycwASPA=" crossorigin="anonymous"></script>
<script type="text/javascript" src="member/member.js"></script>
<style type="text/css">
body {
  background-color: tomato;
  font-family: Verdana;
}
#wrap {
  margin: 0 20px;
}
h1 {
  font-family: "Times New Roman", Times, serif;
  font-size: 45px;
  color: #CCC;
  font-weight: normal;
}
input[type=button], input[type=submit] {
  float: right;
}
label {
  display: inline-block;
  width: 180px;
}
</style>
<script type="text/javascript">
function closeWindow(){
  self.close();
}
</script>
</head>
<body>
<div id="wrap">
  <h1>Host Password Reset</h1>
  <form method="post" name="formm" action="change_host_pwd" id="host_pwd_form" style="margin-right:0">
    <input type="hidden" name="token" value="${token}">
    <label>Host Email</label><input type="text" name="hemail" value="${hemail}" readonly="readonly"><br>
    <div style="margin-top: 20px">
      <c:if test="${message == 1}">
        <label>New Password</label>
        <input type="password" name="pwd" id="pwd"><br>
        <label>Retype Password</label>
        <input type="password" name="pwdCheck" id="pwdcheck"><br>
        <input type="button" value="Confirm" class="cancel" onclick="changeHostPassword()">
      </c:if>
      <c:if test="${message==-1}">
        <p>Account information did not match.</p>
        <input type="button" value="Close" class="cancel" onclick="closeWindow()">
      </c:if>
      <c:if test="${message==-2}">
        <p>Reset token is invalid or expired. Please request a new reset.</p>
        <input type="button" value="Close" class="cancel" onclick="closeWindow()">
      </c:if>
    </div>
  </form>
</div>
</body>
</html>