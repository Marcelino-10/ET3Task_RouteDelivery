package org.example;

import com.google.gson.annotations.SerializedName;

public class Delivery {
    private final int id;
    private final String area;
    private final int priority;
    @SerializedName("packageWeightKg")
    private final double weight;

    public Delivery(int id, String area, int priority, double weight) {
        this.id = id;
        this.area = area;
        this.priority = priority;
        this.weight = weight;
    }

    public int getPriority() {
        return priority;
    }

    public double getWeight() {
        return weight;
    }

    public String getArea() {
        return area;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "    Delivery #" + id + " | Priority: " + priority + " | Weight: " + weight + " kg";
    }
}
