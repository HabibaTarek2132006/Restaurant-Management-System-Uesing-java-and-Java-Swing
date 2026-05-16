/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tarek
 */
class User extends Person {

    protected String username;
    protected String password;
    protected Role role;

    public User(int id, String name, String username, String password, Role role) {
        super(id, name);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public Role getRole() {
        return role;
    }
}