package com.iteesoft.twitterclone.service.impl;

import com.iteesoft.twitterclone.dto.UserDto;
import com.iteesoft.twitterclone.exceptions.AlreadyExistsException;
import com.iteesoft.twitterclone.model.Follow;
import com.iteesoft.twitterclone.model.Like;
import com.iteesoft.twitterclone.model.Post;
import com.iteesoft.twitterclone.model.User;
import com.iteesoft.twitterclone.repository.FollowRepository;
import com.iteesoft.twitterclone.repository.LikeRepository;
import com.iteesoft.twitterclone.repository.PostRepository;
import com.iteesoft.twitterclone.repository.UserRepository;
import com.iteesoft.twitterclone.service.UserService;
import com.iteesoft.twitterclone.service.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final LikeRepository likeRepo;
    private final FollowRepository followRepo;

    @Override
    public User create(UserDto userInfo) {
        var userExist = userRepo.findByEmail(userInfo.getEmail()).isPresent();
        if (userExist) {
            throw new AlreadyExistsException("User already exist with email "+userInfo.getEmail());
        }
        User user = User.builder().name(userInfo.getName()).username(userInfo.getUsername())
                .password(userInfo.getPassword()).email(userInfo.getEmail()).build();
        userRepo.save(user);
        log.info(user.getName() + " created successfully");
        return user;
    }

    @Override
    public Post create(String message, Long id) {
        User user = userRepo.findById(id).orElseThrow(()-> new AppException("User does not exist"));
        Post newPost = Post.builder().message(message).user(user).build();
        postRepo.save(newPost);
        log.info("New post created by " + user.getName());
        return newPost;
    }

    @Override
    public List<Post> viewUserPosts(Long id) {
        User user = userRepo.findById(id).orElseThrow(()-> new AppException("User does not exist"));
        log.info("showing list of posts of " + user.getName());
        return postRepo.findByUserIdOrderByCreatedAtDesc(id);
    }

    @Transactional
    @Override
    public void follow(Long followerId, Long userId) {
        Optional<Follow> mapping = getFollowMapping(followerId, userId);

        if (mapping.isEmpty() && !followerId.equals(userId)) {
            Follow newFollow = new Follow(getUser(followerId), getUser(userId));
            followRepo.save(newFollow);
        }
        log.info("user: "+followerId + " followed user: " + userId);
    }

    @Override
    public void unFollow(Long followerId, Long userId) {
        getFollowMapping(followerId, userId).ifPresent(followRepo::delete);
        log.info("user: "+followerId + " unfollowed user: " + userId);
    }

    private Optional<Follow> getFollowMapping(Long followerId, Long userId) {
        User follower = userRepo.findById(followerId).orElseThrow(()-> new AppException("User does not exist"));
        User followed = userRepo.findById(userId).orElseThrow(()-> new AppException("User does not exist"));
        return followRepo.findByFollowerAndFollowed(follower, followed);
    }

    private User getUser(Long id) {
        return userRepo.findById(id).orElseThrow(()-> new AppException("User does not exist"));
    }

    @Override
    public List<User> viewFollowers(Long id) {
        User user = userRepo.findById(id).orElseThrow(()-> new AppException("User does not exist"));
        log.info("showing list of users following " + user.getName());
        List<Follow> allByFollowerId = followRepo.findAllByFollowerId(id);
        return allByFollowerId.stream().map(Follow::getFollowed).collect(Collectors.toList());
    }

    @Override
    public List<User> viewFollowing(Long id) {
        User user = userRepo.findById(id).orElseThrow(()-> new AppException("User does not exist"));
        log.info("showing list of users followed by " + user.getName());
        List<Follow> allByFollowerId = followRepo.findAllByFollowerId(id);
        return allByFollowerId.stream().map(Follow::getFollower).collect(Collectors.toList());
    }

    @Override
    public List<Post> viewTimeline(Long id) {

        User user = userRepo.findById(id).orElseThrow(()-> new AppException("User does not exist"));
        var userPosts = viewUserPosts(id);
        List<Post> followingPosts = new ArrayList<>();
        var following = viewFollowing(id);

        for (User f : following) {
            followingPosts.addAll(postRepo.findByUserIdOrderByCreatedAtDesc(f.getId()));
        }
        var timeline = new ArrayList<>(userPosts);
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
        User user = userRepo.findById(userId).orElseThrow(()-> new AppException("User does not exist"));
        Post post = postRepo.findById(postId).orElseThrow(()-> new AppException("Post does not exist"));
        Optional<Like> existingLike = likeRepo.findByUserAndPost(user, post);
        if (existingLike.isEmpty()) {
            Like newLike = Like.builder().user(user).post(post).build();
            likeRepo.save(newLike);
        }
        log.info("Post liked by "+ user.getName());
    }

    @Override
    public void unlikePost(Long userId, Long postId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new AppException("User does not exist"));
        Post post = postRepo.findById(postId).orElseThrow(()-> new AppException("Post does not exist"));

        Optional<Like> existingLike = likeRepo.findByUserAndPost(user, post);
        existingLike.ifPresent(likeRepo::delete);
        log.info("Post unliked by "+ user.getName());
    }
}
