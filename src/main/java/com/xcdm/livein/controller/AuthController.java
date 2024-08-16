package com.xcdm.livein.controller;

import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.xcdm.livein.security.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.xcdm.livein.interfaces.UserService;
import com.xcdm.livein.repo.CartRepository;
import com.xcdm.livein.repo.UserRepository;
import org.springframework.http.HttpStatus;
import com.xcdm.livein.entity.TempUser;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.utils.JwtUtil;
import com.xcdm.livein.dto.LoginDTO;
import com.xcdm.livein.dto.TokenDTO;
import com.xcdm.livein.dto.CodeDTO;
import com.xcdm.livein.dto.UserDTO;
import com.xcdm.livein.entity.Cart;
import com.xcdm.livein.entity.User;
import java.time.ZonedDateTime;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        updateLastLogin(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO("Bearer " + jwtUtil.generateAccessToken(userDetails), "Bearer " + jwtUtil.generateRefreshToken(loginDTO)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> accessTokenGeneratorUsingRefreshToken() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("Bearer " + jwtUtil.generateAccessToken(userDetails));
    }

    @PostMapping("/signup")
    public HttpEntity<?> getInfoAndSendCode(@RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        } else {
            String code = userService.codeGenerator();
            userService.sendEmail(userDTO.getEmail(), code);
            userService.addDataToTempDB(userDTO, code);
            return ResponseEntity.ok("Email sent to " + userDTO.getEmail());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> getCodeAndVerify(@RequestBody CodeDTO codeDTO) {

        String email = codeDTO.getEmail();
        String code = codeDTO.getCode();

        TempUser tempUser = userService.getDataFromTempDB(email);

        if (code.equals(tempUser.getCode())) {
            userService.save(tempUser);
            tempUser.setDateJoined(ZonedDateTime.now());
            createCart();
            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }
    }

    private void createCart() {
        Cart cart = Cart.builder().user(userService.getCurrentUser()).isOrdered(false).build();
        cartRepository.save(cart);
    }

    private void updateLastLogin(UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        user.setLastLogin(ZonedDateTime.now());
        userRepository.save(user);
    }
}
