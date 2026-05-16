import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeDashboard extends JFrame {

    User currentUser;

    // ===== Buttons =====
    JButton addCustomerBtn, showCustomerBtn, searchCustomerBtn, deleteCustomerBtn;
    JButton makeOrderBtn, cancelOrderBtn, showOrdersBtn, billingBtn;
    JButton profileBtn, updateInfoBtn, logoutBtn;
    JButton marketingBtn, loyaltyBtn, rewardBtn;
    JButton cancelMarketingBtn, cancelLoyaltyBtn, cancelRewardBtn;

    JTextArea output;

    // ===== Color Palette (same as AdminDashboard) =====
    static final Color BG         = new Color(15, 17, 23);
    static final Color SURFACE    = new Color(26, 29, 39);
    static final Color SURFACE2   = new Color(34, 38, 58);
    static final Color ACCENT     = new Color(79, 142, 247);
    static final Color GREEN      = new Color(34, 201, 123);
    static final Color RED_CLR    = new Color(255, 90, 90);
    static final Color AMBER      = new Color(245, 166, 35);
    static final Color TEXT       = new Color(232, 234, 240);
    static final Color TEXT_MUTED = new Color(122, 127, 154);
    static final Color BORDER     = new Color(255, 255, 255, 18);

    // ===== Button Styles =====
    enum BtnStyle { DEFAULT, DANGER, SUCCESS, WARN, EXTRACT }

    private JButton makeBtn(String label, BtnStyle style) {
        JButton btn = new JButton(label) {
            boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arc = 10;
                Color bg, border, fg;
                switch (style) {
                    case DANGER:
                        bg     = hovered ? new Color(60, 20, 20) : SURFACE2;
                        border = hovered ? RED_CLR : BORDER;
                        fg     = hovered ? RED_CLR : TEXT_MUTED;
                        break;
                    case SUCCESS:
                        bg     = hovered ? new Color(10, 50, 30) : SURFACE2;
                        border = hovered ? GREEN : new Color(34, 201, 123, 40);
                        fg     = hovered ? GREEN : TEXT_MUTED;
                        break;
                    case WARN:
                        bg     = hovered ? new Color(50, 38, 10) : SURFACE2;
                        border = hovered ? AMBER : BORDER;
                        fg     = hovered ? AMBER : TEXT_MUTED;
                        break;
                    case EXTRACT:
                        bg     = hovered ? new Color(20, 70, 45) : new Color(34, 201, 123, 25);
                        border = new Color(34, 201, 123, 80);
                        fg     = GREEN;
                        break;
                    default:
                        bg     = hovered ? new Color(42, 47, 71) : SURFACE2;
                        border = hovered ? new Color(79, 142, 247, 80) : BORDER;
                        fg     = hovered ? ACCENT : TEXT_MUTED;
                        break;
                }
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.setColor(border);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
                g2.setColor(fg);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel sectionLabel(String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        JLabel lbl = new JLabel(text.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        p.add(lbl);
        return p;
    }

    private JPanel makeStatCard(String label, String value, String sub, Color subColor) {
        JPanel card = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(0, 95));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_MUTED);
        lbl.setBounds(14, 12, 220, 18);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 26));
        val.setForeground(TEXT);
        val.setBounds(14, 32, 220, 34);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLbl.setForeground(subColor);
        subLbl.setBounds(14, 68, 220, 16);

        card.add(lbl); card.add(val); card.add(subLbl);
        return card;
    }

    public EmployeeDashboard(User user) {
        this.currentUser = user;

        setTitle("Employee Dashboard");
        setSize(760, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== ROOT =====
        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setOpaque(false);
        setContentPane(root);

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(SURFACE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(BORDER);
                g2.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
                g2.dispose();
            }
        };
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(0, 46));
        topBar.setBorder(new EmptyBorder(0, 16, 0, 16));

        JLabel titleLbl = new JLabel("  Restaurant Management System");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLbl.setForeground(TEXT);

        JLabel userChip = new JLabel("  " + user.name + "  ");
        userChip.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userChip.setForeground(TEXT_MUTED);
        userChip.setOpaque(true);
        userChip.setBackground(SURFACE2);
        userChip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));

        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(userChip, BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(SURFACE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(BORDER);
                g2.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
                g2.dispose();
            }
        };
        sidebar.setOpaque(false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(54, 0));
        sidebar.setBorder(new EmptyBorder(10, 8, 10, 8));

        String[][] icons = {
            {"⊞", "Overview"},
            {"👤", "Customers"},
            {"🧾", "Orders"},
            {"💰", "Billing"},
            {"🎁", "Programs"}
        };
        for (String[] ic : icons) {
            JButton sb = new JButton(ic[0]);
            sb.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            sb.setForeground(TEXT_MUTED);
            sb.setContentAreaFilled(false);
            sb.setBorderPainted(false);
            sb.setFocusPainted(false);
            sb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            sb.setMaximumSize(new Dimension(38, 38));
            sb.setPreferredSize(new Dimension(38, 38));
            sb.setAlignmentX(Component.CENTER_ALIGNMENT);
            sb.setToolTipText(ic[1]);
            sidebar.add(sb);
            sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
        }
        sidebar.add(Box.createVerticalGlue());

        logoutBtn = makeBtn("⎋", BtnStyle.WARN);
        logoutBtn.setMaximumSize(new Dimension(38, 38));
        logoutBtn.setPreferredSize(new Dimension(38, 38));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setToolTipText("Logout");
        sidebar.add(logoutBtn);

        root.add(sidebar, BorderLayout.WEST);

        // ===== MAIN =====
        JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(false);

        // ===== ALL BUTTONS =====
        addCustomerBtn    = makeBtn("＋ Add Customer",    BtnStyle.SUCCESS);
        showCustomerBtn   = makeBtn("≡ Show Customers",   BtnStyle.DEFAULT);
        searchCustomerBtn = makeBtn("⌕ Search Customer",  BtnStyle.DEFAULT);
        deleteCustomerBtn = makeBtn("✕ Delete Customer",  BtnStyle.DANGER);

        makeOrderBtn   = makeBtn("＋ Make Order",   BtnStyle.SUCCESS);
        cancelOrderBtn = makeBtn("✕ Cancel Order",  BtnStyle.DANGER);
        showOrdersBtn  = makeBtn("≡ Show Orders",   BtnStyle.DEFAULT);
        billingBtn     = makeBtn("💰 Billing",       BtnStyle.DEFAULT);

        profileBtn    = makeBtn("◉ Customer Profile", BtnStyle.DEFAULT);
        updateInfoBtn = makeBtn("✎ Update Info",      BtnStyle.DEFAULT);

        marketingBtn = makeBtn("% Marketing",     BtnStyle.WARN);
        loyaltyBtn   = makeBtn("★ Loyalty",        BtnStyle.WARN);
        rewardBtn    = makeBtn("🎁 Reward",         BtnStyle.WARN);

        cancelMarketingBtn = makeBtn("✕ Cancel Marketing", BtnStyle.DANGER);
        cancelLoyaltyBtn   = makeBtn("✕ Cancel Loyalty",   BtnStyle.DANGER);
        cancelRewardBtn    = makeBtn("✕ Cancel Reward",     BtnStyle.DANGER);

        // ===== CONTENT (BorderLayout: NORTH = buttons, CENTER = scroll) =====
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel topSection = new JPanel();
        topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        // --- Stat Cards ---
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 10, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        statsRow.add(makeStatCard("Total Customers", String.valueOf(DataStore.customers.size()), "Registered in system", ACCENT));
        statsRow.add(makeStatCard("Total Orders",    String.valueOf(DataStore.orders.size()),    "All time orders", GREEN));
        statsRow.add(makeStatCard("Active Programs",  "3", "Marketing · Loyalty · Reward", AMBER));
        topSection.add(statsRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 12)));

        // --- Customer Management ---
        topSection.add(sectionLabel("Customer Management"));
        JPanel custRow = new JPanel(new GridLayout(1, 4, 8, 0));
        custRow.setOpaque(false);
        custRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{addCustomerBtn, showCustomerBtn, searchCustomerBtn, deleteCustomerBtn})
            custRow.add(b);
        topSection.add(custRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Orders ---
        topSection.add(sectionLabel("Orders"));
        JPanel orderRow = new JPanel(new GridLayout(1, 4, 8, 0));
        orderRow.setOpaque(false);
        orderRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{makeOrderBtn, cancelOrderBtn, showOrdersBtn, billingBtn})
            orderRow.add(b);
        topSection.add(orderRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Profile & Info ---
        topSection.add(sectionLabel("Profile & Info"));
        JPanel infoRow = new JPanel(new GridLayout(1, 2, 8, 0));
        infoRow.setOpaque(false);
        infoRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{profileBtn, updateInfoBtn})
            infoRow.add(b);
        topSection.add(infoRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Programs ---
        topSection.add(sectionLabel("Programs — Join"));
        JPanel progRow = new JPanel(new GridLayout(1, 3, 8, 0));
        progRow.setOpaque(false);
        progRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{marketingBtn, loyaltyBtn, rewardBtn})
            progRow.add(b);
        topSection.add(progRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 8)));

        // --- Programs Cancel ---
        topSection.add(sectionLabel("Programs — Cancel"));
        JPanel cancelRow = new JPanel(new GridLayout(1, 3, 8, 0));
        cancelRow.setOpaque(false);
        cancelRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{cancelMarketingBtn, cancelLoyaltyBtn, cancelRewardBtn})
            cancelRow.add(b);
        topSection.add(cancelRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        content.add(topSection, BorderLayout.NORTH);

        // ===== OUTPUT SCROLL — fills all remaining space =====
        output = new JTextArea();
        output.setBackground(SURFACE2);
        output.setForeground(new Color(160, 228, 184));
        output.setFont(new Font("Consolas", Font.PLAIN, 13));
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setBorder(new EmptyBorder(10, 12, 10, 12));
        output.setText("// Output area\n✔ System ready — Welcome back, " + user.name);

        JScrollPane scroll = new JScrollPane(output) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(SURFACE2);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        content.add(scroll, BorderLayout.CENTER);
        main.add(content, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);

        // ================= ACTION LISTENERS =================

        // ADD CUSTOMER
        addCustomerBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "Customer Name:");
            if (name == null) return;
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

        // SHOW CUSTOMERS
        showCustomerBtn.addActionListener(e -> {
            StringBuilder data = new StringBuilder();
            for (Customer c : DataStore.customers)
                data.append(c.id).append(" - ").append(c.name)
                    .append(" | Payments: ").append(c.totalPayments)
                    .append(" | Points: ").append(c.loyaltyPoints).append("\n");
            output.setText(data.toString());
        });

        // SEARCH CUSTOMER
        searchCustomerBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "Search Name:");
            if (name == null) return;
            for (Customer c : DataStore.customers) {
                if (c.name.equalsIgnoreCase(name)) { output.setText("✔ Found: " + c.name); return; }
            }
            output.setText("❌ Not Found");
        });

        // DELETE CUSTOMER
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

        // MAKE ORDER
        makeOrderBtn.addActionListener(e -> {
            Integer customerId = InputValidator.getInt(this, "Customer ID:");
            if (customerId == null) return;
            Customer customer = null;
            for (Customer c : DataStore.customers)
                if (c.id == customerId) { customer = c; break; }
            if (customer == null) { output.setText("❌ Customer Not Found"); return; }

            StringBuilder mealsText = new StringBuilder();
            for (Meal m : DataStore.meals)
                mealsText.append(m.id).append(" - ").append(m.name).append(" - ").append(m.price).append("\n");
            JOptionPane.showMessageDialog(this, mealsText.toString());

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
                "🧾 New Order Created\nCustomer: " + customer.name + "\nTotal: " + order.totalPrice);

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

        // CANCEL ORDER
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
                    Notification.show(this, "❌ Order Cancelled\nCustomer: " + customer.name);
                    JOptionPane.showMessageDialog(this, "✔ Order cancelled successfully");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Order not found");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        // SHOW ORDERS
        showOrdersBtn.addActionListener(e -> {
            StringBuilder data = new StringBuilder();
            for (Order o : DataStore.orders)
                data.append("Order ").append(o.id)
                    .append(" | Customer: ").append(o.customer.name)
                    .append(" | Total: ").append(o.totalPrice).append("\n");
            output.setText(data.toString());
        });

        // BILLING
        billingBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            Customer customer = null;
            for (Customer c : DataStore.customers)
                if (c.id == id) { customer = c; break; }
            if (customer == null) { output.setText("❌ Customer Not Found"); return; }

            StringBuilder giftsText = new StringBuilder(customer.gifts.isEmpty() ? "  None\n" : "");
            for (String g : customer.gifts) giftsText.append("  🎁 ").append(g).append("\n");

            StringBuilder billingText = new StringBuilder("=== BILLING ===\n")
                .append("Name       : ").append(customer.name).append("\n\n");

            if (customer.marketingProgram && DataStore.marketingDiscount > 0) {
                billingText.append("Before Discount : ").append(customer.totalOriginalPayments).append("\n")
                           .append("Discount (").append((int)DataStore.marketingDiscount).append("%): -").append(customer.totalSaved).append("\n")
                           .append("After Discount  : ").append(customer.totalPayments).append("\n\n");
            } else {
                billingText.append("Total Payments : ").append(customer.totalPayments).append("\n\n");
            }

            if (customer.loyaltyProgram && DataStore.loyaltyBonusPoints > 0) {
                billingText.append("Base Points  : ").append(customer.basePoints).append("\n")
                           .append("Bonus Points : +").append(customer.bonusPoints).append("\n")
                           .append("Total Points : ").append(customer.loyaltyPoints).append("\n\n");
            } else {
                billingText.append("Points : ").append(customer.loyaltyPoints).append("\n\n");
            }

            billingText.append("--- Gifts ---\n").append(giftsText);
            output.setText(billingText.toString());
            output.setCaretPosition(0);
            Notification.show(this,
                "💰 Billing Generated\nCustomer: " + customer.name + "\nTotal: " + customer.totalPayments);
        });

        // PROFILE
        profileBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    StringBuilder giftsText  = new StringBuilder(c.gifts.isEmpty()  ? "  None\n" : "");
                    for (String g : c.gifts)  giftsText.append("  🎁 ").append(g).append("\n");
                    StringBuilder offersText = new StringBuilder(c.offers.isEmpty() ? "  None\n" : "");
                    for (String o : c.offers) offersText.append("  🏷 ").append(o).append("\n");
                    String ordersText = c.getOrdersInfo().isEmpty() ? "  No Orders\n" : c.getOrdersInfo();

                    String marketingLine = "  🏷 Marketing : ";
                    if (c.marketingProgram) {
                        marketingLine += "✔ Joined - " + (int)DataStore.marketingDiscount + "% Discount\n" +
                            "             Before: " + c.totalOriginalPayments +
                            " | Saved: " + c.totalSaved + " | After: " + c.totalPayments + "\n";
                    } else { marketingLine += "✘ Not Joined\n"; }

                    String loyaltyLine = "  ⭐ Loyalty   : ";
                    if (c.loyaltyProgram) {
                        loyaltyLine += "✔ Joined - +" + DataStore.loyaltyBonusPoints + " Bonus Points/order\n" +
                            "             Base: " + c.basePoints +
                            " | Bonus: +" + c.bonusPoints + " | Total: " + c.loyaltyPoints + "\n";
                    } else { loyaltyLine += "✘ Not Joined\n"; }

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

        // UPDATE INFO
        updateInfoBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "New Name:");
            String username = InputValidator.getText(this, "New Username:");
            if (name == null || username == null) return;
            for (Employee emp : DataStore.employees) {
                if (emp.id != currentUser.id && emp.username.equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(this, "❌ Username already exists");
                    return;
                }
            }
            String password = InputValidator.getText(this, "New Password:");
            if (password == null) return;
            currentUser.name = name;
            currentUser.username = username;
            currentUser.password = password;
            for (Employee emp : DataStore.employees) {
                if (emp.id == currentUser.id) {
                    emp.name = name; emp.username = username; emp.password = password;
                    break;
                }
            }
            FileManager.saveAll();
            output.setText("✔ Updated Successfully");
        });

        // MARKETING
        marketingBtn.addActionListener(e -> {
            if (DataStore.marketingDiscount <= 0) { output.setText("❌ Marketing Program not set by Admin yet"); return; }
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (c.marketingProgram) { output.setText("⚠ Already in Marketing Program"); return; }
                    c.registerMarketing();
                    Notification.show(this, "🏷 Joined Marketing Program\nDiscount: " + (int)DataStore.marketingDiscount + "%");
                    FileManager.saveAll();
                    output.setText("✔ Joined Marketing Program\nDiscount: " + (int)DataStore.marketingDiscount + "% on every order");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // LOYALTY
        loyaltyBtn.addActionListener(e -> {
            if (DataStore.loyaltyBonusPoints <= 0) { output.setText("❌ Loyalty Program not set by Admin yet"); return; }
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (c.loyaltyProgram) { output.setText("⚠ Already in Loyalty Program"); return; }
                    c.registerLoyalty();
                    Notification.show(this, "⭐ Joined Loyalty Program\nBonus: +" + DataStore.loyaltyBonusPoints);
                    FileManager.saveAll();
                    output.setText("✔ Joined Loyalty Program\nBonus: +" + DataStore.loyaltyBonusPoints + " Points per order");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // REWARD
        rewardBtn.addActionListener(e -> {
            if (DataStore.rewardReward.isEmpty()) { output.setText("❌ Reward Program not set by Admin yet"); return; }
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (c.rewardProgram) { output.setText("⚠ Already in Reward Program"); return; }
                    c.registerReward();
                    Notification.show(this, "🎁 Reward Activated\n" + DataStore.rewardReward);
                    FileManager.saveAll();
                    output.setText("✔ Joined Reward Program\nOffer: " + DataStore.rewardReward);
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // CANCEL MARKETING
        cancelMarketingBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (!c.marketingProgram) { output.setText("❌ Customer not in Marketing Program"); return; }
                    c.marketingProgram = false;
                    FileManager.saveAll();
                    output.setText("✔ Marketing Program Cancelled");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // CANCEL LOYALTY
        cancelLoyaltyBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (!c.loyaltyProgram) { output.setText("❌ Customer not in Loyalty Program"); return; }
                    c.loyaltyProgram = false;
                    FileManager.saveAll();
                    output.setText("✔ Loyalty Program Cancelled");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // CANCEL REWARD
        cancelRewardBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Customer ID:");
            if (id == null) return;
            for (Customer c : DataStore.customers) {
                if (c.id == id) {
                    if (!c.rewardProgram) { output.setText("❌ Customer not in Reward Program"); return; }
                    c.rewardProgram = false;
                    FileManager.saveAll();
                    output.setText("✔ Reward Program Cancelled");
                    return;
                }
            }
            output.setText("❌ Customer Not Found");
        });

        // LOGOUT
        logoutBtn.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        setVisible(true);
    }
}
