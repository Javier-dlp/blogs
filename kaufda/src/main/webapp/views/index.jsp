<!-- Main page, it displays a list of all of the entries -->
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<jsp:include page="common/header.jsp"/>
</head>
<body>
	<h2>Index</h2>
	<c:url var="add_url"  value="/entry" />
	<table>
		<c:forEach var="entry" items="${entries }">
			<c:url var="entry_url"  value="/entry/${entry.entryId }" />
			<c:url var="blog_url"  value="/blog/${entry.user.userId }" />
				<tr>
					<td><b><a href="${blog_url }">${entry.user.userName }'s Blog:</a>&nbsp;</b></td>
					<td><a href="${entry_url }">${entry.entryTitle }</a>&nbsp;</td>
					<td><i>(<fmt:formatDate pattern="MMM d, hh:mm" value="${entry.entryDate}" />)</i></td>
				</tr>
		</c:forEach>
	</table>
</body>
</html>
