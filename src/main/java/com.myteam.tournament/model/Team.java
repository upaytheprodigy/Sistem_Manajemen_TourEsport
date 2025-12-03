package com.myteam.tournament.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable-ish Team identifier with mutable name.
 * Keep id final to avoid accidental identity changes.
 */
public final class Team {
    private final String id;
    private String name;

    public Team(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Team(String id, String name) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        setName(name);
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public String toString() {
        return "Team{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return id.equals(team.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
