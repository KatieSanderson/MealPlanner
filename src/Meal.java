import java.util.*;

public class Meal {


    private final Inventory inventory;
    private final List<FoodType> foodTypes;
    private final List<FoodItem> selectedMeal;


    Meal(Inventory inventory, List<FoodType> foodTypes) {
        this.inventory = inventory;
        this.foodTypes = foodTypes;
        selectedMeal = new ArrayList<>();
    }

    Meal(Inventory inventory) {
        this(inventory, Arrays.asList(FoodType.MAIN, FoodType.STARCH, FoodType.VEGETABLE));
    }

    void selectMeal(Scanner scanner) {
        while (true) {
            for (FoodType foodType : foodTypes) {
                Optional<FoodItem> addedFoodItem = inventory.selectFoodItem(foodType);
                addedFoodItem.ifPresent(selectedMeal::add);
            }
            printMeal();
            MealResponse response = getUserInput(scanner);
            response.execute(this, scanner);
        }
    }

    void selectMealWithKeepSet(Scanner scanner, Set<FoodItem> keepSet) {
        while (true) {
            List<FoodItem> nextSelectedMeal = new ArrayList<>();
            for (FoodItem foodItem : selectedMeal) {
                if (keepSet.contains(foodItem)) {
                    keepSet.remove(foodItem);
                    nextSelectedMeal.add(foodItem);
                } else {
                    Optional<FoodItem> addedFoodItem = inventory.selectFoodItem(foodItem.getFoodType());
                    while (addedFoodItem.get() == foodItem) {
                        addedFoodItem = inventory.selectFoodItem(foodItem.getFoodType());
                    }
                    addedFoodItem.ifPresent(selectedMeal::add);
                }
            }
            printMeal();
            MealResponse response = getUserInput(scanner);
            response.execute(this, scanner);
        }
    }

    private void printMeal(List<FoodItem> meal) {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Selected Meal is: \n");
        for (FoodItem foodItem : meal) {
            sb.append(foodItem.getFoodItemOverview()).append("\n");
        }
        System.out.println(sb.toString());
    }

    private void printMeal() {
        printMeal(selectedMeal);
    }

    private enum MealResponse {
        ACCEPT(1, "Accept this meal; will deduct all selected meal items from inventory") {
            @Override
            public void execute(Meal meal, Scanner scanner) {
                for (FoodItem foodItem : meal.selectedMeal) {
                    meal.inventory.removeFromInventory(foodItem);
                }
            }
        },
        SELECT(2, "Select a new meal with same food types; will re-make a selected meal") {
            @Override
            public void execute(Meal meal, Scanner scanner) {
                meal.selectedMeal.clear();
            }
        },
        MODIFY(3, "Modify the current meal; will re-make meal keeping items indicated in prompt") {
            @Override
            public void execute(Meal meal, Scanner scanner) {
                // User input for modifications of "Keep X, Y, Z"
                System.out.println("What items would you like to keep? These will be in the next meal \n" +
                        "Input should be comma separated \"" +
                        meal.selectedMeal.get(0).getName() + ", " +
                        meal.selectedMeal.get(meal.selectedMeal.size() - 1).getName() + "\" for example");
                Set<String> keepSet = Set.of(scanner.nextLine().split(",\\w+"));
                Set<FoodItem> keepSetFromMeal = populateFoodItemsIfPresent(meal, keepSet);

            }

            private Set<FoodItem> populateFoodItemsIfPresent(Meal meal, Set<String> keepSet) {
                Set<FoodItem> keepSetFromMeal = new HashSet<>();
                for (String keepItem : keepSet) {
                    Optional<FoodItem> keptFoodItem = isItemFound(meal, keepItem);
                    keptFoodItem.ifPresent(keepSetFromMeal::add);
                }
                return keepSetFromMeal;
            }

            private Optional<FoodItem> isItemFound(Meal meal, String keepItem) {
                for (FoodItem foodItem : meal.selectedMeal) {
                    if (foodItem.equals(keepItem)) {
                        return Optional.of(foodItem);
                    }
                }
                System.out.println(keepItem + " is not in the selected meal.");
                return Optional.empty();
            }
        },
        EXIT(4, "Exit program") {
            @Override
            public void execute(Meal meal, Scanner scanner) {}
        };

        private final int code;
        private final String description;
        private static final Map<Integer, MealResponse> MAP = new HashMap<>();

        public abstract void execute(Meal meal, Scanner scanner);

        static {
            // Initializes a map of MealResponse's options and codes
            // Used to map user input (code) to MealResponse enum
            EnumSet.allOf(MealResponse.class).forEach(mealResponse -> MAP.put(mealResponse.code, mealResponse));
        }

        MealResponse(int code, String description) {
            this.code = code;
            this.description = description;
        }

        private static MealResponse getMealResponse(int responseCode) {
            // Returns MealResponse from indicated code or throws exception (should be caught in caller) for illegal argument (no MealResponse exists for indicated code)
            if (MAP.containsKey(responseCode)) {
                return MAP.get(responseCode);
            } else {
                throw new IllegalArgumentException("Invalid code for meal response [" + responseCode + "].");
            }
        }

        public void print() {
            System.out.println(this.code + " - " + this.description);
        }
    }

    private MealResponse getUserInput(Scanner scanner) {
        while (true) {
            System.out.println("Please choose an option:");
            EnumSet.allOf(MealResponse.class).forEach(MealResponse::print);
            int response = Integer.parseInt(scanner.nextLine());
            try {
                return MealResponse.getMealResponse(response);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please select from indicated options.");
            }
        }
    }
}
