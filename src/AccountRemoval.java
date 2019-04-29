import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AccountRemoval {
	JFrame accountscreen;
	
	public AccountRemoval(BankApp mainmenu) {
		accountscreen = new JFrame("Banking App");
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
		JMenuItem exit = new JMenuItem("Exit");
		
		JPanel contentpanel = new JPanel();
		contentpanel.setLayout(new GridLayout(3,1));
		JLabel warning = new JLabel("This page is for the deletion of accounts!");
		warning.setForeground(new Color(255,0,0));
		
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
		JButton accountdeletebutton = new JButton("Delete Account");
		
		depositpanel.add(new Label());
		depositpanel.add(new Label());
		depositpanel.add(accountdeletebutton);
		depositpanel.setVisible(false);
		
		p1.setLayout(new GridLayout(1,3));
		
		menu.add(home);
		menu.add(account);
		menu.add(access);
		menu.add(exit);
		account.add(openitem);
		account.add(deleteaccount);
		access.add(deposititem);
		access.add(withdrawitem);
		access.add(checkbalanceitem);
		
		menubar.add(menu);
		
		accountscreen.setJMenuBar(menubar);
		
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
		
		contentpanel.add(p1);
		contentpanel.add(accountdetails);
		contentpanel.add(depositpanel);
		
		accountscreen.add(warning, BorderLayout.NORTH);
		accountscreen.add(contentpanel);
		accountscreen.setSize(400,400);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openHomePage(accountscreen);
			}
		});
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openAccountPage(accountscreen);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openDepositPage(accountscreen);
			}
		});
		
		withdrawitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openWithdrawPage(accountscreen);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openCheckBalancePage(accountscreen);
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
						gendertext.setText(Deposit.genderString(rs.getString(2)));
						accounttypetext.setText(Deposit.accountString(rs.getString(1)));
						currentbalancevalue.setText("£" + Integer.toString(rs.getInt(4) - rs.getInt(5)));
						accountdetails.setVisible(true);
						depositpanel.setVisible(true);
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
		
		accountdeletebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete account " + accountnumbertext.getText() + "?\nName: " + nametext.getText() + "\nAccount: " + accounttypetext.getText() + "\nCurrent Balance: " + currentbalancevalue.getText());
				if(confirmation == 0) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
						Statement st = con.createStatement();
						st.executeUpdate("delete from bank where accno = '" + accountnumbertext.getText() + "'");
						st.executeUpdate("delete from deposit where accno = '" + accountnumbertext.getText() + "'");
						st.executeUpdate("delete from withdraw where accno = '" + accountnumbertext.getText() + "'");
						accountnumbertext.setText("");
						accountdetails.setVisible(false);
						depositpanel.setVisible(false);
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
