import java.util.ArrayList;
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

    void selectLunch(Scanner scanner) {
        for (FoodType foodType : foodTypes) {
            selectedLunch.add(inventory.selectFoodItem(foodType));
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
                "0 - Accept this lunch; will deduct all selected lunch items from inventory \n" +
                "1 - Select a new lunch; will re-make a selected lunch \n" +
                "2 - Modify the current lunch \n" +
                "3 - Exit program");
        int response = scanner.nextInt();
        switch (response) {
            case 0:
                for (FoodItem foodItem : selectedLunch) {
                    inventory.removeFromInventory(foodItem);
                }
                System.out.println("Enjoy your lunch!");
                break;
            case 1:
                clearLunch();
                selectLunch(scanner);
                break;
            case 2:
                // User input for modifications of "Remove X, Y, Z" or "Add X, Y, Z"
                break;
            case 3:
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
