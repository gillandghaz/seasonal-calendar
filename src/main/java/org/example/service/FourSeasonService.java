package org.example.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class FourSeasonService {
    private final JsonArray seasons;

    public FourSeasonService() {
        try (InputStream input = getClass().getResourceAsStream("/data/musim-empat.json")) {
            if (input == null) {
                throw new IllegalStateException("File data/musim-empat.json tidak ditemukan.");
            }
            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
            seasons = root.getAsJsonArray("seasons");
        } catch (Exception exception) {
            throw new IllegalStateException("Gagal membaca data empat musim.", exception);
        }
    }

    public String getSeason(LocalDate date, String hemisphere) {
        int month = date.getMonthValue();

        for (var element : seasons) {
            JsonObject season = element.getAsJsonObject();
            if (!season.get("hemisphere").getAsString().equals(hemisphere)) {
                continue;
            }

            int startMonth = season.get("startMonth").getAsInt();
            int endMonth = season.get("endMonth").getAsInt();

            boolean matches = startMonth <= endMonth
                    ? month >= startMonth && month <= endMonth
                    : month >= startMonth || month <= endMonth;

            if (matches) {
                return season.get("name").getAsString();
            }
        }

        return "Musim tidak diketahui";
    }
}
