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
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;


public class InventoryFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JTable table;
	private Connection connection;
	private DefaultTableModel tableModel;
	private JTextField searchtextField;
	private JTextField quantitytextField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryFrame frame = new InventoryFrame();
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
	public InventoryFrame() {
		connection=DatabaseConnection.getConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1099, 786);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel fuelsupplyLabel = new JLabel("Inventory Table");
		fuelsupplyLabel.setForeground(new Color(255, 255, 255));
		fuelsupplyLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 55));
		fuelsupplyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fuelsupplyLabel.setBounds(0, 0, 1093, 150);
		contentPane.add(fuelsupplyLabel);
		panel.setBackground(new Color(2, 115, 81));
		panel.setBounds(0, 0, 1093, 139);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(213, 233, 136));
		panel_1.setBounds(44, 251, 507, 488);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		
		JLabel SupplierName = new JLabel("Supplier Name:");
		SupplierName.setHorizontalAlignment(SwingConstants.LEFT);
		SupplierName.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		SupplierName.setBounds(17, 20, 157, 35);
		panel_1.add(SupplierName);
		
		JComboBox<String> SupplieromboBox = new JComboBox<>();
		SupplieromboBox.setBounds(184, 22, 226, 29);
		panel_1.add(SupplieromboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(615, 264, 425, 387);
		contentPane.add(scrollPane);
		
		table = new JTable();
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(184, 163, 206, 152);
		panel_1.add(calendar);
		

		String query = "SELECT SupplierName FROM suppliers";
		ArrayList<String> SupplierNames = new ArrayList<>();

		try {
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    ResultSet rs = preparedStatement.executeQuery();

		    while (rs.next()) {
		        SupplierNames.add(rs.getString("SupplierName"));
		    }

		    preparedStatement.close();
		} catch (SQLException e) {
		    System.out.println("Error: " + e.getMessage());
		    e.printStackTrace();
		}

		// Verify if any supplier names were retrieved
		if (SupplierNames.isEmpty()) {
		    System.out.println("No supplier names found in the database.");
		} else {
		    // Populate the JComboBox with supplier names
		    for (String supplierName : SupplierNames) {
		        SupplieromboBox.addItem(supplierName);
		    }
		}

		

		// Populate the JComboBox with fuel names after retrieving all data
		

		
		JButton btnNewButton = new JButton("Insert");
		btnNewButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedSupplierName = (String) SupplieromboBox.getSelectedItem();
		        Date date = calendar.getDate(); // Get java.util.Date
		        String quantity = quantitytextField.getText();

		        try {
		            String supplierIdQuery = "SELECT SupplierID FROM suppliers WHERE SupplierName = ?";
		            PreparedStatement supplierIdPst = connection.prepareStatement(supplierIdQuery);
		            supplierIdPst.setString(1, selectedSupplierName);
		            ResultSet supplierIdResult = supplierIdPst.executeQuery();

		            int supplierId = 0;
		            if (supplierIdResult.next()) {
		                supplierId = supplierIdResult.getInt("SupplierID");
		            }

		            
		            String insertFuelTypeQuery = "INSERT INTO inventory (FuelSupplierID, StockQuantity, LastStockedDate) VALUES (?, ?, ?)";
		            PreparedStatement insertFuelTypePst = connection.prepareStatement(insertFuelTypeQuery);
		            insertFuelTypePst.setInt(1, supplierId);
		            insertFuelTypePst.setString(2, quantity);
		            insertFuelTypePst.setDate(3, new java.sql.Date(date.getTime())); // Convert to java.sql.Date

		            int rowsInserted = insertFuelTypePst.executeUpdate();
		            if (rowsInserted > 0) {
		                System.out.println("Data inserted into inventory successfully.");
		            } else {
		                System.out.println("Failed to insert data into inventory.");
		            }
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		});

		btnNewButton.setBounds(17, 409, 133, 69);
		panel_1.add(btnNewButton);
		
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedSupplierName = (String) SupplieromboBox.getSelectedItem();
		        Date date = calendar.getDate();

		        try {
		            int inventoryID = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
		            int quantity = Integer.parseInt(quantitytextField.getText());

		            
		            PreparedStatement pst2 = connection.prepareStatement("SELECT SupplierID FROM suppliers WHERE SupplierName = ?");
		            pst2.setString(1, selectedSupplierName);
		            ResultSet rs1 = pst2.executeQuery();

		            if (rs1.next()) {
		                int supplierId = rs1.getInt("SupplierID");

		                PreparedStatement pst1 = connection.prepareStatement("SELECT FuelSupplierID FROM fuelsuppliers WHERE SupplierID = ?");
		                pst1.setInt(1, supplierId);
		                ResultSet rs = pst1.executeQuery();

		                if (rs.next()) {
		                    int fuelsupplierId = rs.getInt("FuelSupplierID");

		                    
		                    PreparedStatement pst3 = connection.prepareStatement("UPDATE inventory SET FuelSupplierID = ?, StockQuantity = ?, LastStockedDate = ? WHERE InventoryID = ?");
		                    pst3.setInt(1, fuelsupplierId);
		                    pst3.setInt(2, quantity);
		                    pst3.setDate(3, new java.sql.Date(date.getTime()));
		                    pst3.setInt(4, inventoryID);
		                    
		                    int rowsUpdated = pst3.executeUpdate();
		                    if (rowsUpdated > 0) {
		                        System.out.println("Inventory updated successfully.");
		                    } else {
		                        System.out.println("Failed to update inventory.");
		                    }
		                    pst3.close();
		                }
		                pst1.close();
		            }
		            pst2.close();
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		            // Add code here to handle and report the exception.
		        }
		    }
		});

		updateButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		updateButton.setBounds(173, 409, 133, 69); 
		panel_1.add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int inventoryID=Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
				
				try {
					PreparedStatement pst=connection.prepareStatement("DELETE FROM inventory WHERE InventoryID='"+inventoryID+"'");
					pst.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		deleteButton.setBounds(330, 409, 133, 69);
		panel_1.add(deleteButton);
		
		JLabel QuantityLabel = new JLabel("Quantity:");
		QuantityLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		QuantityLabel.setBounds(17, 82, 114, 35);
		panel_1.add(QuantityLabel);
		
		quantitytextField = new JTextField();
		quantitytextField.setBounds(184, 82, 226, 29);
		panel_1.add(quantitytextField);
		quantitytextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Last Stocked Date:");
		lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		lblNewLabel.setBounds(17, 163, 153, 35);
		panel_1.add(lblNewLabel);
		
		

		
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	tableModel.setRowCount(0);
		        tableModel.setColumnCount(0);

		        try {
		            PreparedStatement st2 = connection.prepareStatement("SELECT * FROM inventory;");
		            ResultSet rs = st2.executeQuery();
		            ResultSetMetaData rsmd = rs.getMetaData();
		            int columnCount = rsmd.getColumnCount();

		            for (int i = 1; i <= columnCount; i++) {
		                tableModel.addColumn(rsmd.getColumnName(i));
		            }

		            while (rs.next()) {
		                Object[] rowData = new Object[columnCount];
		                for (int i = 1; i <= columnCount; i++) {
		                    rowData[i - 1] = rs.getString(i);
		                }
		                tableModel.addRow(rowData);
		            }

		            st2.close();
		        } catch (Exception q) {
		            q.printStackTrace();
		        }
		    }
		});

		refreshButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		refreshButton.setBounds(932, 173, 113, 69);
		contentPane.add(refreshButton);
		
		searchtextField = new JTextField();
		searchtextField.setBounds(627, 197, 182, 31);
		contentPane.add(searchtextField);
		searchtextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Search=searchtextField.getText();
				
				try {
					PreparedStatement pst = connection.prepareStatement("SELECT *, s.SupplierName, ft.FuelName FROM fuelsuppliers fs JOIN suppliers s ON fs.SupplierID = s.SupplierID JOIN fueltypes ft ON fs.FuelTypeID = ft.FuelTypeID WHERE fs.FuelSupplierID LIKE ? OR fs.FuelTypeID LIKE ?");
					 for (int i = 1; i <= 2; i++) {
	                        pst.setString(i, "%" + Search + "%");
	                    }

                    

                    ResultSet rs=pst.executeQuery();
                    tableModel.setRowCount(0);
                    
                    while(rs.next()) {
                    	Object[] RowData=new Object[tableModel.getColumnCount()];
                    	for(int i=1;i<=tableModel.getColumnCount();i++) {
                    		RowData[i-1]=rs.getString(i);
                    		
                    	}
                    	tableModel.addRow(RowData);
                    }
                    
                    pst.close();
               
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		btnSearch.setBounds(819, 173, 103, 69);
		contentPane.add(btnSearch);
		

		
		
	}
}
