import java.util.*;

public class Inventory {

    private final Map<FoodType, List<FoodItem>> currentInventory = new HashMap<>();
    private final Random random = new Random();

    public Inventory() {
        EnumSet.allOf(FoodType.class).forEach(foodType -> currentInventory.put(foodType, new ArrayList<>()));
    }

    public void addToInventory(FoodItem foodItem) {
        currentInventory.get(foodItem.getFoodType()).add(foodItem);
        System.out.println("Adding " + foodItem.getName() + " to inventory");
    }

    public void removeFromInventory(FoodItem foodItem) {
        currentInventory.get(foodItem.getFoodType()).remove(foodItem);
        System.out.println("Removing " + foodItem.getName() + " from inventory");
    }

    public void printInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Inventory is: \n");
        for (Map.Entry<FoodType, List<FoodItem>> entry : currentInventory.entrySet()) {
            for (FoodItem foodItem : entry.getValue()) {
                sb.append(foodItem.getFoodItemOverview()).append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    public Optional<FoodItem> selectFoodItem(FoodType foodType) {
        List<FoodItem> foodItems = currentInventory.get(foodType);
        if (foodItems.isEmpty()) {
            System.out.println("No " + foodType.toString().toLowerCase() + "s in inventory. Not added to lunch");
            return Optional.empty();
        } else {
            return Optional.of(foodItems.get(random.nextInt(foodItems.size())));
        }
    }
}
