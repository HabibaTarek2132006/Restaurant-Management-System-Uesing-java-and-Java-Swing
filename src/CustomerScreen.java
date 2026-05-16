/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tarek
 */
import javax.swing.*;

class CustomerScreen extends JFrame {

    public CustomerScreen() {

        setTitle("Customer Screen");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Customer Area");
        label.setBounds(130, 120, 200, 30);

        add(label);
    }
}