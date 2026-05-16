import java.util.ArrayList;

public class Customer extends Person {

    ArrayList<String> gifts         = new ArrayList<>();
    ArrayList<String> offers        = new ArrayList<>();
    ArrayList<Order>  orders        = new ArrayList<>();
    ArrayList<String> notifications = new ArrayList<>();

    double totalPayments    = 0;
    int    loyaltyPoints    = 0;

    // ✅ لتتبع قبل/بعد الخصم
    double totalOriginalPayments = 0; // مجموع الأسعار قبل الخصم
    double totalSaved            = 0; // مجموع ما وفّره الكاستمر

    // ✅ لتتبع قبل/بعد البونص
    int basePoints  = 0; // البوينتس الأصلية بدون بونص
    int bonusPoints = 0; // البوينتس الإضافية من Loyalty

    boolean marketingProgram = false;
    boolean loyaltyProgram   = false;
    boolean rewardProgram    = false;

    public Customer(int id, String name) {
        super(id, name);
    }

    // ================= REGISTER PROGRAMS =================

    public void registerMarketing() {
        if (DataStore.marketingDiscount <= 0) {
            addNotification("Marketing Program is not active yet");
            return;
        }
        marketingProgram = true;
        addNotification(name + " joined Marketing Program - " + (int)DataStore.marketingDiscount + "% Discount");
    }

    public void registerLoyalty() {
        if (DataStore.loyaltyBonusPoints <= 0) {
            addNotification("Loyalty Program is not active yet");
            return;
        }
        loyaltyProgram = true;
        addNotification(name + " joined Loyalty Program - +" + DataStore.loyaltyBonusPoints + " Bonus Points per order");
    }

    public void registerReward() {
        if (DataStore.rewardReward.isEmpty()) {
            addNotification("Reward Program is not active yet");
            return;
        }
        rewardProgram = true;
        addOffer("🎁 Reward: " + DataStore.rewardReward);
        addNotification(name + " joined Reward Program");
    }

    // ================= ORDERS =================

    public void addOrder(Order order) {
        orders.add(order);
    }

    // ================= PAYMENTS =================
    // ✅ بياخد السعر بعد الخصم + السعر الأصلي
    public void addPayment(double amount, double original) {
        totalPayments         += amount;
        totalOriginalPayments += original;
        totalSaved            += (original - amount);

        // ✅ البوينتس الأصلية على السعر الفعلي المدفوع
        int earned = (int)(amount / 10);
        basePoints    += earned;
        loyaltyPoints += earned;

        // ✅ لو مشترك في Loyalty بيتضاف بونص إضافي
        if (loyaltyProgram && DataStore.loyaltyBonusPoints > 0) {
            bonusPoints   += DataStore.loyaltyBonusPoints;
            loyaltyPoints += DataStore.loyaltyBonusPoints;
        }

        checkGifts();
    }

    // ================= GIFTS =================

    private void checkGifts() {
        gifts.clear();
        if (totalPayments >= 200) gifts.add("Free Drink 🥤");
        if (totalPayments >= 500) gifts.add("Free Dessert 🍰");
        if (totalPayments >= 800) gifts.add("Free Meal 🍔");
    }

    // ================= OFFERS =================

    public void addOffer(String offer) {
        offers.add(offer);
        addNotification("Offer: " + offer);
    }

    // ================= NOTIFICATIONS =================

    public void addNotification(String msg) {
        notifications.add(msg);
        System.out.println("[" + name + "] " + msg);
    }

    // ================= CANCEL ORDER =================

    public boolean cancelOrder(int orderId) {
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            if (o.id == orderId) {
                // ✅ نرجع الحسابات صح
                totalPayments         -= o.totalPrice;
                totalOriginalPayments -= o.originalPrice;
                totalSaved            -= o.discountAmount;
                if (totalPayments         < 0) totalPayments         = 0;
                if (totalOriginalPayments < 0) totalOriginalPayments = 0;
                if (totalSaved            < 0) totalSaved            = 0;

                int earned = (int)(o.totalPrice / 10);
                basePoints    -= earned;
                loyaltyPoints -= earned;
                if (basePoints    < 0) basePoints    = 0;
                if (loyaltyPoints < 0) loyaltyPoints = 0;

                // ✅ لو كان فيه بونص Loyalty نرجعه
                if (loyaltyProgram && DataStore.loyaltyBonusPoints > 0) {
                    bonusPoints   -= DataStore.loyaltyBonusPoints;
                    loyaltyPoints -= DataStore.loyaltyBonusPoints;
                    if (bonusPoints   < 0) bonusPoints   = 0;
                    if (loyaltyPoints < 0) loyaltyPoints = 0;
                }

                orders.remove(i);
                checkGifts();
                addNotification("Order " + orderId + " cancelled");
                return true;
            }
        }
        return false;
    }

    // ================= ORDERS INFO =================

    public String getOrdersInfo() {
        String data = "";
        for (Order o : orders) {
            if (o.discountAmount > 0) {
                data += "Order ID: " + o.id +
                        " | Before: " + o.originalPrice +
                        " | Discount: " + (int)DataStore.marketingDiscount + "%" +
                        " | After: "  + o.totalPrice + "\n";
            } else {
                data += "Order ID: " + o.id + " | Total: " + o.totalPrice + "\n";
            }
        }
        return data;
    }
}
