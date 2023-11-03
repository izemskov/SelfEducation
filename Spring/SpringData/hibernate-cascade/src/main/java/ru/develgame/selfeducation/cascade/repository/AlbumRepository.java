package ru.develgame.selfeducation.cascade.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.develgame.selfeducation.cascade.entity.Album;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {
}
