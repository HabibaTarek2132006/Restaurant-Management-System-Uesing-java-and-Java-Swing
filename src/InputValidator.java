import javax.swing.*;

public class InputValidator {

    // ================= TEXT (GENERAL SAFE INPUT) =================
    public static String getText(JFrame frame, String message) {

    while (true) {

        String input = JOptionPane.showInputDialog(frame, message);

        if (input == null)
            return null;

        input = input.trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Input cannot be empty");

            continue;
        }

        return input;
    }
}

    // ================= NAME ONLY (STRICT VALIDATION) =================
   public static String getNameOnly(JFrame frame, String message) {

    while (true) {

        String input = JOptionPane.showInputDialog(frame, message);

        if (input == null)
            return null;

        input = input.trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Name cannot be empty");

            continue;
        }

        // Arabic + English letters only
        if (!input.matches("[\\p{L} ]+")) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Invalid Name\nOnly letters are allowed");

            continue;
        }

        return input;
    }
}

    // ================= INTEGER VALIDATION =================
    public static Integer getInt(JFrame frame, String message) {

    while (true) {

        String input = JOptionPane.showInputDialog(frame, message);

        if (input == null)
            return null;

        input = input.trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Input cannot be empty");

            continue;
        }

        try {

            int value = Integer.parseInt(input);

            // يمنع السالب
            if (value < 0) {

                JOptionPane.showMessageDialog(frame,
                        "❌ Number must be positive");

                continue;
            }

            return value;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Please enter numbers only");
        }
    }
}

    // ================= DOUBLE VALIDATION =================
   public static Double getDouble(JFrame frame, String message) {

    while (true) {

        String input = JOptionPane.showInputDialog(frame, message);

        if (input == null)
            return null;

        input = input.trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Input cannot be empty");

            continue;
        }

        try {

            double value = Double.parseDouble(input);

            // يمنع الصفر والسالب
            if (value <= 0) {

                JOptionPane.showMessageDialog(frame,
                        "❌ Value must be greater than 0");

                continue;
            }

            return value;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Please enter valid numbers only");
        }
    }}

// ================= USERNAME VALIDATION =================
public static String getUsername(JFrame frame, String message) {

    while (true) {

        String input = JOptionPane.showInputDialog(frame, message);

        if (input == null)
            return null;

        input = input.trim();

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Username cannot be empty");

            continue;
        }

        if (!input.matches("[a-zA-Z0-9_]+")) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Username can contain only letters, numbers and _");

            continue;
        }

        return input;
    }
}
// ================= PASSWORD VALIDATION =================
public static String getPassword(JFrame frame, String message) {

    while (true) {

        String input = JOptionPane.showInputDialog(frame, message);

        if (input == null)
            return null;

        input = input.trim();
        if (input.contains(" ")) {

    JOptionPane.showMessageDialog(frame,
            "❌ Password cannot contain spaces");

    continue;
}

        if (input.isEmpty()) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Password cannot be empty");

            continue;
        }

        if (input.length() < 4) {

            JOptionPane.showMessageDialog(frame,
                    "❌ Password must be at least 4 characters");

            continue;
        }

        return input;
    }
}
// ================= DUPLICATE USERNAME =================
public static boolean usernameExists(String username) {

    for (Employee emp : DataStore.employees) {

        if (emp.username != null &&
            emp.username.equalsIgnoreCase(username)) {

            return true;
        }
    }

    return false;
}
public static int generateEmployeeId() {

    int max = 0;

    for (Employee emp : DataStore.employees) {
        if (emp.id > max) {
            max = emp.id;
        }
    }

    return max + 1;
}

}