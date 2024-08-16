package com.baeda.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequest(@Email @NotBlank @Length(max = 20) String email,
                                @NotBlank @Length(min = 2, max = 12) String nickname,
                                @NotBlank @Length(min = 8, max = 16)String password,
                                String team) {

}
