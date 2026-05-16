import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.FileWriter;

public class AdminDashboard extends JFrame {

    User currentUser;

    // ===== Buttons =====
    JButton addEmpBtn, showEmpBtn, deleteEmpBtn, updateEmpBtn, searchEmpBtn;
    JButton addMealBtn, showMealBtn, updateMealBtn, searchMealBtn, deleteMealBtn;
    JButton addOfferBtn, employeeReportBtn, mealReportBtn;
    JButton updateAdminInfoBtn, logoutBtn;
    JButton customerReportBtn;
    JButton setMarketingBtn, setLoyaltyBtn, setRewardBtn, showProgramsBtn;
    JButton extractFullReportBtn;

    JTextArea output;

    // ===== Color Palette =====
    static final Color BG          = new Color(15, 17, 23);
    static final Color SURFACE     = new Color(26, 29, 39);
    static final Color SURFACE2    = new Color(34, 38, 58);
    static final Color ACCENT      = new Color(79, 142, 247);
    static final Color ACCENT2     = new Color(124, 92, 252);
    static final Color GREEN       = new Color(34, 201, 123);
    static final Color RED_CLR     = new Color(255, 90, 90);
    static final Color AMBER       = new Color(245, 166, 35);
    static final Color TEXT        = new Color(232, 234, 240);
    static final Color TEXT_MUTED  = new Color(122, 127, 154);
    static final Color BORDER      = new Color(255, 255, 255, 18);

    // ===== Styled Button Types =====
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
                        bg = hovered ? new Color(60, 20, 20) : SURFACE2;
                        border = hovered ? RED_CLR : BORDER;
                        fg = hovered ? RED_CLR : TEXT_MUTED;
                        break;
                    case SUCCESS:
                        bg = hovered ? new Color(10, 50, 30) : SURFACE2;
                        border = hovered ? GREEN : new Color(34, 201, 123, 40);
                        fg = hovered ? GREEN : TEXT_MUTED;
                        break;
                    case WARN:
                        bg = hovered ? new Color(50, 38, 10) : SURFACE2;
                        border = hovered ? AMBER : BORDER;
                        fg = hovered ? AMBER : TEXT_MUTED;
                        break;
                    case EXTRACT:
                        bg = hovered ? new Color(20, 70, 45) : new Color(34, 201, 123, 25);
                        border = new Color(34, 201, 123, 80);
                        fg = GREEN;
                        break;
                    default:
                        bg = hovered ? new Color(42, 47, 71) : SURFACE2;
                        border = hovered ? new Color(79, 142, 247, 80) : BORDER;
                        fg = hovered ? ACCENT : TEXT_MUTED;
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
        setSize(760, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== ROOT PANEL =====
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

        JLabel titleLbl = new JLabel("  Admin Dashboard");
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

        String[][] icons = {{"⊞","Overview"},{"👤","Employees"},{"🍽","Meals"},{"📊","Reports"},{"🎁","Programs"}};
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

        // ===== MAIN PANEL =====
        JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(false);

        // ===== BUTTONS =====
        addEmpBtn      = makeBtn("＋ Add Emp",      BtnStyle.DEFAULT);
        showEmpBtn     = makeBtn("≡ Show Emp",      BtnStyle.DEFAULT);
        deleteEmpBtn   = makeBtn("✕ Delete Emp",    BtnStyle.DANGER);
        updateEmpBtn   = makeBtn("✎ Update Emp",    BtnStyle.DEFAULT);
        searchEmpBtn   = makeBtn("⌕ Search Emp",    BtnStyle.DEFAULT);

        addMealBtn     = makeBtn("＋ Add Meal",      BtnStyle.SUCCESS);
        showMealBtn    = makeBtn("≡ Show Meals",     BtnStyle.DEFAULT);
        updateMealBtn  = makeBtn("✎ Update Meal",   BtnStyle.DEFAULT);
        searchMealBtn  = makeBtn("⌕ Search Meal",   BtnStyle.DEFAULT);
        deleteMealBtn  = makeBtn("✕ Delete Meal",   BtnStyle.DANGER);

        addOfferBtn        = makeBtn("🏷 Add Offer",       BtnStyle.DEFAULT);
        employeeReportBtn  = makeBtn("📋 Emp Report",      BtnStyle.DEFAULT);
        mealReportBtn      = makeBtn("📋 Meal Report",     BtnStyle.DEFAULT);
        customerReportBtn  = makeBtn("📋 Customer Report", BtnStyle.DEFAULT);
        updateAdminInfoBtn = makeBtn("✎ Admin Info",       BtnStyle.DEFAULT);

        setMarketingBtn = makeBtn("% Marketing",    BtnStyle.WARN);
        setLoyaltyBtn   = makeBtn("★ Loyalty Pts",  BtnStyle.WARN);
        setRewardBtn    = makeBtn("🎁 Set Reward",   BtnStyle.WARN);
        showProgramsBtn = makeBtn("◉ Show Programs", BtnStyle.DEFAULT);

        extractFullReportBtn = makeBtn("⬇ Extract Full Report", BtnStyle.EXTRACT);

        // ===== CONTENT AREA =====
        // top = buttons, center = scroll (fills remaining space)
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel topSection = new JPanel();
        topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        // Stat cards
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 10, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        statsRow.add(makeStatCard("Total Employees", "12", "↑ 2 this month", GREEN));
        statsRow.add(makeStatCard("Total Revenue",   "24,800", "↑ +12% vs last month", GREEN));
        statsRow.add(makeStatCard("Active Customers","87",     "★ 34 in loyalty program", AMBER));
        topSection.add(statsRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 12)));

        // Employee section
        topSection.add(sectionLabel("Employee Management"));
        JPanel empRow = new JPanel(new GridLayout(1, 5, 8, 0));
        empRow.setOpaque(false);
        empRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{addEmpBtn, showEmpBtn, deleteEmpBtn, updateEmpBtn, searchEmpBtn})
            empRow.add(b);
        topSection.add(empRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // Meal section
        topSection.add(sectionLabel("Meal Management"));
        JPanel mealRow = new JPanel(new GridLayout(1, 5, 8, 0));
        mealRow.setOpaque(false);
        mealRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{addMealBtn, showMealBtn, updateMealBtn, searchMealBtn, deleteMealBtn})
            mealRow.add(b);
        topSection.add(mealRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // Reports section
        topSection.add(sectionLabel("Reports & Offers"));
        JPanel reportRow = new JPanel(new GridLayout(1, 5, 8, 0));
        reportRow.setOpaque(false);
        reportRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{addOfferBtn, employeeReportBtn, mealReportBtn, customerReportBtn, updateAdminInfoBtn})
            reportRow.add(b);
        topSection.add(reportRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // Programs section
        topSection.add(sectionLabel("Programs"));
        JPanel progRow = new JPanel(new GridLayout(1, 4, 8, 0));
        progRow.setOpaque(false);
        progRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        for (JButton b : new JButton[]{setMarketingBtn, setLoyaltyBtn, setRewardBtn, showProgramsBtn})
            progRow.add(b);
        topSection.add(progRow);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        // Extract button
        extractFullReportBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        topSection.add(extractFullReportBtn);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));

        content.add(topSection, BorderLayout.NORTH);

        // Output area — fills all remaining space
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
            StringBuilder text = new StringBuilder();
            for (Employee emp : DataStore.employees)
                text.append(emp.id).append(" - ").append(emp.name).append(" - ").append(emp.username).append("\n");
            output.setText(text.toString());
        });

        deleteEmpBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Employee ID:");
            if (id == null) return;
            DataStore.employees.removeIf(emp -> emp.id == id);
            FileManager.saveAll();
            output.setText("✔ Employee Deleted");
        });

        updateEmpBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Employee ID:");
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
                    emp.name = name; emp.username = username; emp.password = password;
                    FileManager.saveAll();
                    output.setText("✔ Employee Updated");
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        searchEmpBtn.addActionListener(e -> {
            Integer id = InputValidator.getInt(this, "Search by ID:");
            if (id == null) return;
            for (Employee emp : DataStore.employees) {
                if (emp.id == id) {
                    output.setText(emp.id + " - " + emp.name + " - " + emp.username);
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

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
            StringBuilder text = new StringBuilder();
            for (Meal m : DataStore.meals)
                text.append(m.id).append(" - ").append(m.name).append(" - ").append(m.price).append("\n");
            output.setText(text.toString());
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
                    m.name = name; m.price = price;
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
            String name = InputValidator.getText(this, "Meal Name:");
            if (name == null) return;
            for (Meal m : DataStore.meals) {
                if (m.name.equalsIgnoreCase(name)) {
                    output.setText(m.id + " - " + m.name + " - " + m.price);
                    return;
                }
            }
            output.setText("❌ Not Found");
        });

        addOfferBtn.addActionListener(e -> {
            String offer;
            while (true) {
                offer = InputValidator.getText(this, "Offer:");
                if (offer == null) return;
                offer = offer.trim();
                if (offer.isEmpty()) { Notification.show(this, "❌ Offer cannot be empty"); continue; }
                boolean exists = false;
                for (String o : DataStore.offers) if (o.equalsIgnoreCase(offer)) { exists = true; break; }
                if (exists) { Notification.show(this, "❌ Offer already exists"); continue; }
                break;
            }
            DataStore.offers.add(offer);
            FileManager.saveAll();
            for (Customer c : DataStore.customers) c.addOffer(offer);
            Notification.show(this, "📢 New Offer Added!\n" + offer);
            output.setText("✔ Offer Added Successfully");
        });

        employeeReportBtn.addActionListener(e -> {
            StringBuilder report = new StringBuilder("Employees Report:\n");
            for (Employee emp : DataStore.employees)
                report.append(emp.id).append(" - ").append(emp.name).append("\n");
            output.setText(report.toString());
        });

        customerReportBtn.addActionListener(e -> {
            if (DataStore.customers.isEmpty()) { output.setText("❌ No Customers Found"); return; }
            StringBuilder report = new StringBuilder("=== CUSTOMER REPORT ===\n\n");
            double totalRevenue = 0;
            for (Customer c : DataStore.customers) {
                totalRevenue += c.totalPayments;
                report.append("ID: ").append(c.id).append("\n")
                      .append("Name: ").append(c.name).append("\n")
                      .append("Payments: ").append(c.totalPayments).append("\n")
                      .append("Points: ").append(c.loyaltyPoints).append("\n")
                      .append("Marketing: ").append(c.marketingProgram ? "✔" : "✘").append("\n")
                      .append("Loyalty:   ").append(c.loyaltyProgram  ? "✔" : "✘").append("\n")
                      .append("Reward:    ").append(c.rewardProgram   ? "✔" : "✘").append("\n")
                      .append("──────────────────────\n");
            }
            report.append("\nTotal Customers: ").append(DataStore.customers.size());
            report.append("\nTotal Revenue:   ").append(totalRevenue);
            output.setText(report.toString());
        });

        mealReportBtn.addActionListener(e -> {
            StringBuilder report = new StringBuilder("Meals Report:\n");
            for (Meal m : DataStore.meals)
                report.append(m.id).append(" - ").append(m.name).append(" - ").append(m.price).append("\n");
            output.setText(report.toString());
        });

        updateAdminInfoBtn.addActionListener(e -> {
            String name = InputValidator.getNameOnly(this, "New Name:");
            if (name == null) return;
            String username = InputValidator.getUsername(this, "New Username:");
            if (username == null) return;
            String password = InputValidator.getPassword(this, "New Password:");
            if (password == null) return;
            currentUser.name = name; currentUser.username = username; currentUser.password = password;
            FileManager.saveAll();
            output.setText("✔ Admin Updated");
        });

        logoutBtn.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        setMarketingBtn.addActionListener(e -> {
            String input = InputValidator.getText(this, "Marketing Discount %:\n(e.g. 10 for 10%)");
            if (input == null) return;
            try {
                double discount = Double.parseDouble(input.trim());
                if (discount <= 0 || discount > 100) { output.setText("❌ Enter a number between 1 and 100"); return; }
                DataStore.marketingDiscount = discount;
                FileManager.saveAll();
                for (Customer c : DataStore.customers) c.marketingProgram = true;
                Notification.show(this, "🏷 Marketing updated: " + discount + "%");
                output.setText(
                    "✔ Marketing Program Set!\n" +
                    "Discount: " + (int)discount + "% on every order\n\n" +
                    "Example:\n  Order 100 → After discount: " + (100 - discount) + "\n" +
                    "  Order 200 → After discount: " + (200 - 200 * discount / 100)
                );
            } catch (NumberFormatException ex) { output.setText("❌ Invalid number"); }
        });

        setLoyaltyBtn.addActionListener(e -> {
            String input = InputValidator.getText(this, "Loyalty Bonus Points per order:");
            if (input == null) return;
            try {
                int bonus = Integer.parseInt(input.trim());
                if (bonus <= 0) { output.setText("❌ Enter a positive number"); return; }
                DataStore.loyaltyBonusPoints = bonus;
                FileManager.saveAll();
                Notification.show(this, "⭐ Loyalty updated: +" + bonus + " points per order");
                output.setText(
                    "✔ Loyalty Program Set!\nBonus: +" + bonus + " extra points per order\n\n" +
                    "Example (order = 100):\n  Base Points: " + (100/10) +
                    "\n  Bonus: +" + bonus + "\n  Total: " + (100/10 + bonus)
                );
            } catch (NumberFormatException ex) { output.setText("❌ Invalid number"); }
        });

        setRewardBtn.addActionListener(e -> {
            String reward = InputValidator.getText(this, "Reward Program Offer:");
            if (reward == null || reward.trim().isEmpty()) return;
            DataStore.rewardReward = reward.trim();
            FileManager.saveAll();
            for (Customer c : DataStore.customers) c.rewardProgram = true;
            Notification.show(this, "🎁 New Reward: " + reward);
            output.setText("✔ Reward Program Set!\nOffer: " + DataStore.rewardReward);
        });

        showProgramsBtn.addActionListener(e -> {
            StringBuilder text = new StringBuilder("=== Programs Status ===\n\n");
            text.append("🏷 Marketing : ").append(DataStore.marketingDiscount > 0
                ? (int)DataStore.marketingDiscount + "% Discount per order" : "Not Set").append("\n");
            text.append("⭐ Loyalty   : ").append(DataStore.loyaltyBonusPoints > 0
                ? "+" + DataStore.loyaltyBonusPoints + " Bonus Points per order" : "Not Set").append("\n");
            text.append("🎁 Reward    : ").append(DataStore.rewardReward.isEmpty()
                ? "Not Set" : DataStore.rewardReward).append("\n\n=== Customer Subscriptions ===\n");
            for (Customer c : DataStore.customers) {
                text.append(c.name).append(" → ")
                    .append("Marketing: ").append(c.marketingProgram ? "✔" : "✘")
                    .append(" | Loyalty: ").append(c.loyaltyProgram ? "✔" : "✘")
                    .append(" | Reward: ").append(c.rewardProgram ? "✔" : "✘").append("\n");
            }
            output.setText(text.toString());
            output.setCaretPosition(0);
        });

        extractFullReportBtn.addActionListener(e -> {
            StringBuilder report = new StringBuilder();
            report.append("===== RESTAURANT FULL SYSTEM REPORT =====\n");
            report.append("Generated: ").append(new java.util.Date()).append("\n\n");

            report.append("=== Employees (").append(DataStore.employees.size()).append(") ===\n");
            for (Employee emp : DataStore.employees)
                report.append(emp.id).append(" | ").append(emp.name).append(" | ").append(emp.username).append("\n");

            report.append("\n=== Customers (").append(DataStore.customers.size()).append(") ===\n");
            double totalRevenue = 0;
            for (Customer c : DataStore.customers) {
                totalRevenue += c.totalPayments;
                report.append(c.id).append(" | ").append(c.name)
                      .append(" | Payments: ").append(c.totalPayments)
                      .append(" | Points: ").append(c.loyaltyPoints)
                      .append(" | Marketing: ").append(c.marketingProgram ? "✔" : "✘")
                      .append(" | Loyalty: ").append(c.loyaltyProgram ? "✔" : "✘")
                      .append(" | Reward: ").append(c.rewardProgram ? "✔" : "✘").append("\n");
            }
            report.append("Total Revenue: ").append(totalRevenue).append("\n");

            report.append("\n=== Meals (").append(DataStore.meals.size()).append(") ===\n");
            for (Meal m : DataStore.meals)
                report.append(m.id).append(" | ").append(m.name).append(" | ").append(m.price).append("\n");

            report.append("\n=== Orders ===\n");
            for (Customer c : DataStore.customers) {
                for (Order o : c.orders) {
                    report.append("Order ID: ").append(o.id)
                          .append(" | Customer: ").append(o.customer.name)
                          .append(" | Original: ").append(o.originalPrice)
                          .append(" | Discount: ").append(o.discountAmount)
                          .append(" | Paid: ").append(o.totalPrice)
                          .append(" | Meals: ");
                    for (Meal m : o.meals) report.append(m.name).append(" ");
                    report.append("\n");
                }
            }
            report.append("\n==========================================\n");

            output.setText(report.toString());
            output.setCaretPosition(0);
            saveToFile(report.toString(), "SystemReport.txt");
        });

        setVisible(true);
    }
}
