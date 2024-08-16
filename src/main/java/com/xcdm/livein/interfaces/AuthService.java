package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.PasswordDTO;
import com.xcdm.livein.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    HttpEntity<?> changePassword(PasswordDTO passwordDTO);
}
