<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>

	<c:if test="${user != null}"><h1>Welcome, <c:out value="${user.nick }"></c:out></h1> <p><a href="${pageContext.request.contextPath}/servleti/logout">LOGOUT</a></p></c:if>

 <h2>Author: <b>${author}</b></h2>

  <c:choose>
    <c:when test="${entries.isEmpty()}">
      No entries for this author!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${entries}">
        <li>
        	<div style="font-family: Arial">Blog Entry title: <a href="${author}/${e.id}"><c:out value="${e.title}"/></a><br/>
        	Created: <c:out value="${e.createdAt}"/><br/>
        	  <c:if test="${user.nick==author}"><a href="${author}/edit?eid=${e.id}">EDIT THIS BLOG ENTRY</a></c:if>
        	</div>
        </li>
      </c:forEach>
      </ul>
      </c:if>
    </c:otherwise>
  </c:choose>
  
  <c:if test="${user.nick==author}">
  	<p><a href="${author}/new">Create new blog entry</a></p>
  </c:if>

	<p><a href="<%=request.getContextPath()%>/servleti/main">back home..</a></p>

  </body>
</html>
