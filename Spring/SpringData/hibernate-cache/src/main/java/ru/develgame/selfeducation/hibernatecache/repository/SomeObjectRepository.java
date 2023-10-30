package ru.develgame.selfeducation.hibernatecache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.selfeducation.hibernatecache.entity.SomeObject;

@Repository
public interface SomeObjectRepository extends JpaRepository<SomeObject, Long> {
}
