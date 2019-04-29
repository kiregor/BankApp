import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class Deposit {
JFrame depositscreen;
boolean deposit, checkbalance;
	
	public Deposit(BankApp mainmenu) {
		depositscreen = new JFrame("Banking App");
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem home = new JMenuItem("Home");
		JMenuItem openitem = new JMenuItem("Open Account");
		JMenuItem deposititem = new JMenuItem("Deposit");
		JMenuItem withdrawitem = new JMenuItem("Withdraw");
		JMenuItem checkbalanceitem = new JMenuItem("Check Balance");
		
		JLabel accountnumber = new JLabel("Account Number:");
		JTextField accountnumbertext = new JTextField();
		JButton accountcheck = new JButton("...");
		
		JPanel accountdetails = new JPanel();
		accountdetails.setBorder(BorderFactory.createTitledBorder("Account Details"));
		
		JLabel name = new JLabel("Name:");
		JLabel nametext = new JLabel();
		JLabel acounttype = new JLabel("Account Type:");
		JLabel accounttypetext = new JLabel();
		JLabel gender = new JLabel("Gender:");
		JLabel gendertext = new JLabel();
		JLabel currentbalance = new JLabel("Current Balance:");
		JLabel currentbalancevalue = new JLabel();
		
		JPanel p1 = new JPanel();
		JPanel depositpanel = new JPanel();
		JLabel depositammount = new JLabel();
		JTextField depositammounttext = new JTextField(15);
		JButton transaction = new JButton();
		
		depositpanel.add(depositammount);
		depositpanel.add(depositammounttext);
		depositpanel.add(transaction);
		depositpanel.setVisible(false);
		
		p1.setLayout(new GridLayout(1,3));
		
		menu.add(home);
		menu.add(openitem);
		menu.add(deposititem);
		menu.add(withdrawitem);
		menu.add(checkbalanceitem);
		
		menubar.add(menu);
		
		depositscreen.setJMenuBar(menubar);
		depositscreen.setLayout(new GridLayout(3,1));
		
		p1.add(accountnumber);
		p1.add(accountnumbertext);
		p1.add(accountcheck);
		
		accountdetails.setLayout(new GridLayout(4,2));
		accountdetails.add(name);
		accountdetails.add(nametext);
		accountdetails.add(acounttype);
		accountdetails.add(accounttypetext);
		accountdetails.add(gender);
		accountdetails.add(gendertext);
		accountdetails.add(currentbalance);
		accountdetails.add(currentbalancevalue);
		accountdetails.setVisible(false);
		
		depositscreen.add(p1);
		depositscreen.add(accountdetails);
		depositscreen.add(depositpanel);
		
		depositscreen.setSize(400,400);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openHomePage(depositscreen);
			}
		});
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openAccountPage(depositscreen);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openDepositPage(depositscreen);
			}
		});
		
		withdrawitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openWithdrawPage(depositscreen);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openCheckBalancePage(depositscreen);
			}
		});
		
		depositscreen.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				if(!checkbalance) {
					if(deposit) {
						transaction.setText("Deposit");
						depositammount.setText("Deposit Amount");
					}
					else {
						transaction.setText("Withdraw");
						depositammount.setText("Withdrawal Amount");
					}
				}
			}
			
			public void windowDeactivated(WindowEvent e) {
				depositpanel.setVisible(false);
				accountdetails.setVisible(false);
			}
		});
		
		accountcheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select substring(bank.accno,1,1), substring(bank.accno,2,1), name, (select sum(amount) from deposit where accno = '"+ accountnumbertext.getText() +"'), (select sum(amount) from withdraw where accno = '"+ accountnumbertext.getText() +"') as balance from bank where accno = '"+ accountnumbertext.getText() +"'");
					if(rs.next()) {
						nametext.setText(rs.getString(3));
						gendertext.setText(genderString(rs.getString(2)));
						accounttypetext.setText(accountString(rs.getString(1)));
						currentbalancevalue.setText("�" + Integer.toString(rs.getInt(4) - rs.getInt(5)));
						accountdetails.setVisible(true);
						if(!checkbalance) {
						depositpanel.setVisible(true);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "This account does not exist!\nYour account number should follow the pattern: 'CM001'");
						accountnumbertext.setText("");
					}
				} 
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		transaction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
					Statement st = con.createStatement();
					
					if(deposit) {
						int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to deposit " + depositammounttext.getText() + "?");
						if(confirmation == 0) {
							st.executeUpdate("insert into deposit values('"+ accountnumbertext.getText() + "'," + Integer.parseInt(depositammounttext.getText()) + ", current_date)");
						}
					}
					else {
						int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to withdraw " + depositammounttext.getText() + "?");
						if(confirmation == 0) {
							if(Integer.parseInt(depositammounttext.getText())<Integer.parseInt(currentbalancevalue.getText().substring(1))) {
								st.executeUpdate("insert into withdraw values('"+ accountnumbertext.getText() + "'," + Integer.parseInt(depositammounttext.getText()) + ", current_date)");
							}
							else {
								JOptionPane.showMessageDialog(null, "Your account lacks sufficient funds to perform this transaction!");
							}
						}
					}
				} 
				catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please only enter integer values");
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static String genderString(String in) {
		if(in.equals("M")) {
			in = "Male";
		}
		else {
			System.out.println(in);
			in = "Female";
		}
		
		return in;
	}
	
	public static String accountString(String in) {
		if(in.equals("C")) {
			in = "Current";
		}
		else {
			in = "Savings";
		}
		
		return in;
	}
}
