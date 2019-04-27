import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodItem {

    private static final String PARSE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(PARSE_FORMAT);

    private final String name;
    private final FoodType foodType;
//    private int servingsQuantity;
    private Date expiryDate;

    static {
        SIMPLE_DATE_FORMAT.setLenient(false);
    }

    public FoodItem(String name, FoodType foodType, String expiryDate) {
        this.name = name;
        this.foodType = foodType;
        this.expiryDate = parseExpiryDate(expiryDate);
    }

    private Date parseExpiryDate(String expiryDate) {
        try {
            return SIMPLE_DATE_FORMAT.parse(expiryDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse ExpiryDate from [" + expiryDate + "]. Format must be [" + PARSE_FORMAT + "]", e);
        }
    }

    public String getName() {
        return name;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = parseExpiryDate(expiryDate);
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public String getFoodItemOverview() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
