package com.example.taskmanager.service.impl;

import com.example.taskmanager.repository.BaseRepositoryInterface;
import com.example.taskmanager.service.BaseServiceInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseService<T, ID> implements BaseServiceInterface<T, ID> {

    protected final BaseRepositoryInterface<T, ID> repository;

    protected BaseService(BaseRepositoryInterface<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public List<T> get(Map<String, Object> conditions) {
        return repository.findByConditions(conditions);
    }

    @Override
    public List<T> getByIn(String key, List<?> values) {
        return repository.findByIn(key, values);
    }

    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<T> getBy(String attribute, Object value) {
        return repository.findBy(attribute, value);
    }

    @Override
    public T create(T entity) {
        return repository.create(entity);
    }

    @Override
    public boolean update(ID id, Map<String, Object> attributes) {
        return repository.update(id, attributes);
    }

    @Override
    public boolean delete(ID id) {
        return repository.delete(id);
    }

    @Override
    public boolean deleteMultiple(List<ID> ids) {
        return repository.deleteMultiple(ids);
    }

    @Override
    public boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes) {
        return repository.updateByCondition(condition, attributes);
    }
}
