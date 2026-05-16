import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileWriter;

public class AdminDashboard extends JFrame {

    User currentUser;

   JButton addEmpBtn, showEmpBtn, deleteEmpBtn, updateEmpBtn, searchEmpBtn;
JButton addMealBtn, showMealBtn, updateMealBtn, searchMealBtn, deleteMealBtn;
JButton addOfferBtn, employeeReportBtn, mealReportBtn;
JButton updateAdminInfoBtn, logoutBtn;
JButton customerReportBtn;

JButton setMarketingBtn, setLoyaltyBtn, setRewardBtn, showProgramsBtn;

JButton extractFullReportBtn;

    JTextArea output;

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder());
    }

    private void saveToFile(String content, String defaultName) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File(defaultName));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text & CSV Files", "txt", "csv");
        chooser.setFileFilter(filter);
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(chooser.getSelectedFile());
                fw.write(content);
                fw.close();
                JOptionPane.showMessageDialog(this, "✔ Report Saved Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error saving file: " + ex.getMessage());
            }
        }
    }

    public AdminDashboard(User user) {

        this.currentUser = user;

        setTitle("Admin Dashboard");
        setSize(700, 680);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 40));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Admin Dashboard");
        title.setBounds(250, 0, 300, 35);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        // ================= EMPLOYEES =================
        addEmpBtn    = new JButton("Add Emp");
        showEmpBtn   = new JButton("Show Emp");
        deleteEmpBtn = new JButton("Delete Emp");
        updateEmpBtn = new JButton("Update Emp");
        searchEmpBtn = new JButton("Search Emp");

        // ================= MEALS =================
        addMealBtn    = new JButton("Add Meal");
        showMealBtn   = new JButton("Show Meals");
        updateMealBtn = new JButton("Update Meal");
        searchMealBtn = new JButton("Search Meal");
        deleteMealBtn = new JButton("Delete Meal");

        // ================= OFFERS / REPORTS =================
        addOfferBtn       = new JButton("Add Offer");
        employeeReportBtn = new JButton("Emp Report");
        customerReportBtn = new JButton("Customer Report");
        mealReportBtn     = new JButton("Meal Report");

        // ================= ADMIN INFO / LOGOUT =================
        updateAdminInfoBtn = new JButton("Update Admin Info");

        // ================= PROGRAMS =================
        setMarketingBtn = new JButton("Marketing % 🏷");
        setLoyaltyBtn   = new JButton("Loyalty Pts ⭐");
        setRewardBtn    = new JButton("Set Reward 🎁");
        showProgramsBtn = new JButton("Show Programs");
        logoutBtn       = new JButton("Logout");

        extractFullReportBtn = new JButton("📥 Extract Full Report");

        int x1 = 20;
        int x2 = 160;
        int x3 = 300;
        int x4 = 440;
        int x5 = 580;

        int w = 120;
        int h = 30;

        // ================= ROW 1 - EMPLOYEES =================
        addEmpBtn.setBounds(x1, 40, w, h);
        showEmpBtn.setBounds(x2, 40, w, h);
        deleteEmpBtn.setBounds(x3, 40, w, h);
        updateEmpBtn.setBounds(x4, 40, w, h);
        searchEmpBtn.setBounds(x5, 40, w, h);

        // ================= ROW 2 - MEALS =================
        addMealBtn.setBounds(x1, 80, w, h);
        showMealBtn.setBounds(x2, 80, w, h);
        updateMealBtn.setBounds(x3, 80, w, h);
        searchMealBtn.setBounds(x4, 80, w, h);
        deleteMealBtn.setBounds(x5, 80, w, h);

        // ================= ROW 3 - REPORTS =================
        addOfferBtn.setBounds(x1, 120, w, h);
        employeeReportBtn.setBounds(x2, 120, w, h);
        mealReportBtn.setBounds(x3, 120, w, h);
        customerReportBtn.setBounds(x4, 120, w, h);
        updateAdminInfoBtn.setBounds(x5, 120, w, h);

        // ================= ROW 4 - PROGRAMS =================
        setMarketingBtn.setBounds(x1, 160, w, h);
        setLoyaltyBtn.setBounds(x2, 160, w, h);
        setRewardBtn.setBounds(x3, 160, w, h);
        showProgramsBtn.setBounds(x4, 160, w, h);
        logoutBtn.setBounds(x5, 160, w, h);

        // ================= ROW 5 - EXTRACT =================
        extractFullReportBtn.setBounds(230, 200, 220, 30);

        // ================= OUTPUT =================
        output = new JTextArea();
        output.setBackground(new Color(45, 45, 60));
        output.setForeground(Color.WHITE);
        output.setFont(new Font("Consolas", Font.PLAIN, 15));
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(20, 240, 645, 390);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(45, 45, 60));

        // ===== تنسيق =====
        styleButton(addEmpBtn);    styleButton(showEmpBtn);
        styleButton(deleteEmpBtn); styleButton(updateEmpBtn); styleButton(searchEmpBtn);
        styleButton(addMealBtn);   styleButton(showMealBtn);
        styleButton(updateMealBtn);styleButton(searchMealBtn);
        styleButton(deleteMealBtn);
        styleButton(addOfferBtn);  styleButton(employeeReportBtn); styleButton(customerReportBtn); styleButton(mealReportBtn);
        styleButton(updateAdminInfoBtn); styleButton(logoutBtn);
        styleButton(setMarketingBtn);
        styleButton(setLoyaltyBtn);
        styleButton(setRewardBtn); styleButton(showProgramsBtn);

        extractFullReportBtn.setBackground(new Color(34, 139, 34));
        extractFullReportBtn.setForeground(Color.WHITE);
        extractFullReportBtn.setFocusPainted(false);
        extractFullReportBtn.setFont(new Font("Arial", Font.BOLD, 13));
        extractFullReportBtn.setBorder(BorderFactory.createEmptyBorder());

        // ===== إضافة =====
        add(addEmpBtn); add(showEmpBtn); add(deleteEmpBtn); add(updateEmpBtn); add(searchEmpBtn);
        add(addMealBtn); add(showMealBtn); add(updateMealBtn); add(searchMealBtn); add(deleteMealBtn);
        add(addOfferBtn); add(employeeReportBtn); add(mealReportBtn);
        add(updateAdminInfoBtn); add(logoutBtn);
        add(setMarketingBtn); add(setLoyaltyBtn); add(setRewardBtn); add(showProgramsBtn); add(customerReportBtn);
        add(extractFullReportBtn);
        add(scrollPane);

        // ================= EMPLOYEES ACTIONS =================
        addEmpBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "Name:");
            if (name == null) return;
            String username;
            while (true) {
                username = InputValidator.getUsername(this, "Username:");
                if (username == null) return;
                if (InputValidator.usernameExists(username)) {
                    JOptionPane.showMessageDialog(this, "❌ Username already exists");
                    continue;
                }
                break;
            }
            String password = InputValidator.getPassword(this, "Password:");
            if (password == null) return;
            DataStore.employees.add(new Employee(DataStore.employees.size() + 1, name, username, password));
            FileManager.saveAll();
            output.setText("✔ Employee Added Successfully");
        });

        showEmpBtn.addActionListener(e -> {
            String text = "";
            for (Employee emp : DataStore.employees)
                text += emp.id + " - " + emp.name + " - " + emp.username + "\n";
            output.setText(text);
        });

        deleteEmpBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "ID:");
            if (id == null) return;
            DataStore.employees.removeIf(emp -> emp.id == id);
            FileManager.saveAll();
            output.setText("✔ Deleted");
        });

        updateEmpBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "ID:");
            if (id == null) return;
            for (Employee emp : DataStore.employees) {
                if (emp.id == id) {
                    String name = InputValidator.getNameOnly(this, "New Name:");
                    if (name == null) return;
                    String username;
                    while (true) {
                        username = InputValidator.getUsername(this, "New Username:");
                        if (username == null) return;
                        if (InputValidator.usernameExists(username) && !username.equals(emp.username)) {
                            JOptionPane.showMessageDialog(this, "❌ Username already exists");
                            continue;
                        }
                        break;
                    }
                    String password = InputValidator.getPassword(this, "New Password:");
                    if (password == null) return;
                    emp.name = name;
                    emp.username = username;
                    emp.password = password;
                    FileManager.saveAll();
                    output.setText("✔ Employee Updated");
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        searchEmpBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Search ID:");
            if (id == null) return;
            for (Employee emp : DataStore.employees) {
                if (emp.id == id) {
                    output.setText(emp.id + " - " + emp.name + " - " + emp.username);
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        // ================= MEALS ACTIONS =================
        addMealBtn.addActionListener(e -> {
            String name = InputValidator.getText(this, "Meal Name:");
            if (name == null) return;
            Double price = InputValidator.getDouble(this, "Price:");
            if (price == null) return;
            DataStore.meals.add(new Meal(DataStore.meals.size() + 1, name, price));
            FileManager.saveAll();
            output.setText("✔ Meal Added");
        });

        showMealBtn.addActionListener(e -> {
            String text = "";
            for (Meal m : DataStore.meals)
                text += m.id + " - " + m.name + " - " + m.price + "\n";
            output.setText(text);
        });

        updateMealBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Meal ID:");
            if (id == null) return;
            for (Meal m : DataStore.meals) {
                if (m.id == id) {
                    String name = InputValidator.getText(this, "New Name:");
                    if (name == null) return;
                    Double price = InputValidator.getDouble(this, "New Price:");
                    if (price == null) return;
                    m.name = name;
                    m.price = price;
                    FileManager.saveAll();
                    output.setText("✔ Meal Updated");
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        deleteMealBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Meal ID:");
            if (id == null) return;
            DataStore.meals.removeIf(m -> m.id == id);
            FileManager.saveAll();
            output.setText("✔ Meal Deleted");
        });

        searchMealBtn.addActionListener(e -> {
            String name = InputValidator.getText(this, "Search Meal:");
            if (name == null) return;
            for (Meal m : DataStore.meals) {
                if (m.name.equalsIgnoreCase(name)) {
                    output.setText(m.id + " - " + m.name + " - " + m.price);
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        // ================= OFFERS =================
        addOfferBtn.addActionListener(e -> {
            String offer;
            while (true) {
                offer = InputValidator.getText(this, "Offer:");
                if (offer == null) return;
                offer = offer.trim();
                if (offer.isEmpty()) {
                    Notification.show(this, "❌ Offer cannot be empty");
                    continue;
                }
                boolean exists = false;
                for (String o : DataStore.offers) {
                    if (o.equalsIgnoreCase(offer)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    Notification.show(this, "❌ Offer already exists");
                    continue;
                }
                break;
            }
            DataStore.offers.add(offer);
            FileManager.saveAll();
            for (Customer c : DataStore.customers) {
                c.addOffer(offer);
            }
            Notification.show(this, "📢 New Offer Added!\n" + offer);
            output.setText("✔ Offer Added Successfully");
        });

        // ================= REPORTS =================
        employeeReportBtn.addActionListener(e -> {
            String report = "Employees Report:\n";
            for (Employee emp : DataStore.employees) {
                report += emp.id + " - " + emp.name + "\n";
            }
            output.setText(report);
        });

        customerReportBtn.addActionListener(e -> {
            if (DataStore.customers.isEmpty()) {
                output.setText("❌ No Customers Found");
                return;
            }
            String report = "=== CUSTOMER REPORT (Employee View) ===\n\n";
            double totalRevenue = 0;
            for (Customer c : DataStore.customers) {
                totalRevenue += c.totalPayments;
                report += "ID: " + c.id + "\n";
                report += "Name: " + c.name + "\n";
                report += "Payments: " + c.totalPayments + "\n";
                report += "Points: " + c.loyaltyPoints + "\n";
                report += "Marketing: " + (c.marketingProgram ? "✔" : "✘") + "\n";
                report += "Loyalty: " + (c.loyaltyProgram ? "✔" : "✘") + "\n";
                report += "Reward: " + (c.rewardProgram ? "✔" : "✘") + "\n";
                report += "----------------------\n";
            }
            report += "\nTotal Customers: " + DataStore.customers.size();
            report += "\nTotal Revenue: " + totalRevenue;
            output.setText(report);
        });

        mealReportBtn.addActionListener(e -> {
            String report = "Meals Report:\n";
            for (Meal m : DataStore.meals) {
                report += m.id + " - " + m.name + " - " + m.price + "\n";
            }
            output.setText(report);
        });

        // ================= ADMIN UPDATE =================
        updateAdminInfoBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "New Name:");
            if (name == null) return;
            String username = InputValidator.getUsername(this, "New Username:");
            if (username == null) return;
            String password = InputValidator.getPassword(this, "New Password:");
            if (password == null) return;
            currentUser.name = name;
            currentUser.username = username;
            currentUser.password = password;
            FileManager.saveAll();
            output.setText("✔ Admin Updated");
        });

        // ================= LOGOUT =================
        logoutBtn.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        // ================= SET MARKETING % =================
        setMarketingBtn.addActionListener(e -> {
            String input = InputValidator.getText(this, "Marketing Discount %:\n(e.g. 10 for 10%, 50 for 50%)");
            if (input == null) return;
            try {
                double discount = Double.parseDouble(input.trim());
                if (discount <= 0 || discount > 100) {
                    output.setText("❌ Enter a number between 1 and 100");
                    return;
                }
                DataStore.marketingDiscount = discount;
                FileManager.saveAll();
                for (Customer c : DataStore.customers) {
                    c.marketingProgram = true;
                }
                Notification.show(this, "🏷 Marketing updated: " + discount + "%");
                output.setText(
                    "✔ Marketing Program Set!\n" +
                    "Discount: " + (int)discount + "% on every order\n\n" +
                    "Example:\n" +
                    "  Order 100  →  After discount: " + (100 - discount) + "\n" +
                    "  Order 200  →  After discount: " + (200 - 200 * discount / 100)
                );
            } catch (NumberFormatException ex) {
                output.setText("❌ Invalid number");
            }
        });

        // ================= SET LOYALTY BONUS POINTS =================
        setLoyaltyBtn.addActionListener(e -> {
            String input = InputValidator.getText(this, "Loyalty Bonus Points per order:\n(e.g. 5 means +5 extra points per order)");
            if (input == null) return;
            try {
                int bonus = Integer.parseInt(input.trim());
                if (bonus <= 0) {
                    output.setText("❌ Enter a positive number");
                    return;
                }
                DataStore.loyaltyBonusPoints = bonus;
                FileManager.saveAll();
                Notification.show(this, "⭐ Loyalty updated: +" + bonus + " points per order");
                output.setText(
                    "✔ Loyalty Program Set!\n" +
                    "Bonus: +" + bonus + " extra points per order\n\n" +
                    "Example (order = 100):\n" +
                    "  Base Points  : " + (100 / 10) + "\n" +
                    "  Bonus Points : +" + bonus + "\n" +
                    "  Total Points : " + (100 / 10 + bonus)
                );
            } catch (NumberFormatException ex) {
                output.setText("❌ Invalid number");
            }
        });

        // ================= SET REWARD =================
        setRewardBtn.addActionListener(e -> {
            String reward = InputValidator.getText(this, "Reward Program Offer:\n(e.g. Free Dessert every 5 orders)");
            if (reward == null || reward.trim().isEmpty()) return;
            DataStore.rewardReward = reward.trim();
            FileManager.saveAll();
            for (Customer c : DataStore.customers) {
                c.rewardProgram = true;
            }
            Notification.show(this, "🎁 New Reward: " + reward);
            output.setText("✔ Reward Program Set!\nOffer: " + DataStore.rewardReward);
        });

        // ================= SHOW PROGRAMS =================
        showProgramsBtn.addActionListener(e -> {
            String text =
                "=== Programs Status ===\n\n" +
                "🏷 Marketing : " + (DataStore.marketingDiscount > 0
                    ? (int)DataStore.marketingDiscount + "% Discount per order"
                    : "Not Set") + "\n" +
                "⭐ Loyalty   : " + (DataStore.loyaltyBonusPoints > 0
                    ? "+" + DataStore.loyaltyBonusPoints + " Bonus Points per order"
                    : "Not Set") + "\n" +
                "🎁 Reward    : " + (DataStore.rewardReward.isEmpty()
                    ? "Not Set"
                    : DataStore.rewardReward) + "\n\n" +
                "=== Customers Subscriptions ===\n";
            for (Customer c : DataStore.customers) {
                text += c.name + " → " +
                    "Marketing: " + (c.marketingProgram ? "✔" : "✘") +
                    " | Loyalty: " + (c.loyaltyProgram  ? "✔" : "✘") +
                    " | Reward: "  + (c.rewardProgram   ? "✔" : "✘") + "\n";
            }
            output.setText(text);
            Notification.show(this, "📊 Programs status loaded");
            output.setCaretPosition(0);
        });

        // ✅ ================= EXTRACT FULL SYSTEM REPORT =================
        extractFullReportBtn.addActionListener(e -> {

            StringBuilder report = new StringBuilder();

            report.append("===== RESTAURANT FULL SYSTEM REPORT =====\n");
            report.append("Generated: ").append(new java.util.Date()).append("\n\n");

            // --- Employees ---
            report.append("=== Employees (").append(DataStore.employees.size()).append(") ===\n");
            for (Employee emp : DataStore.employees) {
                report.append(emp.id).append(" | ")
                      .append(emp.name).append(" | ")
                      .append(emp.username).append("\n");
            }

            // --- Customers ---
            report.append("\n=== Customers (").append(DataStore.customers.size()).append(") ===\n");
            double totalRevenue = 0;
            for (Customer c : DataStore.customers) {
                totalRevenue += c.totalPayments;
                report.append(c.id).append(" | ")
                      .append(c.name)
                      .append(" | Payments: ").append(c.totalPayments)
                      .append(" | Points: ").append(c.loyaltyPoints)
                      .append(" | Marketing: ").append(c.marketingProgram ? "✔" : "✘")
                      .append(" | Loyalty: ").append(c.loyaltyProgram ? "✔" : "✘")
                      .append(" | Reward: ").append(c.rewardProgram ? "✔" : "✘")
                      .append("\n");
            }
            report.append("Total Revenue: ").append(totalRevenue).append("\n");

            // --- Meals ---
            report.append("\n=== Meals (").append(DataStore.meals.size()).append(") ===\n");
            for (Meal m : DataStore.meals) {
                report.append(m.id).append(" | ")
                      .append(m.name).append(" | ")
                      .append(m.price).append("\n");
            }

            // --- Orders ---
            report.append("\n=== Orders ===\n");
            for (Customer c : DataStore.customers) {
                for (Order o : c.orders) {
                    report.append("Order ID: ").append(o.id)
                          .append(" | Customer: ").append(o.customer.name)
                          .append(" | Original Price: ").append(o.originalPrice)
                          .append(" | Discount: ").append(o.discountAmount)
                          .append(" | Total Paid: ").append(o.totalPrice)
                          .append(" | Meals: ");
                    for (Meal m : o.meals) {
                        report.append(m.name).append(" ");
                    }
                    report.append("\n");
                }
            }

            report.append("\n==========================================\n");

            // عرض في الـ output
            output.setText(report.toString());
            output.setCaretPosition(0);

            // حفظ في ملف
            saveToFile(report.toString(), "SystemReport.txt");
        });

        setVisible(true);
    }
}