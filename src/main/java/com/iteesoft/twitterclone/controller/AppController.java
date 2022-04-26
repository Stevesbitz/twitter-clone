package com.iteesoft.twitterclone.controller;


import com.iteesoft.twitterclone.dto.UserDto;
import com.iteesoft.twitterclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AppController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> createAccount(@RequestBody UserDto userInfo) {
        return new ResponseEntity<>(userService.create(userInfo), HttpStatus.CREATED);
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<?> createPost(@PathVariable Long id, @RequestBody String message) {
        return new ResponseEntity<>(userService.create(message, id), HttpStatus.CREATED);
    }

    @PostMapping("/users/{id}/follow/{userId}")
    public ResponseEntity<?> follow(@PathVariable Long userId, @PathVariable Long id) {
        userService.follow(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{id}/unfollow/{userId}")
    public ResponseEntity<?> unfollow(@PathVariable Long userId, @PathVariable Long id) {
        userService.unFollow(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<?> viewPosts(@PathVariable Long id) {
        return new ResponseEntity<>(userService.viewUserPosts(id), OK);
    }

    @PostMapping("/users/{id}/Posts/{id}/like")
    public ResponseEntity<?> likePost(@RequestParam Long userId, @PathVariable Long id) {
        userService.likePost(userId, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{id}/Posts/{id}/unlike")
    public ResponseEntity<?> unlikePost(@RequestParam Long userId, @PathVariable Long id) {
        userService.likePost(userId, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/followers")
    public ResponseEntity<?> viewFollowers(@PathVariable Long id) {
        return new ResponseEntity<> (userService.viewFollowers(id), OK);
    }

    @GetMapping("/users/{id}/following")
    public ResponseEntity<?> viewFollowing(@PathVariable Long id) {
        return new ResponseEntity<> (userService.viewFollowing(id), OK);
    }

    @GetMapping("/users/{id}/timeline")
    public ResponseEntity<?> viewTimeline(@PathVariable Long id) {
        return new ResponseEntity<>(userService.viewTimeline(id), OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> viewAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), OK);
    }
}
