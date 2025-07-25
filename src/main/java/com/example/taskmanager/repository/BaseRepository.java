package com.example.taskmanager.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    
    List<T> findByConditions(Map<String, Object> conditions);

    Optional<T> findBy(String field, Object value);

    boolean update(ID id, Map<String, Object> attributes);

    boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes);

    boolean deleteMultiple(List<ID> ids);
}
