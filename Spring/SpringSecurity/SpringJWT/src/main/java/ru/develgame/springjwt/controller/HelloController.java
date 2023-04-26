package ru.develgame.springjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.springjwt.security.AuthService;
import ru.develgame.springjwt.security.JwtAuthentication;

@RestController
@RequestMapping("api/hello")
public class HelloController {
    @Autowired
    private AuthService authService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> hello() {
        JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }
}
