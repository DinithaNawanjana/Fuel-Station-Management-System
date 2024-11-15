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

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class EmployeeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
    private Connection connection;
    private JTextField searchField;
    private final JPanel panel = new JPanel();
    private JTextField nictextField;
    private JTextField firstnametextField;
    private JTextField lastnametextField;;
    private JTextField emailtextField;
    private JTextField contacttextField;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String NIC_PATTERN = "^[0-9]{9}[vVxX]$"; 
    private static final String CONTACT_PATTERN = "^[0-9]{10}$";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeFrame frame = new EmployeeFrame();
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
	public EmployeeFrame() {
		
		
		connection = DatabaseConnection.getConnection();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1099, 703);
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
        
        JLabel lblNewLabel = new JLabel("Employee Table");
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

        
        JLabel Niclbl = new JLabel("NIC:");
        Niclbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        Niclbl.setBounds(22, 86, 78, 28);
        panel_1.add(Niclbl);
        
        JLabel FirstNamelbl = new JLabel("First Name:");
        FirstNamelbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        FirstNamelbl.setBounds(22, 10, 88, 28);
        panel_1.add(FirstNamelbl);
        
        JLabel LastLbl = new JLabel("Last Name:");
        LastLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        LastLbl.setBounds(22, 48, 88, 28);
        panel_1.add(LastLbl);
        
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        emailLbl.setBounds(22, 124, 78, 28);
        panel_1.add(emailLbl);
        
        nictextField = new JTextField();
        nictextField.setBounds(151, 86, 237, 28);
        panel_1.add(nictextField);
        nictextField.setColumns(10);
        
        firstnametextField = new JTextField();
        firstnametextField.setColumns(10);
        firstnametextField.setBounds(151, 10, 237, 28);
        panel_1.add(firstnametextField);
        
        lastnametextField = new JTextField();
        lastnametextField.setColumns(10);
        lastnametextField.setBounds(151, 48, 237, 28);
        panel_1.add(lastnametextField);
        
        emailtextField = new JTextField();
        emailtextField.setColumns(10);
        emailtextField.setBounds(151, 124, 237, 28);
        panel_1.add(emailtextField);
        
        contacttextField = new JTextField();
        contacttextField.setColumns(10);
        contacttextField.setBounds(151, 162, 237, 28);
        panel_1.add(contacttextField);
        
        JComboBox empTypecomboBox = new JComboBox();
        empTypecomboBox.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        empTypecomboBox.setModel(new DefaultComboBoxModel(new String[] {"Manager", "Attendant", "Cashier"}));
        empTypecomboBox.setBounds(151, 200, 237, 28);
        panel_1.add(empTypecomboBox);
        

        
        
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields when the Insert button is clicked
                String Nic = nictextField.getText();
                String Firstname = firstnametextField.getText();
                String Lastname = lastnametextField.getText();
                String Email = emailtextField.getText();
                String Contact = contacttextField.getText();
                String EmployeeType=(String) empTypecomboBox.getSelectedItem();
                
                String errorMessage = "";

                if (Firstname.isEmpty()) {
                    errorMessage += "First Name is required.\n";
                }

                if (Lastname.isEmpty()) {
                    errorMessage += "Last Name is required.\n";
                }

              

                if (Email.isEmpty()) {
                    errorMessage += "Email is required.\n";
                } else {
                    if (!Pattern.matches(EMAIL_PATTERN, Email)) {
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
                    PreparedStatement checkStatement = connection.prepareStatement("SELECT EmployeeID FROM employees WHERE NIC = ?");
                    checkStatement.setString(1, Nic);
                    ResultSet checkResult = checkStatement.executeQuery();

                    if (checkResult.next()) {
                        // A record with the same NIC already exists, prevent insertion
                        JOptionPane.showMessageDialog(insertButton, "A record with the same Data already exists.");
                    } else {
                        // Insert the new record
                        Statement st = connection.createStatement();
                        String sql = "INSERT INTO employees (FirstName, LastName, NIC, EmployeeType, Email, Contact) VALUES ('" + Firstname + "','" + Lastname + "','" + Nic + "','" + EmployeeType + "','" + Email + "','" + Contact + "')";
                        st.executeUpdate(sql);
                        refreshTable();

                        JOptionPane.showMessageDialog(insertButton, "Data inserted.....");

                        // Reset text fields
                        nictextField.setText("");
                        firstnametextField.setText("");
                        lastnametextField.setText("");
                        emailtextField.setText("");
                        contacttextField.setText("");
                        empTypecomboBox.setSelectedItem("");
                        st.close();
                    }

                    checkStatement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
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
        		String Nic = nictextField.getText();
                String Firstname = firstnametextField.getText();
                String Lastname = lastnametextField.getText();
                String Email = emailtextField.getText();
                String Contact = contacttextField.getText();
                String EmployeeType=(String) empTypecomboBox.getSelectedItem();
                
                String errorMessage = "";

                if (Firstname.isEmpty()) {
                    errorMessage += "First Name is required.\n";
                }

                if (Lastname.isEmpty()) {
                    errorMessage += "Last Name is required.\n";
                }

              

                if (Email.isEmpty()) {
                    errorMessage += "Email is required.\n";
                } else {
                    if (!Pattern.matches(EMAIL_PATTERN, Email)) {
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
        			int empID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql = "UPDATE employees SET FirstName='" + Firstname + "', LastName='" + Lastname + "', NIC='" + Nic + "', EmployeeType='" + EmployeeType + "', Email='" + Email + "', Contact='" + Contact + "' WHERE EmployeeID=" + empID;
					st.executeUpdate(sql);
					
					refreshTable();
					st.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}    
                }
        	}
        });
        updatebutton.setFocusable(false);
        updatebutton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        updatebutton.setBounds(183, 317, 117, 75);
        panel_1.add(updatebutton);
        
        JButton DeleteButton = new JButton("Delete");
        DeleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			int empID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql = "DELETE FROM employees WHERE EmployeeID = " + empID;
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
        DeleteButton.setBounds(378, 316, 117, 75);
        panel_1.add(DeleteButton);
        
        JLabel contactLbl = new JLabel("Contact:");
        contactLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        contactLbl.setHorizontalAlignment(SwingConstants.LEFT);
        contactLbl.setBounds(22, 162, 78, 28);
        panel_1.add(contactLbl);
        
       
        
        JLabel typeLbl = new JLabel("Employee Type:");
        typeLbl.setHorizontalAlignment(SwingConstants.LEFT);
        typeLbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        typeLbl.setBounds(22, 200, 119, 28);
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
            PreparedStatement st3 = connection.prepareStatement("Select * From employees");
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
