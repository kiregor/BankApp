import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		p1.setLayout(new GridLayout(2,2));
		p2.setLayout(new GridLayout(1,2));
		p3.setLayout(new GridLayout(3,1));
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
		p3.add(accounttype);
		p3.add(current);
		p3.add(saving);
		p4.add(gender);
		p4.add(male);
		p4.add(female);
		
		accountscreen.add(p1);
		accountscreen.add(p2);
		accountscreen.add(create);
		
		accountscreen.setSize(400,400);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.mainscreen.setLocation(accountscreen.getLocation());
				mainmenu.mainscreen.setVisible(true);
				accountscreen.setVisible(false);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.d.depositscreen.setLocation(accountscreen.getLocation());
				mainmenu.d.depositscreen.setVisible(true);
				mainmenu.d.deposit=true;
				accountscreen.setVisible(false);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.cb.balancescreen.setLocation(accountscreen.getLocation());
				mainmenu.cb.balancescreen.setVisible(true);
				accountscreen.setVisible(false);
			}
		});
	}
}
