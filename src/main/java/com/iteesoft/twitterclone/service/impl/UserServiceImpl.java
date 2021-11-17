package com.iteesoft.twitterclone.service.impl;

import com.iteesoft.twitterclone.dto.UserDto;
import com.iteesoft.twitterclone.model.Post;
import com.iteesoft.twitterclone.model.User;
import com.iteesoft.twitterclone.repository.PostRepo;
import com.iteesoft.twitterclone.repository.UserRepo;
import com.iteesoft.twitterclone.service.UserService;
import com.iteesoft.twitterclone.service.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PostRepo postRepo;

    @Override
    public User create(UserDto userInfo) {
        User user = new User();
        user.setName(userInfo.getName());
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        user.setPassword(userInfo.getPassword());
        user.setEmail(userInfo.getEmail());
        user.setCreatedAt(Instant.now());
        userRepo.save(user);
        log.info(user.getName() + " created successfully");

        return user;
    }

    @Override
    public Post create(String message, Long id) {
        User user = userRepo.findById(id).orElseThrow(()->
                new AppException("User does not exist"));
        Post newPost = new Post();
        newPost.setMessage(message);
        newPost.setUser(user);
        newPost.setCreatedAt(Instant.now());
        postRepo.save(newPost);
        log.info("New post created by " + user.getName());
        return newPost;
    }

    @Override
    public List<Post> viewUserPosts(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->
                new AppException("User does not exist"));
        log.info("showing list of posts by " + user.getName());
        return postRepo.findByUserIdOrderByCreatedAtDesc(id);
    }

    @Transactional
    @Override
    public void follow(Long followerId, Long userId) {
        User user1 = userRepo.findById(followerId).orElseThrow(()->
                new AppException("User does not exist"));
        User user2 = userRepo.findById(userId).orElseThrow(()->
                new AppException("User does not exist"));

        user2.getFollowers().add(user1);
        userRepo.save(user2);
        user1.getFollowing().add(user2);
        userRepo.save(user1);
        log.info(user1.getName() + " followed " + user2.getName());
    }

    @Override
    public void unFollow(Long followerId, Long userId) {
        User user1 = userRepo.findById(followerId).orElseThrow(()->
                new AppException("User does not exist"));
        User user2 = userRepo.findById(userId).orElseThrow(()->
                new AppException("User does not exist"));

        user2.getFollowers().remove(user1);
        userRepo.save(user2);
        user1.getFollowing().remove(user2);
        userRepo.save(user1);
        log.info(user1.getName() + " unfollowed " + user2.getName());
    }

    @Override
    public List<User> viewFollowers(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->
                new AppException("User does not exist"));
        log.info("showing list of users following " + user.getName());
        return user.getFollowers();
    }

    @Override
    public List<User> viewFollowing(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->
                new AppException("User does not exist"));
        log.info("showing list of users followed by " + user.getName());
        return user.getFollowing();
    }

    @Override
    public List<Post> viewTimeline(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->
                new AppException("User does not exist"));
        List<Post> userPosts = viewUserPosts(id);
        List<Post> followingPosts = new ArrayList<>();

        for (User f : user.getFollowing()) {
            followingPosts.addAll(f.getPosts());
        }
        List<Post> timeline = new ArrayList<>(userPosts);
        timeline.addAll(followingPosts);
        log.info("showing timeline of " + user.getName());
        return timeline;
    }

    @Override
    public List<User> getUsers() {
        log.info("showing list of all users");
        return userRepo.findAll();
    }

    @Override
    public void likePost(Long userId, Long postId) {
        User user = userRepo.findById(userId).orElseThrow(()->
                new AppException("User does not exist"));
        Post post = postRepo.findById(postId).orElseThrow(()->
                new AppException("Post does not exist"));

        post.setLikes(post.getLikes()+1);
        postRepo.save(post);

        user.getLikedPosts().add(post);
        userRepo.save(user);
        log.info("Post liked by "+ user.getName());
    }

    @Override
    public void unlikePost(Long userId, Long postId) {
        User user = userRepo.findById(userId).orElseThrow(()->
                new AppException("User does not exist"));
        Post post = postRepo.findById(postId).orElseThrow(()->
                new AppException("Post does not exist"));

        post.setLikes(post.getLikes()-1);
        postRepo.save(post);

        user.getLikedPosts().add(post);
        userRepo.save(user);
        log.info("Post unliked by "+ user.getName());
    }
}
