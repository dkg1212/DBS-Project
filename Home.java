package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    public Home() {
        setLayout(null);
        
        // Resizing and displaying the background image
        ImageIcon originalImage = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front2.jpg"));
        Image resizedImage = originalImage.getImage().getScaledInstance(1600, 1000, Image.SCALE_SMOOTH); // Adjust as per frame size
        ImageIcon scaledIcon = new ImageIcon(resizedImage);
        
        JLabel image = new JLabel(scaledIcon);
        image.setBounds(0, 0, 1600, 1000); // Set label size matching the JFrame's content pane
        add(image);
        
        // Heading on top of the homepage
        JLabel heading = new JLabel("getYourFlight");
heading.setBounds(700, 80, 300, 50);
heading.setForeground(Color.RED);
heading.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 40)); // Bold + Italic font for elegance
heading.setHorizontalAlignment(SwingConstants.LEFT); // Align heading text to the left

// Add a border or shadow effect to make it pop
heading.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.RED, 2),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
));

ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/logo2.jpg")); // Specify the path to your image file
Image scaledImage = logoIcon.getImage().getScaledInstance(480, 120, Image.SCALE_SMOOTH); // Scale the image
logoIcon = new ImageIcon(scaledImage);

JLabel logo = new JLabel(logoIcon);
logo.setBounds(535, 60, 480, 120); // Position the logo to align with the heading

// Add both logo and heading to the parent component
image.add(logo); 

//image.add(heading); 
        
        // Buttons for "Details" options
        JButton flightDetails = createButton("Flight Details", "airlinemanagementsystem/icons/flight.png", 400, 200);
        flightDetails.setActionCommand("Flight Details");
        flightDetails.addActionListener(this);
        image.add(flightDetails);

        JButton customerDetails = createButton("Add Customer Details", "airlinemanagementsystem/icons/customer.png", 650, 200);
        customerDetails.setActionCommand("Add Customer Details");
        customerDetails.addActionListener(this);
        image.add(customerDetails);

        JButton bookFlight = createButton("Book Flight", "airlinemanagementsystem/icons/book.png", 900, 200);
        bookFlight.setActionCommand("Book Flight");
        bookFlight.addActionListener(this);
        image.add(bookFlight);

        JButton journeyDetails = createButton("Journey Details", "airlinemanagementsystem/icons/journey.png", 400, 400);
        journeyDetails.setActionCommand("Journey Details");
        journeyDetails.addActionListener(this);
        image.add(journeyDetails);

        JButton ticketCancellation = createButton("Cancel Ticket", "airlinemanagementsystem/icons/cancel.png", 650, 400);
        ticketCancellation.setActionCommand("Cancel Ticket");
        ticketCancellation.addActionListener(this);
        image.add(ticketCancellation);

        // Buttons for "Ticket" options
        JButton boardingPass = createButton("Boarding Pass", "airlinemanagementsystem/icons/ticket.png", 900, 400);
        boardingPass.setActionCommand("Boarding Pass");
        boardingPass.addActionListener(this);
        image.add(boardingPass);
        
        // Button for "Customer Info"
        JButton customerInfoButton = createButton("Customers Details", "airlinemanagementsystem/icons/Info.png", 650, 600);
        customerInfoButton.setActionCommand("Customers Details");
        customerInfoButton.addActionListener(this);
        image.add(customerInfoButton);

        
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Makes the JFrame fullscreen
        setVisible(true);
    }

    // Utility method to create buttons with icons and text
    private JButton createButton(String text, String iconPath, int x, int y) {
        ImageIcon icon = resizeIcon(iconPath, 100, 100); // Resizing the icon
        JButton button = new JButton(text, icon);
        button.setBounds(x, y, 250, 150); // Set size and position
        button.setHorizontalTextPosition(SwingConstants.CENTER); // Center text below the icon
        button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below icon
        button.setFont(new Font("Tahoma", Font.PLAIN, 20)); // Set font for button text
        button.setFocusPainted(false); // Remove focus highlight
        button.setBackground(Color.WHITE); // Button background color
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Button border
        return button;
    }

    // Utility method to resize icons
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    // Handling button actions
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();
        
        if (text.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (text.equals("Flight Details")) {
            new FlightInfo();
        } else if (text.equals("Book Flight")) {
            new BookFlight();
        } else if (text.equals("Journey Details")) {
            if (isAdminVerified()) { // Verify admin credentials
                new JourneyDetails();
            }
        } else if (text.equals("Cancel Ticket")) {
            new Cancel();
        } else if (text.equals("Boarding Pass")) {
            if (isAdminVerified()) { // Verify admin credentials
                new BoardingPass();
            }
        }
         else if (text.equals("Customers Details")) {
            if (isAdminVerified()) { // Verify admin credentials             
                    new CustomerInfo();
            }
         }
    }
    
    private boolean isAdminVerified() {
        JPasswordField passwordField = new JPasswordField(); // Password input
        Object[] message = {
            "Enter Admin Password:", passwordField
        };
        
        int option = JOptionPane.showConfirmDialog(null, message, "Admin Verification", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            return verifyAdminPassword(password); // Verify the password
        }
        return false;
    }

    // Password verification logic
    private boolean verifyAdminPassword(String password) {
        // Hardcoded admin password for now
        final String ADMIN_PASSWORD = "admin123"; // Replace with your secure password
        if (password.equals(ADMIN_PASSWORD)) {
            return true; // Allow access
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Password. Access Denied.");
            return false; // Deny access
        }
    }


    public static void main(String[] args) {
        new Home();
    }
}
