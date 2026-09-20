package org.example;

import java.util.*;

public class RoutePlanner {
    private final List<Delivery> deliveries;
    private final PriorityQueue<Delivery> deliverySet;
    private final List<Delivery> invalidDeliveries;

    public RoutePlanner(List<Delivery> deliveries) {
        invalidDeliveries = new ArrayList<>();
        this.deliveries = deliveries;
        deliverySet = new PriorityQueue<>(Comparator.comparingInt(Delivery::getPriority).thenComparing(Comparator.comparingDouble(Delivery::getWeight).reversed()));

    }

    public List<Trip> route() {
        invalidDeliveries.clear();
        deliverySet.clear();

        deliverySet.addAll(deliveries);

        if (deliveries == null || deliveries.isEmpty()) {
            return new ArrayList<>();
        }
        List<Trip> trips = new ArrayList<>();

        while(!deliverySet.isEmpty()) {
            Delivery delivery = deliverySet.poll();
            String currentArea = delivery.getArea();
            double currentWeight = delivery.getWeight();

            if (currentWeight > 10.0) {
                invalidDeliveries.add(delivery);
                continue;
            }

            Trip currentTrip = null;
            for (Trip t : trips) {
                if (t.getArea().equals(currentArea) && t.getTotalWeight() + currentWeight <= 10.0) {
                    currentTrip = t;
                    break;
                }
            }
            if(currentTrip == null){
                currentTrip = new Trip();
                currentTrip.setArea(currentArea);
                trips.add(currentTrip);
            }

            currentTrip.setArea(currentArea);
            currentTrip.setTotalWeight(currentTrip.getTotalWeight() + currentWeight);
            currentTrip.addDelivery(delivery);
        }
        return trips;
    }

    public void printRoute() {
        List<Trip> trips = route();
        System.out.println("=== Route Plan ===");
        System.out.println();
        if(trips.isEmpty()) {
            System.out.println("No trips found");
            return;
        }
        for (int i = 0; i < trips.size(); i++) {
            Trip trip = trips.get(i);
            if (trip.getDeliveries().isEmpty()) {
                continue;
            }
            System.out.println("Trip #" + (i + 1));
            System.out.println("  Area: " + trip.getArea());
            System.out.printf("  Total Weight: %.2f kg%n", trip.getTotalWeight());
            System.out.println("  Deliveries:");
            for (Delivery d : trip.getDeliveries()) {
                System.out.println(d);
            }
            System.out.println();
        }
        if (!invalidDeliveries.isEmpty()) {
            System.out.println("=== Invalid Deliveries (weight > 10 kg) ===");
            for (Delivery d : invalidDeliveries) {
                System.out.println(d);
            }
        }
    }
}
