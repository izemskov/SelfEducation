package ru.develgame.selfeducation.cascade.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ValidatedResponseDto<List<AlbumDtoResponse>>> fetchAll(@PathVariable Long authorId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ValidatedResponseDto<AlbumDtoResponse>> createOne(@PathVariable Long authorId,
                                                                            @RequestBody AlbumDtoRequest albumDtoRequest) {

        albumService.createOne(authorId, albumDtoRequest);
        return null;
    }
}
