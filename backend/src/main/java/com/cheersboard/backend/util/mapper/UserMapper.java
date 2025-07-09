package com.cheersboard.backend.util.mapper;

import com.cheersboard.backend.dto.user.CreateUserRequest;
import com.cheersboard.backend.dto.user.UserResponse;
import com.cheersboard.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(CreateUserRequest requestDto, String hashedPassword){
        if (requestDto == null || hashedPassword == null) return null;

        return new User(
                requestDto.getUsername(),
                hashedPassword,
                requestDto.getEmail()
        );
    }

    public UserResponse toResponse(User user){
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }

    public List<UserResponse> toResponseList(List<User> users){
        if (users == null) return null;

        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
