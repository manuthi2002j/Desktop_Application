import java.awt.EventQueue;
import java.sql.*;

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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;

public class Login extends JFrame {
    private Connection connection;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField UsertextField;
    private JPasswordField passwordField;
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setResizable(false);
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
               
            }
        });
        
    }

    public Login() {
    	
    	ImageIcon img =new ImageIcon("new.jpg");
    	Image im=img.getImage().getScaledInstance(466, 308, DO_NOTHING_ON_CLOSE);
		img=new ImageIcon(im);
    	
        setBounds(100, 100, 939, 688);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.text);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel UsernameLabel = new JLabel("Username:");
        UsernameLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        UsernameLabel.setBounds(547, 210, 73, 18);
        contentPane.add(UsernameLabel);

        UsertextField =  new JTextField();
        UsertextField.setBounds(547, 245, 299, 33);
        contentPane.add(UsertextField);
        UsertextField.setColumns(10);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        passwordLabel.setBounds(547, 300, 73, 18);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(547, 335, 299, 33);
        contentPane.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String username = UsertextField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and password are required.");
                    return;
                }

                try {
                    connection = DatabaseConnection.getConnection();
                    Statement stmt = connection.createStatement();
                    String sql = "SELECT * FROM admins WHERE Username='" + username + "' AND Password='" + password + "'";
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        HomeView HV = new HomeView();
                        HV.setVisible(true);
                        connection.close();
                        dispose();
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loginButton.setBounds(625, 404, 164, 43);
        contentPane.add(loginButton);

        JButton addUserButton = new JButton("Register Admin");
        addUserButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    AdminTable at = new AdminTable();
                    at.setVisible(true);
                    dispose();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        });
        addUserButton.setBounds(625, 462, 164, 43);
        contentPane.add(addUserButton);
        
        JPanel panel = new JPanel();
        panel.setForeground(new Color(212, 232, 133));
        panel.setBackground(new Color(186, 218, 56));
        panel.setBounds(0, 156, 466, 495);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel bg = new JLabel("");
        bg.setBounds(0, 94, 466, 294);
        bg.setIcon(img);
        panel.add(bg);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(2, 115, 81));
        panel_1.setBounds(0, 0, 925, 156);
        contentPane.add(panel_1);
                panel_1.setLayout(null);
                
                        JLabel Login = new JLabel("Login Page");
                        Login.setForeground(new Color(255, 255, 255));
                        Login.setBounds(331, 36, 259, 91);
                        panel_1.add(Login);
                        Login.setHorizontalAlignment(SwingConstants.CENTER);
                        Login.setFont(new Font("Times New Roman", Font.BOLD, 50));
        
        
        
    }
}
