<%@page import="java.util.Optional"%>
<%@ page import="java.util.Map" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Script to show voting results -->
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
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Stavka</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${options}" var="item">
			<tr><td> ${item.getTitle()}</td><td>${item.getNumberOfVotes()}</td></tr>		
		</c:forEach>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="600" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul style="display: inline-block">
		<c:forEach items="${options}" var="item">
			<li><a href="${item.getLink() }">${item.getTitle() }</a></li>		
		</c:forEach>
	</ul>
</body>
</html>