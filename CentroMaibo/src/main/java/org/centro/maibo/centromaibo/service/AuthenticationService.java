package org.centro.maibo.centromaibo.service;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.config.JwtUtil;
import org.centro.maibo.centromaibo.domain.User;
import org.centro.maibo.centromaibo.dto.JWTToken;
import org.centro.maibo.centromaibo.dto.LoginRequestDTO;
import org.centro.maibo.centromaibo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<JWTToken> authenticate(LoginRequestDTO login) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = userRepository.findByEmail(login.getEmail());
        List<String> roles =                 Arrays.stream(userDetails.getRole().getPermissions())
                .map(String::new)
                .collect(Collectors.toList());

        String accessToken = jwtUtil.generateTokenWithRoles(authentication, roles, userDetails.getId().toString());

        return ResponseEntity.ok(new JWTToken(accessToken));
    }

    public JWTToken refreshToken(String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            User userDetails = userRepository.findByEmail(username);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getEmail(),
                    null,
                    Arrays.stream(userDetails.getRole().getPermissions())
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );

            String newAccessToken = jwtUtil.generateToken(authentication);
            return new JWTToken(newAccessToken);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
