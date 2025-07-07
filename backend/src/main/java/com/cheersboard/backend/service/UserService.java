package com.cheersboard.backend.service;

import com.cheersboard.backend.exception.DuplicateResourceException;
import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.Like;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * CREATE Methods
     */
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already taken");
        }

        if (userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateResourceException("Email already taken");
        }

        return userRepository.save(user);
    }

    /**
     * GET Methods
     */
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<Pin> getAllUserPins(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getPins();
    }

    public List<Like> getAllUserLikes(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getLikes();
    }

    /**
     * UPDATE Methods
     */
    public User updateUserEmail(Long id, String newEmail){
        if (userRepository.existsByEmail(newEmail)){
            throw new DuplicateResourceException("Email already taken");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    public User updateUserPassword(Long id, String oldPassword, String newPassword){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // TODO: Verify that the old password matches the stored hash one!
        // TODO: Hash + Salt the password!
        user.setPasswordHash(newPassword);
        return userRepository.save(user);
    }

    /**
     * DELETE Methods
     */
    public void deleteUserById(Long id){
        if (!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }
}
