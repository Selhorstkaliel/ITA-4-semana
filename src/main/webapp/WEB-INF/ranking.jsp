<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ranking - Forum</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>User Ranking</h1>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/topics">Back to Topics</a> |
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <table>
            <thead>
                <tr>
                    <th>Rank</th>
                    <th>Name</th>
                    <th>Login</th>
                    <th>Points</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <tr>
                        <td class="ranking-position">${status.index + 1}</td>
                        <td>${user.name}</td>
                        <td>${user.login}</td>
                        <td>${user.points}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <p style="margin-top: 20px;">
            <strong>Points system:</strong> Create a topic = 10 points, Add a comment = 3 points
        </p>
    </div>
</body>
</html>
