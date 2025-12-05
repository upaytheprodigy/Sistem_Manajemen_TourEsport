package com.myteam.tournament.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public final class LogService {
    private LogService() {}
    public static void write(String tournamentName, String message) {
        try {
            Path folder = Path.of("tournament-data", sanitize(tournamentName));
            Files.createDirectories(folder);
            try (FileWriter w = new FileWriter(folder.resolve("log.txt").toFile(), true)) {
                w.write(LocalDateTime.now() + " - " + message + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Gagal menulis log: " + e.getMessage());
        }
    }
    private static String sanitize(String s) { return s.replaceAll("[^a-zA-Z0-9_\\- ]", "_"); }
}
