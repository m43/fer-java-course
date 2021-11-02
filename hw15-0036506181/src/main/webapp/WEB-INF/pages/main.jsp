<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Blog home</title>
		<style type="text/css">
			.formInvalid {
				font-family: Arial;
				font-size: 0.9em;
				color: #FF0000;
				padding-left: 110px;
			}
			
			.formLabel {
				display: inline-block;
				width: 100px;
				font-weight: bold;
				text-align: right;
				padding-right: 10px;
			}
			
			.formControls {
				margin-top: 10px;
			}
		</style>
	</head>

	<body>
		<c:choose>
		    <c:when test="${user != null}">
		    	<h1>Welcome, <c:out value="${user.nick }"></c:out></h1> <p><a href="${pageContext.request.contextPath}/servleti/logout">LOGOUT</a></p> 
		    </c:when>    
		    <c:otherwise>
		        <h1>Login:</h1>
		        	<form action="main" method="post">

					<div>
						<span class="formLabel">Nick</span>
						<input type="text" name="nick" value='<c:out value='${nick}'/>' size="20">
					</div>
					<div>
						<span class="formLabel">Password</span>
						<input type="password" name="password" value="" size="15">
					</div>
					<div class="formInvalid">${errorMessage}</div>
			
					<div class="formControls">
						<span class="formLabel">&nbsp;</span> <input type="submit" name="method" value="Login">
						<input type="submit" name="method" value="Cancel">
					</div>
			
				</form>
		    </c:otherwise>
		</c:choose>

		<h2>Register: <a href="register">HERE</a></h2>

		<c:if test="${!authors.isEmpty()}">
		<h2>Authors:</h2>
			<ul>
			<c:forEach items="${authors}" var="a">
			<li>author with nickname <a href="author/${ a.nick }">${a.nick}</a></li>
			</c:forEach>
			</ul>
		</c:if>
	
	</body>
</html>
