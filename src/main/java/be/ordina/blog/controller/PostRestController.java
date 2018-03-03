package be.ordina.blog.controller;

import be.ordina.blog.model.Post;
import be.ordina.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/posts")
public class PostRestController {

    private static Logger logger = LoggerFactory.getLogger(PostRestController.class);

    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = GET)
    public List<Post> getAllPosts() {
        logger.info("Request to get all posts");
        return postService.getAllPosts();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Post getPostById(@PathVariable long id) {
        logger.info("Request to get post with id {}", id);
        return postService.getPostById(id);
    }

    @RequestMapping(value = "/{id}/posts", method = POST)
    public void addPostToPost(@PathVariable Long id, @RequestBody Post post) {
        postService.addPostToPost(post, id);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void addPostToPost(@PathVariable Long id) {
        postService.deletePostById(id);
    }
}