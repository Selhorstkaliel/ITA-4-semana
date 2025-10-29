package br.ita.forum.dao;

import br.ita.forum.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    
    public void insert(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (content, topic_id, user_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, comment.getContent());
            stmt.setInt(2, comment.getTopicId());
            stmt.setInt(3, comment.getUserId());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    comment.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public Comment findById(int id) throws SQLException {
        String sql = "SELECT c.*, u.name as user_name FROM comment c " +
                    "INNER JOIN user u ON c.user_id = u.id " +
                    "WHERE c.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractCommentFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    public List<Comment> findByTopicId(int topicId) throws SQLException {
        String sql = "SELECT c.*, u.name as user_name FROM comment c " +
                    "INNER JOIN user u ON c.user_id = u.id " +
                    "WHERE c.topic_id = ? " +
                    "ORDER BY c.created_at ASC";
        List<Comment> comments = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, topicId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(extractCommentFromResultSet(rs));
                }
            }
        }
        return comments;
    }
    
    public void update(Comment comment) throws SQLException {
        String sql = "UPDATE comment SET content = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, comment.getContent());
            stmt.setInt(2, comment.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM comment WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    private Comment extractCommentFromResultSet(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setContent(rs.getString("content"));
        comment.setTopicId(rs.getInt("topic_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setUserName(rs.getString("user_name"));
        comment.setCreatedAt(rs.getTimestamp("created_at"));
        return comment;
    }
}
