package com.myteam.tournament.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Very small in-memory repository demonstrating Generics usage.
 * Not thread-safe (single-thread demo).
 */
public class Repository<T, ID> {

    private final Map<ID, T> storage = new LinkedHashMap<>();
    private final java.util.function.Function<T, ID> idMapper;

    public Repository(java.util.function.Function<T, ID> idMapper) {
        this.idMapper = Objects.requireNonNull(idMapper);
    }

    public void add(T item) {
        storage.put(idMapper.apply(item), item);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<T> findWhere(Predicate<T> predicate) {
        return storage.values().stream().filter(predicate).collect(Collectors.toList());
    }

    public void removeById(ID id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
    }
}
