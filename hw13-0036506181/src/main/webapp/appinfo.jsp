<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Script to show the time from server creation -->
<!DOCTYPE HTML>

<html>
	<head>
		<style type="text/css">
			body {
				font-family: "Arial";
				text-align: center;
				background-color: #<%String color = session.getAttribute("pickedBgCol") instanceof String
					? (String) session.getAttribute("pickedBgCol")
					: null;
				out.write(color == null ? "#FFFFFF" : color);%>;
			}
		</style>
	</head>
	<body>
		<p>App running now for: <%
			long timeNow = System.currentTimeMillis();
			Object startTimeObject = application.getAttribute("creationTime");
			if ( startTimeObject instanceof Long ){
				long deltaTime = timeNow - (long) startTimeObject;
				
				long seconds = deltaTime / 1000;
				long minutes = seconds / 60;
				long hours = minutes / 60;
				long days = hours / 24; 
				
				out.write(String.valueOf(days + " days " + hours % 24 + " hours " + minutes % 60 + " minutes  and " + seconds % 60 + " seconds"));
			} else {
				out.write("whoops, some error happened..");
			}
		%></p>
	</body>
</html>