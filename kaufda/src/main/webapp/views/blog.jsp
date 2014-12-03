<!-- JSP for the blog navigation -->
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<jsp:include page="common/header.jsp"/>
</head>
<body>
	<h2><c:out value="${blogger.userName }"/></h2>
	<%-- If the user has permission it allows to create new entries on this blog --%>
	<c:if test="${edition }">
		<c:url var="new_url" value="/entry/create/${blogger.userId }" />
		<a href="${new_url }"><button>New Entry</button></a>
	</c:if>
	<ul>
		<%-- Iterates through the entries, allows navigation to the entry --%>
		<c:forEach var="entry" items="${entries }">
			<li>
				<c:url var="entry_url" value="/entry/${entry.entryId }" />
				<a href="${entry_url }"><c:out value="${entry.entryTitle }" /></a>&nbsp;
				<i>(<fmt:formatDate pattern="MMM d, hh:mm" value="${entry.entryDate}" />)</i>
			</li>
		</c:forEach>
	</ul>
</body>
</html>
