package com.example.taskmanager.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseRepositoryInterface<T, ID> {
    List<T> findAll();

    List<T> findByConditions(Map<String, Object> conditions);

    List<T> findByIn(String key, List<?> values);

    Optional<T> findById(ID id);

    Optional<T> findBy(String attribute, Object value);

    T create(T entity);

    boolean update(ID id, Map<String, Object> attributes);

    boolean delete(ID id);

    boolean deleteMultiple(List<ID> ids);

    boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes);
}
