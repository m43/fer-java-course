<%@ page import="java.util.Map" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Script to show voting table and enable clicking on a band to vote for it -->
<!DOCTYPE HTML>

<html>
<head>
<style type="text/css">
body {
	background-color: #<%String color = session.getAttribute("pickedBgCol") instanceof String
					? (String) session.getAttribute("pickedBgCol")
					: null;
			out.write(color == null ? "FFFFFF" : color);%>;
}
</style>
</head>
<body>
	<h1>Polls</h1>
	<p>Pick a poll you where you want to vote:</p>
	<ol>
		<c:forEach items="${polls}" var="item">
			<li>${item.getTitle()} <a href="glasanje?pollID=${item.getId()}">glasaj!</a></li>
		</c:forEach>
	</ol>
</body>
</html>