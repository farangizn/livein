package com.xcdm.livein.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileCreateDTO {

    private String firstName;

    private String lastName;

    @Email
    @NotNull
    private String email;

    private String phone;


}
