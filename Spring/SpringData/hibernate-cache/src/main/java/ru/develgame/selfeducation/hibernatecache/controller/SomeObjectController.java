package ru.develgame.selfeducation.hibernatecache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develgame.selfeducation.hibernatecache.dto.SomeObjectDto;
import ru.develgame.selfeducation.hibernatecache.dto.SomeObjectRequest;
import ru.develgame.selfeducation.hibernatecache.entity.SomeObject;
import ru.develgame.selfeducation.hibernatecache.repository.SomeObjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("someObject")
public class SomeObjectController {
    @Autowired
    private SomeObjectRepository someObjectRepository;

    @GetMapping
    public ResponseEntity<List<SomeObjectDto>> getSomeObjects() {
        return ResponseEntity.ok(someObjectRepository.findAll().stream()
                .map(t -> new SomeObjectDto(t.getId(), t.getName()))
                .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<SomeObjectDto> getSomeObject(@PathVariable Long id) {
        Optional<SomeObject> someObjectOpt = someObjectRepository.findById(id);
        if (someObjectOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        System.out.println(someObjectOpt.get().getName());
        someObjectOpt = someObjectRepository.findById(id);

        return ResponseEntity.ok(new SomeObjectDto(someObjectOpt.get().getId(), someObjectOpt.get().getName()));
    }

    @PostMapping
    public ResponseEntity<SomeObjectDto> addSomeObject(@RequestBody SomeObjectRequest someObjectRequest) {
        SomeObject someObject = new SomeObject();
        someObject.setName(someObjectRequest.name());
        someObject = someObjectRepository.save(someObject);
        return ResponseEntity.ok(new SomeObjectDto(someObject.getId(), someObject.getName()));
    }
}
