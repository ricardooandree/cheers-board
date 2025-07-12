package com.cheersboard.backend.dto.location;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateLocationRequest {
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
