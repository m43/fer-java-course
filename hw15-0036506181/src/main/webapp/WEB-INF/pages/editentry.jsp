<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Edit ${entry.title}</title>
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
	
	
        <h1>Edit entry</h1>
        	<form action="edit?eid=${eid}" method="post">

			<div>
				<span class="formLabel">Title: <c:out value='${entry.title}'/></span>
			</div>
			<div>
				<span class="formLabel">Text</span>
				<input type="text" name="text" value="${entry.text }">
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
