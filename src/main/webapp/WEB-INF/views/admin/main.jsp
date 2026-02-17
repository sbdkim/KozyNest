<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KozyNest Admin</title>
<link rel="stylesheet" href="admin/css/admin.css">
<script type="text/javascript">
function admin_check()
{
  if(document.frm.id.value==""){
      alert("?꾩씠?붾? ?낅젰?섏꽭??");
      return false;
  } else if(document.frm.pwd.value==""){
     alert("鍮꾨?踰덊샇瑜??낅젰?섏꽭??");
      return false;
  }
  
  return true;  
}
</script>
</head>

<body>
  <div id="wrap">
    <header>      
      <div id="logo">
        <a href="admin_login_form"> 
        <img src="admin/images/bar_01.gif" style="float:left">
        <img src="admin/images/text.gif">
        </a>
      </div>      
    </header>
    <div class="clear"></div>
    <article>
      <div id="loginform">
      <form name="frm" method="post" action="admin_login" autocomplete="on">
      <table>
        <tr>
          <td> ??????</td>
          <td> <input type="text" id="workId" name="id" size="10" autocomplete="username" required></td>
        </tr>
        <tr>
          <td> 鍮꾨?踰덊샇 </td>
          <td> 
            <input type="password" id="workPw" name="pwd" size="10" autocomplete="current-password" required>
          </td>
        </tr>
        <tr align="center" >
          <td  colspan="2">          
            <input class="btn" type="submit" value="?낅Т 濡쒓렇?? onclick="return admin_check()"><br><br>
            <h4 style="color:red">${message}</h4>
          </td>
        </tr>
      </table>
      </form>
      </div>
    </article>
  </div>
</body>
</html>
