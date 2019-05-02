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
            switch (response) {
                case ACCEPT:
                    //Accept
                    for (FoodItem foodItem : selectedMeal) {
                        inventory.removeFromInventory(foodItem);
                    }
                    System.out.println("Enjoy your meal!");
                    return;
                case SELECT:
                    clearMeal();
                    break;
                case MODIFY:
                    modifyMeal(scanner);
                    return;
                case EXIT:
                    return;
            }
        }
    }

    private void modifyMeal(Scanner scanner) {
        // User input for modifications of "Keep X, Y, Z"
        System.out.println("What items would you like to keep? These will be in the next meal \n" +
                "Input should be comma separated \"" +
                selectedMeal.get(0).getName() + ", " +
                selectedMeal.get(selectedMeal.size() - 1).getName() + "\" for example");
        String[] keepItems = scanner.nextLine().split(",\\w+");
        for (String keepItem : keepItems) {
            for (FoodItem foodItem : selectedMeal) {
                if (foodItem.equals(keepItem)) {

                }
            }
        }
        System.out.println(Arrays.toString(keepItems));
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
        ACCEPT(1, "Accept this meal; will deduct all selected meal items from inventory"),
        SELECT(2, "Select a new meal with same food types; will re-make a selected meal"),
        MODIFY(3, "Modify the current meal; will re-make keeping items indicated in this prompt"),
        EXIT(4, "Exit program");

        private final int code;
        private final String description;
        private static final Map<Integer, MealResponse> MAP = new HashMap<>();

        static {
            // Initializes a map of MealResponse's options and codes
            // Used to map user input (code) to MealResponse enum
            EnumSet.allOf(MealResponse.class).forEach(mealResponse -> MAP.put(mealResponse.code, mealResponse));
        }

        MealResponse(int code, String description) {
            //TODO add a function to carry out the action
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

    private void clearMeal() {
        selectedMeal.clear();
    }
}
