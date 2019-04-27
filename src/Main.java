import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodItem potatoes = new FoodItem("POTATOES", FoodType.STARCH, "2019-05-31");
        FoodItem mashed_potatoes = new FoodItem("MASHED POTATOES", FoodType.STARCH, "2019-05-31");
        FoodItem rice = new FoodItem("RICE", FoodType.STARCH, "2019-05-31");
        FoodItem quinoa = new FoodItem("QUINOA", FoodType.STARCH, "2019-05-31");
        FoodItem chicken = new FoodItem("CHICKEN", FoodType.MAIN, "2019-05-31");
        FoodItem beef = new FoodItem("BEEF", FoodType.MAIN, "2019-05-31");
        FoodItem pork = new FoodItem("PORK", FoodType.MAIN, "2019-05-31");
        FoodItem tofu = new FoodItem("TOFU", FoodType.MAIN, "2019-05-31");
        FoodItem peas = new FoodItem("PEAS", FoodType.VEGETABLE, "2019-05-31");
        FoodItem carrots = new FoodItem("CARROTS", FoodType.VEGETABLE, "2019-05-31");

        Inventory inventory = new Inventory();
        inventory.addToInventory(potatoes);
        inventory.addToInventory(mashed_potatoes);
        inventory.addToInventory(rice);
        inventory.addToInventory(quinoa);
        inventory.addToInventory(chicken);
        inventory.addToInventory(beef);
        inventory.addToInventory(pork);
        inventory.addToInventory(tofu);

        Lunch lunch = new Lunch(inventory, Arrays.asList(FoodType.MAIN, FoodType.STARCH, FoodType.STARCH, FoodType.STARCH));
        lunch.selectLunch(scanner);


        lunch = new Lunch(inventory);
        lunch.selectLunch(scanner);

//        System.out.println(potatoes.getFoodItemOverview());
//        inventory.printInventory();
    }
}
