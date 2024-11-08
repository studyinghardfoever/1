package edu.meu.mgen.data;

import java.util.*;

public class Food {

    // Food class
    static class Food {
        private String name;
        private double cal; // Calories per pound
        private double prot; // Protein per pound

        // Constructor
        public Food(String name, double cal, double prot) {
            this.name = name;
            this.cal = cal;
            this.prot = prot;
        }

        public String getName() {
            return name;
        }

        public double getCal() {
            return cal;
        }

        public double getProt() {
            return prot;
        }

        // Calculate calories
        public double calcCal(double weight) {
            return cal * weight;
        }

        // Calculate protein
        public double calcProt(double weight) {
            return prot * weight;
        }

        @Override
        public String toString() {
            return String.format("Food{name='%s', cal=%.2f, prot=%.2f g}", name, cal, prot);
        }
    }

    // Mixed food class
    static class MixedFood extends Food {
        private List<Food> ingredients;

        // Constructor
        public MixedFood(String name, List<Food> ingredients, double calAdj, double protAdj) {
            super(name, sumCal(ingredients) + calAdj, sumProt(ingredients) + protAdj);
            this.ingredients = ingredients;
        }

        private static double sumCal(List<Food> ingredients) {
            return ingredients.stream().mapToDouble(Food::getCal).sum();
        }

        private static double sumProt(List<Food> ingredients) {
            return ingredients.stream().mapToDouble(Food::getProt).sum();
        }

        @Override
        public String toString() {
            return String.format("MixedFood{name='%s', cal=%.2f, prot=%.2f g, ingredients=%s}",
                    getName(), getCal(), getProt(), ingredients);
        }
    }

    // Initialize food data
    private static Map<String, Food> initFoods() {
        Food potato = new Food("Potato", 680.0, 7.5);
        Food latte = new Food("Latte", 146.0, 7.9);
        Food pizza = new Food("Pizza", 484.0, 16.0);
        Food broccoli = new Food("Broccoli", 15.0, 1.3);

        MixedFood fries = new MixedFood("Fries", List.of(potato), 250.0, 4.0);
        MixedFood americano = new MixedFood("Americano", List.of(latte), -146.0, -7.9);
        MixedFood cheesePizza = new MixedFood("Cheese Pizza", List.of(pizza), 300.0, 12.0);
        MixedFood stirFriedBroccoli = new MixedFood("Stir-fried Broccoli", List.of(broccoli), 75.0, 2.0);

        Map<String, Food> foodMap = new HashMap<>();
        for (Food food : List.of(potato, latte, pizza, broccoli, fries, americano, cheesePizza, stirFriedBroccoli)) {
            foodMap.put(food.getName().toLowerCase(), food);
        }

        return foodMap;
    }

    // Query food information
    private static void query(Map<String, Food> foodMap, String name, double weight) {
        Food food = foodMap.get(name.toLowerCase());
        if (food != null) {
            double cal = food.calcCal(weight);
            double prot = food.calcProt(weight);
            System.out.printf("Food: %s (%.2f lbs)%n", food.getName(), weight);
            System.out.printf("Calories: %.2f kcal, Protein: %.2f g%n", cal, prot);
        } else {
            System.out.println("Food not found: " + name);
            System.out.println("Available foods:");
            foodMap.keySet().forEach(System.out::println);
        }
    }

    // Compare first food and second food based on adjustments
    private static void compare(Map<String, Food> foodMap, String name1, String name2, double weight) {
        Food food1 = foodMap.get(name1.toLowerCase());
        Food food2 = foodMap.get(name2.toLowerCase());

        if (food1 != null && food2 != null) {
            double cal1 = food1.calcCal(weight);
            double prot1 = food1.calcProt(weight);
            double cal2 = food2.calcCal(weight);
            double prot2 = food2.calcProt(weight);

            // Adjust the second food's values by subtracting the first food's calories and
            // protein
            double adjustedCal2 = cal2 - cal1;
            double adjustedProt2 = prot2 - prot1;

            System.out.printf("Comparing %s and %s for %.2f lbs:%n", food1.getName(), food2.getName(), weight);
            System.out.printf("%s: %.2f kcal, %.2f g protein.%n", food1.getName(), cal1, prot1);
            System.out.printf("%s: %.2f kcal, %.2f g protein.%n", food2.getName(), cal2, prot2);

            // Display the adjusted differences with food names
            System.out.printf("%s calorie difference (compared to %s): %.2f kcal%n", food2.getName(), food1.getName(),
                    adjustedCal2);
            System.out.printf("%s protein difference (compared to %s): %.2f g%n", food2.getName(), food1.getName(),
                    adjustedProt2);
        } else {
            System.out.println("One or both food items not found. Available foods:");
            foodMap.keySet().forEach(System.out::println);
        }
    }
}
