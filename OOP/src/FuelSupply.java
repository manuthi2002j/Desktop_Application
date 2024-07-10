import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class FuelSupply extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JTable table;
	private Connection connection;
	private DefaultTableModel tableModel;
	private JTextField searchtextField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FuelSupply frame = new FuelSupply();
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
	public FuelSupply() {
		connection=DatabaseConnection.getConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1096, 698);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel fuelsupplyLabel = new JLabel("Fuel Supply Table");
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
		panel_1.setBounds(44, 251, 507, 400);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel SupplierName = new JLabel("Supplier Name:");
		SupplierName.setHorizontalAlignment(SwingConstants.LEFT);
		SupplierName.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		SupplierName.setBounds(17, 61, 114, 35);
		panel_1.add(SupplierName);
		
		JComboBox<String> SuppliercomboBox = new JComboBox<>();
		SuppliercomboBox.setBounds(148, 63, 226, 29);
		panel_1.add(SuppliercomboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(616, 264, 425, 387);
		contentPane.add(scrollPane);
		
		table = new JTable();
		tableModel=new DefaultTableModel();
		table=new JTable(tableModel);
		scrollPane.setViewportView(table);
		

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
		        SuppliercomboBox.addItem(supplierName);
		    }
		}
		
		JComboBox<String> fuelComboBox = new JComboBox<>();
		fuelComboBox.setBounds(148, 118, 226, 29);
		panel_1.add(fuelComboBox);

		String query1 = "SELECT FuelName FROM fueltypes";
		ArrayList<String> fuelNames = new ArrayList<>();

		try {
		    PreparedStatement pst1 = connection.prepareStatement(query1);
		    ResultSet rs1 = pst1.executeQuery();

		    while (rs1.next()) {
		        fuelNames.add(rs1.getString("FuelName"));
		    }

		    pst1.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		// Populate the JComboBox with fuel names after retrieving all data
		for (String fuelName : fuelNames) {
		    fuelComboBox.addItem(fuelName);
		}

		
		JButton btnNewButton = new JButton("Insert");
		btnNewButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedFuelName = (String) fuelComboBox.getSelectedItem();
				String selectedSupplierName = (String) SuppliercomboBox.getSelectedItem();

				try {
				    // Step 1: Get the FuelID for the selected fuel name
				    String fuelIdQuery = "SELECT FuelTypeID FROM fueltypes WHERE FuelName = ?";
				    PreparedStatement fuelIdPst = connection.prepareStatement(fuelIdQuery);
				    fuelIdPst.setString(1, selectedFuelName);
				    ResultSet fuelIdResult = fuelIdPst.executeQuery();

				    int fuelId = 0;
				    if (fuelIdResult.next()) {
				        fuelId = fuelIdResult.getInt("FuelTypeID");
				    }

				    // Step 2: Get the SupplierID for the selected supplier name
				    String supplierIdQuery = "SELECT SupplierID FROM suppliers WHERE SupplierName = ?";
				    PreparedStatement supplierIdPst = connection.prepareStatement(supplierIdQuery);
				    supplierIdPst.setString(1, selectedSupplierName);
				    ResultSet supplierIdResult = supplierIdPst.executeQuery();

				    int supplierId = 0;
				    if (supplierIdResult.next()) {
				        supplierId = supplierIdResult.getInt("SupplierID");
				    }
				    try {
				        // Step 3: Insert data into fueltypes table
				        String insertFuelTypeQuery = "INSERT INTO fuelsuppliers (FuelTypeID, SupplierID) VALUES (?, ?)";
				        PreparedStatement insertFuelTypePst = connection.prepareStatement(insertFuelTypeQuery);
				        insertFuelTypePst.setInt(1, fuelId); // Make sure the column name is 'FuelID'
				        insertFuelTypePst.setInt(2, supplierId);

				        int rowsInserted = insertFuelTypePst.executeUpdate();
				        if (rowsInserted > 0) {
				            System.out.println("Data inserted into fueltypes successfully.");
				        } else {
				            System.out.println("Failed to insert data into fueltypes.");
				        }
				    } catch (SQLException e1) {
				        e1.printStackTrace();
				    }
				    
				} catch (SQLException e2) {
				    e2.printStackTrace();
				    

				}
			}
		});
		btnNewButton.setBounds(17, 321, 133, 69);
		panel_1.add(btnNewButton);
		
		JLabel lblFuelType = new JLabel("Fuel Type:");
		lblFuelType.setHorizontalAlignment(SwingConstants.LEFT);
		lblFuelType.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		lblFuelType.setBounds(17, 116, 114, 35);
		panel_1.add(lblFuelType);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedFuelName = (String) fuelComboBox.getSelectedItem();
		        String selectedSupplierName = (String) SuppliercomboBox.getSelectedItem();

		        try {
		            // Get FuelSupplyID from the selected row in your table
		        	int FuelSupplyID = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));


		            // Prepare statements for querying FuelTypeID and SupplierID
		            PreparedStatement pst1 = connection.prepareStatement("SELECT FuelTypeID FROM fueltypes WHERE FuelName = '"+selectedFuelName+"'");   
		            ResultSet rs = pst1.executeQuery();
		            
		            PreparedStatement pst2 = connection.prepareStatement("SELECT SupplierID FROM suppliers WHERE SupplierName = '"+selectedSupplierName+"'");
		            ResultSet rs1 = pst2.executeQuery();
		            if(rs.next()&& rs1.next()) {
		            int FuelId=rs.getInt("FuelTypeID");
		            int SupplierId=rs1.getInt("SupplierID");

		            PreparedStatement pst3 = connection.prepareStatement("UPDATE fuelsuppliers SET FuelTypeID = '"+FuelId+"', SupplierID ='"+SupplierId+"' WHERE FuelSupplierID = '"+FuelSupplyID+"'");;
		            pst3.executeUpdate();}
		            


		        } catch (SQLException e1) {
		            e1.printStackTrace();
		            // Add code here to handle and report the exception.
		        }
		    }
		});
		updateButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		updateButton.setBounds(173, 321, 133, 69);
		panel_1.add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fuelSupplyID=Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
				
				try {
					PreparedStatement pst=connection.prepareStatement("DELETE FROM fuelsuppliers WHERE FuelSupplierID='"+fuelSupplyID+"'");
					pst.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		deleteButton.setBounds(329, 321, 133, 69);
		panel_1.add(deleteButton);

		
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	tableModel.setRowCount(0);
		        tableModel.setColumnCount(0);

		        try {
		            PreparedStatement st2 = connection.prepareStatement("SELECT fs.*, s.SupplierName, ft.FuelName FROM fuelsuppliers fs JOIN suppliers s ON fs.SupplierID = s.SupplierID JOIN fueltypes ft ON fs.FuelTypeID = ft.FuelTypeID;");
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
		searchtextField.setBounds(616, 197, 193, 31);
		contentPane.add(searchtextField);
		searchtextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Search=searchtextField.getText();
				
				try {
					PreparedStatement pst = connection.prepareStatement("SELECT fs.*, s.SupplierName, ft.FuelName FROM fuelsuppliers fs JOIN suppliers s ON fs.SupplierID = s.SupplierID JOIN fueltypes ft ON fs.FuelTypeID = ft.FuelTypeID WHERE fs.FuelSupplierID LIKE ? OR fs.FuelTypeID LIKE ?");
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
