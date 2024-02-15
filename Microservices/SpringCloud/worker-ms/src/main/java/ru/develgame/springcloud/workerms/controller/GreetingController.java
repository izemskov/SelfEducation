package ru.develgame.springcloud.workerms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.springcloud.workerms.dto.GreetingDto;

import java.util.UUID;

@RestController
@RequestMapping("greeting")
@RequiredArgsConstructor
public class GreetingController {
    private final UUID appUuid;

    @GetMapping
    public ResponseEntity<GreetingDto> greeting() {
        return ResponseEntity.ok(GreetingDto.builder().uuid(appUuid.toString()).build());
    }
}
