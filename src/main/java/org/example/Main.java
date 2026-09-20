package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Delivery> deliveries = loadDeliveries();
        RoutePlanner planner = new RoutePlanner(deliveries);
        planner.printRoute();
    }

    private static List<Delivery> loadDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        Gson gson = new Gson();

        try (InputStream is = Main.class.getResourceAsStream("/Deliveries.json");
             Reader reader = new InputStreamReader(is)) {

            JsonObject root = gson.fromJson(reader, JsonObject.class);
            JsonArray array = root.getAsJsonArray("deliveries");

            for (JsonElement element : array) {
                Delivery delivery = gson.fromJson(element, Delivery.class);
                deliveries.add(delivery);
            }
        } catch (IOException e) {
            System.err.println("Error reading Deliveries.json: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Deliveries.json not found on classpath.");
        }

        return deliveries;
    }
}