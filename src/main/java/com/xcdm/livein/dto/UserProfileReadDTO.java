package com.xcdm.livein.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.enums.AccountType;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileReadDTO {

    private Integer id;

    private ZonedDateTime lastLogin;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean isActive;

    private ZonedDateTime dateJoined;

    private String avatar;

    private String phone;

    private AccountType accountType;
}