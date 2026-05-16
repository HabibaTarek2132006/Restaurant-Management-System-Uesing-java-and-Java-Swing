/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tarek
 */
public class AuthService {

    public static Employee login(String username, String password) {

        for (Employee e : DataStore.employees) {

            if (e.username.equals(username) && e.password.equals(password)) {
                return e;
            }
        }

        return null;
    }
}