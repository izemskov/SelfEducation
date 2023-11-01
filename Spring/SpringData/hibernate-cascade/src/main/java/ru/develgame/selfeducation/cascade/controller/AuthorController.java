package ru.develgame.selfeducation.cascade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;
import ru.develgame.selfeducation.cascade.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<ValidatedResponseDto<List<AuthorDtoResponse>>> fetchAll() {
        return ResponseEntity.ok(ValidatedResponseDto.<List<AuthorDtoResponse>>builder()
                .data(authorService.fetchAll())
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ValidatedResponseDto<AuthorDtoResponse>> fetchOne(@PathVariable Long id) {
        ValidatedResponseDto<AuthorDtoResponse> responseDto = authorService.fetchOne(id);
        if (responseDto.errors() != null && !responseDto.errors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ValidatedResponseDto<AuthorDtoResponse>> createOne(@RequestBody AuthorDtoRequest authorDtoRequest) {
        ValidatedResponseDto<AuthorDtoResponse> responseDto = authorService.createOne(authorDtoRequest);
        if (responseDto.errors() != null && !responseDto.errors().isEmpty()) {
            return ResponseEntity.badRequest().body(responseDto);
        }
        return ResponseEntity.ok(responseDto);
    }
}
