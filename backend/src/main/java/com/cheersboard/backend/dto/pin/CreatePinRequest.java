package com.cheersboard.backend.dto.pin;

import com.cheersboard.backend.model.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePinRequest {
    // TODO: Remove userId once JWT is integrated
    private Long userId;
    private Double latitude;
    private Double longitude;
    private String description;
}
