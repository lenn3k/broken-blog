package be.ordina.blog.controller;

import be.ordina.blog.model.Post;
import be.ordina.blog.model.Topic;
import be.ordina.blog.service.TopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/v1/topics")
@CrossOrigin("*")
public class TopicRestController {

    private TopicService topicService;

    public TopicRestController(TopicService topicService) {
        this.topicService = topicService;
    }

    @ApiOperation(
            value = "Get all topics")
    @RequestMapping(method = GET)
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @ApiOperation(
            value = "Get a topic by id")
    @RequestMapping(value = "/{topicId}", method = GET)
    public Topic getTopicById(@PathVariable long topicId) {
        return topicService.findTopicById(topicId);
    }

    @ApiOperation(
            value = "Create a new topic")
    @RequestMapping(method = POST)
    public void addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
    }

    @ApiOperation(
            value = "update a topic",
            notes = "id is expected in the request body topic")
    @RequestMapping(method = PUT)
    public void updateTopic(@RequestBody Topic topic) {
        topicService.updateTopic(topic);
    }

    @ApiOperation(
            value = "Update a topic",
            notes = "topicId in the URL is used to select the topic")
    @RequestMapping(value="/{topicId}" , method = PUT)
    public void updateTopic(@PathVariable long topicId, @RequestBody Topic topic) {
        topic.setId(topicId);
        topicService.updateTopic(topic);
    }

    @ApiOperation(
            value = "Delete a topic")
    @RequestMapping(value="/{topicId}" ,method = DELETE)
    public void deleteTopicById(@PathVariable Long topicId) {
        topicService.deleteTopicById(topicId);
    }

    @ApiOperation(
            value = "Get all post of a topic")
    @RequestMapping(value="/{topicId}/posts", method = GET)
    public List<Post> getPostsForTopic(@PathVariable long topicId) {
        return topicService.getPostsForTopic(topicId);
    }

    @ApiOperation(
            value = "Get a post of a topic")
    @RequestMapping(value="/{topicId}/posts/{postId}", method = GET)
    public Post getPostForTopic(@PathVariable long topicId, @PathVariable long postId) {
        return topicService.getPostForTopic(postId, topicId);
    }

    @ApiOperation(
            value = "Create a new post for a topic")
    @RequestMapping(value="/{topicId}/posts", method = POST)
    public void addPostForTopic(@PathVariable long topicId, @RequestBody Post post) {
        topicService.addPostForTopic(post, topicId);
    }

    @ApiOperation(
            value = "Update a post from a topic",
            notes = "id is expected in the request body post")
    @RequestMapping(value="/{topicId}/posts", method = PUT)
    public void updatePostForTopic(@PathVariable long topicId, @RequestBody Post post) {
        topicService.updatePostForTopic(post, topicId);
    }

    @ApiOperation(
            value = "Update a post from a topic",
            notes = "postId from URL is used to select the post")
    @RequestMapping(value="/{topicId}/posts/{postId}", method = PUT)
    public void updatePostForTopic(@PathVariable long topicId, @PathVariable long postId, @RequestBody Post post) {
        post.setId(postId);
        topicService.updatePostForTopic(post, topicId);
    }

    @ApiOperation(
            value = "Delete a post from a topic")
    @RequestMapping(value = "/{topicId}/posts/{postId}", method = DELETE)
    public void deletePostForTopic(@PathVariable long topicId, @PathVariable long postId) {
        topicService.removePostForTopic(postId, topicId);
    }
}
