<!-- Page for the displaying and edition of entries on blogs -->
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" href="<c:url value='/css/style.css' />" />
	<jsp:include page="common/header.jsp"/>
</head>
<body>
	<c:url var="blog_url" value="/blog/${entry.user.userId }" />
	<h3><a href="${blog_url }">${entry.user.userName }'s Blog</a></h3>
	<c:choose>
		<%-- When we receive the parameters edition or creation we display an editable form --%>
		<c:when test="${(edition) || (creation)}">
			<h4>
				<c:choose>
					<c:when test="${edition }">Edit entry</c:when>
					<c:otherwise>New entry</c:otherwise>
				</c:choose>
			</h4>
			<!-- Show errors (if any) -->
			<c:if test="${not empty error }">
		 		<font color="red">Error:&nbsp;<c:out value="${error}"/>.</font><br/><br/>
			</c:if>
			<c:url var="save_url" value="/entry/save" />
			<form:form action="${save_url }" modelAttribute="entry" enctype="multipart/form-data" method="post">
				<table>
					<tr>
						<td><form:label path="entryTitle">Title:</form:label></td>
						<td><form:input path="entryTitle"/></td>
					</tr>
					<!-- We create a new line when there is a photo for the input -->
					<c:choose>
						<c:when test="${not empty photo }" >
							<tr>
								<td><label>Image:</label></td>
								<td><img id="photo" src="data:image/png;base64,${photo }" /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="file" name="file"></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td><label>Image:</label></td>
								<td><input type="file" name="file"></td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td><form:label path="entryText">Text:</form:label></td>
						<td><form:textarea path="entryText"/></td>
					</tr>
				</table>
				<br/>
				<!-- All the needed values to not delete the data -->
				<form:hidden path="entryId"/>
				<form:hidden path="entryImage"/>
				<form:hidden path="user.userId"/>
				<input type="submit" value="Save"/>&nbsp;
				<c:if test="${edition }">
					<c:url var="delete_url" value="/entry/delete/${entry.entryId }" />
					<a href="${delete_url }"><input type="button" value="Delete"/></a>
				</c:if>
			</form:form>
		</c:when>
		<%-- When there is no edition or creation parameters, we just show the entry --%>
		<c:otherwise>
			<table>
				<tr>
					<td><b>${entry.entryTitle }</b>&nbsp;</td>
					<td><i>(<fmt:formatDate pattern="MMM d, hh:mm" value="${entry.entryDate}" />)</i></td>
				</tr>
				<c:if test="${not empty photo }">
					<tr>
						<td colspan="2"><img src="data:image/png;base64,${photo }" />
					</tr>
				</c:if>
				<tr>
					<td colspan="2">${entry.entryText }</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>
