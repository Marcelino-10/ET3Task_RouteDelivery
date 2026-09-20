# Delivery Route Planner

## Setup and Execution Instructions

### Prerequisites
* **Java Development Kit (JDK):** Version 8 or higher.
* **Maven:** Ensure Maven is installed and available in your system's PATH.

### Running from the Command Line
1. **Navigate to the Project Root:** Open your terminal and navigate to the directory containing the `pom.xml` file.
2. **Compile the Project:**
   ```bash
   mvn clean compile
   ```
3. **Run the Application:**
   Execute the `Main` class using Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```

### Running from an IDE
1. Open your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse, VS Code).
2. Select **Open** or **Import Project** and choose the root folder of this project.
3. Allow the IDE to automatically resolve the Maven dependencies.
4. Locate `src/main/java/org/example/Main.java`.
5. Run the `main` method directly from the IDE.

---

## 1. Solution Approach
I designed the program using object-oriented principles with the following core classes:
* `Delivery`: Stores individual delivery data (ID, area, priority, weight).
* `Trip`: Manages a collection of deliveries heading to a specific area, ensuring the 10 kg capacity limit is not breached.
* `RoutePlanner`: Handles the main business logic of organizing delivery requests into trips.

**Algorithm Logic:**
First, I sort all valid delivery requests based on their priority (using a Priority Queue). **If two deliveries have the same priority, they are then sorted based on their weight.** Then, I iterate over each sorted request and attempt to add it to an existing valid trip for its specific area. If no current trip for that area has enough remaining capacity, a new `Trip` is created. This guarantees that deliveries for the same area are grouped together, while strictly ensuring that the most urgent deliveries (lowest priority number) are processed and loaded first. Invalid deliveries (e.g., weighing more than 10kg) are filtered out and stored in a separate list for reporting.

## 2. Most Difficult Part
The most challenging part of the task was balancing the two distinct grouping requirements: grouping by *Area* while simultaneously respecting the *Priority* (and weight tie-breakers) of each delivery. Designing the logic to maintain a priority queue to handle urgency, while dynamically managing trip capacities grouped by area, required careful consideration to ensure neither rule was violated.

## 3. Algorithm Limitations (Best Grouping)
There are situations where this algorithm may not produce the absolute most efficient grouping (i.e., the mathematical minimum number of trips). Because the algorithm prioritizes *urgency*, it acts as a "Greedy Algorithm". By forcing the system to pack Priority 1 items before Priority 2 or 3 items, it might leave awkward unused weight capacities (like 2kg or 3kg) in a vehicle that cannot be filled by the next priority items. If we completely ignored priority, a classic Bin-Packing algorithm could likely pack the vehicles more densely, but in the context of this business problem, delivering urgent packages on time is more important than perfect spatial efficiency.

## 4. Performance at 1,000,000 Requests
If the input contained 1,000,000 delivery requests, the primary bottleneck would be the logic used to find an available trip. Currently, adding a new delivery requires iterating over the existing list of trips to find one that matches the area and has available weight capacity. This results in an $O(N \times M)$ time complexity (where N is deliveries and M is trips). As the number of trips grows, this continuous iteration would become highly CPU-intensive and slow.

## 5. Future Improvements
If I had another day to work on this solution, I would optimize the trip assignment logic. Instead of iterating over all trips, I would implement a Hash Map (Dictionary) where the keys are the `Area` names, and the values are lists of active `Trip` objects for that area. This would reduce the search time to find a matching trip to $O(1)$, resolving the bottleneck mentioned above. 

## 6. Extension Feature: JSON Export
**Feature Added:** Output Export to JSON.
**Why I chose it:** While printing to a console is good for debugging, a real-world route planner needs to integrate with other software (like a driver navigation app or a dispatch dashboard). I added a feature that exports the final organized trips into a structured JSON file. This makes the output of my algorithm immediately useful for downstream systems.
