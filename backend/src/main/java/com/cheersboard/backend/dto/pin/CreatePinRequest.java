package com.cheersboard.backend.dto.pin;

import com.cheersboard.backend.model.Location;
import lombok.Getter;

@Getter
public class CreatePinRequest {
    private Long userId;
    private Location location;
    private String description;
}
