package com.example.taskmanager.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.NoRepositoryBean;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@NoRepositoryBean
public class BaseRepositoryImpl<T, ID> implements BaseRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> domainClass;

    public BaseRepositoryImpl(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    public List<T> findByConditions(Map<String, Object> conditions) {
        StringBuilder queryStr = new StringBuilder("SELECT e FROM " + domainClass.getSimpleName() + " e WHERE ");
        queryStr.append(
                conditions.keySet().stream().map(k -> "e." + k + " = :" + k).collect(Collectors.joining(" AND "))
        );
        var query = entityManager.createQuery(queryStr.toString(), domainClass);
        conditions.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public Optional<T> findBy(String field, Object value) {
        String queryStr = "SELECT e FROM " + domainClass.getSimpleName() + " e WHERE e." + field + " = :value";
        var query = entityManager.createQuery(queryStr, domainClass);
        query.setParameter("value", value);
        List<T> result = query.getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    @Transactional
    public boolean update(ID id, Map<String, Object> attributes) {
        T entity = entityManager.find(domainClass, id);
        if (entity == null) return false;
        fillAttributes(entity, attributes);
        entityManager.merge(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean updateByCondition(Map<String, Object> condition, Map<String, Object> attributes) {
        List<T> entities = findByConditions(condition);
        if (entities.isEmpty()) return false;
        for (T entity : entities) {
            fillAttributes(entity, attributes);
            entityManager.merge(entity);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMultiple(List<ID> ids) {
        for (ID id : ids) {
            T entity = entityManager.find(domainClass, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
        }
        return true;
    }

    protected void fillAttributes(T entity, Map<String, Object> attributes) {
        attributes.forEach((k, v) -> {
            try {
                Field field = domainClass.getDeclaredField(k);
                field.setAccessible(true);
                field.set(entity, v);
            } catch (Exception ignored) {}
        });
    }
}