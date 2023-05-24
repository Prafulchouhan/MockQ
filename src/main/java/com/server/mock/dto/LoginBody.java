package com.server.mock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody {
    @NotBlank
    @NotNull
    private String userName;
    @NotBlank
    @NotNull
    private String password;
}
