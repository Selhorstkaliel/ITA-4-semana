package br.ita.forum.dao;

import br.ita.forum.model.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    
    public void insert(Topic topic) throws SQLException {
        String sql = "INSERT INTO topic (title, content, user_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, topic.getTitle());
            stmt.setString(2, topic.getContent());
            stmt.setInt(3, topic.getUserId());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    topic.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public Topic findById(int id) throws SQLException {
        String sql = "SELECT t.*, u.name as user_name FROM topic t " +
                    "INNER JOIN user u ON t.user_id = u.id " +
                    "WHERE t.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractTopicFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    public List<Topic> findAll() throws SQLException {
        String sql = "SELECT t.*, u.name as user_name FROM topic t " +
                    "INNER JOIN user u ON t.user_id = u.id " +
                    "ORDER BY t.created_at DESC";
        List<Topic> topics = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                topics.add(extractTopicFromResultSet(rs));
            }
        }
        return topics;
    }
    
    public void update(Topic topic) throws SQLException {
        String sql = "UPDATE topic SET title = ?, content = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, topic.getTitle());
            stmt.setString(2, topic.getContent());
            stmt.setInt(3, topic.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM topic WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    private Topic extractTopicFromResultSet(ResultSet rs) throws SQLException {
        Topic topic = new Topic();
        topic.setId(rs.getInt("id"));
        topic.setTitle(rs.getString("title"));
        topic.setContent(rs.getString("content"));
        topic.setUserId(rs.getInt("user_id"));
        topic.setUserName(rs.getString("user_name"));
        topic.setCreatedAt(rs.getTimestamp("created_at"));
        return topic;
    }
}
