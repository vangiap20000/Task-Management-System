package com.example.taskmanager.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseServiceInterface<T, ID> {
    List<T> getAll();

    List<T> get(Map<String, Object> conditions);

    List<T> getByIn(String key, List<?> values);

    Optional<T> getById(ID id);

    Optional<T> getBy(String attribute, Object value);

    T create(T entity);

    boolean update(ID id, Map<String, Object> attributes);

    boolean delete(ID id);

    boolean deleteMultiple(List<ID> ids);

    boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes);
}
