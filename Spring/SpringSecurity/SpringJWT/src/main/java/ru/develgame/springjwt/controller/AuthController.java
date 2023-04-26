package ru.develgame.springjwt.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develgame.springjwt.domain.JwtResponse;
import ru.develgame.springjwt.domain.RefreshJwtRequest;
import ru.develgame.springjwt.security.AuthService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @GetMapping("google")
    public ResponseEntity<JwtResponse> exchange(@Autowired NetHttpTransport transport, @Autowired GsonFactory factory, HttpServletRequest request)
            throws GeneralSecurityException, IOException, IllegalAccessException {
        String token = this.getTokenFromRequest(request);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, factory)
                .setAudience(Collections.singletonList(""))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            throw new IllegalAccessException("Invalid id_token");
        }

        return ResponseEntity.ok(authService.loginViaOauth2(idToken.getPayload().getSubject()));
    }

    public String getTokenFromRequest(HttpServletRequest request) throws IllegalAccessException {
        String token = request.getHeader("Authorization");
        String[] parts = token.split(" ");
        if (parts.length != 2 || !parts[0].contains("Bearer")) {
            throw new IllegalAccessException("Authorization Bearer format invalid. <Bearer {token}>");
        }
        return parts[1];
    }
}
