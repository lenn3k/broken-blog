package be.ordina.blog.service;

import be.ordina.blog.model.Comment;
import be.ordina.blog.model.Ownable;
import be.ordina.blog.model.Post;
import be.ordina.blog.model.Topic;
import be.ordina.blog.repository.CommentRepository;
import be.ordina.blog.repository.PostRepository;
import be.ordina.blog.repository.TopicRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.security.Principal;
import javax.transaction.Transactional;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class PostService {

    private TopicRepository topicRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.topicRepository = topicRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(long id) {
        return postRepository.findOne(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_user')")
    public void addCommentToPost(Comment comment, Long id) {
        comment.setCreationTime(now());
        Post parentPost = postRepository.findOne(id);
        parentPost.getComments().add(comment);
        commentRepository.save(comment);
        postRepository.save(parentPost);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_moderator')")
    public void deletePostById(long postId) {
        List<Topic> allTopics = topicRepository.findAll();
        // Terrible performance. It' the worst performance. Huuuuge performance loss. Like you wouldn't believe
        Topic parentTopic = allTopics.stream().filter(t->t.getPosts().stream().filter(p->p.getId() == postId).count() > 0).findFirst().get();
        Post postToRemove = parentTopic.getPosts().stream().filter(p->p.getId() == postId).findFirst().get();
        parentTopic.getPosts().remove(postToRemove);
        topicRepository.saveAndFlush(parentTopic);
        postRepository.delete(postId);
    }

    @Transactional
    @PreAuthorize("#comment.getAuthor().equals(principal.name) OR hasRole('ROLE_moderator')")
    public void updateCommentForPost(Comment comment, long postId) {
        Post post = postRepository.getOne(postId);
        Comment commentToModify = post.getComments().stream().filter(c->c.getId() == comment.getId()).findFirst().get();
        commentToModify.setBody(comment.getBody());
        commentToModify.setAuthor(comment.getAuthor());
        commentRepository.save(commentToModify);
    }

    public List<Comment> getCommentsForPost(long postId) {
        return postRepository.getOne(postId).getComments();
    }

    @Transactional
    @PreAuthorize("#post.getAuthor().equals(principal.name) OR hasRole('ROLE_moderator')")
    public void updatePost(Post post) {
        Post postToModify = postRepository.getOne(post.getId());
        if(!StringUtils.isEmpty(post.getTitle())) {
            postToModify.setTitle(post.getTitle());
        }
        if(!StringUtils.isEmpty(post.getBody())) {
            postToModify.setBody(post.getBody());
        }
        if(!StringUtils.isEmpty(post.getAuthor())) {
            postToModify.setAuthor(post.getAuthor());
        }
        postRepository.saveAndFlush(postToModify);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_admin')")
    public void deleteCommentForPost(long commentId, long postId) {
        Post post = postRepository.getOne(postId);
        Comment comment = post.getComments().stream().filter(c->c.getId() == commentId).findFirst().get();
        post.getComments().remove(comment);
        postRepository.saveAndFlush(post);
        commentRepository.delete(comment);
    }

    public Comment getCommentForPost(long commentId, long postId) {
        Post post = postRepository.getOne(postId);
        return post.getComments().stream().filter(c->c.getId() == commentId).findFirst().get();
    }

    public boolean sameUser(Principal principal , Ownable ownable){
        return principal.getName().equals(ownable.getAuthor());
    }
}
