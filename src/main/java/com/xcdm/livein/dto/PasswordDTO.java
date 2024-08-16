package com.xcdm.livein.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDTO {

    @NotNull
    private String token;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;
}
