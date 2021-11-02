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
		<h1>Glasanje za omiljeni bend:</h1>
		<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
		<ol>
			<%
				@SuppressWarnings("unchecked")
				Map<Integer, String> mapIDtoName = (Map<Integer, String>) request.getAttribute("mapIDtoName");
					
				for (Map.Entry<Integer, String> entry: mapIDtoName.entrySet()){
					out.write("<li><a href=\"glasanje-glasaj?id="+entry.getKey()+"\">"+entry.getValue()+"</a></li>");
				}			
			%>
		</ol>
	</body>
</html>