package com.cheersboard.backend.util.mapper;

import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.model.Like;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LikeMapper {
    private final UserMapper userMapper;
    private final PinMapper pinMapper;

    public LikeMapper(UserMapper userMapper, PinMapper pinMapper){
        this.userMapper = userMapper;
        this.pinMapper = pinMapper;
    }

    public Like toEntity(User user, Pin pin){
        if (user == null || pin == null) return null;

        return new Like(
                user,
                pin
        );
    }

    public LikeResponse toResponse(Like like){
        if (like == null) return null;

        return new LikeResponse(
                like.getId(),
                like.getLikedAt(),
                userMapper.toResponse(like.getUser()),
                pinMapper.toResponse(like.getPin())
        );
    }

    public List<LikeResponse> toResponseList(List<Like> likes){
        if (likes == null) return null;

        return likes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
