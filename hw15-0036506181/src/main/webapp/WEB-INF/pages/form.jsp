<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register user</title>
		
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
	
		<h1>
		Create new user
		</h1>

		<form action="register" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="firstname" value='<c:out value="${form.firstName}"/>' size="20">
		 </div>
		 <c:if test="${form.hasInconsistency('firstname')}">
		 <div class="formInvalid"><c:out value="${form.getInconsistency('firstname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="lastname" value='<c:out value="${form.lastName}"/>' size="20">
		 </div>
		 <c:if test="${form.hasInconsistency('lastname')}">
		 <div class="formInvalid"><c:out value="${form.getInconsistency('lastname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="15">
		 </div>
		 <c:if test="${form.hasInconsistency('nick')}">
		 <div class="formInvalid"><c:out value="${form.getInconsistency('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Email</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="15">
		 </div>
		 <c:if test="${form.hasInconsistency('email')}">
		 <div class="formInvalid"><c:out value="${form.getInconsistency('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value="" size="15">
		 </div>
		 <c:if test="${form.hasInconsistency('password')}">
		 <div class="formInvalid"><c:out value="${form.getInconsistency('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Save">
		  <input type="submit" name="method" value="Cancel">
		</div>
		
		</form>

		<p><a href="<%=request.getContextPath()%>/servleti/main">back home..</a></p>

	</body>
</html>
