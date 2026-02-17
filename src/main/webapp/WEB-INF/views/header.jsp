<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>KozyNest : Korean Comfort Lodging</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
<script src="js/jquery-3.6.3.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="member/member.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
function requestPay() {
	var bprice = parseInt($("#bprice").val());
	var result = false;
	IMP.init("imp80657743");
	IMP.request_pay({
		pg : "html5_inicis.INIpayTest",
		pay_method : "card",
		merchant_uid : "merchant_" + new Date().getTime(),
		name : "KozyNest Payment",
		amount : bprice,
		buyer_email : "${loginUser.email}",
		buyer_name : "${loginUser.name}",
		buyer_tel : "${loginUser.phone}",
		buyer_postcode : "123-456"
	}, function(rsp) {
		if (rsp.success) {
			var theform = document.getElementById("book_form");
			theform.action = "booking_insert";
			theform.submit();
		} else {
			alert("Payment failed.");
			result = false;
		}
	});
	return false;
}
</script>
</head>
<body class="app-shell">
<header class="site-header">
	<div class="site-header-inner">
		<a class="site-logo" href="index" aria-label="KozyNest Home">
			<img class="header-image" id="header-image" alt="KozyNest logo" src="css/KozyNestLogo4.jpg">
		</a>
		<nav id="catergory_menu" class="site-nav" aria-label="Primary">
			<ul>
				<c:choose>
					<c:when test="${empty sessionScope.loginUser && empty sessionScope.loginHost}">
						<li><a href="login_form">Login</a></li>
					</c:when>
					<c:when test="${not empty sessionScope.loginHost}">
						<li><a href="logout">Logout</a></li>
						<li><a href="host_mypage">${sessionScope.loginHost.name} Dashboard</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="logout">Logout</a></li>
						<li><a href="mypage">${sessionScope.loginUser.name} My Page</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="map">Map</a></li>
			</ul>
		</nav>
	</div>
</header>
