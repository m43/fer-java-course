<%@ page import="java.util.Map" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<h1><c:out value="${poll.getTitle()}"/></h1>
		<p><c:out value="${poll.getMessage()}"/></p>
		<ol>
			<c:forEach items="${options}" var="item">
				<li><a href="glasanje-glasaj?pollID=${poll.getId()}&id=${item.getId()}">${item.getTitle()}</a></li>
			</c:forEach>							
		</ol>
	</body>
</html>