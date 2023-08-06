package com.auth.authRouting.service;

import com.auth.authRouting.dao.request.SignUpRequest;
import com.auth.authRouting.dao.request.SigninRequest;
import com.auth.authRouting.dao.response.AuthenticationResponse;
import com.auth.authRouting.mapper.UserMapper;
import com.auth.authRouting.model.Role;
import com.auth.authRouting.model.User;
import com.auth.authRouting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        long timeToExpirationInSeconds = jwtService.getTimeToExpiration(jwt) / 1000;
        System.out.println("Time to expiration in seconds: " + timeToExpirationInSeconds);

        return AuthenticationResponse.builder().token(jwt).user(UserMapper.getDto(user)).build();
    }

    public AuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        long timeToExpirationInSeconds = jwtService.getTimeToExpiration(jwt) / 1000;
        System.out.println("Time to expiration in seconds: " + timeToExpirationInSeconds);

        return AuthenticationResponse.builder().token(jwt).user(UserMapper.getDto(user)).build();
    }

    public void logout(String token) {
        jwtService.invalidateToken(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return !jwtService.isTokenExpired(token);
    }
}