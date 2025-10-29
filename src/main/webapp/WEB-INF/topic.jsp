<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${topic.title} - Forum</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/topics">Back to Topics</a> |
            <a href="${pageContext.request.contextPath}/ranking">Ranking</a> |
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <div class="topic-detail">
            <h1 class="topic-title">${topic.title}</h1>
            <p class="topic-author">Posted by: ${topic.userName}</p>
            <div class="topic-content">${topic.content}</div>
        </div>
        
        <h2>Comments</h2>
        
        <c:choose>
            <c:when test="${empty comments}">
                <p>No comments yet. Be the first to comment!</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="comment" items="${comments}">
                    <div class="comment">
                        <div class="comment-author">${comment.userName}</div>
                        <div class="comment-content">${comment.content}</div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        
        <h3>Add a Comment</h3>
        <form method="post" action="${pageContext.request.contextPath}/topic">
            <input type="hidden" name="topicId" value="${topic.id}">
            
            <div class="form-group">
                <label for="content">Your Comment:</label>
                <textarea id="content" name="content" rows="5" required></textarea>
            </div>
            
            <button type="submit">Add Comment</button>
        </form>
    </div>
</body>
</html>
