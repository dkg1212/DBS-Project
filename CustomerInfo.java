package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CustomerInfo extends JFrame implements ActionListener {
    JTextField tfUsername;
    JButton searchButton, resetButton;
    JTable table;

    public CustomerInfo() {
        setTitle("Customer Information");
        getContentPane().setBackground(new Color(240, 248, 255)); // Light background color
        setLayout(null);

        ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/logo.jpg")); // Replace with your image path
        Image img = backgroundImage.getImage().getScaledInstance(800, 550, Image.SCALE_SMOOTH); // Resize the image to fit the frame size
        backgroundImage = new ImageIcon(img);
        // Search Username Label
        JLabel lblUsername = new JLabel("Enter Username:");
        lblUsername.setBounds(50, 20, 150, 30);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsername.setForeground(new Color(0, 102, 204)); // Blue color for label text
        add(lblUsername);

        // Search Username Text Field
        tfUsername = new JTextField();
        tfUsername.setBounds(200, 20, 200, 30);
        tfUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        tfUsername.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(tfUsername);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setBounds(420, 20, 100, 30);
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setOpaque(true);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        searchButton.addActionListener(this);
        addHoverEffect(searchButton, new Color(0, 85, 170)); // Darker blue on hover
        add(searchButton);

        // Reset Button
        resetButton = new JButton("Reset");
        resetButton.setBounds(530, 20, 100, 30);
        resetButton.setBackground(new Color(34, 139, 34)); // Green button
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        resetButton.setOpaque(true);
        resetButton.addActionListener(this);
        addHoverEffect(resetButton, new Color(28, 120, 28)); // Darker green on hover
        add(resetButton);

        // Customer Table with some padding and customized header
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 80, 700, 400);
        add(jsp);

        // Style Table Header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 102, 204)); // Blue header background
        table.getTableHeader().setForeground(Color.WHITE); // White header text
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Header border

        // Customize Table Body
        table.setRowHeight(30); // Increase row height for better readability
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for table content
        table.setGridColor(Color.LIGHT_GRAY); // Grid lines in light gray
        table.setShowGrid(true); // Show grid lines between cells

        // Load all customers initially
        loadAllCustomers();

        setSize(800, 550);
        setLocation(400, 200);
        setVisible(true);
        setResizable(false); // Prevent resizing to keep the layout intact
    }

    public void actionPerformed(ActionEvent ae) {
        String username = tfUsername.getText().trim(); // Trim spaces for clean input

        if (ae.getSource() == searchButton) {
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username!");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM login WHERE username = ?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, username);

                ResultSet rs = pst.executeQuery();

                if (rs.isBeforeFirst()) { // Check if the ResultSet has data
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    JOptionPane.showMessageDialog(null, "No customer found with this username!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while fetching customer details!");
            }
        } else if (ae.getSource() == resetButton) {
            // Reset search field and reload all customers
            tfUsername.setText("");
            loadAllCustomers();
        }
    }

    // Method to load all customer data into the table
    private void loadAllCustomers() {
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM login";
            ResultSet rs = conn.s.executeQuery(query);

            table.setModel(DbUtils.resultSetToTableModel(rs));

            // Alternate row colors for the table
            table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        c.setBackground(new Color(230, 230, 255)); // Light blue for even rows
                    } else {
                        c.setBackground(Color.WHITE); // White for odd rows
                    }
                    return c;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while loading customer data!");
        }
    }

    // Hover effect for buttons
    private void addHoverEffect(JButton button, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor); // Change color on hover
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(button.getBackground()); // Reset color when not hovering
            }
        });
    }

    public static void main(String[] args) {
        new CustomerInfo();
    }
}
