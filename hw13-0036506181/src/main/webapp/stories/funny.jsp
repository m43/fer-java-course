<%@page import="java.util.Random"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Script to render a story using a randomly selected font -->
<!DOCTYPE HTML>
<%
String[] fonts = new String[] {
		"Times New Roman", "Arial", "Helvetica", "Georgia", "Arial Black",
		"Comic Sans MS", "Lucida Sans Unicode", "Tahoma", "Verdana", "Geneva",
		"Courier New", "Lucida Console"
};

String[] colors = new String[] {"Red", "Yellow", "Cyan", "Blue", "Magenta"};

%>


<html>
	<head>
		<style type="text/css">
			body {
				font-family: "<%= fonts[new Random().nextInt(fonts.length)] %>";
				text-align: center;
				background-color: #<%String color = session.getAttribute("pickedBgCol") instanceof String
					? (String) session.getAttribute("pickedBgCol")
					: null;
				out.write(color == null ? "#FFFFFF" : color);%>;
			}
			
			h1 {
				font-size: 72px;
			}
			
			p {
				font-size: 32px;
				color: <%= colors[new Random().nextInt(colors.length)] %>;
			}
		</style>
	</head>
	<body>
		<h1>funny story</h1>
		<p>i'm funny at parties</p>
	</body>
</html>