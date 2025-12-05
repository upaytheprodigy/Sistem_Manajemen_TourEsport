package com.myteam.tournament.model;

import java.util.Objects;
import java.util.UUID;

public final class Team {
    private final String id;
    private String name;

    public Team(String name) { this(UUID.randomUUID().toString(), name); }
    public Team(String id, String name) {
        this.id = Objects.requireNonNull(id);
        setName(name);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }

    @Override public String toString() { return name + " (" + id + ")"; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team t = (Team) o; return id.equals(t.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
