<%@page import="java.util.Optional"%>
<%@ page import="java.util.Map" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Script to show voting results -->
<!DOCTYPE HTML>

<%
	@SuppressWarnings("unchecked")
	Map<Integer, String> mapIDtoName = (Map<Integer, String>) request.getAttribute("mapIDtoName");

	@SuppressWarnings("unchecked")
	Map<Integer, String> mapIDtoLink = (Map<Integer, String>) request.getAttribute("mapIDtoLink");

	@SuppressWarnings("unchecked")
	Map<Integer, Integer> mapIDtoVotes = (Map<Integer, Integer>) request.getAttribute("mapIDtoVotes");
%>

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
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Map.Entry<Integer, Integer> entry : mapIDtoVotes.entrySet()) {
					out.write("<tr><td>" + mapIDtoName.get(entry.getKey()) + "</td><td>" + entry.getValue() + "</td></tr>");
				}
			%>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="<%=request.getContextPath()%>/glasanje-grafika" width="600" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="<%=request.getContextPath()%>/glasanje-xls">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<%
			Optional<Integer> optionalInteger = mapIDtoVotes.entrySet().stream().map(e->e.getValue()).max(Integer::compare);
			int max = optionalInteger.isPresent()?optionalInteger.get():0;
			
			for ( Map.Entry<Integer, Integer> e: mapIDtoVotes.entrySet()){
				if (e.getValue().equals(max)){
					out.write("<li><a href=\""+mapIDtoLink.get(e.getKey())+"\" target=\"_blank\">"+mapIDtoName.get(e.getKey())+"</a></li>");		
				}
			}
		%>
	</ul>
</body>
</html>