import java.util.ArrayList;
import java.util.List;

public class Lunch {


    private final Inventory inventory;
    private final List<FoodType> foodTypes;
    private final List<FoodItem> selectedLunch;


    public Lunch(Inventory inventory, List<FoodType> foodTypes) {
        this.inventory = inventory;
        this.foodTypes = foodTypes;
        selectedLunch = new ArrayList<>();
        // Standard lunch: protein, vegetable, starch
    }

    public List<FoodItem> selectLunch() {
        for (FoodType foodType : foodTypes) {
            selectedLunch.add(inventory.selectFoodItem(foodType));
        }
        return selectedLunch;
    }

    public void printLunch(List<FoodItem> lunch) {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Selected Lunch is: \n");
        for (FoodItem foodItem : lunch) {
            sb.append(foodItem.getFoodItemOverview()).append("\n");
        }
        System.out.println(sb.toString());
    }

    public void printLunch() {
        printLunch(selectedLunch);
    }
}
