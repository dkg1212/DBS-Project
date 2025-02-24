package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightInfo extends JFrame implements ActionListener {
    JTextField tfFCode;
    JButton searchButton, resetButton;
    JTable table;

    public FlightInfo() {
        setTitle("Flight Information");
        setLayout(null);

        // Load the background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front2.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(800, 550, Image.SCALE_SMOOTH);
        backgroundIcon = new ImageIcon(backgroundImage);

        // Set the background image to a label
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 550); // Set background label size to match the frame
        add(backgroundLabel); // Add the background label to the frame

        // Search Flight Code Label
        JLabel lblFCode = new JLabel("Enter Flight Code:");
        lblFCode.setBounds(50, 20, 180, 30);
        lblFCode.setFont(new Font("Arial", Font.BOLD, 16));
        lblFCode.setForeground(new Color(0, 102, 204)); // Blue color for label text
        backgroundLabel.add(lblFCode); // Add label on top of background

        // Search Flight Code Text Field
        tfFCode = new JTextField();
        tfFCode.setBounds(230, 20, 200, 30);
        tfFCode.setFont(new Font("Arial", Font.PLAIN, 14));
        tfFCode.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        backgroundLabel.add(tfFCode); // Add text field on top of background

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setBounds(440, 20, 100, 30);
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setOpaque(true);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        searchButton.addActionListener(this);
        addHoverEffect(searchButton, new Color(0, 85, 170)); // Darker blue on hover
        backgroundLabel.add(searchButton); // Add button on top of background

        // Reset Button
        resetButton = new JButton("Reset");
        resetButton.setBounds(550, 20, 100, 30);
        resetButton.setBackground(new Color(34, 139, 34)); // Green button
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        resetButton.setOpaque(true);
        resetButton.addActionListener(this);
        addHoverEffect(resetButton, new Color(28, 120, 28)); // Darker green on hover
        backgroundLabel.add(resetButton); // Add button on top of background

        // Flight Table with some padding and customized header
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 80, 700, 400);
        backgroundLabel.add(jsp); // Add table on top of background

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

        // Load all flights initially
        loadAllFlights();

        setSize(800, 550);
        setLocation(400, 200);
        setVisible(true);
        setResizable(false); // Prevent resizing to keep the layout intact
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {
            String fCode = tfFCode.getText().trim(); // Trim spaces for clean input

            if (fCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a flight code!");
                return;
            }

             try {
                Conn conn = new Conn();
                String query = "SELECT * FROM flight WHERE f_code = ?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, fCode);

                ResultSet rs = pst.executeQuery();

                if (rs.isBeforeFirst()) { // Check if the ResultSet has data
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong flight code!");
                }
            }catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while fetching flight details!");
            }
        } else if (ae.getSource() == resetButton) {
            // Reset search field and reload all flights
            tfFCode.setText("");
            loadAllFlights();
        }
    }

    // Method to load all flight data into the table
    private void loadAllFlights() {
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM flight";
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
            JOptionPane.showMessageDialog(null, "Error while loading flight data!");
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
        new FlightInfo();
    }
}
