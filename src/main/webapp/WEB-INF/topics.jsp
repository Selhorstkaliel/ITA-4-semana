<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Topics - Forum</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Forum Topics</h1>
        
        <div class="nav-links">
            <span>Welcome, ${user.name}!</span> |
            <a href="${pageContext.request.contextPath}/new-topic">New Topic</a> |
            <a href="${pageContext.request.contextPath}/ranking">Ranking</a> |
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <h2>All Topics</h2>
        
        <c:choose>
            <c:when test="${empty topics}">
                <p>No topics available. Be the first to create one!</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="topic" items="${topics}">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/topic?id=${topic.id}" class="topic-link">
                                        ${topic.title}
                                    </a>
                                </td>
                                <td>${topic.userName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
