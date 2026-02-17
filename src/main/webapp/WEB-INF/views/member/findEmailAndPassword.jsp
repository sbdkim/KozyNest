<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
      <link href="css/shopping.css" rel="stylesheet">
      <script src="https://code.jquery.com/jquery-3.6.2.min.js" integrity="sha256-2krYZKh//PcchRtd+H+VyyQoZ/e3EcrkxhM8ycwASPA=" crossorigin="anonymous"></script>
      <script type="text/javascript" src="member/member.js"></script>
  </head>
  <body>
  <div id="wrap">
    <h1>Find Email and Password</h1>
    <br>
    <form name="findEmail" id="findEmail" action="find_email" method="post">
    <table>
    <tr>
        <td align="right"><label>Name</label></td>
        <td><input type="text" name="name" id="name" value=""></td>
    </tr>
    <tr>
        <td align="right"><label>Phone</label></td>
        <td><input type="text" name="phone" id="phone" value=""></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><input type="button" value="Find Email" onclick="findMemberEmail()"></td>
    </tr>
    </table>
    </form>
    <p><p><p><p><p><p><p><p><p><p><p><p>

    <form name="findPW" id="findPW" action="find_pwd" method="post">
    <table>
    <tr>
        <td align="right"><label>Email</label></td>
        <td><input type="text" name="email" id="email2" value=""></td>
    </tr>
    <tr>
        <td align="right"><label>Name</label></td>
        <td><input type="text" name="name" id="name2" value=""></td>
    </tr>
    <tr>
        <td align="right"><label>Phone</label></td>
        <td><input type="text" name="phone" id="phone2" value=""></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><input type="button" value="Find Password" onclick="findPassword()"></td>
    </tr>
    </table>
    </form>
    </div>
</body>
</html>