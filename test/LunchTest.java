import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class LunchTest {

    private Lunch lunch;

    @Before
    public void setUp() throws Exception {
        lunch = new Lunch(Arrays.asList(FoodType.MAIN, FoodType.STARCH, FoodType.STARCH, FoodType.STARCH));
    }

    @Test
    public void selectLunch() {
//        Assert.assertEquals(lunch.selectedLunch, );
    }

}