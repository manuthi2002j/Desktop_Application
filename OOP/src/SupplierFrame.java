import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JComboBox;

public class SupplierFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
    private Connection connection;
    private JTextField searchField;
    private final JPanel panel = new JPanel();
    private JTextField suppilernametextField;
    private JTextField contactnametextField;;
    private JTextField emailtextField;
    private JTextField contacttextField;
    private JTextField Brntext;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String CONTACT_PATTERN = "^[0-9]{10}$";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierFrame frame = new SupplierFrame();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SupplierFrame() {
		connection = DatabaseConnection.getConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1099, 703);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(568, 209, 478, 402);
		contentPane.add(scrollPane);
		
		tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        panel.setBackground(new Color(2, 115, 81));
        panel.setBounds(0, 0, 1085, 136);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Supplier Table");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 56));
        lblNewLabel.setBounds(0, 0, 1088, 136);
        panel.add(lblNewLabel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(213, 233, 136));
        panel_1.setBounds(35, 213, 505, 402);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        
        JButton InsertButton = new JButton("Insert");
        InsertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields when the Insert button is clicked
                
                String Suppliername = suppilernametextField.getText();
                String Brn = Brntext.getText();
                String Contactname = contactnametextField.getText();
                String Email = emailtextField.getText();
                String Contact = contacttextField.getText();
                
                String errorMessage = "";

                if (Suppliername.isEmpty()) {
                    errorMessage += "First Name is required.\n";
                }

                if (Suppliername.isEmpty()) {
                    errorMessage += "Last Name is required.\n";
                }

              

                if (Email.isEmpty()) {
                    errorMessage += "Email is required.\n";
                } else {
                    if (!Pattern.matches(EMAIL_PATTERN, Email)) {
                        errorMessage += "Invalid email format(contains one or more alphanumeric characters, plus signs, underscores, periods, or hyphens, followed by an at sign (@), and then one or more characters, including periods (.)).\n";
                    }
                }

                if (Brn.isEmpty()) {
                    errorMessage += "NIC is required.\n";
                } 

                if (Contact.isEmpty()) {
                    errorMessage += "Contact is required.\n";
                } else {
                    if (!Pattern.matches(CONTACT_PATTERN, Contact)) {
                        errorMessage += "Invalid contact format(contains 10 digits).\n";
                    }
                }

                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage);
                } else {

                try {
                    // Check if a record with the same NIC already exists
                    PreparedStatement checkStatement = connection.prepareStatement("SELECT SupplierID FROM suppliers WHERE BRN = ?");
                    checkStatement.setString(1, Brn);
                    ResultSet checkResult = checkStatement.executeQuery();

                    if (checkResult.next()) {
                        // A record with the same NIC already exists, prevent insertion
                        JOptionPane.showMessageDialog(InsertButton, "A record with the same Data already exists.");
                    } else {
                        // Insert the new record
                        Statement st = connection.createStatement();
                        String sql = "INSERT INTO suppliers (SupplierName, BRN, ContactName, Email, Contact) VALUES ('" + Suppliername + "','" + Brn + "','" + Contactname + "','" + Email + "','" + Contact + "')";
                        st.executeUpdate(sql);
                        refreshTable();

                        JOptionPane.showMessageDialog(InsertButton, "Data inserted.....");

                        // Reset text fields
                        suppilernametextField.setText("");
                        contactnametextField.setText("");
                        emailtextField.setText("");
                        contacttextField.setText("");
                        Brntext.setText("");
                        st.close();
                    }

                    checkStatement.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                }
            }
        });
        InsertButton.setFocusable(false);
        InsertButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        InsertButton.setBounds(22, 326, 117, 66);
        panel_1.add(InsertButton);
        
        JButton updatebutton = new JButton("Update");
        updatebutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String Suppliername = suppilernametextField.getText();
                String Brn = Brntext.getText();
                String Contactname = contactnametextField.getText();
                String Email = emailtextField.getText();
                String Contact = contacttextField.getText();
                
                String errorMessage = "";

                if (Suppliername.isEmpty()) {
                    errorMessage += "First Name is required.\n";
                }

                if (Suppliername.isEmpty()) {
                    errorMessage += "Last Name is required.\n";
                }

              

                if (Email.isEmpty()) {
                    errorMessage += "Email is required.\n";
                } else {
                    if (!Pattern.matches(EMAIL_PATTERN, Email)) {
                        errorMessage += "Invalid email format(contains one or more alphanumeric characters, plus signs, underscores, periods, or hyphens, followed by an at sign (@), and then one or more characters, including periods (.)).\n";
                    }
                }

                if (Brn.isEmpty()) {
                    errorMessage += "NIC is required.\n";
                } 

                if (Contact.isEmpty()) {
                    errorMessage += "Contact is required.\n";
                } else {
                    if (!Pattern.matches(CONTACT_PATTERN, Contact)) {
                        errorMessage += "Invalid contact format(contains 10 digits).\n";
                    }
                }

                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage);
                } else {
        		try {
        			int supID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql="Update suppliers Set SupplierName='"+Suppliername+"',BRN='"+Brn+"',ContactName='"+Contactname+"',Email='"+Email+"',Contact='"+Contact+"' Where SupplierID="+supID;
					st.executeUpdate(sql);
					refreshTable();
					
					suppilernametextField.setText("");
                    contactnametextField.setText("");
                    emailtextField.setText("");
                    contacttextField.setText("");
                    Brntext.setText("");
					
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}  
                }
        		
        	}
        });
        updatebutton.setFocusable(false);
        updatebutton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        updatebutton.setBounds(183, 326, 117, 66);
        panel_1.add(updatebutton);
        
        JButton DeleteButton = new JButton("Delete");
        DeleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			int supID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql="Delete from suppliers Where SupplierID="+supID;
					st.executeUpdate(sql);
					refreshTable();
					
					
					
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}        	
        	}
        });
        DeleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        DeleteButton.setFocusable(false);
        DeleteButton.setBounds(378, 325, 117, 66);
        panel_1.add(DeleteButton);
        
        JLabel SupplierNamelbl = new JLabel("Supplier Name:");
        SupplierNamelbl.setHorizontalAlignment(SwingConstants.LEFT);
        SupplierNamelbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        SupplierNamelbl.setBounds(22, 10, 105, 28);
        panel_1.add(SupplierNamelbl);
        
        JLabel ContactLbl = new JLabel("Contact Name:");
        ContactLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        ContactLbl.setBounds(22, 83, 105, 28);
        panel_1.add(ContactLbl);
        
        JLabel lblNewLabel_1_5 = new JLabel("Email:");
        lblNewLabel_1_5.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel_1_5.setBounds(22, 121, 78, 28);
        panel_1.add(lblNewLabel_1_5);
        
        suppilernametextField = new JTextField();
        suppilernametextField.setColumns(10);
        suppilernametextField.setBounds(149, 10, 237, 28);
        panel_1.add(suppilernametextField);
        
        contactnametextField = new JTextField();
        contactnametextField.setColumns(10);
        contactnametextField.setBounds(149, 83, 237, 28);
        panel_1.add(contactnametextField);
        
        emailtextField = new JTextField();
        emailtextField.setColumns(10);
        emailtextField.setBounds(149, 121, 237, 28);
        panel_1.add(emailtextField);
        
        contacttextField = new JTextField();
        contacttextField.setColumns(10);
        contacttextField.setBounds(149, 159, 237, 28);
        panel_1.add(contacttextField);
        
        
        
        
        JLabel lblNewLabel_1_5_1 = new JLabel("Contact:");
        lblNewLabel_1_5_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel_1_5_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_5_1.setBounds(22, 159, 78, 28);
        panel_1.add(lblNewLabel_1_5_1);
        
        JLabel BRNlbl = new JLabel("BRN:");
        BRNlbl.setHorizontalAlignment(SwingConstants.LEFT);
        BRNlbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        BRNlbl.setBounds(22, 45, 105, 28);
        panel_1.add(BRNlbl);
        
        Brntext = new JTextField();
        Brntext.setColumns(10);
        Brntext.setBounds(149, 45, 237, 28);
        panel_1.add(Brntext);
        

		
		
        
                searchField = new JTextField();
                searchField.setBounds(581, 158, 200, 30);
                contentPane.add(searchField);
        
        JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
				
			}
		});
		refreshButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		refreshButton.setFocusable(false);
		refreshButton.setBounds(923, 146, 123, 53);
		contentPane.add(refreshButton);
		
		JButton searchButton = new JButton("Search");
	     searchButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
	     searchButton.setFocusable(false);
	     searchButton.setBounds(791, 146, 114, 53);
	     contentPane.add(searchButton);
	     
	     searchButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	String searchText = searchField.getText();

	                try {
	                    PreparedStatement st3 = connection.prepareStatement("SELECT * FROM suppliers WHERE SupplierID LIKE ? or SupplierName LIKE ? ");
	                    for (int i = 1; i <= 2; i++) {
	                        st3.setString(i, "%" + searchText + "%");
	                    }

	                    ResultSet rs = st3.executeQuery();
	                    tableModel.setRowCount(0);

	                    while (rs.next()) {
	                        Object[] rowData = new Object[tableModel.getColumnCount()];
	                        for (int i = 1; i <= tableModel.getColumnCount(); i++) {
	                            rowData[i - 1] = rs.getString(i);
	                        }
	                        tableModel.addRow(rowData);
	                    }

	                    st3.close();
	                } catch (SQLException q) {
	                    q.printStackTrace();
	                }
	            }
	            
	        });
	}
	private void refreshTable() {
		tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        try {
            PreparedStatement st3 = connection.prepareStatement("SELECT * FROM suppliers ;"
            		+ "");
            ResultSet rs1 = st3.executeQuery();
            ResultSetMetaData rsmd1 = rs1.getMetaData();
            int columnCount = rsmd1.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(rsmd1.getColumnName(i));
            }

            while (rs1.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs1.getString(i);
                }
                tableModel.addRow(rowData);
            }

            st3.close();
            rs1.close();
        } catch (SQLException q) {
            q.printStackTrace();
        }
	}
}
