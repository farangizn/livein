package com.xcdm.livein.service;

import com.xcdm.livein.dto.PasswordDTO;
import com.xcdm.livein.entity.User;
import com.xcdm.livein.interfaces.AuthService;
import com.xcdm.livein.repo.UserRepository;
import com.xcdm.livein.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public HttpEntity<?> changePassword(PasswordDTO passwordDTO) {
        String token = passwordDTO.getToken();
        if (!jwtUtil.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String username = jwtUtil.getUserName(token);

        if (!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        User user = (User) userDetails;
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}
