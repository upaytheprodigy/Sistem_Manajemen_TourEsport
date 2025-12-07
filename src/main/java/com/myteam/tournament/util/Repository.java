package com.myteam.tournament.util;
import java.util.*;
import java.util.function.Function;

import com.myteam.tournament.model.Match;
public class Repository<T> {
    private final Map<String,T> storage = new LinkedHashMap<>();
    private final Function<T,String> idExtractor;
    public Repository(Function<T,String> idExtractor){ this.idExtractor = idExtractor; }
    public Repository(Object idExtractor2) {
        this.idExtractor = (Function<T,String>) idExtractor2;
    }
    public void add(T obj){ storage.put(idExtractor.apply(obj), obj); }
    public Optional<T> findById(String id){ return Optional.ofNullable(storage.get(id)); }
    public List<T> findAll(){ return new ArrayList<>(storage.values()); }
    public void clear(){ storage.clear(); }
    public void overrideAll(List<T> list){
        storage.clear();
        if (list!=null) for (T t: list) storage.put(idExtractor.apply(t), t);
    }

    public void update(Match match) {
        String id = idExtractor.apply((T) match);
        if (storage.containsKey(id)) {
            storage.put(id, (T) match);
        } else {
            throw new NoSuchElementException("No match found with id: " + id);
        }
    }
}
