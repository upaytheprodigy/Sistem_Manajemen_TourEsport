package com.myteam.tournament.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * Simple JSON save/load using Gson.
 */
public final class JsonStorage {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public <T> void save(String path, T data) throws IOException {
        Path p = Path.of(path);
        Files.createDirectories(p.getParent());
        try (Writer w = new FileWriter(p.toFile())) { gson.toJson(data, w); }
    }

    public <T> T[] loadArray(String path, Class<T[]> clazz) throws IOException {
        File f = new File(path);
        if (!f.exists()) return null;
        try (Reader r = new FileReader(f)) { return gson.fromJson(r, clazz); }
    }
}
