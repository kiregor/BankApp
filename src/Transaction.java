import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class Transaction {
	JFrame transactionscreen;
	
	public Transaction(BankApp mainmenu) {
		transactionscreen = new JFrame("Banking App");
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenu account = new JMenu("Account Tools");
		JMenu access = new JMenu("Acount Access");
		JMenuItem home = new JMenuItem("Home");
		JMenuItem openitem = new JMenuItem("Open Account");
		JMenuItem deleteaccount = new JMenuItem("Delete Account");
		JMenuItem deposititem = new JMenuItem("Deposit");
		JMenuItem withdrawitem = new JMenuItem("Withdraw");
		JMenuItem checkbalanceitem = new JMenuItem("Check Balance");
		JMenuItem transactionitem = new JMenuItem("Transaction");
		JMenuItem exit = new JMenuItem("Exit");
		
		menu.add(home);
		menu.add(account);
		menu.add(access);
		menu.add(exit);
		account.add(openitem);
		account.add(deleteaccount);
		access.add(deposititem);
		access.add(withdrawitem);
		access.add(checkbalanceitem);
		access.add(transactionitem);
		menubar.add(menu);
		transactionscreen.setJMenuBar(menubar);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openHomePage(transactionscreen);
			}
		});
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openAccountPage(transactionscreen);
			}
		});
		
		deleteaccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openAccountDeletionPage(transactionscreen);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openDepositPage(transactionscreen);
			}
		});
		
		withdrawitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openWithdrawPage(transactionscreen);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openCheckBalancePage(transactionscreen);
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JPanel toppanel = new JPanel();
		JLabel accountnumber = new JLabel("Account Number:");
		JTextField accountnumbertext = new JTextField(5);
		JButton accountcheck = new JButton("..."); 
		
		toppanel.add(accountnumber);
		toppanel.add(accountnumbertext);
		toppanel.add(accountcheck);
		
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
		
		JPanel transactionpanel = new JPanel();
		transactionpanel.setBorder(BorderFactory.createTitledBorder("Transaction"));
		
		JPanel amountpanel = new JPanel();
		amountpanel.setBorder(BorderFactory.createTitledBorder("Amount"));
		JTextField amount = new JTextField(5);
		amountpanel.add(amount);
		JPanel recipientpanel = new JPanel();
		recipientpanel.setBorder(BorderFactory.createTitledBorder("Recipient"));
		JTextField recipientaccountnumber = new JTextField(5);
		recipientpanel.add(recipientaccountnumber);
		JButton makepayment = new JButton("Make Payment");
		
		transactionpanel.add(amountpanel);
		transactionpanel.add(recipientpanel);
		transactionpanel.add(makepayment);
		transactionpanel.setVisible(false);
				
		transactionscreen.add(toppanel, BorderLayout.NORTH);
		transactionscreen.add(accountdetails);
		transactionscreen.add(transactionpanel, BorderLayout.SOUTH);
		transactionscreen.setSize(400,400);
		
		
		accountcheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select substring(bank.accno,1,1), substring(bank.accno,2,1), name, (select sum(amount) from deposit where accno = '"+ accountnumbertext.getText() +"'), (select sum(amount) from withdraw where accno = '"+ accountnumbertext.getText() +"') as balance from bank where accno = '"+ accountnumbertext.getText() +"'");
					if(rs.next()) {
						nametext.setText(rs.getString(3));
						gendertext.setText(Deposit.genderString(rs.getString(2)));
						accounttypetext.setText(Deposit.accountString(rs.getString(1)));
						currentbalancevalue.setText("£" + Integer.toString(rs.getInt(4) - rs.getInt(5)));
						accountdetails.setVisible(true);
						transactionpanel.setVisible(true);
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
		
		makepayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select substring(bank.accno,1,1), substring(bank.accno,2,1), name, (select sum(amount) from deposit where accno = '"+ recipientaccountnumber.getText() +"'), (select sum(amount) from withdraw where accno = '"+ accountnumbertext.getText() +"') as balance from bank where accno = '"+ accountnumbertext.getText() +"'");
					if(rs.next()) {
						int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to transfer " + amount.getText() + " to " + rs.getString(3) + " (" + recipientaccountnumber.getText() + ")?");
						if(confirmation == 0) {
							st.executeUpdate("insert into withdraw values('" + accountnumbertext.getText() + "', " + Integer.parseInt(amount.getText()) + ", current_date)");
							st.executeUpdate("insert into deposit values('" + recipientaccountnumber.getText() + "', " + Integer.parseInt(amount.getText()) + ", current_date)");
						}
						accountnumbertext.setText("");
						recipientaccountnumber.setText("");
						amount.setText("");
						accountdetails.setVisible(false);
						transactionpanel.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "The recipient account does not exist!\nThe account number should follow the pattern: 'CM001'");
						recipientaccountnumber.setText("");
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
}
