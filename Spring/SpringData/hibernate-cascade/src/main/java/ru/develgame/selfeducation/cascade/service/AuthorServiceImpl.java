package ru.develgame.selfeducation.cascade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoRequest;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.dto.ValidatedResponseDto;
import ru.develgame.selfeducation.cascade.entity.Author;
import ru.develgame.selfeducation.cascade.exception.AuthorNotFoundException;
import ru.develgame.selfeducation.cascade.mapper.AuthorMapper;
import ru.develgame.selfeducation.cascade.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public List<AuthorDtoResponse> fetchAll() {
        return authorRepository.findAll().stream().map(t -> authorMapper.toDto(t)).toList();
    }

    @Override
    public AuthorDtoResponse fetchOne(Long id) {
        return authorMapper.toDto(authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id %d not found", id))));
    }

    private List<String> validateAuthor(AuthorDtoRequest authorDtoRequest) {
        List<String> errors = new ArrayList<>();

        if (authorDtoRequest.firstName() == null || authorDtoRequest.firstName().isBlank()) {
            errors.add("First name cannot be empty");
        }

        if (authorDtoRequest.secondName() == null || authorDtoRequest.secondName().isBlank()) {
            errors.add("Second name cannot be empty");
        }

        return errors;
    }

    @Override
    public ValidatedResponseDto<AuthorDtoResponse> createOne(AuthorDtoRequest authorDtoRequest) {
        List<String> errors = validateAuthor(authorDtoRequest);

        if (!errors.isEmpty()) {
            return ValidatedResponseDto.<AuthorDtoResponse>builder()
                    .errors(errors)
                    .build();
        }

        Author author = new Author();
        author.setFirstName(authorDtoRequest.firstName());
        author.setSecondName(authorDtoRequest.secondName());
        author = authorRepository.save(author);

        return ValidatedResponseDto.<AuthorDtoResponse>builder()
                .data(authorMapper.toDto(author))
                .build();
    }

    @Override
    public ValidatedResponseDto<AuthorDtoResponse> update(Long id, AuthorDtoRequest authorDtoRequest) {
        List<String> errors = validateAuthor(authorDtoRequest);

        if (!errors.isEmpty()) {
            return ValidatedResponseDto.<AuthorDtoResponse>builder()
                    .errors(errors)
                    .build();
        }

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id %d not found", id)));
        author.setFirstName(authorDtoRequest.firstName());
        author.setSecondName(authorDtoRequest.secondName());
        author = authorRepository.save(author);

        return ValidatedResponseDto.<AuthorDtoResponse>builder()
                .data(authorMapper.toDto(author))
                .build();
    }

    @Override
    @Transactional
    public void deleteOne(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id %d not found", id)));
        authorRepository.delete(author);
    }
}
