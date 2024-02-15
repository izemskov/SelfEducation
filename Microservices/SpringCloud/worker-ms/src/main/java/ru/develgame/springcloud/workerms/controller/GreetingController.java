package ru.develgame.springcloud.workerms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.springcloud.workerms.dto.GreetingDto;

import java.util.UUID;

@RestController
@RequestMapping("greeting")
public class GreetingController {
    @GetMapping
    public ResponseEntity<GreetingDto> greeting() {
        return ResponseEntity.ok(GreetingDto.builder().uuid(UUID.randomUUID().toString()).build());
    }
}
