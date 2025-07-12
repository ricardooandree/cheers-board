package com.cheersboard.backend.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private Long id;
    private Double latitude;
    private Double longitude;
    private int pinsCount;
}
