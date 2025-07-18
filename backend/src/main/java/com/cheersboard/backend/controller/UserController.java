package com.cheersboard.backend.controller;

import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.dto.user.CreateUserRequest;
import com.cheersboard.backend.dto.user.UpdateEmailRequest;
import com.cheersboard.backend.dto.user.UpdatePasswordRequest;
import com.cheersboard.backend.dto.user.UserResponse;
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

    /**
     * CREATE Routes
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        UserResponse createdUser = userService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * GET Routes
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id){
        UserResponse user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){
        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}/pins")
    public ResponseEntity<List<PinResponse>> getUserPins(@PathVariable("id") Long id){
        List<PinResponse> userPins = userService.getUserPins(id);

        return ResponseEntity.status(HttpStatus.OK).body(userPins);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<LikeResponse>> getUserLikes(@PathVariable("id") Long id){
        List<LikeResponse> userLikes = userService.getUserLikes(id);

        return ResponseEntity.status(HttpStatus.OK).body(userLikes);
    }

    /**
     * UPDATE Routes
     */
    @PutMapping("/{id}/email")
    public ResponseEntity<UserResponse> updateUserEmail(@PathVariable("id") Long id,
                                                        @Valid @RequestBody UpdateEmailRequest updateEmailRequest){
        UserResponse user = userService.updateUserEmail(id, updateEmailRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponse> updateUserPassword(@PathVariable("id") Long id,
                                                           @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        UserResponse user = userService.updateUserPassword(id, updatePasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * DELETE Routes
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
