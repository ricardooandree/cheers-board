package com.cheersboard.backend.service;

import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.dto.user.CreateUserRequest;
import com.cheersboard.backend.dto.user.UpdateEmailRequest;
import com.cheersboard.backend.dto.user.UpdatePasswordRequest;
import com.cheersboard.backend.dto.user.UserResponse;
import com.cheersboard.backend.exception.DuplicateResourceException;
import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.repository.UserRepository;
import com.cheersboard.backend.util.mapper.LikeMapper;
import com.cheersboard.backend.util.mapper.PinMapper;
import com.cheersboard.backend.util.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PinMapper pinMapper;
    private final LikeMapper likeMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PinMapper pinMapper,
                       LikeMapper likeMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pinMapper = pinMapper;
        this.likeMapper = likeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * CREATE Methods
     */
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new DuplicateResourceException("Username already taken");
        }

        if (userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new DuplicateResourceException("Email already taken");
        }

        String password = createUserRequest.getPassword();

        User newUser = userMapper.toEntity(createUserRequest, passwordEncoder.encode(password));
        userRepository.save(newUser);

        return userMapper.toResponse(newUser);
    }

    /**
     * GET Methods
     */
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    public UserResponse getUserByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();

        return userMapper.toResponseList(users);
    }

    public List<PinResponse> getUserPins(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getPins().stream()
                .map(pinMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<LikeResponse> getUserLikes(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getLikes().stream()
                .map(likeMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE Methods
     */
    public UserResponse updateUserEmail(Long id, UpdateEmailRequest updateEmailRequest){
        String newEmail = updateEmailRequest.getEmail();

        if (userRepository.existsByEmail(newEmail)){
            throw new DuplicateResourceException("Email already taken");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEmail(newEmail);
        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    public UserResponse updateUserPassword(Long id, UpdatePasswordRequest updatePasswordRequest){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // TODO: Verify that the old password matches the stored hash one!
        // TODO: Hash + Salt the password!
        String newPassword = updatePasswordRequest.getNewPassword();
        user.setPasswordHash(newPassword);

        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    /**
     * DELETE Methods
     */
    public void deleteUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }
}
