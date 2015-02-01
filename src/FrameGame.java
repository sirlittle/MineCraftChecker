import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class FrameGame extends JFrame {

	private static final long serialVersionUID = 1L;


	public FrameGame(User curUser)
	{
		final JFrame frame = new JFrame();
		frame.setTitle("Checkout");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel topBottom = new JPanel();
		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); 
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		topBottom.setLayout(new BoxLayout(topBottom, BoxLayout.X_AXIS));

		JPanel numAccounts = new JPanel();
		JLabel num = new JLabel("Number of Accounts",SwingConstants.CENTER);
		final JTextField txtF = new JTextField("",4);
		JLabel num2 = new JLabel("out of: " + Accounts.howManyFree() + " currently available" ,SwingConstants.CENTER);
		numAccounts.add(num);
		numAccounts.add(txtF);
		numAccounts.add(num2);
		topBottom.add(numAccounts);
		container.add(topBottom);
		class Go implements ActionListener{
			User currUser;
			public Go(User person){
				currUser = person;
			}
			public void actionPerformed(ActionEvent e){
				String num = txtF.getText();
				num = num.replaceAll("[^\\d.]", "");
				int requested = Integer.parseInt(num);
				if(Accounts.howManyFree() < requested){
					return;
				}
				Accounts.checkout(requested, currUser.getName());
				ArrayList<String> stuff = Accounts.getUserAccounts(currUser.getName());
				Writer w = null;

				try {
					File desktop = new File(System.getProperty("user.home") + "/Desktop/accts.txt");
					FileOutputStream is = new FileOutputStream(desktop);
					OutputStreamWriter osw = new OutputStreamWriter(is);
					w = new BufferedWriter(osw);
					for(int i = 0; i < stuff.size(); i += 2){
						w.write(stuff.get(i) + "  " + stuff.get(i+1) + "\n");
					}
					w.close();
				} catch (IOException ex) {
					// report
				} finally {
				   try {w.close();} catch (Exception ex) {}
				}

			}
		}
		JButton go = new JButton("Go");
		go.addActionListener(new Go(curUser));
		JPanel goContainer = new JPanel(new GridLayout(1,1));
		goContainer.add(go);
		container.add(goContainer);

		JButton back = new JButton("Back");
		JButton logout = new JButton("Logout");

		JPanel pContainer = new JPanel(new GridLayout(1,2));
		pContainer.add(back); pContainer.add(logout);

		container.add(pContainer);

		frame.add(container);

		frame.pack();
		frame.setVisible(true);
	}
}

