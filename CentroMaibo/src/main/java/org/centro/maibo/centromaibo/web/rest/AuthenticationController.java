package org.centro.maibo.centromaibo.web.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.centro.maibo.centromaibo.dto.JWTToken;
import org.centro.maibo.centromaibo.dto.LoginRequestDTO;
import org.centro.maibo.centromaibo.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<JWTToken> authenticate(@RequestBody LoginRequestDTO login) {
        return authenticationService.authenticate(login);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTToken> refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}
