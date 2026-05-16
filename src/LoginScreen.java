import javax.swing.*;

public class LoginScreen extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn;

    public LoginScreen() {

        setTitle("Login Screen");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginBtn = new JButton("Login");

        usernameField.setBounds(80, 40, 180, 30);
        passwordField.setBounds(80, 90, 180, 30);
        loginBtn.setBounds(80, 140, 180, 35);

        add(usernameField);
        add(passwordField);
        add(loginBtn);

        // ================= LOGIN =================
        loginBtn.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // ================= LOAD DATA (optional safety) =================
            FileManager.loadAll();

            // ================= EMPLOYEE LOGIN =================
            for (Employee emp : DataStore.employees) {

                if (emp.username.equals(username) && emp.password.equals(password)) {

                    JOptionPane.showMessageDialog(this, "Welcome " + emp.name);

                    User user = new User(
                            emp.id,
                            emp.name,
                            emp.username,
                            emp.password,
                            Role.EMPLOYEE
                    );

                    new EmployeeDashboard(user).setVisible(true);
                    this.dispose();
                    return;
                }
            }

            // ================= ADMIN LOGIN =================
            if (DataStore.admin != null &&
                DataStore.admin.username.equals(username) &&
                DataStore.admin.password.equals(password)) {

                JOptionPane.showMessageDialog(this,
                        "Welcome Admin " + DataStore.admin.name);

                new AdminDashboard(DataStore.admin).setVisible(true);
                this.dispose();
                return;
            }

            // ================= INVALID =================
            JOptionPane.showMessageDialog(this, "Invalid Login");
        });

        setVisible(true);
    }
}