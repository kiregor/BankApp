import javax.swing.*;
import java.awt.event.*;

public class BankApp {
	
	JFrame mainscreen;
	AccountCreation ac;
	Deposit d;
	
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
		
		menu.add(home);
		menu.add(openitem);
		menu.add(deposititem);
		menu.add(withdrawitem);
		menu.add(checkbalanceitem);
		
		menubar.add(menu);
		
		mainscreen.setJMenuBar(menubar);
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAccountPage(mainscreen);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDepositPage(mainscreen);
			}
		});
		
		withdrawitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWithdrawPage(mainscreen);
			}
		});
		
		checkbalanceitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCheckBalancePage(mainscreen);
			}
		});
		
		mainscreen.setSize(400,400);
		mainscreen.setVisible(true);
	}
	
	public static void main(String[] args) {
		new BankApp();
	}
	
	public void openHomePage(JFrame currentwindow) {
		mainscreen.setLocation(currentwindow.getLocation());
		mainscreen.setVisible(true);
		currentwindow.setVisible(false);
	}
	
	public void openAccountPage(JFrame currentwindow) {
		ac.accountscreen.setLocation(currentwindow.getLocation());
		ac.accountscreen.setVisible(true);
		currentwindow.setVisible(false);
	}
	
	public void openDepositPage(JFrame currentwindow) {
		d.depositscreen.setLocation(currentwindow.getLocation());
		d.deposit=true;
		d.checkbalance=false;
		currentwindow.setVisible(false);
		d.depositscreen.setVisible(true);
	}
	
	public void openWithdrawPage(JFrame currentwindow) {
		d.depositscreen.setLocation(currentwindow.getLocation());
		d.deposit=false;
		d.checkbalance=false;
		currentwindow.setVisible(false);
		d.depositscreen.setVisible(true);
	}
	
	public void openCheckBalancePage(JFrame currentwindow) {
		d.depositscreen.setLocation(currentwindow.getLocation());
		d.checkbalance=true;
		d.deposit=false;
		currentwindow.setVisible(false);
		d.depositscreen.setVisible(true);
	}

}