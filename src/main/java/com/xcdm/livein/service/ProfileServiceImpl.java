package com.xcdm.livein.service;

import com.xcdm.livein.entity.Product;
import com.xcdm.livein.interfaces.ProfileService;
import com.xcdm.livein.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.dto.UserProfileCreateDTO;
import com.xcdm.livein.entity.User;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProductRepository productRepository;

    @Override
    public void updateProfile(User user, UserProfileCreateDTO userProfileCreateDTO) {

        if (userProfileCreateDTO.getEmail() != null) {
            user.setEmail(userProfileCreateDTO.getEmail());
        }
        if (userProfileCreateDTO.getPhone() != null) {
            user.setPhone(userProfileCreateDTO.getPhone());
        }
        if (userProfileCreateDTO.getFirstName() != null) {
            user.setFirstName(userProfileCreateDTO.getFirstName());
        }
        if (userProfileCreateDTO.getLastName() != null) {
            user.setLastName(userProfileCreateDTO.getLastName());
        }
    }

    @Override
    public User saveProfile(UserProfileCreateDTO userProfileCreateDTO) {
        return User.builder()
                .firstName(userProfileCreateDTO.getFirstName())
                .lastName(userProfileCreateDTO.getLastName())
                .email(userProfileCreateDTO.getEmail())
                .phone(userProfileCreateDTO.getPhone())
                .isSuperUser(false)
                .dateJoined(ZonedDateTime.now())
                .lastLogin(ZonedDateTime.now())
                .build();
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }
}
