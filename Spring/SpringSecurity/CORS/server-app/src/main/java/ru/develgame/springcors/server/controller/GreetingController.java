package ru.develgame.springcors.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develgame.springcors.server.dto.HelloRequestDto;
import ru.develgame.springcors.server.dto.HelloResponseDto;

@RestController
@RequestMapping("hello")
public class GreetingController {
    @GetMapping
    public ResponseEntity<HelloResponseDto> helloGet() {
        return ResponseEntity.ok(HelloResponseDto.builder().greeting("Hello world").build());
    }

    @PostMapping
    public ResponseEntity<HelloResponseDto> helloPost(@RequestBody  HelloRequestDto helloRequestDto) {
        return ResponseEntity.ok(HelloResponseDto.builder().greeting("Hello " + helloRequestDto.name()).build());
    }
}
