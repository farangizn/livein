package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    User getCurrentUser();

}
