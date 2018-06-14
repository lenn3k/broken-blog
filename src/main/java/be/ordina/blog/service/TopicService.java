package be.ordina.blog.service;

import be.ordina.blog.model.Post;
import be.ordina.blog.model.Topic;
import be.ordina.blog.repository.PostRepository;
import be.ordina.blog.repository.TopicRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.security.Principal;

import javax.transaction.Transactional;
//import java.security.Principal;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class TopicService {

    private TopicRepository topicRepository;
    private PostRepository postRepository;

    public TopicService(TopicRepository topicRepository,
                        PostRepository postRepository) {
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    public void addTopic(Topic topic) {
        topic.setId(0);
        topic.setCreationTime(now());
        topicRepository.saveAndFlush(topic);
    }

    public Topic findTopicById(long id) {
        return topicRepository.findOne(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_admin')")
    public void deleteTopicById(long id) {
        topicRepository.delete(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_user')")
    public void addPostForTopic(Post post, long topicId) {
        post.setCreationTime(now());
        Topic topic = topicRepository.findOne(topicId);
        postRepository.save(post);
        topic.getPosts().add(post);
        topicRepository.saveAndFlush(topic);
    }

    public List<Post> getPostsForTopic(long id) {
        return topicRepository.findOne(id).getPosts();
    }

    @Transactional
    @PreAuthorize("#topic.getAuthor().equals(principal.name) OR hasRole('ROLE_admin')")
    public void updateTopic(Topic topic) {
        Topic persistedTopic = topicRepository.findOne(topic.getId());
        persistedTopic.setTitle(topic.getTitle());
        persistedTopic.setDescription(topic.getDescription());
        persistedTopic.setAuthor(topic.getAuthor());
        topicRepository.saveAndFlush(persistedTopic);
    }

    public Post getPostForTopic(long postId, long topicId) {
        Topic topic = topicRepository.getOne(topicId);
        return topic.getPosts().stream().filter(p->p.getId() == postId).findFirst().get();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_admin')")
    public void removePostForTopic(long postId, long topicId) {
        Topic topic = topicRepository.getOne(topicId);
        Post postToRemove = topic.getPosts().stream().filter(p->p.getId() == postId).findFirst().get();
        topic.getPosts().remove(postToRemove);
        topicRepository.saveAndFlush(topic);
    }

    @Transactional
    @PreAuthorize("#post.getAuthor().equals(principal.name) OR hasRole('ROLE_moderator')")
    public void updatePostForTopic(Post post, long topicId) {
        Topic topic = topicRepository.getOne(topicId);
        Post postToModify = topic.getPosts().stream().filter(p->p.getId() == post.getId()).findFirst().get();
        if(!StringUtils.isEmpty(post.getBody())) {
            postToModify.setBody(post.getBody());
        }
        if(!StringUtils.isEmpty(post.getTitle())) {
            postToModify.setTitle(post.getTitle());
        }
        if(!StringUtils.isEmpty(post.getAuthor())) {
            postToModify.setAuthor(post.getAuthor());
        }
        postRepository.saveAndFlush(postToModify);
    }
}
