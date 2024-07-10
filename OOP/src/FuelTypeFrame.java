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

public class FuelTypeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
    private Connection connection;
    private JTextField searchField;
    private final JPanel panel = new JPanel();
    private JTextField fuelnametextField;
    private JTextField pricetextField;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FuelTypeFrame frame = new FuelTypeFrame();
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
	public FuelTypeFrame() {
		connection = DatabaseConnection.getConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1099, 703);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 255, 255));
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
	        
	        JLabel lblNewLabel = new JLabel("Fuel Types Table");
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
	        
	        JLabel Fuelnamelbl = new JLabel("Fuel Names:");
	        Fuelnamelbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
	        Fuelnamelbl.setBounds(22, 10, 105, 28);
	        panel_1.add(Fuelnamelbl);
	        
	        JLabel PriceLbl = new JLabel("Price Per Liter:");
	        PriceLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
	        PriceLbl.setBounds(22, 48, 117, 28);
	        panel_1.add(PriceLbl);
	        
	        fuelnametextField = new JTextField();
	        fuelnametextField.setColumns(10);
	        fuelnametextField.setBounds(196, 10, 237, 28);
	        panel_1.add(fuelnametextField);
	        
	        pricetextField = new JTextField();
	        pricetextField.setColumns(10);
	        pricetextField.setBounds(196, 48, 237, 28);
	        panel_1.add(pricetextField);
	        
	        JButton insertButton = new JButton("Insert");
	        insertButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Get data from text fields when the Insert button is clicked
	                String Fuelname = fuelnametextField.getText();
	                String Price = pricetextField.getText();
	                

	                try {
	                    // Create a prepared statement with placeholders for the values
	                    PreparedStatement pst = connection.prepareStatement("INSERT INTO fueltypes (FuelName, PricePerLiter) VALUES (?, ?)");

	                    // Set the values for the placeholders
	                    pst.setString(1, Fuelname);
	                    pst.setString(2, Price);

	                    // Execute the INSERT statement
	                    pst.executeUpdate();
	                    pst.close();
	                    fuelnametextField.setText("");
		                pricetextField.setText("");


	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }

	            }
	        });
	        insertButton.setFocusable(false);
	        insertButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
	        insertButton.setBounds(10, 317, 117, 75);
	        panel_1.add(insertButton);
	        
	       
	        
	        JButton updatebutton = new JButton("Update");
	        updatebutton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	                String Fuelname = fuelnametextField.getText();
	                String Price = pricetextField.getText();
	                
	               
	        		try {
	        			int fueltypeID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
						Statement st=connection.createStatement();
						String sql = "UPDATE fueltypes SET FuelName='"+Fuelname+"', PricePerLiter='"+Price+"' Where FuelTypeID="+fueltypeID;
						st.executeUpdate(sql);
						
						refreshTable();
						st.close();
						
						fuelnametextField.setText("");
		                pricetextField.setText("");
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}    
	        		
	        	}
	        });
	        updatebutton.setFocusable(false);
	        updatebutton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
	        updatebutton.setBounds(196, 317, 117, 75);
	        panel_1.add(updatebutton);
	        
	        JButton DeleteButton = new JButton("Delete");
	        DeleteButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		try {
	        			int fueltypeID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
						Statement st=connection.createStatement();
						String sql = "DELETE FROM fueltypes WHERE FuelTypeID = " + fueltypeID;
						st.executeUpdate(sql);
						
						refreshTable();
						st.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}        	
	        	}
	        });
	        DeleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
	        DeleteButton.setFocusable(false);
	        DeleteButton.setBounds(378, 317, 117, 75);
	        panel_1.add(DeleteButton);
	        
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
		                    PreparedStatement st3 = connection.prepareStatement("SELECT * FROM employees WHERE EmployeeID LIKE ? or FirstName LIKE ? ");
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
		                } catch (Exception q) {
		                    q.printStackTrace();
		                }
		            }
		            
		        });
		}
		private void refreshTable() {
			tableModel.setRowCount(0);
	        tableModel.setColumnCount(0);

	        try {
	            PreparedStatement st3 = connection.prepareStatement("Select * From fueltypes");
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
