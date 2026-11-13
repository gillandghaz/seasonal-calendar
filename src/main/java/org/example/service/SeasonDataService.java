package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.example.model.SeasonForecast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.List;

public class SeasonDataService {
    private final List<SeasonForecast> forecasts;

    public SeasonDataService() {
        try (InputStream input = getClass().getResourceAsStream("/data/musim-indonesia.json")) {
            if (input == null) {
                throw new IllegalStateException("File data/musim-indonesia.json tidak ditemukan.");
            }

            JsonObject root = new Gson().fromJson(
                    new InputStreamReader(input, StandardCharsets.UTF_8), JsonObject.class);

            Type listType = new TypeToken<List<SeasonForecast>>() {}.getType();
            forecasts = new Gson().fromJson(root.get("forecasts"), listType);
        } catch (Exception exception) {
            throw new IllegalStateException("Gagal membaca data musim Indonesia.", exception);
        }
    }

    public SeasonForecast findForecast(String locationId, YearMonth month) {
        String key = month.toString();
        return forecasts.stream()
                .filter(item -> item.getLocationId().equals(locationId))
                .filter(item -> item.getReferenceMonth().equals(key))
                .findFirst()
                .orElse(null);
    }
}
