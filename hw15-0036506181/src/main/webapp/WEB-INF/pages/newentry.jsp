<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>New blog entry</title>
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
		
		<c:if test="${user != null}"><h1>Welcome, <c:out value="${user.nick }"></c:out></h1> <p><a href="${request.getContextPath()}/servleti/logout">LOGOUT</a></p></c:if>
	
	
        <h3>Create new entry</h3>
        	<form action="new" method="post">

			<div>
				<span class="formLabel">Title:</span>
				<input type="text" name="title" value="${entry.text }">
			
			</div>
			<div>
				<span class="formLabel">Text:</span>
				<textarea name="text" cols="40" rows="5" value="${entry.text }"></textarea>
			</div>

				<c:if test="${error!=null}">
	 			<div class="formInvalid"><c:out value="${error}"/></div>
	 			</c:if>

			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit" name="method" value="Save">
				<input type="submit" name="method" value="Cancel">
			</div>
	
		</form>
		
		<p><a href="<%=request.getContextPath()%>/servleti/main">back home..</a></p>
		
	</body>
</html>
