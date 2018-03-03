package be.ordina.blog.service;

import be.ordina.blog.repository.PostRepository;
import be.ordina.blog.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(long id) {
        return postRepository.findOne(id);
    }

    public void addPostToPost(Post post, Long id) {
        Post parentPost = postRepository.findOne(id);
        parentPost.getComments().add(post);
        postRepository.save(post);
        postRepository.save(parentPost);
    }

    public void deletePostById(Long id) {

    }
}
