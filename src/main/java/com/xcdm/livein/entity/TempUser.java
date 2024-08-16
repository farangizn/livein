package com.xcdm.livein.entity;

import com.xcdm.livein.entity.abs.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xcdm.livein.enums.AccountType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TempUser extends BaseEntity implements UserDetails {


    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(name = "first_name", nullable = false, length = 150)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "last_login", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime lastLogin;

    @Column(name = "is_superuser")
    private Boolean isSuperUser;

    @Column(name = "is_staff")
    private Boolean isStaff;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "date_joined", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime dateJoined;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AccountType accountType;

    @Column(length = 100)
    private String avatar;

    private String code;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
