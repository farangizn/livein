package com.xcdm.livein.repo;

import com.xcdm.livein.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            DELETE FROM order_item WHERE order_id IN (SELECT id FROM orders WHERE user_id = :userId);
            DELETE FROM orders WHERE user_id = :userId;
            DELETE FROM card WHERE user_id = :userId;
            DELETE FROM wish_list WHERE user_id = :userId;
            DELETE FROM users WHERE id = :userId;
            """)
    void deleteFully(Integer userId);

}