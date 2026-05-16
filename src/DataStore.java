import java.util.ArrayList;

public class DataStore {
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<Customer> customers = new ArrayList<>();
    public static ArrayList<Meal>     meals     = new ArrayList<>();
    public static ArrayList<Order>    orders    = new ArrayList<>();
    public static ArrayList<String>   offers    = new ArrayList<>();

    // ================= ADMIN =================
    public static User admin = new User(0, "Admin", "admin", "1234", Role.ADMIN);

    // ================= PROGRAM REWARDS =================
    // Marketing: خصم % على كل أوردر (رقم بس زي 10 يعني 10%)
    public static double marketingDiscount = 0; // 0 = مش متفعّل

    // Loyalty: بونص بوينتس إضافية على كل أوردر
    public static int loyaltyBonusPoints = 0; // 0 = مش متفعّل

    // Reward: offer نصية بس
    public static String rewardReward = "";
}
