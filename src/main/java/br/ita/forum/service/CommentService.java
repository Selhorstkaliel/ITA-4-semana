package br.ita.forum.service;

import br.ita.forum.dao.CommentDAO;
import br.ita.forum.dao.UserDAO;
import br.ita.forum.model.Comment;

import java.sql.SQLException;
import java.util.List;

public class CommentService {
    private CommentDAO commentDAO;
    private UserDAO userDAO;
    
    public CommentService() {
        this.commentDAO = new CommentDAO();
        this.userDAO = new UserDAO();
    }
    
    public void createComment(Comment comment) throws SQLException {
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Content is required");
        }
        
        commentDAO.insert(comment);
        
        // Add 3 points to the user
        userDAO.addPoints(comment.getUserId(), 3);
    }
    
    public List<Comment> getCommentsByTopicId(int topicId) throws SQLException {
        return commentDAO.findByTopicId(topicId);
    }
}
