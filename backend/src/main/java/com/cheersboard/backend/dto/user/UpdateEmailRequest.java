package com.cheersboard.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateEmailRequest {
    @NotBlank
    @Email
    private String email;

}
