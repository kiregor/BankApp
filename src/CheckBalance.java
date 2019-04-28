import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckBalance {
	JFrame balancescreen;
	
	public CheckBalance(BankApp mainmenu) {
		balancescreen = new JFrame();
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem home = new JMenuItem("Home");
		JMenuItem openitem = new JMenuItem("Open Account");
		JMenuItem deposititem = new JMenuItem("Deposit");
		JMenuItem withdrawitem = new JMenuItem("Withdraw");
		JMenuItem checkbalanceitem = new JMenuItem("Check Balance");
		
		menu.add(home);
		menu.add(openitem);
		menu.add(deposititem);
		menu.add(withdrawitem);
		menu.add(checkbalanceitem);
		
		menubar.add(menu);
		
		balancescreen.setJMenuBar(menubar);
		
		balancescreen.setLayout(new GridLayout(3,1));
		
		balancescreen.setSize(400,400);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.mainscreen.setLocation(balancescreen.getLocation());
				mainmenu.mainscreen.setVisible(true);
				balancescreen.setVisible(false);
			}
		});
		
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.ac.accountscreen.setLocation(balancescreen.getLocation());
				mainmenu.ac.accountscreen.setVisible(true);
				balancescreen.setVisible(false);
			}
		});
		
		deposititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainmenu.d.depositscreen.setLocation(balancescreen.getLocation());
				mainmenu.d.depositscreen.setVisible(true);
				mainmenu.d.deposit=true;
				balancescreen.setVisible(false);
			}
		});
	}

}
