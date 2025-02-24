package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton submit, reset, toggleSignup, close;
    JTextField tfusername, tfemail, tfphno;
    JPasswordField tfpassword;
    JLabel lblusername, lblpassword, lblphno, lblemail, title, subtitle;
    boolean isSignupMode = false; // Toggle between Login and Sign-Up modes

    public Login() {
        setTitle("Airline Management System - Login/Sign-Up");
        setSize(500, 450);
        setLocation(600, 250);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Title Label
        title = new JLabel("Welcome Back!");
        title.setBounds(150, 20, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        // Subtitle Label
        subtitle = new JLabel("Please login to your account");
        subtitle.setBounds(140, 50, 220, 20);
        subtitle.setForeground(new Color(255, 102, 0));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(subtitle);

        // Username Label
        lblusername = new JLabel("Username");
        lblusername.setBounds(80, 100, 100, 20);
        lblusername.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(200, 100, 200, 30);
        tfusername.setFont(new Font("Arial", Font.PLAIN, 14));
        tfusername.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfusername);

        // Password Label
        lblpassword = new JLabel("Password");
        lblpassword.setBounds(80, 150, 100, 20);
        lblpassword.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(200, 150, 200, 30);
        tfpassword.setFont(new Font("Arial", Font.PLAIN, 14));
        tfpassword.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfpassword);

        // Email Label (Sign-Up only)
        lblemail = new JLabel("Email");
        lblemail.setBounds(80, 200, 100, 20);
        lblemail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblemail.setVisible(false); // Hidden by default for Login
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 200, 200, 30);
        tfemail.setFont(new Font("Arial", Font.PLAIN, 14));
        tfemail.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        tfemail.setVisible(false); // Hidden by default for Login
        add(tfemail);

        // Phone Number Label (Sign-Up only)
        lblphno = new JLabel("Phone Number");
        lblphno.setBounds(80, 250, 100, 20);
        lblphno.setFont(new Font("Arial", Font.PLAIN, 14));
        lblphno.setVisible(false); // Hidden by default for Login
        add(lblphno);

        tfphno = new JTextField();
        tfphno.setBounds(200, 250, 200, 30);
        tfphno.setFont(new Font("Arial", Font.PLAIN, 14));
        tfphno.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        tfphno.setVisible(false); // Hidden by default for Login
        add(tfphno);

        // Submit Button
        submit = new JButton("Login");
        submit.setBounds(200, 300, 200, 30);
        submit.setBackground(new Color(255, 102, 0));
        submit.setForeground(Color.white);
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.setFocusPainted(false);
        submit.setBorder(BorderFactory.createLineBorder(new Color(255, 102, 0), 1));
        submit.setOpaque(true);
        submit.addActionListener(this);
        add(submit);

        // Toggle to Sign-Up Button
        toggleSignup = new JButton("Create an account");
        toggleSignup.setBounds(200, 340, 200, 30);
        toggleSignup.setBackground(Color.WHITE);
        toggleSignup.setForeground(new Color(255, 102, 0));
        toggleSignup.setFont(new Font("Arial", Font.BOLD, 14));
        toggleSignup.setFocusPainted(false);
        toggleSignup.setBorder(BorderFactory.createLineBorder(new Color(255, 102, 0), 1));
        toggleSignup.setOpaque(true);
        toggleSignup.addActionListener(this);
        add(toggleSignup);

        // Reset Button
        reset = new JButton("Reset");
        reset.setBounds(80, 390, 100, 30);
        reset.setBackground(new Color(240, 240, 240));
        reset.setForeground(Color.BLACK);
        reset.setFont(new Font("Arial", Font.PLAIN, 12));
        reset.setFocusPainted(false);       
        reset.addActionListener(this);
        add(reset);

        // Close Button
        close = new JButton("Close");
        close.setBounds(320, 390, 100, 30);
        close.setBackground(new Color(240, 240, 240));
        close.setForeground(Color.red);
        close.setFont(new Font("Arial", Font.PLAIN, 12));
        close.setFocusPainted(false);
        close.addActionListener(this);
        add(close);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == submit) {
        if (!isSignupMode) {
            // Login logic
            String username = tfusername.getText().trim();
            String password = new String(tfpassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement pst = c.c.prepareStatement(query);

                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new Home(); // Navigate to the Home page
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Sign-Up logic
            String username = tfusername.getText().trim();
            String password = new String(tfpassword.getPassword()).trim();
            String email = tfemail.getText().trim();
            String phone = tfphno.getText().trim();

            // Validation for empty fields
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                return;
            }

            // Validate phone number
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid 10-digit phone number!");
                return;
            }

            // Validate email format
            if (!email.contains("@") || !email.contains(".com")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address!");
                return;
            }

            try {
                Conn c = new Conn();

                String checkQuery = "SELECT * FROM login WHERE username = ?";
                PreparedStatement pstCheck = c.c.prepareStatement(checkQuery);
                pstCheck.setString(1, username);
                ResultSet rs = pstCheck.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                } else {
                    String insertQuery = "INSERT INTO login (username, password, phno, email) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstInsert = c.c.prepareStatement(insertQuery);

                    pstInsert.setString(1, username);
                    pstInsert.setString(2, password);
                    pstInsert.setString(3, phone);
                    pstInsert.setString(4, email);

                    int i = pstInsert.executeUpdate();

                    if (i > 0) {
                        JOptionPane.showMessageDialog(null, "Account Created Successfully!");
                        toggleLoginMode(); // Switch back to login mode
                    } else {
                        JOptionPane.showMessageDialog(null, "Error in Sign-Up!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } else if (ae.getSource() == toggleSignup) {
        toggleLoginMode();
    } else if (ae.getSource() == reset) {
        tfusername.setText("");
        tfpassword.setText("");
        tfemail.setText("");
        tfphno.setText("");
    } else if (ae.getSource() == close) {
        System.exit(0);
    }
}

    private void toggleLoginMode() {
        isSignupMode = !isSignupMode;

        // Update UI components
        title.setText(isSignupMode ? "Create Your Account" : "Welcome Back!");
        subtitle.setText(isSignupMode ? "Sign up for a new account" : "Please login to your account");
        submit.setText(isSignupMode ? "Sign-Up" : "Login");
        toggleSignup.setText(isSignupMode ? "Already have an account?" : "Create an account");

        // Show/Hide Sign-Up fields
        lblemail.setVisible(isSignupMode);
        tfemail.setVisible(isSignupMode);
        lblphno.setVisible(isSignupMode);
        tfphno.setVisible(isSignupMode);
    }

    public static void main(String[] args) {
        new Login();
    }
}
