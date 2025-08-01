package com.cheersboard.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    @Size(min = 8, max = 50)
    private String username;

    @NotBlank
    @Size(min = 8, max = 150)
    private String password;

    @NotBlank
    @Email
    private String email;
}
