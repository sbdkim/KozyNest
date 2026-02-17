<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<style>
body {
	font-family: "Segoe UI", Arial, sans-serif;
	background: #f7f9fc;
	color: #1f2937;
	margin: 0;
}
.wrap {
	max-width: 760px;
	margin: 10vh auto;
	padding: 24px;
}
.card {
	background: #ffffff;
	border: 1px solid #e5e7eb;
	border-radius: 10px;
	padding: 28px;
	box-shadow: 0 8px 24px rgba(17, 24, 39, 0.06);
}
h1 {
	margin: 0 0 16px;
	font-size: 24px;
}
p {
	margin: 10px 0;
	line-height: 1.5;
}
.meta {
	color: #6b7280;
	font-size: 14px;
}
.actions {
	margin-top: 20px;
}
.btn {
	display: inline-block;
	padding: 10px 14px;
	background: #2563eb;
	color: #fff;
	text-decoration: none;
	border-radius: 6px;
}
</style>
</head>
<body>
	<div class="wrap">
		<div class="card">
			<h1>Something went wrong</h1>
			<p>${errorMessage}</p>
			<p class="meta">Error code: ${errorCode}</p>
			<p class="meta">Request path: ${requestPath}</p>
			<p class="meta">Request id: ${requestId}</p>
			<div class="actions">
				<a class="btn" href="${pageContext.request.contextPath}/index">Go to Home</a>
			</div>
		</div>
	</div>
</body>
</html>
