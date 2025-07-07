package com.cheersboard.backend.controller;

import com.cheersboard.backend.dto.UpdateEmailRequest;
import com.cheersboard.backend.dto.UpdatePasswordRequest;
import com.cheersboard.backend.model.Like;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<User> updateUserEmail(@PathVariable("id") Long id, @Valid @RequestBody UpdateEmailRequest updateEmailRequest){
        User user = userService.updateUserEmail(id, updateEmailRequest.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> updateUserPassword(@PathVariable("id") Long id, @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        User user = userService.updateUserPassword(id, updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ---
    @GetMapping("/{id}/pins")
    public ResponseEntity<List<Pin>> getUserPins(@PathVariable("id") Long id){
        List<Pin> userPins = userService.getAllUserPins(id);

        return ResponseEntity.status(HttpStatus.OK).body(userPins);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<Like>> getUserLikes(@PathVariable("id") Long id){
        List<Like> userLikes = userService.getAllUserLikes(id);

        return ResponseEntity.status(HttpStatus.OK).body(userLikes);
    }
}
