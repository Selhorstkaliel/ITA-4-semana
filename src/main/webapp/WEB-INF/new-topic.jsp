<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Topic - Forum</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>Create New Topic</h1>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/topics">Back to Topics</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <form method="post" action="${pageContext.request.contextPath}/new-topic">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" required>
            </div>
            
            <div class="form-group">
                <label for="content">Content:</label>
                <textarea id="content" name="content" rows="10" required></textarea>
            </div>
            
            <button type="submit">Create Topic</button>
            <a href="${pageContext.request.contextPath}/topics" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
