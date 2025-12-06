package com.myteam.tournament.util;

import java.util.*;
import java.util.function.Function;

/**
 * Simple in-memory repository with a required idExtractor.
 */
public class Repository<T> {

    private final Map<String, T> storage = new LinkedHashMap<>();
    private final Function<T,String> idExtractor;

    public Repository(Function<T,String> idExtractor) {
        this.idExtractor = Objects.requireNonNull(idExtractor);
    }

    public void add(T obj) { 
        storage.put(idExtractor.apply(obj), obj); 
    }

    public Optional<T> findById(String id) { 
        return Optional.ofNullable(storage.get(id)); 
    }
    
    public List<T> findAll() { 
        return new ArrayList<>(storage.values()); 
    }

    public void clear() { storage.clear(); }

    public void overrideAll(List<T> list) {
        storage.clear();
        if (list != null) list.forEach(t -> storage.put(idExtractor.apply(t), t));
    }
}
