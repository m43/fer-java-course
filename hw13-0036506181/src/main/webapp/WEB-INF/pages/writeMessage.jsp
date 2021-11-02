<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- JSP that writes a message given in message parameter and also gives a link for to return to home page -->
<!DOCTYPE HTML>

<html>
	<head>
		<style type="text/css">
			body {
				background-color: #<%String color = session.getAttribute("pickedBgCol") instanceof String
					? (String) session.getAttribute("pickedBgCol")
					: null;
				out.write(color == null ? "#FFFFFF" : color);%>;
			}
		</style>
	</head>
	<body>
		<h1>${message}</h1>
		<p><a href="<%=request.getContextPath()%>/">back home..</a></p>
	</body>
</html>