package ru.develgame.selfeducation.cascade.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.develgame.selfeducation.cascade.entity.Author;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
}
