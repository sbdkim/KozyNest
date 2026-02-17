<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<section class="hero-home">
	<div class="hero-content">
		<h1 class="hero-title">Find Your Next Stay</h1>
		<p class="hero-subtitle">Comfortable rooms across Korea, ready for your trip.</p>

		<form action="acc_search_list" name="f" method="get" class="hero-search-form">
			<input type="search" class="main_search_text" placeholder="Seoul, Busan, Jeju" name="key" id="key" required>
			<input type="date" id="checkin" class="main_checkin_1" name="checkin" required>
			<input type="date" id="checkout" class="main_checkout_1" name="checkout" required>
			<select id="select_ro_count" name="ro_count" class="guest-select" aria-label="Guest count">
				<option value="1">1</option>
				<option value="2" selected>2</option>
				<option value="3">3</option>
				<option value="4">4</option>
			</select>
			<button type="submit" class="btn main_search_button">Search</button>
		</form>
	</div>
</section>

<input type="hidden" id="today" name="today">
<input type="hidden" id="tomorrow" name="tomorrow">

<section class="home-section">
	<div class="white-box">
		<h3 class="section-title">Browse Popular Regions</h3>
		<div id="regionacc" class="row region-grid">
			<div class="cell">
				<a href="#" onclick="location.href='acc_search_list?key=%EC%84%9C%EC%9A%B8&checkin='+document.getElementById('today').value+'&checkout='+document.getElementById('tomorrow').value+'&ro_count=2'">
					<img src="css/seoul.jpg" alt="Seoul">
					<p>Seoul</p>
				</a>
			</div>
			<div class="cell">
				<a href="#" onclick="location.href='acc_search_list?key=%EB%B6%80%EC%82%B0&checkin='+document.getElementById('today').value+'&checkout='+document.getElementById('tomorrow').value+'&ro_count=2'">
					<img src="css/busan.jpg" alt="Busan">
					<p>Busan</p>
				</a>
			</div>
			<div class="cell">
				<a href="#" onclick="location.href='acc_search_list?key=%EC%A0%9C%EC%A3%BC&checkin='+document.getElementById('today').value+'&checkout='+document.getElementById('tomorrow').value+'&ro_count=2'">
					<img src="css/jeju.jpg" alt="Jeju">
					<p>Jeju</p>
				</a>
			</div>
			<div class="cell">
				<a href="#" onclick="location.href='acc_search_list?key=%EB%8C%80%EA%B5%AC&checkin='+document.getElementById('today').value+'&checkout='+document.getElementById('tomorrow').value+'&ro_count=2'">
					<img src="css/daegu.jpg" alt="Daegu">
					<p>Daegu</p>
				</a>
			</div>
		</div>
	</div>

	<div class="white-box">
		<h3 class="section-title">Trending Accommodations</h3>
		<div id="trendingacc" class="row trending-grid">
			<c:forEach items="${bestAccommodationList}" var="bookingVO">
				<div class="cell">
					<a href="#" onclick="location.href='room?aseq=${bookingVO.aseq}&checkin='+document.getElementById('today').value+'&checkout='+document.getElementById('tomorrow').value">
						<img src="accommodation_images/${bookingVO.aseq}.jpg" alt="${bookingVO.aname}">
						<p>${bookingVO.aname}</p>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<script>
	const today = new Date().toISOString().split('T')[0];
	const tomorrow = new Date();
	tomorrow.setDate(tomorrow.getDate() + 1);
	const tomorrowISO = tomorrow.toISOString().split('T')[0];

	document.getElementById("checkin").setAttribute("min", today);
	document.getElementById("checkout").setAttribute("min", tomorrowISO);
	document.getElementById("checkin").setAttribute("value", today);
	document.getElementById("checkout").setAttribute("value", tomorrowISO);
	document.getElementById("today").setAttribute("value", today);
	document.getElementById("tomorrow").setAttribute("value", tomorrowISO);
</script>
<%@ include file="footer.jsp"%>