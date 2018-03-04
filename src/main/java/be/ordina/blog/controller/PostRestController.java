package be.ordina.blog.controller;

import be.ordina.blog.model.Comment;
import be.ordina.blog.model.Post;
import be.ordina.blog.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private static Logger logger = LoggerFactory.getLogger(PostRestController.class);

    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(
            value = "Get all posts from all topics")
    @RequestMapping(method = GET)
    public List<Post> getAllPosts() {
        logger.info("Request to get all posts");
        return postService.getAllPosts();
    }

    @ApiOperation(
            value = "Get a post by id")
    @RequestMapping(value = "/{postId}", method = GET)
    public Post getPostById(@PathVariable long postId) {
        logger.info("Request to get post with id {}", postId);
        return postService.getPostById(postId);
    }

    @ApiOperation(
            value = "Update a post",
            notes = "id should be provided in the request body of the post")
    @RequestMapping(method = PUT)
    public void updatePost(@PathVariable long postId, @RequestBody Post post) {
        logger.info("Request to update post", postId);
        postService.updatePost(post);
    }

    @ApiOperation(
            value = "Update a post",
            notes = "postId from the URL is used to select the post")
    @RequestMapping(value = "/{postId}", method = PUT)
    public void updatePostById(@PathVariable long postId, @RequestBody Post post) {
        logger.info("Request to update post with id {}", postId);
        post.setId(postId);
        postService.updatePost(post);
    }

    @ApiOperation(
            value = "Deletes a post",
            notes = "This method is far less performing than /topics/{topicId}/posts/{postsId}. Use with caution.")
    @RequestMapping(value = "/{postId}", method = DELETE)
    public void deletePostById(@PathVariable long postId) {
        postService.deletePostById(postId);
    }

    @ApiOperation(
            value = "Get all comment of a post")
    @RequestMapping(value = "/{postId}/comments", method = GET)
    public List<Comment> getCommentsForPost(@PathVariable long postId) {
        return postService.getCommentsForPost(postId);
    }

    @ApiOperation(
            value = "Get a comment of a post")
    @RequestMapping(value = "/{postId}/comments/{commentId}", method = GET)
    public Comment getCommentForPost(@PathVariable long postId, @PathVariable long commentId) {
        return postService.getCommentForPost(commentId, postId);
    }

    @ApiOperation(
            value = "Create a new comment for a post")
    @RequestMapping(value = "/{postId}/comments", method = POST)
    public void addCommentToPost(@PathVariable long postId, @RequestBody Comment comment) {
        postService.addCommentToPost(comment, postId);
    }

    @ApiOperation(
            value = "Update a comment from a post",
            notes = "id should be provided in the request body comment")
    @RequestMapping(value = "/{postId}/comments", method = PUT)
    public void updateCommentForPost(@PathVariable long postId, @RequestBody Comment comment) {
        postService.updateCommentForPost(comment, postId);
    }

    @ApiOperation(
            value = "Update a comment from a post",
            notes = "commentId in URL will be used for selecting the comment")
    @RequestMapping(value = "/{postId}/comments/{commentId}", method = PUT)
    public void updateCommentForPost(@PathVariable long postId, @PathVariable long commentId, @RequestBody Comment comment) {
        comment.setId(commentId);
        postService.updateCommentForPost(comment, postId);
    }

    @ApiOperation(
            value = "Delete a comment from a post")
    @RequestMapping(value = "/{postId}/comments/{commentId}", method = DELETE)
    public void deleteCommentForPost(@PathVariable long postId, @PathVariable long commentId) {
        postService.deleteCommentForPost(commentId, postId);
    }
}
