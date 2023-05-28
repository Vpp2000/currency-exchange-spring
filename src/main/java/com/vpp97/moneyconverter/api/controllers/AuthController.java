package com.vpp97.moneyconverter.api.controllers;

import com.vpp97.moneyconverter.api.repositories.RoleRepository;
import com.vpp97.moneyconverter.api.repositories.UserRepository;
import com.vpp97.moneyconverter.api.services.AuthService;
import com.vpp97.moneyconverter.dto.request.LoginDto;
import com.vpp97.moneyconverter.dto.request.RegisterDto;
import com.vpp97.moneyconverter.dto.response.AuthResponseDTO;
import com.vpp97.moneyconverter.dto.response.RegisterResponse;
import com.vpp97.moneyconverter.entities.Role;
import com.vpp97.moneyconverter.entities.UserEntity;
import com.vpp97.moneyconverter.security.JWTGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Tag(name = "Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        AuthResponseDTO authResponseDTO = this.authService.login(loginDto);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterDto registerDto) {
        RegisterResponse registerResponse = this.authService.register(registerDto);
        return ResponseEntity.ok(registerResponse);
    }
}