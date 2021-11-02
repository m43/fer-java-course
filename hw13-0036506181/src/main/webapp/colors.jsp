<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Script to pick a background color -->
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
		<h1>Please pick a background color</h1>
		
		<h2><a href="<%=request.getContextPath()%>/setcolor?color=ffffff">WHITE</a></h2>
		<h2><a href="<%=request.getContextPath()%>/setcolor?color=e74c3c">RED</a></h2>
		<h2><a href="<%=request.getContextPath()%>/setcolor?color=2ecc71">GREEN</a></h2>
		<h2><a href="<%=request.getContextPath()%>/setcolor?color=00b5cc">CYAN</a></h2>
		
	</body>
</html>