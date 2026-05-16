import javax.swing.*;
import java.awt.*;

public class EmployeeDashboard extends JFrame {

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder());
    }

    User currentUser;

    JButton addCustomerBtn, showCustomerBtn, searchCustomerBtn, deleteCustomerBtn;
    JButton makeOrderBtn, cancelOrderBtn, showOrdersBtn;
    JButton billingBtn, logoutBtn, updateInfoBtn, profileBtn;
    JButton marketingBtn, loyaltyBtn, rewardBtn;
    JButton cancelMarketingBtn, cancelLoyaltyBtn, cancelRewardBtn;
    JTextArea output;

    public EmployeeDashboard(User user) {

        this.currentUser = user;

        setTitle("Employee Dashboard");
        setSize(730, 600);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 40));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Restaurant Management System");
        title.setBounds(180, 0, 500, 40);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title);

        addCustomerBtn    = new JButton("Add Customer");
        showCustomerBtn   = new JButton("Show Customers");
        searchCustomerBtn = new JButton("Search Customer");
        deleteCustomerBtn = new JButton("Delete Customer");
        makeOrderBtn      = new JButton("Make Order");
        cancelOrderBtn    = new JButton("Cancel Order");
        showOrdersBtn     = new JButton("Show Orders");
        billingBtn        = new JButton("Billing");
        profileBtn        = new JButton("Customer Profile");
        updateInfoBtn     = new JButton("Update Info");
        logoutBtn         = new JButton("Logout");
        marketingBtn      = new JButton("Marketing 🏷");
        loyaltyBtn        = new JButton("Loyalty ⭐");
        rewardBtn         = new JButton("Reward 🎁");
        cancelMarketingBtn = new JButton("Cancel Marketing");
        cancelLoyaltyBtn   = new JButton("Cancel Loyalty");
        cancelRewardBtn    = new JButton("Cancel Reward");

        styleButton(addCustomerBtn);  styleButton(showCustomerBtn);
        styleButton(searchCustomerBtn); styleButton(deleteCustomerBtn);
        styleButton(makeOrderBtn);    styleButton(cancelOrderBtn);
        styleButton(showOrdersBtn);   styleButton(billingBtn);
        styleButton(profileBtn);      styleButton(updateInfoBtn);
        styleButton(logoutBtn);
        styleButton(marketingBtn);    styleButton(loyaltyBtn); styleButton(rewardBtn);
        styleButton(cancelMarketingBtn);
        styleButton(cancelLoyaltyBtn);
        styleButton(cancelRewardBtn);

        // ROW 1
        addCustomerBtn.setBounds(20, 50, 160, 35);
        showCustomerBtn.setBounds(200, 50, 160, 35);
        searchCustomerBtn.setBounds(380, 50, 160, 35);
        deleteCustomerBtn.setBounds(560, 50, 160, 35);
        // ROW 2
        makeOrderBtn.setBounds(20, 100, 160, 35);
        cancelOrderBtn.setBounds(200, 100, 160, 35);
        showOrdersBtn.setBounds(380, 100, 160, 35);
        billingBtn.setBounds(560, 100, 160, 35);
        // ROW 3
        profileBtn.setBounds(20, 150, 160, 35);
        updateInfoBtn.setBounds(200, 150, 160, 35);
        logoutBtn.setBounds(380, 150, 160, 35);
        // ROW 4
        marketingBtn.setBounds(20, 200, 160, 35);
        loyaltyBtn.setBounds(200, 200, 160, 35);
        rewardBtn.setBounds(380, 200, 160, 35);
        cancelMarketingBtn.setBounds(20, 250, 160, 35);
         cancelLoyaltyBtn.setBounds(200, 250, 160, 35);
        cancelRewardBtn.setBounds(380, 250, 160, 35);

        output = new JTextArea();
        output.setBackground(new Color(45, 45, 60));
        output.setForeground(Color.WHITE);
        output.setFont(new Font("Consolas", Font.PLAIN, 16));
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(output);
       scrollPane.setBounds(20, 310, 700, 225);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(45, 45, 60));

        add(addCustomerBtn);  add(showCustomerBtn);
        add(searchCustomerBtn); add(deleteCustomerBtn);
        add(makeOrderBtn);    add(cancelOrderBtn);
        add(showOrdersBtn);   add(billingBtn);
        add(profileBtn);      add(updateInfoBtn); add(logoutBtn);
        add(marketingBtn);    add(loyaltyBtn);    add(rewardBtn);
        add(cancelMarketingBtn);
        add(cancelLoyaltyBtn);
        add(cancelRewardBtn);
        add(scrollPane);

        // ================= ADD CUSTOMER =================
        addCustomerBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "Customer Name:");
             if (name == null) return;
            // ================= CHECK DUPLICATE =================
      for (Customer customer : DataStore.customers) {

       if (customer.name.equalsIgnoreCase(name)) {

        output.setText("❌ Customer already exists");
        return;
    }
}
       
            Customer c = new Customer(DataStore.customers.size() + 1, name);
            DataStore.customers.add(c);
            Notification.show(this, "✔ New Customer Added: " + name);
            FileManager.saveAll();
            output.setText("✔ Customer Added");
        });

        // ================= SHOW CUSTOMERS =================
        showCustomerBtn.addActionListener(e -> {
            String data = "";
            for (Customer c : DataStore.customers)
                data += c.id + " - " + c.name +
                        " | Payments: " + c.totalPayments +
                        " | Points: "   + c.loyaltyPoints + "\n";
            output.setText(data);
        });

        // ================= SEARCH CUSTOMER =================
        searchCustomerBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "Search Name:");
            if (name == null) return;
            for (Customer c : DataStore.customers) {
                if (c.name.equalsIgnoreCase(name)) { output.setText("✔ Found: " + c.name); return; }
            }
            output.setText("❌ Not Found");
        });

        // ================= DELETE CUSTOMER =================
        deleteCustomerBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Enter Customer ID:");
            if (id == null) return;
            Customer customer = null;
            for (Customer c : DataStore.customers)
                if (c.id == id) { customer = c; break; }
            if (customer == null) { output.setText("❌ Customer Not Found"); return; }
            DataStore.orders.removeIf(o -> o.customer.id == id);
            DataStore.customers.remove(customer);
            FileManager.saveAll();
            output.setText("✔ Customer Deleted");
        });

        // ================= MAKE ORDER =================
        makeOrderBtn.addActionListener(e -> {
            Integer customerId = InputValidator.getInt(this, "Customer ID:");
            if (customerId == null) return;
            Customer customer = null;
            for (Customer c : DataStore.customers)
                if (c.id == customerId) { customer = c; break; }
            if (customer == null) { output.setText("❌ Customer Not Found"); return; }

            String mealsText = "";
            for (Meal m : DataStore.meals)
                mealsText += m.id + " - " + m.name + " - " + m.price + "\n";
            JOptionPane.showMessageDialog(this, mealsText);

            Integer mealId = InputValidator.getInt(this, "Enter Meal ID:");
            if (mealId == null) return;
            Meal selectedMeal = null;
            for (Meal m : DataStore.meals)
                if (m.id == mealId) { selectedMeal = m; break; }
            if (selectedMeal == null) { output.setText("❌ Meal Not Found"); return; }

            Order order = new Order(customer);
            order.meals.add(selectedMeal);
            order.checkout();
            DataStore.orders.add(order);
            FileManager.saveAll();
            Notification.show(this,
        "🧾 New Order Created\n" +
        "Customer: " + customer.name +
        "\nTotal: " + order.totalPrice
);

            // ✅ يظهر before/after لو فيه خصم
            if (order.discountAmount > 0) {
                output.setText(
                    "✔ Order Created!\n" +
                    "Before  : " + order.originalPrice + "\n" +
                    "Discount: " + (int)DataStore.marketingDiscount + "% (-" + order.discountAmount + ")\n" +
                    "After   : " + order.totalPrice
                );
            } else {
                output.setText("✔ Order Created | Total: " + order.totalPrice);
            }
        });

        // ================= CANCEL ORDER =================
        cancelOrderBtn.addActionListener(e -> {
            try {
                int custId = Integer.parseInt(JOptionPane.showInputDialog("Enter Customer ID"));
                Customer customer = null;
                for (Customer c : DataStore.customers)
                    if (c.id == custId) { customer = c; break; }
                if (customer == null) { JOptionPane.showMessageDialog(this, "Customer not found"); return; }

                String ordersInfo = customer.getOrdersInfo();
                if (ordersInfo == null || ordersInfo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No orders found"); return;
                }
                JOptionPane.showMessageDialog(this, ordersInfo);

                int orderId = Integer.parseInt(JOptionPane.showInputDialog("Enter Order ID to cancel"));
                boolean removed = customer.cancelOrder(orderId);
                if (removed) {
                    DataStore.orders.removeIf(o -> o.id == orderId);
                    FileManager.saveAll();
                    Notification.show(this,
        "❌ Order Cancelled\nCustomer: " + customer.name
);
                    JOptionPane.showMessageDialog(this, "✔ Order cancelled successfully");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Order not found");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        // ================= SHOW ORDERS =================
        showOrdersBtn.addActionListener(e -> {
            String data = "";
            for (Order o : DataStore.orders)
                data += "Order " + o.id + " | Customer: " + o.customer.name +
                        " | Total: " + o.totalPrice + "\n";
            output.setText(data);
        });

        // ================= BILLING =================
        billingBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            Customer customer = null;
            for (Customer c : DataStore.customers)
                if (c.id == id) { customer = c; break; }
            if (customer == null) { output.setText("❌ Customer Not Found"); return; }

            String giftsText = customer.gifts.isEmpty() ? "  None\n" : "";
            for (String g : customer.gifts) giftsText += "  🎁 " + g + "\n";

            // ✅ Billing مع before/after
            String billingText = "=== BILLING ===\n" +
                "Name       : " + customer.name + "\n\n";

            if (customer.marketingProgram && DataStore.marketingDiscount > 0) {
                billingText +=
                    "Before Discount : " + customer.totalOriginalPayments + "\n" +
                    "Discount ("+ (int)DataStore.marketingDiscount +"%): -" + customer.totalSaved + "\n" +
                    "After Discount  : " + customer.totalPayments + "\n\n";
            } else {
                billingText += "Total Payments : " + customer.totalPayments + "\n\n";
            }

            if (customer.loyaltyProgram && DataStore.loyaltyBonusPoints > 0) {
                billingText +=
                    "Base Points  : " + customer.basePoints + "\n" +
                    "Bonus Points : +" + customer.bonusPoints + "\n" +
                    "Total Points : " + customer.loyaltyPoints + "\n\n";
            } else {
                billingText += "Points : " + customer.loyaltyPoints + "\n\n";
            }

            billingText += "--- Gifts ---\n" + giftsText;

            output.setText(billingText);
            output.setCaretPosition(0);
            Notification.show(this,
        "💰 Billing Generated\nCustomer: " + customer.name +
        "\nTotal: " + customer.totalPayments
);
        });

        // ================= PROFILE =================
        profileBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;

            for (Customer c : DataStore.customers) {
                if (c.id == id) {

                    String giftsText = c.gifts.isEmpty() ? "  None\n" : "";
                    for (String g : c.gifts) giftsText += "  🎁 " + g + "\n";

                    String offersText = c.offers.isEmpty() ? "  None\n" : "";
                    for (String o : c.offers) offersText += "  🏷 " + o + "\n";

                    String ordersText = c.getOrdersInfo().isEmpty() ? "  No Orders\n" : c.getOrdersInfo();

                    // ✅ Programs مع before/after
                    String marketingLine = "  🏷 Marketing : ";
                    if (c.marketingProgram) {
                        marketingLine += "✔ Joined - " + (int)DataStore.marketingDiscount + "% Discount\n" +
                            "             Before: " + c.totalOriginalPayments +
                            " | Saved: " + c.totalSaved +
                            " | After: " + c.totalPayments + "\n";
                    } else {
                        marketingLine += "✘ Not Joined\n";
                    }

                    String loyaltyLine = "  ⭐ Loyalty   : ";
                    if (c.loyaltyProgram) {
                        loyaltyLine += "✔ Joined - +" + DataStore.loyaltyBonusPoints + " Bonus Points/order\n" +
                            "             Base: " + c.basePoints +
                            " | Bonus: +" + c.bonusPoints +
                            " | Total: " + c.loyaltyPoints + "\n";
                    } else {
                        loyaltyLine += "✘ Not Joined\n";
                    }

                    String rewardLine = "  🎁 Reward    : " +
                        (c.rewardProgram ? "✔ Joined - " + DataStore.rewardReward : "✘ Not Joined") + "\n";

                    output.setText(
                        "=== CUSTOMER PROFILE ===\n" +
                        "Name    : " + c.name + "\n" +
                        "ID      : " + c.id   + "\n\n" +
                        "Payments: " + c.totalPayments + "\n" +
                        "Points  : " + c.loyaltyPoints + "\n\n" +
                        "--- Orders ---\n"   + ordersText + "\n" +
                        "--- Gifts ---\n"    + giftsText  + "\n" +
                        "--- Offers ---\n"   + offersText + "\n" +
                        "--- Programs ---\n" + marketingLine + loyaltyLine + rewardLine
                    );
                    output.setCaretPosition(0);
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        // ================= UPDATE INFO =================
      updateInfoBtn.addActionListener(e -> {

    String name = InputValidator.getNameOnly(this, "New Name:");
    String username = InputValidator.getText(this, "New Username:");

    if (name == null || username == null) return;

    // 🔴 التحقق من تكرار username
    for (Employee emp : DataStore.employees) {

        if (emp.id != currentUser.id &&
            emp.username.equalsIgnoreCase(username)) {

            JOptionPane.showMessageDialog(this,
                    "❌ Username already exists");

            return;
        }
    }

    String password = InputValidator.getText(this, "New Password:");
    if (password == null) return;

    // update current user
    currentUser.name = name;
    currentUser.username = username;
    currentUser.password = password;

    // update in DataStore
    for (Employee emp : DataStore.employees) {
        if (emp.id == currentUser.id) {
            emp.name = name;
            emp.username = username;
            emp.password = password;
            break;
        }
    }

    FileManager.saveAll();
    output.setText("✔ Updated Successfully");
});
        // ================= MARKETING =================
        marketingBtn.addActionListener(e -> {
            if (DataStore.marketingDiscount <= 0) {
                output.setText("❌ Marketing Program not set by Admin yet");
                return;
            }
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (c.marketingProgram) { output.setText("⚠ Already in Marketing Program"); return; }
                    c.registerMarketing();
                    Notification.show(this,
        "🏷 Joined Marketing Program\nDiscount: " +
        (int)DataStore.marketingDiscount + "%"
);
                    FileManager.saveAll();
                    output.setText("✔ Joined Marketing Program\nDiscount: " + (int)DataStore.marketingDiscount + "% on every order");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // ================= LOYALTY =================
        loyaltyBtn.addActionListener(e -> {
            if (DataStore.loyaltyBonusPoints <= 0) {
                output.setText("❌ Loyalty Program not set by Admin yet");
                return;
            }
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (c.loyaltyProgram) { output.setText("⚠ Already in Loyalty Program"); return; }
                    c.registerLoyalty();
                    Notification.show(this,
        "⭐ Joined Loyalty Program\nBonus: +" +
        DataStore.loyaltyBonusPoints
);
                    FileManager.saveAll();
                    output.setText("✔ Joined Loyalty Program\nBonus: +" + DataStore.loyaltyBonusPoints + " Points per order");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // ================= REWARD =================
        rewardBtn.addActionListener(e -> {
            if (DataStore.rewardReward.isEmpty()) {
                output.setText("❌ Reward Program not set by Admin yet");
                return;
            }
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (c.rewardProgram) { output.setText("⚠ Already in Reward Program"); return; }
                    c.registerReward();
                    Notification.show(this,
        "🎁 Reward Activated\n" + DataStore.rewardReward
);
                    FileManager.saveAll();
                    output.setText("✔ Joined Reward Program\nOffer: " + DataStore.rewardReward);
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });
        // ================= CANCEL MARKETING =================
cancelMarketingBtn.addActionListener(e -> {

    Integer id = InputValidator.getInt(this, "Customer ID:");
    if (id == null) return;

    for (Customer c : DataStore.customers) {

        if (c.id == id) {

            if (!c.marketingProgram) {
                output.setText("❌ Customer not in Marketing Program");
                return;
            }

            c.marketingProgram = false;

            FileManager.saveAll();

            output.setText("✔ Marketing Program Cancelled");
            return;
        }
    }

    output.setText("❌ Customer Not Found");
});

// ================= CANCEL LOYALTY =================
cancelLoyaltyBtn.addActionListener(e -> {

    Integer id = InputValidator.getInt(this, "Customer ID:");
    if (id == null) return;

    for (Customer c : DataStore.customers) {

        if (c.id == id) {

            if (!c.loyaltyProgram) {
                output.setText("❌ Customer not in Loyalty Program");
                return;
            }

            c.loyaltyProgram = false;

            FileManager.saveAll();

            output.setText("✔ Loyalty Program Cancelled");
            return;
        }
    }

    output.setText("❌ Customer Not Found");
});

// ================= CANCEL REWARD =================
cancelRewardBtn.addActionListener(e -> {

    Integer id = InputValidator.getInt(this, "Customer ID:");
    if (id == null) return;

    for (Customer c : DataStore.customers) {

        if (c.id == id) {

            if (!c.rewardProgram) {
                output.setText("❌ Customer not in Reward Program");
                return;
            }

            c.rewardProgram = false;

            FileManager.saveAll();

            output.setText("✔ Reward Program Cancelled");
            return;
        }
    }

    output.setText("❌ Customer Not Found");
});

        // ================= LOGOUT =================
        logoutBtn.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        setVisible(true);
    }
}
