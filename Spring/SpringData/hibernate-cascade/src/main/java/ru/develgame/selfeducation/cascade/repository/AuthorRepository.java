package ru.develgame.selfeducation.cascade.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.selfeducation.cascade.entity.Author;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {
}
