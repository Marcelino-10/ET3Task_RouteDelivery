package org.example;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private double totalWeight;
    private String area;
    private final List<Delivery> deliveries;

    public Trip() {
        deliveries = new ArrayList<>();
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public String getArea() {
        return area;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }
}
