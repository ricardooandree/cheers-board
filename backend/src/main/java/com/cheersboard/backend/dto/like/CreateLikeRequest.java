package com.cheersboard.backend.dto.like;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateLikeRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long pinId;
}
