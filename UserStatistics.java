package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserStatistics extends JPanel {

    JLabel totalUsersLabel;

    public UserStatistics() {
        setLayout(new GridLayout(2, 1, 10, 10));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Website Statistics", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(titleLabel);

        totalUsersLabel = new JLabel("Total Users: Loading...", JLabel.CENTER);
        totalUsersLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(totalUsersLabel);

        // Fetch the total user count from the database
        fetchUserCount();
    }

    private void fetchUserCount() {
        try {
            Conn conn = new Conn(); // Assuming this class is already handling DB connection
            String query = "SELECT COUNT(*) AS total_users FROM login"; // Replace 'users' with your user table name
            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                int totalUsers = rs.getInt("total_users");
                totalUsersLabel.setText("Total Users: " + totalUsers);
            }
        } catch (Exception e) {
            totalUsersLabel.setText("Error loading user data");
            e.printStackTrace();
        }
    }
}
