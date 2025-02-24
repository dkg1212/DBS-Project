package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import net.proteanit.sql.DbUtils;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    Choice source, destination;
    JButton show;
    JTextField pnrField;

    public JourneyDetails() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Source Label
        JLabel lblsource = new JLabel("Source");
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblsource.setBounds(50, 50, 100, 25);
        add(lblsource);

        // Source Choice Dropdown
        source = new Choice();
        source.setBounds(160, 50, 150, 25);
        add(source);

        // Destination Label
        JLabel lbldestination = new JLabel("Destination");
        lbldestination.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbldestination.setBounds(350, 50, 100, 25);
        add(lbldestination);

        // Destination Choice Dropdown
        destination = new Choice();
        destination.setBounds(460, 50, 150, 25);
        add(destination);

        // PNR Label and Text Field
        JLabel lblpnr = new JLabel("PNR (Optional)");
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblpnr.setBounds(50, 90, 120, 25);
        add(lblpnr);

        pnrField = new JTextField();
        pnrField.setBounds(160, 90, 150, 25);
        add(pnrField);

        // Fetch data for Source and Destination dropdowns
        try {
            Conn conn = new Conn();
            // Populate source dropdown
            ResultSet rs = conn.s.executeQuery("SELECT DISTINCT src FROM reservation");
            while (rs.next()) {
                source.add(rs.getString("src"));
            }

            // Populate destination dropdown
            rs = conn.s.executeQuery("SELECT DISTINCT des FROM reservation");
            while (rs.next()) {
                destination.add(rs.getString("des"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Show Details Button
        show = new JButton("Show Details");
        show.setBackground(Color.blue);
        show.setForeground(Color.WHITE);
        show.setBounds(650, 50, 120, 25);
        show.setBorder(BorderFactory.createLineBorder(new Color(255, 102, 0), 0));
        show.setOpaque(true);
        show.addActionListener(this);
        add(show);

        // Table to Display Data
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 150, 800, 400);
        jsp.setBackground(Color.WHITE);
        add(jsp);

        setSize(800, 600);
        setLocation(400, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            // Fetch selected source, destination, and PNR
            String selectedSource = source.getSelectedItem();
            String selectedDestination = destination.getSelectedItem();
            String pnr = pnrField.getText().trim();

            String query = "SELECT PNR, name, nationality, flightname, flightcode, ddate, src, des " +
                           "FROM reservation ";

            // Modify query based on input
            if (!pnr.isEmpty()) {
                // Search by PNR if provided
                query += "WHERE PNR = '" + pnr + "'";
            } else if (!selectedSource.equals("Select Source") && !selectedDestination.equals("Select Destination")) {
                // Search by source and destination
                query += "WHERE src = '" + selectedSource + "' AND des = '" + selectedDestination + "'";
            } else {
                // No search filter, show all data
                query += "";
            }

            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery(query);

            // Check if results exist
            if (!rs.isBeforeFirst()) { // If no rows in ResultSet
                JOptionPane.showMessageDialog(null, "No Journeys Found for the Selected Criteria");
                table.setModel(new javax.swing.table.DefaultTableModel()); // Clear table if no data
                return;
            }

            // Display data in table
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
