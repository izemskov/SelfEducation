package ru.develgame.selfeducation.cascade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.develgame.selfeducation.cascade.dto.AuthorDtoResponse;
import ru.develgame.selfeducation.cascade.mapper.AuthorMapper;
import ru.develgame.selfeducation.cascade.repository.AuthorRepository;

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
}
