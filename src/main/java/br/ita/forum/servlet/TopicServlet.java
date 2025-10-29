package br.ita.forum.servlet;

import br.ita.forum.model.Comment;
import br.ita.forum.model.Topic;
import br.ita.forum.model.User;
import br.ita.forum.service.CommentService;
import br.ita.forum.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/topic")
public class TopicServlet extends HttpServlet {
    private TopicService topicService;
    private CommentService commentService;
    
    @Override
    public void init() throws ServletException {
        topicService = new TopicService();
        commentService = new CommentService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String topicIdParam = request.getParameter("id");
        if (topicIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/topics");
            return;
        }
        
        try {
            int topicId = Integer.parseInt(topicIdParam);
            Topic topic = topicService.getTopicById(topicId);
            
            if (topic == null) {
                response.sendRedirect(request.getContextPath() + "/topics");
                return;
            }
            
            List<Comment> comments = commentService.getCommentsByTopicId(topicId);
            
            request.setAttribute("topic", topic);
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("/WEB-INF/topic.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/topics");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/topic.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String topicIdParam = request.getParameter("topicId");
        String content = request.getParameter("content");
        
        try {
            int topicId = Integer.parseInt(topicIdParam);
            Comment comment = new Comment(content, topicId, user.getId());
            commentService.createComment(comment);
            
            response.sendRedirect(request.getContextPath() + "/topic?id=" + topicId);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/topics");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            doGet(request, response);
        }
    }
}
