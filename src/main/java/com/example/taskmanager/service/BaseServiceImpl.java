package com.example.taskmanager.service;

import com.example.taskmanager.repository.BaseRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    protected final BaseRepository<T, ID> repository;

    protected BaseServiceImpl(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public List<T> get(Map<String, Object> conditions) {
        return repository.findByConditions(conditions);
    }

    public List<T> getByIn(String key, List<?> values) {
        return repository.findByIn(key, values);
    }

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public Optional<T> getBy(String attribute, Object value) {
        return repository.findByAttribute(attribute, value);
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public boolean update(ID id, Map<String, Object> attributes) {
        Optional<T> opt = repository.findById(id);
        if (opt.isPresent()) {
            T entity = opt.get();
            updateEntityFields(entity, attributes);
            repository.save(entity);
            return true;
        }
        return false;
    }

    public boolean delete(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteMultiple(List<ID> ids) {
        List<T> entities = repository.findAllById(ids);
        if (!entities.isEmpty()) {
            repository.deleteAll(entities);
            return true;
        }
        return false;
    }

    public boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes) {
        return repository.updateByCondition(condition, attributes);
    }

    protected void updateEntityFields(T entity, Map<String, Object> attributes) {
        attributes.forEach((fieldName, value) -> {
            try {
                Field field = entity.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(entity, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                
            }
        });
    }
}
