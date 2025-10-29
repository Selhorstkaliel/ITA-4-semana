package br.ita.forum.service;

import br.ita.forum.dao.TopicDAO;
import br.ita.forum.dao.UserDAO;
import br.ita.forum.model.Topic;

import java.sql.SQLException;
import java.util.List;

public class TopicService {
    private TopicDAO topicDAO;
    private UserDAO userDAO;
    
    public TopicService() {
        this.topicDAO = new TopicDAO();
        this.userDAO = new UserDAO();
    }
    
    public void createTopic(Topic topic) throws SQLException {
        if (topic.getTitle() == null || topic.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (topic.getContent() == null || topic.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Content is required");
        }
        
        topicDAO.insert(topic);
        
        // Add 10 points to the user
        userDAO.addPoints(topic.getUserId(), 10);
    }
    
    public Topic getTopicById(int id) throws SQLException {
        return topicDAO.findById(id);
    }
    
    public List<Topic> getAllTopics() throws SQLException {
        return topicDAO.findAll();
    }
}
