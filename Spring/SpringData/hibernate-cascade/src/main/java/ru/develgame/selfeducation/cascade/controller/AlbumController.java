package ru.develgame.selfeducation.cascade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AlbumDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;
import ru.develgame.selfeducation.cascade.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("author/{authorId}/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDtoResponse>> fetchAll(@PathVariable Long authorId) {
        return ResponseEntity.ok(albumService.fetchAll(authorId));
    }

    @GetMapping("{albumId}")
    public ResponseEntity<AlbumDtoResponse> fetchOne(@PathVariable Long authorId, @PathVariable Long albumId) {
        return ResponseEntity.ok(albumService.fetchOne(authorId, albumId));
    }

    @PostMapping
    public ResponseEntity<ValidatedResponseDto<AlbumDtoResponse>> createOne(@PathVariable Long authorId,
                                                                            @RequestBody AlbumDtoRequest albumDtoRequest) {
        ValidatedResponseDto<AlbumDtoResponse> response = albumService.createOne(authorId, albumDtoRequest);
        if (response.errors() != null && !response.errors().isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(response.status())).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
