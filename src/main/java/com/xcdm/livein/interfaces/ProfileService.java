package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.UserProfileCreateDTO;
import com.xcdm.livein.entity.Product;
import com.xcdm.livein.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProfileService {
    void updateProfile(User user, UserProfileCreateDTO userProfileCreateDTO);

    User saveProfile(UserProfileCreateDTO userProfileCreateDTO);

    Optional<Product> findById(Integer productId);
}
