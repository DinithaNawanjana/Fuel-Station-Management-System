import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AdminFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection connection;
    private JTextField searchField;
    private final JPanel panel = new JPanel();
    private JTextField nictextField;
    private JTextField firstnametextField;
    private JTextField lastnametextField;
    private JTextField usernametextField;
    private JTextField passwordtextField;
    private JTextField emailtextField;
    private JTextField contacttextField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminFrame frame = new AdminFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminFrame() {
        connection = DatabaseConnection.getConnection();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1099, 703);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBackground(Color.LIGHT_GRAY);
        scrollPane.setBounds(568, 209, 478, 402);
        contentPane.add(scrollPane);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        refreshButton.setFocusable(false);
        refreshButton.setBounds(923, 146, 123, 53);
        contentPane.add(refreshButton);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        searchButton.setFocusable(false);
        searchButton.setBounds(791, 146, 114, 53);
        contentPane.add(searchButton);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        panel.setBackground(new Color(2, 115, 81));
        panel.setBounds(0, 0, 1085, 136);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Admin Table");
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
        
       
        
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get data from text fields when the Insert button is clicked
                String Nic = nictextField.getText();
                String Username = usernametextField.getText();
                String Firstname = firstnametextField.getText();
                String Lastname = lastnametextField.getText();
                String PW = passwordtextField.getText();
                String Email = emailtextField.getText();
                String Contact = contacttextField.getText();

                try {
                    // Check if a record with the same NIC already exists
                    PreparedStatement checkStatement = connection.prepareStatement("SELECT AdminID FROM admins WHERE NIC = ?");
                    checkStatement.setString(1, Nic);
                    ResultSet checkResult = checkStatement.executeQuery();

                    if (checkResult.next()) {
                        // A record with the same NIC already exists, prevent insertion
                        JOptionPane.showMessageDialog(insertButton, "A record with the same Data already exists.");
                    } else {
                        // Insert the new record
                        Statement st = connection.createStatement();
                        String sql = "INSERT INTO admins ( FirstName, LastName,NIC, Username, Password, Email, Contact) VALUES('" + Firstname + "','" + Lastname + "','" + Nic + "','" + Username + "','" + PW + "','" + Email + "','" + Contact + "')";
                        st.executeUpdate(sql);

                        JOptionPane.showMessageDialog(insertButton, "Data inserted.....");

                        // Reset text fields
                        nictextField.setText("");
                        usernametextField.setText("");
                        firstnametextField.setText("");
                        lastnametextField.setText("");
                        passwordtextField.setText("");
                        emailtextField.setText("");
                        contacttextField.setText("");

                        refreshTable();
                    }

                    checkStatement.close();
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
        		String Nic = nictextField.getText();
                String Username = usernametextField.getText();
                String Firstname = firstnametextField.getText();
                String Lastname = lastnametextField.getText();
                String PW = passwordtextField.getText();
                String Email = emailtextField.getText();
                String Contact = contacttextField.getText();
        		try {
        			int adminID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql="Update admins Set FirstName='"+Firstname+"',LastName='"+Lastname+"',NIC='"+Nic+"',Username='"+Username+"',Password='"+PW+"',Email='"+Email+"',Contact='"+Contact+"' Where AdminID="+adminID;
					st.executeUpdate(sql);
					refreshTable();
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
        			int adminID=Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
					Statement st=connection.createStatement();
					String sql="Delete from admins Where AdminID="+adminID;
					st.executeUpdate(sql);
					refreshTable();
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        	
        	}
        });
        DeleteButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        DeleteButton.setFocusable(false);
        DeleteButton.setBounds(378, 316, 117, 75);
        panel_1.add(DeleteButton);
        
        JLabel Niclbl = new JLabel("NIC:");
        Niclbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        Niclbl.setBounds(22, 86, 78, 28);
        panel_1.add(Niclbl);
        
        JLabel FirstNamelbl = new JLabel("First Name:");
        FirstNamelbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        FirstNamelbl.setBounds(22, 10, 88, 28);
        panel_1.add(FirstNamelbl);
        
        JLabel lblNewLabel_1_2 = new JLabel("Last Name:");
        lblNewLabel_1_2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel_1_2.setBounds(22, 48, 88, 28);
        panel_1.add(lblNewLabel_1_2);
        
        JLabel Usernamelbl = new JLabel("Username:");
        Usernamelbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        Usernamelbl.setBounds(22, 123, 78, 28);
        panel_1.add(Usernamelbl);
        
        JLabel Password = new JLabel("Password:");
        Password.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        Password.setBounds(22, 161, 78, 28);
        panel_1.add(Password);
        
        JLabel lblNewLabel_1_5 = new JLabel("Email:");
        lblNewLabel_1_5.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel_1_5.setBounds(22, 199, 78, 28);
        panel_1.add(lblNewLabel_1_5);
        
        nictextField = new JTextField();
        nictextField.setBounds(110, 85, 237, 28);
        panel_1.add(nictextField);
        nictextField.setColumns(10);
        
        firstnametextField = new JTextField();
        firstnametextField.setColumns(10);
        firstnametextField.setBounds(110, 10, 237, 28);
        panel_1.add(firstnametextField);
        
        lastnametextField = new JTextField();
        lastnametextField.setColumns(10);
        lastnametextField.setBounds(110, 48, 237, 28);
        panel_1.add(lastnametextField);
        
        usernametextField = new JTextField();
        usernametextField.setColumns(10);
        usernametextField.setBounds(110, 124, 237, 28);
        panel_1.add(usernametextField);
        
        passwordtextField = new JTextField();
        passwordtextField.setColumns(10);
        passwordtextField.setBounds(110, 162, 237, 28);
        panel_1.add(passwordtextField);
        
        emailtextField = new JTextField();
        emailtextField.setColumns(10);
        emailtextField.setBounds(110, 200, 237, 28);
        panel_1.add(emailtextField);
        
        contacttextField = new JTextField();
        contacttextField.setColumns(10);
        contacttextField.setBounds(110, 238, 237, 28);
        panel_1.add(contacttextField);
        
        
        
        
        JLabel lblNewLabel_1_5_1 = new JLabel("Contact:");
        lblNewLabel_1_5_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lblNewLabel_1_5_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_5_1.setBounds(22, 237, 78, 28);
        panel_1.add(lblNewLabel_1_5_1);
        
                searchField = new JTextField();
                searchField.setBounds(581, 158, 200, 30);
                contentPane.add(searchField);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 refreshTable();
             
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String searchText = searchField.getText();

                try {
                    PreparedStatement st3 = connection.prepareStatement("SELECT * FROM admins WHERE AdminID LIKE ? or FirstName LIKE ? or LastName LIKE ? or NIC LIKE ? ");
                    for (int i = 1; i <= 4; i++) {
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
            PreparedStatement st2 = connection.prepareStatement("SELECT * FROM admins");
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
        } catch (SQLException q) {
            q.printStackTrace();
        }
    }

}
  