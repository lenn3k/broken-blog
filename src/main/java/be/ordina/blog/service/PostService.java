package be.ordina.blog.service;

import be.ordina.blog.model.Comment;
import be.ordina.blog.model.Post;
import be.ordina.blog.repository.CommentRepository;
import be.ordina.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class PostService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(long id) {
        return postRepository.findOne(id);
    }

    public void addCommentToPost(Comment comment, Long id) {
        comment.setCreationTime(now());
        Post parentPost = postRepository.findOne(id);
        parentPost.getComments().add(comment);
        commentRepository.save(comment);
        postRepository.save(parentPost);
    }

    public void deletePostById(Long id) {
        postRepository.delete(id);
    }
}
