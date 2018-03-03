package be.ordina.blog.controller;

import be.ordina.blog.model.Post;
import be.ordina.blog.model.Topic;
import be.ordina.blog.service.TopicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @RequestMapping(method = GET)
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @RequestMapping(method = POST)
    public void addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Topic getTopicById(@PathVariable Long id) {
        return topicService.findTopicById(id);
    }

    @RequestMapping(value="/{id}" ,method = DELETE)
    public void deleteTopicById(@PathVariable Long id) {
        topicService.deleteTopicById(id);
    }

    @RequestMapping(value="/{topicId}/posts", method = GET)
    public void getPostsForTopic(@PathVariable long topicId, @PathVariable long postId, @RequestBody Post post) {
        topicService.addPostToTopic(topicId, post);
    }

    @RequestMapping(value="/{topicId}/posts", method = POST)
    public void addPostToTopic(@PathVariable long topicId, @RequestBody Post post) {
        topicService.addPostToTopic(topicId, post);
    }
}
