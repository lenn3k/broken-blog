package be.ordina.blog.service;

import be.ordina.blog.model.Post;
import be.ordina.blog.model.Topic;
import be.ordina.blog.repository.PostRepository;
import be.ordina.blog.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
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
        return topicRepository.findById(id).get();
    }

    @Transactional
    public void deleteTopicById(long id) {
        topicRepository.deleteById(id);
    }

    @Transactional
    public void addPostForTopic(Post post, long topicId) {
        post.setCreationTime(now());
        Topic topic = topicRepository.findById(topicId).get();
        postRepository.save(post);
        topic.getPosts().add(post);
        topicRepository.saveAndFlush(topic);
    }

    public List<Post> getPostsForTopic(long id) {
        return topicRepository.findById(id).get().getPosts();
    }

    @Transactional
    public void updateTopic(Topic topic) {
        Topic persistedTopic = topicRepository.findById(topic.getId()).get();
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
    public void removePostForTopic(long postId, long topicId) {
        Topic topic = topicRepository.getOne(topicId);
        Post postToRemove = topic.getPosts().stream().filter(p->p.getId() == postId).findFirst().get();
        topic.getPosts().remove(postToRemove);
        topicRepository.saveAndFlush(topic);
    }

    @Transactional
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
