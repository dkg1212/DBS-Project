package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;
    JButton save, back;

    public AddCustomer() {
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background
        setLayout(null);

        // Heading
        JLabel heading = new JLabel("Add Customer Details");
        heading.setBounds(250, 20, 500, 40);
        heading.setFont(new Font("Serif", Font.BOLD, 36));
        heading.setForeground(new Color(0, 102, 204)); // Deep blue color
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading);

        // Name
        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(60, 100, 150, 30);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblname.setForeground(Color.DARK_GRAY);
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(200, 100, 200, 30);
        tfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tfname.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfname);

        // Nationality
        JLabel lblnationality = new JLabel("Nationality:");
        lblnationality.setBounds(60, 150, 150, 30);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblnationality.setForeground(Color.DARK_GRAY);
        add(lblnationality);

        tfnationality = new JTextField();
        tfnationality.setBounds(200, 150, 200, 30);
        tfnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tfnationality.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfnationality);

        // Aadhar
        JLabel lblaadhar = new JLabel("Aadhar No:");
        lblaadhar.setBounds(60, 200, 150, 30);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblaadhar.setForeground(Color.DARK_GRAY);
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(200, 200, 200, 30);
        tfaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tfaadhar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfaadhar);

        // Address
        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setBounds(60, 250, 150, 30);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbladdress.setForeground(Color.DARK_GRAY);
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 200, 30);
        tfaddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tfaddress.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfaddress);

        // Gender
        JLabel lblgender = new JLabel("Gender:");
        lblgender.setBounds(60, 300, 150, 30);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblgender.setForeground(Color.DARK_GRAY);
        add(lblgender);

        ButtonGroup gendergroup = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(200, 300, 70, 30);
        rbmale.setBackground(new Color(240, 248, 255));
        rbmale.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(280, 300, 80, 30);
        rbfemale.setBackground(new Color(240, 248, 255));
        rbfemale.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(rbfemale);

        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);

        // Phone
        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(60, 350, 150, 30);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblphone.setForeground(Color.DARK_GRAY);
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(200, 350, 200, 30);
        tfphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tfphone.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(tfphone);

        // Save Button
        save = new JButton("Save");
        save.setBounds(200, 420, 100, 40);
        save.setBackground(new Color(0, 102, 204));
        save.setForeground(Color.WHITE);
        save.setFont(new Font("Tahoma", Font.BOLD, 16));
        save.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        save.setOpaque(true);
        save.addActionListener(this);
        save.setFocusPainted(false);
        add(save);

        // Back Button
        back = new JButton("Back");
        back.setBounds(320, 420, 100, 40);
        back.setBackground(new Color(128, 128, 128));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 16));
        back.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128), 1));
        back.setFocusPainted(false);
        back.setOpaque(true);
        back.addActionListener(ae -> setVisible(false));
        add(back);

        // Image Icon
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/customer.png"));
        Image scaledImage = image.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(450, 70, 400,400 );
        add(lblimage);

        // Frame settings
        setSize(900, 550);
        setLocation(300, 150);
        setTitle("Add Customer Details");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String address = tfaddress.getText();
        String aadhar = tfaadhar.getText();
        String gender = rbmale.isSelected() ? "Male" : rbfemale.isSelected() ? "Female" : null;

        if (name.isEmpty() || nationality.isEmpty() || phone.isEmpty() || address.isEmpty() || aadhar.isEmpty() || gender == null) {
            JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "insert into passenger values('"+name+"', '"+nationality+"', '"+phone+"', '"+address+"', '"+aadhar+"', '"+gender+"')";
        
            conn.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Customer Details Added Successfully!");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
