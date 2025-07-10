package com.cheersboard.backend.dto.pin;

import com.cheersboard.backend.dto.location.LocationResponse;
import com.cheersboard.backend.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinResponse {
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private UserResponse user;
    private LocationResponse location;
    private int likesCount;
}
