package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.User;
import com.xcdm.livein.dto.UserDTO;
import com.xcdm.livein.entity.TempUser;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void sendEmail(String email, String code);

    String codeGenerator();

    void save(TempUser user);

    void save(User user);

    void addDataToTempDB(UserDTO userDTO, String code);

    TempUser getDataFromTempDB(String email);

    User getCurrentUser();


    User findByEmail(String email);

    void delete(User user);
}
