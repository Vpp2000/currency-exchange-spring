package com.vpp97.moneyconverter.api.services;

import com.vpp97.moneyconverter.api.repositories.RoleRepository;
import com.vpp97.moneyconverter.api.repositories.UserRepository;
import com.vpp97.moneyconverter.dto.request.LoginDto;
import com.vpp97.moneyconverter.dto.request.RegisterDto;
import com.vpp97.moneyconverter.dto.response.AuthResponseDTO;
import com.vpp97.moneyconverter.entities.Role;
import com.vpp97.moneyconverter.entities.UserEntity;
import com.vpp97.moneyconverter.exceptions.ElementNotFoundException;
import com.vpp97.moneyconverter.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public AuthResponseDTO login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return AuthResponseDTO.builder()
                .accessToken(token)
                .build();
    }

    public void register(RegisterDto registerDto){
        if (this.userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username already registered");
        }

        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode((registerDto.getPassword())))
                .build();

        Role roles = this.roleRepository.findByName("USER").orElseThrow(() -> new ElementNotFoundException("Role not found by name"));
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
    }
}
