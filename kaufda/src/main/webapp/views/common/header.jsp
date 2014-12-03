<!-- Common header for all JSPs -->
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
	<c:url var="index_url" value="/" />
	<a href="${index_url }"><button>Index</button></a>
	<h2 style="display: inline;">Hello Blog World!</h2>
	<!-- Allows navigation to your own blog when logged -->
	<sec:authorize access="isAuthenticated()">
		<c:url var="blog_url" value="/blog" />
		<a href="${blog_url }"><button>My Blog</button></a>
	</sec:authorize>
	<!-- Shows login button when not logged -->
	<sec:authorize access="!isAuthenticated()">
		<c:url value="/user/login" var="loginUrl" />
		<a href="${loginUrl }" ><button>Login</button></a>
	</sec:authorize>
	<!-- Allows navigation to My Profile and to logout when logged -->
	<sec:authorize access="isAuthenticated()">
		<c:url value="/user/edit" var="editUrl" />
		<a href="${editUrl }" ><button>My Profile</button></a>
		<c:url value="/logout" var="logoutUrl" />
		<a href="${logoutUrl }" ><button>Logout</button></a>
	</sec:authorize>
</body>
</html>
