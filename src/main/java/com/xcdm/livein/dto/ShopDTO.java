package com.xcdm.livein.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ShopDTO {
    private Integer id;

    private String name;

    private String logo;

    private String banner;

    private String phone;

    private String email;

    private String description;

    private String instagram;

    private String telegram;

    private String facebook;

    private Integer ownerId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
