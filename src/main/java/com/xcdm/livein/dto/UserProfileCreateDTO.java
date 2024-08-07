package com.xcdm.livein.dto;

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

    private String email;

    private String phone;

}
