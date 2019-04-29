import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class AccountCreation {
	JFrame accountscreen;
	
	public AccountCreation(BankApp mainmenu) {
		accountscreen = new JFrame("Banking App");
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem home = new JMenuItem("Home");
		JMenuItem openitem = new JMenuItem("Open Account");
		JMenuItem deposititem = new JMenuItem("Deposit");
		JMenuItem withdrawitem = new JMenuItem("Withdraw");
		JMenuItem checkbalanceitem = new JMenuItem("Check Balance");
		
		JLabel name = new JLabel("Name:");
		JLabel address = new JLabel("Address:");
		JTextField nametext = new JTextField();
		JTextField addresstext = new JTextField();
		
		JRadioButton current = new JRadioButton("Current");
		JRadioButton saving = new JRadioButton("Savings");
		JRadioButton male = new JRadioButton("Male");
		JRadioButton female = new JRadioButton("Female");
		JRadioButton nonbinary = new JRadioButton("Non-binary");
		
		JButton create = new JButton("Create Account");
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		
		p1.setLayout(new GridLayout(2,2));
		p2.setLayout(new GridLayout(1,2));
		p3.setLayout(new GridLayout(2,1));
		p4.setLayout(new GridLayout(3,1));
		
		menu.add(home);
		menu.add(openitem);
		menu.add(deposititem);
		menu.add(withdrawitem);
		menu.add(checkbalanceitem);
		
		menubar.add(menu);
		
		accountscreen.setJMenuBar(menubar);
		accountscreen.setLayout(new GridLayout(3,1));
		
		p1.add(name);
		p1.add(nametext);
		p1.add(address);
		p1.add(addresstext);
		
		p2.add(p3);
		p2.add(p4);
		p3.add(current);
		p3.add(saving);
		p4.add(male);
		p4.add(female);
		p4.add(nonbinary);
		
		p3.setBorder(BorderFactory.createTitledBorder("Acount Type"));
		p4.setBorder(BorderFactory.createTitledBorder("Gender"));
		
		accountscreen.add(p1);
		accountscreen.add(p2);
		accountscreen.add(create);
		
		accountscreen.setSize(400,400);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.openHomePage(accountscreen);
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
		
		current.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saving.setSelected(false);
			}
		});
		
		saving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current.setSelected(false);
			}
		});
		
		male.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				female.setSelected(false);
				nonbinary.setSelected(false);
			}
		});
		
		female.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				male.setSelected(false);
				nonbinary.setSelected(false);
			}
		});
		
		nonbinary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				male.setSelected(false);
				female.setSelected(false);
			}
		});
		
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String accounttypechar = "", genderchar = "";
				
				if(current.isSelected()) {
					accounttypechar = "C";
				}
				else if(saving.isSelected()) {
					accounttypechar = "S";
				}
				else {
					JOptionPane.showInputDialog("Please Enter Your Account Type");
				}
				
				if(male.isSelected()) {
					genderchar = "M";
				}
				else if(female.isSelected()) {
					genderchar = "F";
				}
				else if(nonbinary.isSelected()) {
					genderchar = "X";
				}
				else {
					JOptionPane.showInputDialog("Please Enter Your Gender");
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select LPAD(ifnull(max(substring(accno,3,5))+1,'001'),3,'0') from (select * from bank where accno like '" + accounttypechar + "%') as t1");
					rs.next();
					int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you wish to create the account " + accounttypechar + genderchar + rs.getString(1) + "?");
					if(confirmation == 0) {
						st.executeUpdate("insert into bank values ('" + accounttypechar + genderchar + rs.getString(1) + "','" + nametext.getText() + "','"  + addresstext.getText() + "')");
						nametext.setText("");
						addresstext.setText("");
						current.setSelected(false);
						saving.setSelected(false);
						male.setSelected(false);
						female.setSelected(false);
						nonbinary.setSelected(false);
					}
				}
				catch(Exception x) {
					System.out.println(e.toString());
				}
			}
		});
	}
}
