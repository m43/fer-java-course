<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Index page script -->
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
		<h1>Welcome to <%= request.getContextPath() %></h1><br/>
		<h4>Background color chooser --> <a href="<%=request.getContextPath()%>/colors.jsp">HERE</a></h4>
		<h4>Trigonometric values of angles in range [0, 90] --> <a href="<%=request.getContextPath()%>/trigonometric?a=0&b=90">HERE</a></h4>
		<form action="trigonometric" method="GET">
		Starting angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Angle to stop at:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Create table"><input type="reset" value="Reset">
		</form>
		<h4>SOME FUNNY STORY --> <a href="<%=request.getContextPath()%>/stories/funny.jsp">HERE</a></h4>
		<h4>OS usage --> <a href="<%= request.getContextPath() %>/report.jsp">HERE</a></h4>
		<h4>Powers --> <a href="<%= request.getContextPath() %>/powers?a=1&b=100&n=3">HERE</a></h4>
		<h4>Check the application information --> <a href="<%= request.getContextPath() %>/appinfo.jsp">HERE</a></h4>
		<h4>Vote for your favorite band! --> <a href="<%= request.getContextPath() %>/glasanje">HERE</a></h4>
		<h4>View results of vote for favorite band --> <a href="<%= request.getContextPath() %>/glasanje-rezultati">HERE</a></h4>
	</body>
</html>