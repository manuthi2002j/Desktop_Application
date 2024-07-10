import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.Window.Type;


public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Home Page");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView frame = new HomeView();
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
	public HomeView() {
		//setBounds(new Rectangle(500, 500, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 1116, 707);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(213, 233, 136));
		contentPane.setBackground(new Color(213, 233, 136));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon Ad=new ImageIcon("admin ().png");
		Image ADscale=Ad.getImage().getScaledInstance(50, 50, DO_NOTHING_ON_CLOSE);
		Ad=new ImageIcon(ADscale);
		
		ImageIcon Ad1=new ImageIcon("customer.png");
		Image ADscale1=Ad1.getImage().getScaledInstance(50, 50, DO_NOTHING_ON_CLOSE);
		Ad1=new ImageIcon(ADscale1);
		
		JButton ADMINbtn = new JButton("Admin");
		ADMINbtn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		ADMINbtn.setFocusable(false);
		ADMINbtn.setBorderPainted(true);
		ADMINbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminFrame af=new AdminFrame();
				af.setVisible(true);
			}
		});
		ADMINbtn.setForeground(new Color(255, 255, 255));
		ADMINbtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		ADMINbtn.setBackground(new Color(47, 152, 153));
		ADMINbtn.setBounds(23, 172, 178, 153);
		contentPane.add(ADMINbtn);
		
		JButton Employeebtn = new JButton("Employee");
		Employeebtn.setForeground(new Color(255, 255, 255));
		Employeebtn.setFocusable(false);
		Employeebtn.setBackground(new Color(47, 152, 153));
		Employeebtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		Employeebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeFrame ef=new EmployeeFrame();
				ef.setVisible(true);
			}
		});
		Employeebtn.setBounds(453, 171, 178, 153);
		contentPane.add(Employeebtn);
		
		JButton Cusbtn = new JButton("Customer");
		Cusbtn.setFocusable(false);
		Cusbtn.setForeground(new Color(255, 255, 255));
		Cusbtn.setBackground(new Color(47, 152, 153));
		Cusbtn.setIcon(null);
		Cusbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerFrame cf=new CustomerFrame();
				cf.setVisible(true);
			}
		});
		Cusbtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		Cusbtn.setBounds(225, 172, 178, 153);
		contentPane.add(Cusbtn);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(2, 115, 81));
		panel.setBounds(0, 0, 1102, 148);
		contentPane.add(panel);
		panel.setLayout(null);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 56));
		lblNewLabel.setBounds(0, 0, 1101, 148);
		panel.add(lblNewLabel);
		
		JButton Supplierbtn = new JButton("Supplier");
		Supplierbtn.setForeground(new Color(255, 255, 255));
		Supplierbtn.setFocusable(false);
		Supplierbtn.setBackground(new Color(47, 152, 153));
		Supplierbtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		Supplierbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupplierFrame sf=new SupplierFrame();
				sf.setVisible(true);
			}
		});
		Supplierbtn.setBounds(684, 171, 178, 153);
		contentPane.add(Supplierbtn);
		
		JButton btnNewButton_1_3 = new JButton("Fuel Types");
		btnNewButton_1_3.setForeground(new Color(255, 255, 255));
		btnNewButton_1_3.setFocusable(false);
		btnNewButton_1_3.setBackground(new Color(47, 152, 153));
		btnNewButton_1_3.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FuelTypeFrame FTF=new FuelTypeFrame();
				FTF.setVisible(true);
			}
		});
		btnNewButton_1_3.setBounds(906, 171, 178, 153);
		contentPane.add(btnNewButton_1_3);
		
		JButton btnNewButton_1_4 = new JButton("Inventory");
		btnNewButton_1_4.setForeground(new Color(255, 255, 255));
		btnNewButton_1_4.setFocusable(false);
		btnNewButton_1_4.setBackground(new Color(47, 152, 153));
		btnNewButton_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InventoryFrame IF=new InventoryFrame();
				IF.setVisible(true);
			}
		});
		btnNewButton_1_4.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		btnNewButton_1_4.setBounds(325, 380, 210, 153);
		contentPane.add(btnNewButton_1_4);
		
		JButton btnNewButton_1_5 = new JButton("Customer Transaction");
		btnNewButton_1_5.setForeground(Color.WHITE);
		btnNewButton_1_5.setFocusable(false);
		btnNewButton_1_5.setBackground(new Color(47, 152, 153));
		btnNewButton_1_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TransactionFrame TF=new TransactionFrame();
				TF.setVisible(true);
			}
		});
		btnNewButton_1_5.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		btnNewButton_1_5.setBounds(818, 380, 210, 153);
		contentPane.add(btnNewButton_1_5);
		
		JButton supplierfuelbtn = new JButton("Fuel Supply");
		supplierfuelbtn.setForeground(new Color(255, 255, 255));
		supplierfuelbtn.setFocusable(false);
		supplierfuelbtn.setBackground(new Color(47, 152, 153));
		supplierfuelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FuelSupply FS=new FuelSupply();
				FS.setVisible(true);
			}
		});
		supplierfuelbtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		supplierfuelbtn.setBounds(69, 380, 210, 153);
		contentPane.add(supplierfuelbtn);
		
		JButton Transaction2 = new JButton("Supplier Transaction");
		Transaction2.setForeground(new Color(255, 255, 255));
		Transaction2.setFocusable(false);
		Transaction2.setBackground(new Color(47, 152, 153));
		Transaction2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeeTransaction ET=new employeeTransaction();
				ET.setVisible(true);
			}
		});
		Transaction2.setFont(new Font("Tahoma", Font.BOLD, 15));
		Transaction2.setBounds(560, 380, 210, 153);
		contentPane.add(Transaction2);
	}
}
