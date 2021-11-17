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

    @PostMapping("/newAccount")
    public ResponseEntity<?> createAccount(@RequestBody UserDto userInfo) {
        return new ResponseEntity<>(userService.create(userInfo), HttpStatus.CREATED);
    }

    @PostMapping("/newPost/{id}")
    public ResponseEntity<?> createPost(@PathVariable Long id, @RequestBody String message) {
        return new ResponseEntity<>(userService.create(message, id), HttpStatus.CREATED);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> follow(@RequestParam Long userId, @PathVariable Long id) {
        userService.follow(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfollow/{id}")
    public ResponseEntity<?> unfollow(@RequestParam Long userId, @PathVariable Long id) {
        userService.unFollow(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> viewPosts(@PathVariable Long id) {
        return new ResponseEntity<>(userService.viewUserPosts(id), OK);
    }

    @PostMapping("/likePost/{id}")
    public ResponseEntity<?> likePost(@RequestParam Long userId, @PathVariable Long id) {
        userService.likePost(userId, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlikePost/{id}")
    public ResponseEntity<?> unlikePost(@RequestParam Long userId, @PathVariable Long id) {
        userService.likePost(userId, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/{id}")
    public ResponseEntity<?> viewFollowers(@PathVariable Long id) {
        return new ResponseEntity<> (userService.viewFollowers(id), OK);
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<?> viewFollowing(@PathVariable Long id) {
        return new ResponseEntity<> (userService.viewFollowing(id), OK);
    }

    @GetMapping("/timeline/{id}")
    public ResponseEntity<?> viewTimeline(@PathVariable Long id) {
        return new ResponseEntity<>(userService.viewTimeline(id), OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> viewAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), OK);
    }
}
