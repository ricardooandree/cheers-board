package com.cheersboard.backend.dto.like;

import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private Long id;
    private LocalDateTime likedAt;
    private UserResponse user;
    private PinResponse pin;
}
