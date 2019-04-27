import java.util.*;

public class Lunch {


    private final Inventory inventory;
    private final List<FoodType> foodTypes;
    private final List<FoodItem> selectedLunch;


    Lunch(Inventory inventory, List<FoodType> foodTypes) {
        this.inventory = inventory;
        this.foodTypes = foodTypes;
        selectedLunch = new ArrayList<>();
    }

    Lunch(Inventory inventory) {
        this(inventory, Arrays.asList(FoodType.MAIN, FoodType.STARCH, FoodType.VEGETABLE));
    }

    void selectLunch(Scanner scanner) {
        while (true) {
            for (FoodType foodType : foodTypes) {
                Optional<FoodItem> addedFoodItem = inventory.selectFoodItem(foodType);
                addedFoodItem.ifPresent(selectedLunch::add);
            }
            printLunch();
            LunchResponse response = getUserInput(scanner);
            switch (response) {
                case ACCEPT:
                    //Accept
                    for (FoodItem foodItem : selectedLunch) {
                        inventory.removeFromInventory(foodItem);
                    }
                    System.out.println("Enjoy your lunch!");
                    return;
                case SELECT:
                    clearLunch();
                    break;
                case MODIFY:
                    // User input for modifications of "Keep X, Y, Z"
                    //TODO make its own method
                    System.out.println("What items would you like to keep? These will be in the next lunch \n" +
                            "Input should be comma separated \"" +
                            selectedLunch.get(0).getName() + ", " +
                            selectedLunch.get(selectedLunch.size() - 1).getName() + "\" for example");
                    String[] keepItems = scanner.nextLine().split(",\\w+");
                    for (String keepItem : keepItems) {
                        for (FoodItem foodItem : selectedLunch) {
                            if (foodItem.equals(keepItem)) {

                            }
                        }
                    }
                    System.out.println(Arrays.toString(keepItems));
                    return;
                case EXIT:
                    return;
            }
        }
    }

    private void printLunch(List<FoodItem> lunch) {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Selected Lunch is: \n");
        for (FoodItem foodItem : lunch) {
            sb.append(foodItem.getFoodItemOverview()).append("\n");
        }
        System.out.println(sb.toString());
    }

    private void printLunch() {
        printLunch(selectedLunch);
    }

    private enum LunchResponse {
        ACCEPT(1, "Accept this lunch; will deduct all selected lunch items from inventory"),
        SELECT(2, "Select a new lunch with same food types; will re-make a selected lunch"),
        MODIFY(3, "Modify the current lunch; will re-make keeping items indicated in this prompt"),
        EXIT(4, "Exit program");

        private final int code;
        private final String description;
        private static final Map<Integer, LunchResponse> MAP = new HashMap<>();

        static {
            EnumSet.allOf(LunchResponse.class).forEach(lunchResponse -> MAP.put(lunchResponse.code, lunchResponse));
        }

        LunchResponse(int code, String description) {
            //TODO add a function to carry out the action
            this.code = code;
            this.description = description;
        }

        private static LunchResponse getLunchResponse(int responseCode) {
            if (MAP.containsKey(responseCode)) {
                return MAP.get(responseCode);
            } else {
                throw new IllegalArgumentException("Invalid code for lunch response [" + responseCode + "].");
            }
        }

        public void print() {
            System.out.println(this.code + " - " + this.description);
        }
    }

    private LunchResponse getUserInput(Scanner scanner) {
        while (true) {
            System.out.println("Please choose an option:");
            EnumSet.allOf(LunchResponse.class).forEach(LunchResponse::print);
            int response = Integer.parseInt(scanner.nextLine());
            try {
                return LunchResponse.getLunchResponse(response);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please select from indicated options.");
            }
        }
    }

    private void clearLunch() {
        selectedLunch.clear();
    }
}
