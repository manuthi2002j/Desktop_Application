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
import java.util.Date;

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

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JCalendar;


public class TransactionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
    private Connection connection;
    private JTextField searchField;
    private final JPanel panel = new JPanel();
    private JTextField CusIDtext;;
    private JTextField litertextField;
    private JTextField pricetextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransactionFrame frame = new TransactionFrame();
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
	public TransactionFrame() {
		connection = DatabaseConnection.getConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1099, 732);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
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
        
        JLabel lblNewLabel = new JLabel("Customer Transaction Table");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 56));
        lblNewLabel.setBounds(0, 0, 1088, 136);
        panel.add(lblNewLabel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(213, 233, 136));
        panel_1.setBounds(35, 213, 505, 446);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel FirstNamelbl = new JLabel("Customer ID\r\n");
        FirstNamelbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        FirstNamelbl.setBounds(22, 10, 151, 28);
        panel_1.add(FirstNamelbl);
        
        JLabel LastLbl = new JLabel("Date");
        LastLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        LastLbl.setBounds(22, 48, 151, 28);
        panel_1.add(LastLbl);
        
        JLabel emailLbl = new JLabel("Liter Dispensed");
        emailLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        emailLbl.setBounds(22, 216, 151, 28);
        panel_1.add(emailLbl);
        
        CusIDtext = new JTextField();
        CusIDtext.setColumns(10);
        CusIDtext.setBounds(183, 10, 237, 28);
        panel_1.add(CusIDtext);
        
        litertextField = new JTextField();
        litertextField.setColumns(10);
        litertextField.setBounds(183, 216, 237, 28);
        panel_1.add(litertextField);
        
        pricetextField = new JTextField();
        pricetextField.setColumns(10);
        pricetextField.setBounds(183, 254, 237, 28);
        panel_1.add(pricetextField);
        
        JComboBox paymentcomboBox = new JComboBox();
        paymentcomboBox.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        paymentcomboBox.setModel(new DefaultComboBoxModel(new String[] {"Cash", "Credit Card", "Debit Card"}));
        paymentcomboBox.setBounds(183, 292, 237, 28);
        panel_1.add(paymentcomboBox);
        
        JCalendar calendar = new JCalendar();
        calendar.setBounds(183, 48, 206, 152);
        panel_1.add(calendar);
        
        
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int cusID=Integer.parseInt(CusIDtext.getText());
                int liter=Integer.parseInt(litertextField.getText());
                int price=Integer.parseInt(pricetextField.getText());
                String paymentMethod=(String)paymentcomboBox.getSelectedItem();
                Date date=calendar.getDate();
                
                try {
                    // Check if a customer ID exists in the customers table
                    PreparedStatement checkStatement = connection.prepareStatement("SELECT CustomerID FROM customers WHERE CustomerID = ?");
                    checkStatement.setInt(1, cusID);
                    ResultSet checkResult = checkStatement.executeQuery();

                    if (!checkResult.next()) {

                        // Customer ID does not exist, show message
                    	JOptionPane.showMessageDialog(insertButton, "Customer ID does not exist.");
                        
                    } else {
                        // Customer ID exists, insert data into the transactions table
                    	PreparedStatement st = connection.prepareStatement("INSERT INTO customertransactions (CustomerID, LitersDispensed, TotalPrice, PaymentMethod, TransactionDate) VALUES (?, ?, ?, ?, ?)");
                        
                        st.setInt(1, cusID);
                        st.setInt(2, liter);
                        st.setInt(3, price);
                        st.setString(4, paymentMethod);
                        st.setDate(5, new java.sql.Date(date.getTime()));
                        st.executeUpdate();
                        refreshTable();

                        JOptionPane.showMessageDialog(insertButton, "Data inserted successfully.");

                        // Reset text fields
                        CusIDtext.setText("");
                        litertextField.setText("");
                        pricetextField.setText("");
                        paymentcomboBox.setSelectedItem("");
                        st.close();
                    	
                    }

                    checkStatement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        insertButton.setFocusable(false);
        insertButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        insertButton.setBounds(10, 361, 117, 75);
        panel_1.add(insertButton);
        
        JButton updatebutton = new JButton("Update");
        updatebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int cusID = Integer.parseInt(CusIDtext.getText());
                    int liter = Integer.parseInt(litertextField.getText());
                    int price = Integer.parseInt(pricetextField.getText());
                    String paymentMethod = (String) paymentcomboBox.getSelectedItem();
                    Date date = calendar.getDate();

                    int customerTransactionID = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());

                    // Check if the CustomerID value exists in the customers table
                    PreparedStatement st = connection.prepareStatement("SELECT CustomerID FROM customers WHERE CustomerID = ?");
                    st.setInt(1, cusID);
                    ResultSet rs = st.executeQuery();

                    if (!rs.next()) {
                        // Customer ID does not exist, display an error message
                        JOptionPane.showMessageDialog(updatebutton, "Customer ID does not exist in the customers table.");
                    } else {
                        // Customer ID exists, proceed with the update
                        PreparedStatement st1 = connection.prepareStatement("UPDATE customertransactions SET LitersDispensed = ?, TotalPrice = ?, PaymentMethod = ?, CustomerID = ? WHERE CustomerTransactionID = ?");
                        st1.setInt(1, liter);
                        st1.setInt(2, price);
                        st1.setString(3, paymentMethod);
                        st1.setInt(4, cusID);
                        st1.setInt(5, customerTransactionID);
                        st1.executeUpdate();

                        refreshTable();
                        st1.close();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace(); // Handle the exception as needed
                    // Display an error message to the user
                    JOptionPane.showMessageDialog(updatebutton, "An error occurred while updating the transaction.");
                } catch (NumberFormatException e2) {
                    // Handle NumberFormatException (e.g., when parsing text fields)
                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(updatebutton, "Invalid input. Please enter valid numeric values.");
                }
            }
        });


        updatebutton.setFocusable(false);
        updatebutton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        updatebutton.setBounds(183, 361, 117, 75);
        panel_1.add(updatebutton);
        
        JButton DeleteButton = new JButton("Delete");
        DeleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			int CustransactionID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql = "DELETE FROM customertransactions WHERE CustomerTransactionID = " + CustransactionID;
					st.executeUpdate(sql);
					
					refreshTable();
					st.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}        	
        	}
        });
        DeleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        DeleteButton.setFocusable(false);
        DeleteButton.setBounds(378, 361, 117, 75);
        panel_1.add(DeleteButton);
        
        JLabel contactLbl = new JLabel("Total Price:");
        contactLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        contactLbl.setHorizontalAlignment(SwingConstants.LEFT);
        contactLbl.setBounds(22, 254, 151, 28);
        panel_1.add(contactLbl);
        
       
        
        JLabel typeLbl = new JLabel("Payment Method");
        typeLbl.setHorizontalAlignment(SwingConstants.LEFT);
        typeLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        typeLbl.setBounds(22, 292, 151, 28);
        panel_1.add(typeLbl);
        
        
        
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
	                    PreparedStatement st3 = connection.prepareStatement("SELECT * FROM customertransactions WHERE CustomerID LIKE ? or CustomerTransactionID LIKE ? ");
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
            PreparedStatement st3 = connection.prepareStatement("Select * From customertransactions");
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
        } catch (Exception q) {
            q.printStackTrace();
        }
	}
}
