import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class FoodItemTest {

    private static final String EXPIRY_DATE = "2019-11-01";
    private static final long EXPIRY_DATE_MILLIS = 1572566400000L;

    private FoodItem foodItem;

    @Before
    public void setup() {
        foodItem = new FoodItem("POTATOES", FoodType.STARCH, EXPIRY_DATE);
    }

    @Test
    public void getName() {
        Assert.assertEquals("POTATOES", foodItem.getName());
    }

    @Test
    public void getExpiryDate() {
        Assert.assertEquals(new Date(EXPIRY_DATE_MILLIS), foodItem.getExpiryDate());
    }

    @Test
    public void setParsableExpiryDate() {
        foodItem.setExpiryDate("2019-11-30");
    }

    @Test (expected = IllegalArgumentException.class)
    public void setExpiryDateIllegalArgumentException() {
        foodItem.setExpiryDate("11-31-2019");
    }

    @Test
    public void getFoodType() {
        Assert.assertEquals(FoodType.STARCH, foodItem.getFoodType());
    }
}