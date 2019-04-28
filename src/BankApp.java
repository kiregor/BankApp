import javax.swing.*;
import java.awt.event.*;

public class BankApp {
	
	JFrame mainscreen;
	AccountCreation ac;
	Deposit d;
	CheckBalance cb;
	
	public BankApp() {
		mainscreen = new JFrame("Banking App");
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem home = new JMenuItem("Home");
		JMenuItem openitem = new JMenuItem("Open Account");
		JMenuItem deposititem = new JMenuItem("Deposit");
		JMenuItem withdrawitem = new JMenuItem("Withdraw");
		JMenuItem checkbalanceitem = new JMenuItem("Check Balance");
		
		ac = new AccountCreation(this);
		d = new Deposit(this);
		cb = new CheckBalance(this);
		
		menu.add(home);
		menu.add(openitem);
		menu.add(deposititem);
		menu.add(withdrawitem);
		menu.add(checkbalanceitem);
		
		menubar.add(menu);
		
		mainscreen.setJMenuBar(menubar);
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac.accountscreen.setLocation(mainscreen.getLocation());
				ac.accountscreen.setVisible(true);
				mainscreen.setVisible(false);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d.depositscreen.setLocation(mainscreen.getLocation());
				d.depositscreen.setVisible(true);
				d.deposit=true;
				mainscreen.setVisible(false);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb.balancescreen.setLocation(mainscreen.getLocation());
				cb.balancescreen.setVisible(true);
				mainscreen.setVisible(false);
			}
		});
		
		mainscreen.setSize(400,400);
		mainscreen.setVisible(true);
	}
	
	public static void main(String[] args) {
		new BankApp();
	}

}