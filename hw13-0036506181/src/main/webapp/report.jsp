<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Script for showing OS usage -->
<!DOCTYPE HTML>

<html>
	<head>
		<style type="text/css">
			body {
				text-align: center;
				background-color: #<%String color = session.getAttribute("pickedBgCol") instanceof String
					? (String) session.getAttribute("pickedBgCol")
					: null;
				out.write(color == null ? "#FFFFFF" : color);%>;
			}
		</style>
	</head>
	<body>
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we've completed.</p>
		<img src="<%=request.getContextPath()%>/reportImage"/>
	</body>
</html>