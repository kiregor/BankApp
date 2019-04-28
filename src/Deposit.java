import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Deposit {
JFrame depositscreen;
boolean deposit;
	
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
		
		JLabel accounttype = new JLabel("Account Type");
		JLabel gender = new JLabel("Gender");
		JRadioButton current = new JRadioButton("Current");
		JRadioButton saving = new JRadioButton("Current");
		JRadioButton male = new JRadioButton("Current");
		JRadioButton female = new JRadioButton("Current");
		
		JButton create = new JButton("Create Account");
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		p1.setLayout(new GridLayout(1,3));
		p2.setLayout(new GridLayout(1,2));
		p3.setLayout(new GridLayout(3,1));
		p4.setLayout(new GridLayout(3,1));
		
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
		
		p2.add(p3);
		p2.add(p4);
		p3.add(accounttype);
		p3.add(current);
		p3.add(saving);
		p4.add(gender);
		p4.add(male);
		p4.add(female);
		
		depositscreen.add(p1);
		depositscreen.add(p2);
		depositscreen.add(create);
		
		depositscreen.setSize(400,400);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.mainscreen.setLocation(depositscreen.getLocation());
				mainmenu.mainscreen.setVisible(true);
				depositscreen.setVisible(false);
			}
		});
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.ac.accountscreen.setLocation(depositscreen.getLocation());
				mainmenu.ac.accountscreen.setVisible(true);
				depositscreen.setVisible(false);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.cb.balancescreen.setLocation(depositscreen.getLocation());
				mainmenu.cb.balancescreen.setVisible(true);
				depositscreen.setVisible(false);
			}
		});
	}
}
