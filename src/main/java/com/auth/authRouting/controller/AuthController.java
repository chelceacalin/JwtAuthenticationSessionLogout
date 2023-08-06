package com.auth.authRouting.controller;


import com.auth.authRouting.dao.request.SignUpRequest;
import com.auth.authRouting.dao.request.SigninRequest;
import com.auth.authRouting.dao.response.AuthenticationResponse;
import com.auth.authRouting.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin()
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            authenticationService.logout(token);
            return ResponseEntity.ok("Logout successful.");
        }
        return ResponseEntity.badRequest().body("Invalid token.");
    }

    // Helper method to extract token from the request
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }


    @GetMapping("/istokenvalid/{token}")
    public Boolean istokenValid(@PathVariable String token){
        return  authenticationService.isTokenValid(token,null);
    }
}