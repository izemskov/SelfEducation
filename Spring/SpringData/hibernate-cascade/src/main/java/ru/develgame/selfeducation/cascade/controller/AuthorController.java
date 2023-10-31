package ru.develgame.selfeducation.cascade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDtoResponse>> fetchAll() {
        return ResponseEntity.ok(authorService.fetchAll());
    }
}
