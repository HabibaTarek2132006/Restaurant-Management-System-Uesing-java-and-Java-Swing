import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {

    JTextField     usernameField;
    JPasswordField passwordField;
    JButton        loginBtn;

    // ===== Color Palette (same as dashboards) =====
    static final Color BG         = new Color(15, 17, 23);
    static final Color SURFACE    = new Color(26, 29, 39);
    static final Color SURFACE2   = new Color(34, 38, 58);
    static final Color ACCENT     = new Color(79, 142, 247);
    static final Color GREEN      = new Color(34, 201, 123);
    static final Color TEXT       = new Color(232, 234, 240);
    static final Color TEXT_MUTED = new Color(122, 127, 154);
    static final Color BORDER     = new Color(255, 255, 255, 18);
    static final Color BORDER_MID = new Color(255, 255, 255, 45);

    public LoginScreen() {
        setTitle("Login");
        setSize(400, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ===== ROOT =====
        JPanel root = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setOpaque(false);
        setContentPane(root);

        // ===== CARD =====
        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(BORDER_MID);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 18, 18);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(320, 340));

        // ===== LOGO DOT =====
        JPanel logoDot = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // outer glow ring
                g2.setColor(new Color(79, 142, 247, 30));
                g2.fillOval(2, 2, 48, 48);
                // inner circle
                g2.setColor(new Color(34, 38, 58));
                g2.fillOval(8, 8, 36, 36);
                // accent ring
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(8, 8, 36, 36);
                // center dot
                g2.setColor(ACCENT);
                g2.fillOval(20, 20, 12, 12);
                g2.dispose();
            }
        };
        logoDot.setOpaque(false);
        logoDot.setBounds(135, 30, 52, 52);

        // ===== TITLE =====
        JLabel titleLbl = new JLabel("Welcome Back", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLbl.setForeground(TEXT);
        titleLbl.setBounds(0, 92, 320, 28);

        JLabel subLbl = new JLabel("Sign in to continue", SwingConstants.CENTER);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subLbl.setForeground(TEXT_MUTED);
        subLbl.setBounds(0, 120, 320, 20);

        // ===== USERNAME FIELD =====
        JLabel userLbl = new JLabel("USERNAME");
        userLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        userLbl.setForeground(TEXT_MUTED);
        userLbl.setBounds(30, 158, 120, 14);

        usernameField = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        usernameField.setOpaque(false);
        usernameField.setBackground(new Color(0,0,0,0));
        usernameField.setForeground(TEXT);
        usernameField.setCaretColor(ACCENT);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, BORDER_MID),
            new EmptyBorder(0, 12, 0, 12)
        ));
        usernameField.setBounds(30, 176, 260, 38);

        // ===== PASSWORD FIELD =====
        JLabel passLbl = new JLabel("PASSWORD");
        passLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        passLbl.setForeground(TEXT_MUTED);
        passLbl.setBounds(30, 228, 120, 14);

        passwordField = new JPasswordField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE2);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(0,0,0,0));
        passwordField.setForeground(TEXT);
        passwordField.setCaretColor(ACCENT);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, BORDER_MID),
            new EmptyBorder(0, 12, 0, 12)
        ));
        passwordField.setBounds(30, 246, 260, 38);

        // ===== LOGIN BUTTON =====
        loginBtn = new JButton("Sign In") {
            boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = hovered ? new Color(99, 162, 255) : ACCENT;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TEXT);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        loginBtn.setOpaque(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setBounds(30, 300, 260, 42);

        card.add(logoDot);
        card.add(titleLbl);
        card.add(subLbl);
        card.add(userLbl);
        card.add(usernameField);
        card.add(passLbl);
        card.add(passwordField);
        card.add(loginBtn);

        root.add(card);

        // ================= LOGIN ACTION =================
        loginBtn.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin()); // Enter key in password field

        setVisible(true);
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        FileManager.loadAll();

        // EMPLOYEE LOGIN
        for (Employee emp : DataStore.employees) {
            if (emp.username.equals(username) && emp.password.equals(password)) {
                JOptionPane.showMessageDialog(this, "Welcome " + emp.name);
                User user = new User(emp.id, emp.name, emp.username, emp.password, Role.EMPLOYEE);
                new EmployeeDashboard(user).setVisible(true);
                this.dispose();
                return;
            }
        }

        // ADMIN LOGIN
        if (DataStore.admin != null &&
            DataStore.admin.username.equals(username) &&
            DataStore.admin.password.equals(password)) {
            JOptionPane.showMessageDialog(this, "Welcome Admin " + DataStore.admin.name);
            new AdminDashboard(DataStore.admin).setVisible(true);
            this.dispose();
            return;
        }

        // INVALID
        JOptionPane.showMessageDialog(this, "❌ Invalid username or password");
        passwordField.setText("");
    }

    // ===== Helper: Rounded Border =====
    static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;
        RoundedBorder(int radius, Color color) { this.radius = radius; this.color = color; }
        @Override public Insets getBorderInsets(Component c) { return new Insets(1,1,1,1); }
        @Override public boolean isBorderOpaque() { return false; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(x, y, w-1, h-1, radius, radius);
            g2.dispose();
        }
    }
}
