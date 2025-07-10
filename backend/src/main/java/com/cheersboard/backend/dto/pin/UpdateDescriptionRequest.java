package com.cheersboard.backend.dto.pin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateDescriptionRequest {
    @NotBlank
    @Size(max = 200)
    private String description;
}
