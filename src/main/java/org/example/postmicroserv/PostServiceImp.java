package org.example.postmicroserv;

import java.util.List;
import java.util.Optional;

public interface PostServiceImp {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Optional<Post> getPostById(Long id);
    Post updatePost(Long id, Post postDetails);
    void deletePost(Long id);
}