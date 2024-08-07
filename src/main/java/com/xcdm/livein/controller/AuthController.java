package com.xcdm.livein.controller;

import com.xcdm.livein.dto.CodeDTO;
import com.xcdm.livein.dto.LoginDTO;
import com.xcdm.livein.dto.TokenDTO;
import com.xcdm.livein.dto.UserDTO;
import com.xcdm.livein.entity.TempUser;
import com.xcdm.livein.interfaces.CartService;
import com.xcdm.livein.interfaces.ProfileService;
import com.xcdm.livein.interfaces.UserService;
import com.xcdm.livein.mappers.UserMapper;
import com.xcdm.livein.security.CustomUserDetailsService;
import com.xcdm.livein.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final CartService cartService;
    private final UserMapper userMapper;
    private final ProfileService profileService;

    //-------------------------------------//

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO("Bearer " + jwtUtil.generateAccessToken(userDetails), "Bearer " + jwtUtil.generateRefreshToken(loginDTO)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> accessTokenGeneratorUsingRefreshToken() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("Bearer " + jwtUtil.generateAccessToken(userDetails));
    }

    @PostMapping("/signup")
    public void getInfoAndSendCode(@RequestBody UserDTO userDTO) {
        String code = userService.codeGenerator();
        userService.sendEmail(userDTO.getEmail(), code);
        userService.addDataToTempDB(userDTO, code);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> getCodeAndVerify(@RequestBody CodeDTO codeDTO) {
        String email = codeDTO.getEmail();
        String code = codeDTO.getCode();
        TempUser tempUser = userService.getDataFromTempDB(email);

        if (code.equals(tempUser.getCode())) {
            userService.save(tempUser);
            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }
    }
}
