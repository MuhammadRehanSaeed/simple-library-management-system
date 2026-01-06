package com.rehancode.library_ms.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    @NotNull
    @Email
    private String email;
    @NotBlank
    @NotNull
    private String password;

}
