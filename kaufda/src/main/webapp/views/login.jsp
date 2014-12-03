<!-- Login page, it allows us to create or edit a user too -->
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<jsp:include page="common/header.jsp"/>
	<script type="text/javascript" src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/login.js' />"></script>
</head>
<body>
	<div>
		<c:choose>
			<%-- We display an editable form when we receive the parameters creation or edition --%>
			<c:when test="${creation || edition }">
				<h3>
					<c:choose>
						<c:when test="${creation }">New User</c:when>
						<c:otherwise>Edit User</c:otherwise>
					</c:choose>
				</h3>
				<%-- Show errors (if any) --%>
				<c:if test="${not empty error }">
					<font color="red">Error:&nbsp;<c:out value="${error}"/>.</font><br/><br/>
				</c:if>
				<c:url var="save_url" value="/user/save" />
				<form:form action="${save_url }" modelAttribute="user" onsubmit="return validatePassword()">
					<table>
						<tr>
							<td><form:label path="userName">Name</form:label></td>
							<td><form:input path="userName"/></td>
						</tr>
						<tr>
							<td><form:label path="userPassword">Password</form:label></td>
							<td><form:password id="pass1" path="userPassword" /></td>
						</tr>
						<tr>
							<td><label>Repeat password</label></td>
							<td><input type="password" id="pass2" value="${user.userPassword }"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="submit" value="Submit"/>
							</td>
						</tr>
					</table>
					<c:if test="${not empty user.userId }">
						<form:hidden path="userId"/></c:if>
				</form:form>
			</c:when>
			<%-- If there is no parameters creation or edition specified, we just show the login form --%>
			<c:otherwise>
				<h3>Login</h3>
				<c:if test="${not empty error }">
					<font color="red">
						Your login attempt was not successful due to:<c:out value="${error}"/>.<br/>
					</font>
				</c:if>
				<c:url var="login_url" value="/user/login" />
				<form action="${login_url }" method="post">
					<table>
						<tr>
							<td>Username:</td>
							<td><input type='text' name='username' /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type='password' name='password'></td>
						</tr>
						<tr>
							<td colspan='2'><input name="submit" type="submit" value="Submit"></td>
						</tr>
					</table>
				</form>
				<%-- Allow the user to create a new profile --%>
				<p>Not registered yet?</p>
				<c:url value="/user/create" var="createUrl" />
				<a href="${createUrl }" ><button>Create New User</button></a>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
