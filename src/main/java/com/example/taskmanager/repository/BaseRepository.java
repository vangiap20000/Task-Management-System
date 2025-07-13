package com.example.taskmanager.repository;

import com.example.taskmanager.repository.BaseRepositoryInterface;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseRepository<T, ID> implements BaseRepositoryInterface<T, ID> {

    protected final List<T> data = new ArrayList<>();

    protected abstract ID getId(T entity);

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public List<T> findByConditions(Map<String, Object> conditions) {
        return data.stream()
                .filter(entity -> {
                    for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                        Object fieldValue = getFieldValue(entity, entry.getKey());
                        if (fieldValue == null || !fieldValue.equals(entry.getValue())) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<T> findByIn(String key, List<?> values) {
        return data.stream()
                .filter(entity -> {
                    Object fieldValue = getFieldValue(entity, key);
                    return values.contains(fieldValue);
                })
                .collect(Collectors.toList());
    }


    @Override
    public Optional<T> findById(ID id) {
        return data.stream()
                .filter(entity -> getId(entity).equals(id))
                .findFirst();
    }

    @Override
    public Optional<T> findBy(String attribute, Object value) {
        return data.stream()
                .filter(entity -> {
                    Object fieldValue = getFieldValue(entity, attribute);
                    return value.equals(fieldValue);
                })
                .findFirst();
    }

    @Override
    public T create(T entity) {
        data.add(entity);
        return entity;
    }

    @Override
    public boolean update(ID id, Map<String, Object> attributes) {
        Optional<T> opt = findById(id);
        if (opt.isPresent()) {
            T entity = opt.get();
            fillAttributes(entity, attributes);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(ID id) {
        Optional<T> opt = findById(id);
        if (opt.isPresent()) {
            data.remove(opt.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMultiple(List<ID> ids) {
        List<T> toRemove = data.stream()
                .filter(entity -> ids.contains(getId(entity)))
                .collect(Collectors.toList());
        return data.removeAll(toRemove);
    }

    @Override
    public boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes) {
        List<T> toUpdate = findByConditions(condition);
        toUpdate.forEach(entity -> fillAttributes(entity, attributes));
        return !toUpdate.isEmpty();
    }

    protected Object getFieldValue(T entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch (Exception e) {
            return null;
        }
    }

    protected void fillAttributes(T entity, Map<String, Object> attributes) {
        attributes.forEach((k, v) -> {
            try {
                Field field = entity.getClass().getDeclaredField(k);
                field.setAccessible(true);
                field.set(entity, v);
            } catch (Exception ignored) {}
        });
    }
}
