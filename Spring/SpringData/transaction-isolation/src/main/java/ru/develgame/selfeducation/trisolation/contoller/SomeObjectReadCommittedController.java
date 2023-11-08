package ru.develgame.selfeducation.trisolation.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectDto;
import ru.develgame.selfeducation.trisolation.dto.SomeObjectRequest;
import ru.develgame.selfeducation.trisolation.service.SomeObjectService;

@RestController
@RequestMapping("readCommitted/someObject")
public class SomeObjectReadCommittedController {
    @Autowired
    private SomeObjectService someObjectReadCommittedService;

    @PostMapping
    public ResponseEntity<SomeObjectDto> create(@RequestBody SomeObjectRequest someObjectRequest) {
        return ResponseEntity.ok(someObjectReadCommittedService.create(someObjectRequest));
    }

    @PutMapping("{someObjectId}")
    public ResponseEntity<SomeObjectDto> edit(@PathVariable Long someObjectId, @RequestBody SomeObjectRequest someObjectRequest) {
        try {
            return ResponseEntity.ok(someObjectReadCommittedService.edit(someObjectId, someObjectRequest));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{someObjectId}/attachment")
    public ResponseEntity<SomeObjectDto> addSubObject(@PathVariable Long someObjectId) {
        try {
            return ResponseEntity.ok(someObjectReadCommittedService.addAttachment(someObjectId));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{someObjectId}")
    public ResponseEntity<SomeObjectDto> getSomeObject(@PathVariable Long someObjectId) {
        try {
            return ResponseEntity.ok(someObjectReadCommittedService.getSomeObject(someObjectId));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
