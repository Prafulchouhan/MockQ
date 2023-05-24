package com.server.mock.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationBody {

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String userName;

    @NotNull
    @Email
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6,max = 32)
    //Minimum 6 characters, at least one letter and one number
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String password;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    private String phone;

    private Boolean enable = true;

    private String profile;

}
