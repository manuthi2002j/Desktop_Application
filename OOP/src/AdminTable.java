import java.awt.EventQueue;
import java.sql.*;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;

public class AdminTable extends JFrame {
    private Connection connection;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField AdFirstNameText;
    private JTextField AdLastNameText;
    private JTextField AdUserNameText;
    private JTextField AdcontactText;
    private JTextField AdEmailText;
    private JPasswordField AdPWText;
    private JTextField textField;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String NIC_PATTERN = "^[0-9]{9}[vVxX]$"; 
    private static final String CONTACT_PATTERN = "^[0-9]{10}$"; 
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminTable frame = new AdminTable();
                    frame.setResizable(false);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminTable() {
    	
    	ImageIcon img =new ImageIcon("new.jpg");
    	Image im=img.getImage().getScaledInstance(466, 308, DO_NOTHING_ON_CLOSE);
		img=new ImageIcon(im);
		
        setBounds(100, 100, 955, 599);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel AdFirst_name = new JLabel("First Name:");
        AdFirst_name.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        AdFirst_name.setBounds(507, 153, 94, 32);

        AdFirstNameText = new JTextField();
        AdFirstNameText.setBounds(626, 156, 257, 30);
        AdFirstNameText.setColumns(10);

        JLabel AdLast_name = new JLabel("Last Name:");
        AdLast_name.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        AdLast_name.setBounds(507, 195, 94, 32);

        JLabel AdUser_name = new JLabel("Username:");
        AdUser_name.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        AdUser_name.setBounds(507, 237, 94, 32);

        JLabel lblNewLabel_1 = new JLabel("Password:");
        lblNewLabel_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel_1.setBounds(507, 320, 94, 32);

        JLabel AdEmail = new JLabel("Email:");
        AdEmail.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        AdEmail.setBounds(507, 362, 94, 32);

        JLabel AdContact = new JLabel("Contact:");
        AdContact.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        AdContact.setBounds(507, 404, 94, 32);
        
        JLabel lblNewLabel = new JLabel("NIC:");
        lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel.setBounds(507, 279, 94, 32);
        contentPane.add(lblNewLabel);

        AdLastNameText = new JTextField();
        AdLastNameText.setBounds(626, 196, 257, 30);
        AdLastNameText.setColumns(10);

        AdUserNameText = new JTextField();
        AdUserNameText.setBounds(626, 240, 257, 30);
        AdUserNameText.setColumns(10);

        AdPWText = new JPasswordField();
        AdPWText.setBounds(626, 320, 257, 32);

        AdEmailText = new JTextField();
        AdEmailText.setBounds(626, 363, 257, 30);
        AdEmailText.setColumns(10);

        AdcontactText = new JTextField();
        AdcontactText.setBounds(626, 405, 257, 30);
        AdcontactText.setColumns(10);
        
        textField = new JTextField();
        textField.setBounds(626, 280, 257, 30);
        contentPane.add(textField);
        textField.setColumns(10);

     

        JButton Register = new JButton("Register");
        Register.setBounds(775, 470, 114, 43);
        Register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = AdFirstNameText.getText();
                String lastName = AdLastNameText.getText();
                String userName = AdUserNameText.getText();
                String password = new String(AdPWText.getPassword());
                String email = AdEmailText.getText();
                String contact = AdcontactText.getText();
                String Nic = textField.getText();

                String errorMessage = "";

                if (firstName.isEmpty()) {
                    errorMessage += "First Name is required.\n";
                }

                if (lastName.isEmpty()) {
                    errorMessage += "Last Name is required.\n";
                }

                if (userName.isEmpty()) {
                    errorMessage += "Username is required.\n";
                } else {
                    if (!Pattern.matches(USERNAME_PATTERN, userName)) {
                        errorMessage += "Invalid username format(contains 3 to 15 characters, including alphanumeric characters, underscores, and hyphens).\n";
                    }
                }

                if (password.isEmpty()) {
                    errorMessage += "Password is required.\n";
                } else {
                    if (!Pattern.matches(PASSWORD_PATTERN, password)) {
                        errorMessage += "Invalid password format(contains at least 6 characters, including at least one digit, one lowercase letter, and one uppercase letter).\n";
                    }
                }

                if (email.isEmpty()) {
                    errorMessage += "Email is required.\n";
                } else {
                    if (!Pattern.matches(EMAIL_PATTERN, email)) {
                        errorMessage += "Invalid email format(contains one or more alphanumeric characters, plus signs, underscores, periods, or hyphens, followed by an at sign (@), and then one or more characters, including periods (.)).\n";
                    }
                }

                if (Nic.isEmpty()) {
                    errorMessage += "NIC is required.\n";
                } else {
                    if (!Pattern.matches(NIC_PATTERN, Nic)) {
                        errorMessage += "Invalid NIC format(contains 9 digits, followed by a single character, which must be either v, V, x, or X).\n";
                    }
                }

                if (contact.isEmpty()) {
                    errorMessage += "Contact is required.\n";
                } else {
                    if (!Pattern.matches(CONTACT_PATTERN, contact)) {
                        errorMessage += "Invalid contact format(contains 10 digits).\n";
                    }
                }

                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage);
                } else {
                    try {
                        connection = DatabaseConnection.getConnection();
                        Statement stml1 = connection.createStatement();
                        String sql1 = "INSERT INTO Admins (FirstName, LastName, NIC, Username, Password, Email, Contact) " +
                                "VALUES ('" + firstName + "','" + lastName + "','" + Nic + "','" + userName + "','" + password + "','" + email + "','" + contact + "')";
                        stml1.executeUpdate(sql1);
                        JOptionPane.showMessageDialog(null, "Registration successful.");
                        clearFields(); // Clear the input fields
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        

        Register.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));

        contentPane.setLayout(null);
        contentPane.add(lblNewLabel_1);
        contentPane.add(AdPWText);
        contentPane.add(AdEmail);
        contentPane.add(AdEmailText);
        contentPane.add(AdContact);
        contentPane.add(AdcontactText);
        contentPane.add(AdUser_name);
        contentPane.add(AdUserNameText);
        contentPane.add(AdLast_name);
        contentPane.add(AdLastNameText);
        contentPane.add(AdFirst_name);
        contentPane.add(AdFirstNameText);
        contentPane.add(Register);
        
        JButton login2Button = new JButton("Login");
        login2Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Login login2=new Login();
        		login2.setVisible(true);
        	}
        });
        login2Button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        login2Button.setBounds(626, 470, 114, 43);
        contentPane.add(login2Button);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(213, 233, 136));
        panel.setBounds(0, 136, 416, 474);
        contentPane.add(panel);
        panel.setLayout(null);
        
                JLabel Bgicon = new JLabel("");
                Bgicon.setBounds(0, 55, 416, 282);
                panel.add(Bgicon);
                Bgicon.setBackground(new Color(126, 0, 1));
                Bgicon.setIcon(img);
                        
                        JPanel panel_1 = new JPanel();
                        panel_1.setBackground(new Color(2, 115, 81));
                        panel_1.setBounds(0, 0, 941, 138);
                        contentPane.add(panel_1);
                        panel_1.setLayout(null);
                        
                                JLabel AdmintopicLabel = new JLabel("Registration");
                                AdmintopicLabel.setBounds(0, 0, 941, 138);
                                panel_1.add(AdmintopicLabel);
                                AdmintopicLabel.setForeground(new Color(255, 255, 255));
                                AdmintopicLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                AdmintopicLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
    }

    // Method to clear input fields
    private void clearFields() {
        AdFirstNameText.setText("");
        AdLastNameText.setText("");
        AdUserNameText.setText("");
        AdPWText.setText("");
        AdEmailText.setText("");
        AdcontactText.setText("");
    }
}
