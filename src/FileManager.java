import java.io.*;
import java.util.*;

public class FileManager {

    public static void saveAll() {
        saveEmployees();
        saveMeals();
        saveCustomers();
        saveOrders();
        saveAdmin();
        saveProgramRewards();
    }

    public static void loadAll() {
        loadEmployees();
        loadMeals();
        loadCustomers();
        loadOrders();
        loadAdmin();
        loadProgramRewards();
    }

    // ================= EMPLOYEES =================

    private static void saveEmployees() {
        try (PrintWriter pw = new PrintWriter("employees.txt")) {
            for (Employee e : DataStore.employees)
                pw.println(e.id + "," + e.name + "," + e.username + "," + e.password);
        } catch (Exception e) { System.out.println("Error saving employees"); }
    }

    private static void loadEmployees() {
        try (Scanner sc = new Scanner(new File("employees.txt"))) {
            DataStore.employees.clear();
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                DataStore.employees.add(new Employee(Integer.parseInt(p[0]), p[1], p[2], p[3]));
            }
        } catch (Exception e) { System.out.println("No employees file found"); }
    }

    // ================= MEALS =================

    private static void saveMeals() {
        try (PrintWriter pw = new PrintWriter("meals.txt")) {
            for (Meal m : DataStore.meals)
                pw.println(m.id + "," + m.name + "," + m.price);
        } catch (Exception e) { System.out.println("Error saving meals"); }
    }

    private static void loadMeals() {
        try (Scanner sc = new Scanner(new File("meals.txt"))) {
            DataStore.meals.clear();
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                DataStore.meals.add(new Meal(Integer.parseInt(p[0]), p[1], Double.parseDouble(p[2])));
            }
        } catch (Exception e) { System.out.println("No meals file found"); }
    }

    // ================= CUSTOMERS =================

    private static void saveCustomers() {
        try (PrintWriter pw = new PrintWriter("customers.txt")) {
            for (Customer c : DataStore.customers) {

                String giftsStr = "";
                for (String g : c.gifts) giftsStr += g + "|";

                String offersStr = "";
                for (String o : c.offers) offersStr += o + "|";

                pw.println(
                    c.id + "," +
                    c.name + "," +
                    c.totalPayments + "," +
                    c.loyaltyPoints + "," +
                    c.marketingProgram + "," +
                    c.loyaltyProgram + "," +
                    c.rewardProgram + "," +
                    c.totalOriginalPayments + "," +
                    c.totalSaved + "," +
                    c.basePoints + "," +
                    c.bonusPoints + "," +
                    giftsStr + "," +
                    offersStr
                );
            }
        } catch (Exception e) { System.out.println("Error saving customers"); }
    }

    private static void loadCustomers() {
        try (Scanner sc = new Scanner(new File("customers.txt"))) {
            DataStore.customers.clear();
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",", -1);

                Customer c = new Customer(Integer.parseInt(p[0]), p[1]);
                c.totalPayments         = Double.parseDouble(p[2]);
                c.loyaltyPoints         = Integer.parseInt(p[3]);
                c.marketingProgram      = Boolean.parseBoolean(p[4]);
                c.loyaltyProgram        = Boolean.parseBoolean(p[5]);
                c.rewardProgram         = Boolean.parseBoolean(p[6]);
                c.totalOriginalPayments = Double.parseDouble(p[7]);
                c.totalSaved            = Double.parseDouble(p[8]);
                c.basePoints            = Integer.parseInt(p[9]);
                c.bonusPoints           = Integer.parseInt(p[10]);

                if (!p[11].isEmpty())
                    for (String g : p[11].split("\\|"))
                        if (!g.isEmpty()) c.gifts.add(g);

                if (!p[12].isEmpty())
                    for (String o : p[12].split("\\|"))
                        if (!o.isEmpty()) c.offers.add(o);

                DataStore.customers.add(c);
            }
        } catch (Exception e) { System.out.println("No customers file found"); }
    }

    // ================= ORDERS =================

    private static void saveOrders() {
        try (PrintWriter pw = new PrintWriter("orders.txt")) {
            for (Order o : DataStore.orders) {
                String mealsIds = "";
                for (Meal m : o.meals) mealsIds += m.id + "-";
                pw.println(o.id + "," + o.customer.id + "," + o.totalPrice + "," +
                           o.originalPrice + "," + o.discountAmount + "," + mealsIds);
            }
        } catch (Exception e) { System.out.println("Error saving orders"); }
    }

    private static void loadOrders() {
        try (Scanner sc = new Scanner(new File("orders.txt"))) {
            DataStore.orders.clear();
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                int    orderId    = Integer.parseInt(p[0]);
                int    customerId = Integer.parseInt(p[1]);
                double total      = Double.parseDouble(p[2]);
                double original   = Double.parseDouble(p[3]);
                double discount   = Double.parseDouble(p[4]);

                Customer customer = null;
                for (Customer c : DataStore.customers)
                    if (c.id == customerId) { customer = c; break; }
                if (customer == null) continue;

                Order o = new Order(customer);
                o.id             = orderId;
                o.totalPrice     = total;
                o.originalPrice  = original;
                o.discountAmount = discount;

                DataStore.orders.add(o);
                customer.orders.add(o);
            }
        } catch (Exception e) { System.out.println("No orders file found"); }
    }

    // ================= ADMIN =================

    private static void saveAdmin() {
        try (PrintWriter pw = new PrintWriter("admin.txt")) {
            if (DataStore.admin != null)
                pw.println(DataStore.admin.id + "," + DataStore.admin.name + "," +
                           DataStore.admin.username + "," + DataStore.admin.password);
        } catch (Exception e) { System.out.println("Error saving admin"); }
    }

    private static void loadAdmin() {
        try (Scanner sc = new Scanner(new File("admin.txt"))) {
            if (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                DataStore.admin = new User(Integer.parseInt(p[0]), p[1], p[2], p[3], Role.ADMIN);
            }
        } catch (Exception e) {
            System.out.println("No admin file found");
            DataStore.admin = new User(0, "Admin", "admin", "1234", Role.ADMIN);
        }
    }

    // ================= PROGRAM REWARDS =================

    private static void saveProgramRewards() {
        try (PrintWriter pw = new PrintWriter("programs.txt")) {
            pw.println(DataStore.marketingDiscount);
            pw.println(DataStore.loyaltyBonusPoints);
            pw.println(DataStore.rewardReward);
        } catch (Exception e) { System.out.println("Error saving programs"); }
    }

    private static void loadProgramRewards() {
        try (Scanner sc = new Scanner(new File("programs.txt"))) {
            DataStore.marketingDiscount  = sc.hasNextLine() ? Double.parseDouble(sc.nextLine()) : 0;
            DataStore.loyaltyBonusPoints = sc.hasNextLine() ? Integer.parseInt(sc.nextLine())   : 0;
            DataStore.rewardReward       = sc.hasNextLine() ? sc.nextLine() : "";
        } catch (Exception e) { System.out.println("No programs file found"); }
    }
}
