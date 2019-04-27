import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Lunch {


    private final Inventory inventory;
    private final List<FoodType> foodTypes;
    private final List<FoodItem> selectedLunch;


    Lunch(Inventory inventory, List<FoodType> foodTypes) {
        this.inventory = inventory;
        this.foodTypes = foodTypes;
        selectedLunch = new ArrayList<>();
        // Standard lunch: protein, vegetable, starch
    }

    Lunch(Inventory inventory) {
        this.inventory = inventory;
        this.foodTypes = Arrays.asList(FoodType.MAIN, FoodType.STARCH, FoodType.VEGETABLE);
        selectedLunch = new ArrayList<>();
    }

    void selectLunch(Scanner scanner) {
        for (FoodType foodType : foodTypes) {
            FoodItem addedFoodItem = inventory.selectFoodItem(foodType);
            if (addedFoodItem != null) {
                selectedLunch.add(addedFoodItem);
            }
        }
        printLunch();
        acceptLunch(scanner);
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

    private void acceptLunch(Scanner scanner) {
        System.out.println("Please choose an option: \n" +
                "1 - Accept this lunch; will deduct all selected lunch items from inventory \n" +
                "2 - Select a new lunch with same food types; will re-make a selected lunch \n" +
                "3 - Modify the current lunch; will re-make keeping items indicated in this prompt \n" +
                "4 - Exit program");
        int response = Integer.parseInt(scanner.nextLine());
        switch (response) {
            case 1:
                for (FoodItem foodItem : selectedLunch) {
                    inventory.removeFromInventory(foodItem);
                }
                System.out.println("Enjoy your lunch!");
                break;
            case 2:
                clearLunch();
                selectLunch(scanner);
                break;
            case 3:
                // User input for modifications of "Keep X, Y, Z"
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
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid input. Please select from indicated options.");
                acceptLunch(scanner);
        }
    }

    private void clearLunch() {
        selectedLunch.clear();
    }
}
