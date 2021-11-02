<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
		body {
			font-family: Arial;
		}
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

	<c:if test="${user != null}">
		<h1>
			Welcome,
			<c:out value="${user.nick }"></c:out>
		</h1>
		<p>
			<a href="${pageContext.request.contextPath}/servleti/logout">LOGOUT</a>
		</p>
	</c:if>

	<h1>Blog title: ${entry.title }</h1>
	<h4>Blog creation date: ${entry.createdAt }</h4>
	<h4>Blog was last modified at: ${entry.lastModifiedAt }</h4>
	<h4>Blog comments count: ${entry.comments.size() }</h4>

	<p><span style = "font-weight: bold;">Blog text:</span> ${entry.text }</p>

	<p>
		<c:if test="${user.nick==author}">
			<a href="edit?eid=${entry.id}">EDIT</a>
		</c:if>
	</p>

<br/><br/>

	<p>Comments:</p>
	
	<c:choose>
		<c:when test="${comments.isEmpty()}">
			<p>No comments - be the first one to comment!</p>
		</c:when>
		<c:otherwise>
			<ul>
			<c:forEach items="${comments}" var="c">
				<li><div>
				<span style = "font-weight: bold;">EMAIL: ${c.getUsersEmail()}</span><br/>
				POSTED ON: ${c.getPostedOn()}<br/>
				MESSAGE: ${c.getMessage()}
				</div></li>
			</c:forEach>
			</ul>			
		</c:otherwise>
	</c:choose>

	<br/>
	
	<p>Add new comment:</p>

		<form action="addcomment?eid=${eid}" method="post">

			<c:choose>
				<c:when test="${user != null}">
					<div><span class="formLabel"><c:out value="Email:${user.email}"></c:out></span></div>
				</c:when>
				<c:otherwise>
					<div>
						<span class="formLabel">Email:</span>
						<input type="text" name="email">
					</div>
					<c:if test="${emailError!=null}">
		 			<div class="formInvalid"><c:out value="${emailError}"/></div>
		 			</c:if>
		
				</c:otherwise>
			</c:choose>
			<div>
				<span class="formLabel">*Message:</span>
				<input type="text" name="message">
				<c:if test="${messageError!=null}">
	 			<div class="formInvalid"><c:out value="${messageError}"/></div>
	 			</c:if>
			</div>

			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit" name="method" value="AddComment">
			</div>	
		</form>

		<p><a href="<%=request.getContextPath()%>/servleti/author/${author}">back to author..</a></p>
		<p><a href="<%=request.getContextPath()%>/servleti/main">back home..</a></p>

</body>
</html>
