<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Script to draw a table of trigonometric values that were store in temporary request attributes -->
<!DOCTYPE HTML>

<html>
	<head>
		<style type="text/css">
			body {
				background-color: #<%String color = session.getAttribute("pickedBgCol") instanceof String
					? (String) session.getAttribute("pickedBgCol")
					: null;
				out.write(color == null ? "FFFFFF" : color);%>;
				text-align: center;
			}
			
			table {
				margin: auto;
				text-align: center;
				border: 1px solid black;
			}
			
			td {
				padding: 12px;
			}
		</style>
	</head>
	<body>
		<h3>Trigonometric values of functions sin(x) and cos(x) from ${a} to ${b}</h3>
		
		<table>
			<tr>
				<td>angle</td>
				<td>sin(angle)</td>
				<td>cos(angle)</td>
			</tr>
			<c:forEach items="${data}" var="row">
				<tr>
					<td style="border-right: 2px dotted black;">${String.format("%.2f", row[0])}</td>
					<td style="border-right: 2px dotted black;">${String.format("%.2f", row[1])}</td>
					<td>${String.format("%.2f", row[2])}</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>