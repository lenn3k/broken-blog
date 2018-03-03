package be.ordina.blog.service;

import be.ordina.blog.model.Post;
import be.ordina.blog.model.Topic;
import be.ordina.blog.repository.PostRepository;
import be.ordina.blog.repository.TopicRepository;
import org.springframework.stereotype.Service;

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

    public void addTopic(Topic topic) {
        topic.setCreationTime(now());
        topicRepository.saveAndFlush(topic);
    }

    public Topic findTopicById(long id) {
        return topicRepository.findOne(id);
    }

    public void deleteTopicById(long id) {
        topicRepository.delete(id);
    }

    public void addPostToTopic(long topicId, Post post) {
        post.setCreationTime(now());
        Topic topic = topicRepository.findOne(topicId);
        postRepository.save(post);
        topic.getPosts().add(post);
        topicRepository.saveAndFlush(topic);
    }
}
