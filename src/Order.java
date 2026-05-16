import java.util.ArrayList;

public class Order {
    public int    id;
    public Customer customer;
    public ArrayList<Meal> meals = new ArrayList<>();
    public double totalPrice;
    public double originalPrice;   // ✅ السعر قبل الخصم
    public double discountAmount;  // ✅ قيمة الخصم

    public Order(Customer customer) {
        this.customer = customer;
        this.id = generateCustomerOrderId(customer);
    }

    private int generateCustomerOrderId(Customer customer) {
        int id = 1;
        for (Order o : customer.orders) {
            if (o.id >= id) id = o.id + 1;
        }
        return id;
    }

    public void calculateTotal() {
        totalPrice = 0;
        for (Meal m : meals) totalPrice += m.price;
    }

    public void checkout() {
        calculateTotal();

        originalPrice  = totalPrice;
        discountAmount = 0;

        // ✅ لو الكاستمر مشترك في Marketing وفيه خصم محدد
        if (customer.marketingProgram && DataStore.marketingDiscount > 0) {
            discountAmount = originalPrice * (DataStore.marketingDiscount / 100.0);
            totalPrice     = originalPrice - discountAmount;
        }

        customer.orders.add(this);
        customer.addPayment(totalPrice, originalPrice); // ✅ بنبعت الاتنين
    }
}
