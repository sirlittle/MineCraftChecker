import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UserInterface{
	public static void main(String[] args){
		final User curUser = new User("");
		final JFrame frame = new JFrame();
		frame.setTitle("Login");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel username = new JPanel();
		JLabel text = new JLabel("Username:", SwingConstants.CENTER);
		final JTextField tf = new JTextField("",25);
		username.add(text);
		username.add(tf);
		JPanel pass = new JPanel();
		JLabel moretext = new JLabel("Password:", SwingConstants.CENTER);
		final JPasswordField pw = new JPasswordField(25);
		pass.add(moretext);
		pass.add(pw);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(username);
		container.add(pass);
		JButton go = new JButton("Go");
		container.add(go);
		frame.add(container);
		final JFrame accts = new JFrame();
		accts.setTitle("Accounts");
		accts.setSize(500, 500);
		accts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton checkout = new JButton();
		checkout.setText("Checkout");
		checkout.setSize(45, 45);
		checkout.setAlignmentY(Component.CENTER_ALIGNMENT);
		checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkout.setActionCommand("checkout");

		JButton back = new JButton("Back");
	    back.setForeground(Color.BLACK);
	    back.setFont(new Font("Arial", Font.PLAIN, 40));
	    back.setSize(45, 45);
		back.setAlignmentY(Component.CENTER_ALIGNMENT);
		back.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.setActionCommand("Back");
		
	    
	    JButton logout = new JButton("Logout");
	    logout.setForeground(Color.BLACK);
	    logout.setFont(new Font("Arial", Font.PLAIN, 40));
	    
	    
		JButton revoke = new JButton("Revoke");
		revoke.setSize(45, 45);
		revoke.setAlignmentY(Component.CENTER_ALIGNMENT);
		revoke.setAlignmentX(Component.CENTER_ALIGNMENT);
		revoke.setActionCommand("revoke");
		
		JButton seeactive = new JButton("See Active");
		seeactive.setSize(45,45);
		seeactive.setAlignmentY(Component.CENTER_ALIGNMENT);
		seeactive.setAlignmentX(Component.CENTER_ALIGNMENT);
		seeactive.setActionCommand("seeactive");
		
		JPanel checkpanel = new JPanel();
		checkpanel.setLayout(new BoxLayout(checkpanel, BoxLayout.Y_AXIS));
		checkpanel.add(checkout);
		checkpanel.add(revoke);
		checkpanel.add(seeactive);
		checkpanel.add(back); 
	    checkpanel.add(logout);
		
		checkpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		accts.add(checkpanel, BorderLayout.CENTER);
		class UserStuff implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String password = new String(pw.getPassword());
				String username = tf.getText();
				boolean check = Users.checkPwd(username, password);
				if(check){
					curUser.setName(username);
					frame.setVisible(false);
					accts.setVisible(true);
				}
				else{
					tf.setText("");
					pw.setText("");
					JOptionPane.showMessageDialog(frame, "Nope. Just Nope. Try Again.");

				}
			}
		}
		class Logout implements ActionListener{
            public void actionPerfromed(ActionEvent e){
                accts.setVisible(false);
                frame.setVisible(true);
                curUser.setName("");
                tf.setText("");
                pw.setText("");
                JOptionPane.showMessageDialog(frame, "You Have Been Logged Out");
            	}
            
           }

		class AcctsButtons implements ActionListener{
			public void actionPerformed(ActionEvent e){
				String command = e.getActionCommand();
				switch(command){
				case "revoke":
					break;
				case "checkout":
					System.out.println("Commencing FrameGame");
					FrameGame checoutFrame = new FrameGame(curUser);
					
					break;
				case "seeactive":
					ActiveAcctsFrame acctsFrame = new ActiveAcctsFrame(curUser);
					break;
				}
			}
		}
		
	    
		checkout.addActionListener(new AcctsButtons());
		revoke.addActionListener(new AcctsButtons());
		seeactive.addActionListener(new AcctsButtons());
		go.addActionListener(new UserStuff());
		frame.pack();
		frame.setVisible(true);

	}
}
class User{
	private String name;
	public User(String nam){
		name = nam;
	}
	public void setName(String newName){
		name = newName;
	}
	public String getName(){
		return name;
	}
}
class ActiveAcctsFrame extends JFrame{
	public ActiveAcctsFrame(User curUser){
		super();
		setTitle("Your Active Accounts");
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton back = new JButton("Back");
		back.setForeground(Color.BLACK);
		JButton logout = new JButton("Logout");
		logout.setForeground(Color.BLACK);
		/*back.setOpaque(false);
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);*/
		back.setFont(new Font("Arial", Font.PLAIN, 40));
		logout.setFont(new Font("Arial", Font.PLAIN, 40));
		//back.setMaximumSize(new Dimension(50,25));

		/*logout.setOpaque(false);
		logout.setContentAreaFilled(false);
		logout.setBorderPainted(false);*/
		
		JPanel pContainer = new JPanel(new GridLayout(1,2));
		pContainer.setMaximumSize(new Dimension(300,20));
		pContainer.add(back); pContainer.add(logout);
		add(pContainer);
		JPanel whitespace = new JPanel();
		whitespace.setMaximumSize(new Dimension(300,20));
		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File("/Users/vanvari/Minecraft/mine.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//bgImage = bgImage.getScaledInstance(700, 700, Image.SCALE_AREA_AVERAGING);
		class Back implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
			}
		}
		BackgroundPanel container = new BackgroundPanel(bgImage);
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		container.add(new TextRect(Accounts.getUserAccounts(curUser.getName())));
		container.add(pContainer);
		container.add(whitespace);
		this.add(container);
		
		setVisible(true);
	}
}
class TextRect extends JPanel {
	Rectangle[] rects;
	ArrayList<String> allAccts;
	
	public TextRect(ArrayList<String> allAccts){
		super();
		this.allAccts = allAccts;
		allAccts.add(0, "Password");
		allAccts.add(0, "Login:");
		rects = new Rectangle[allAccts.size()];
		int y = 112;
		for(int i = 0; i < rects.length; i++){
			if(i%2 == 0){
				rects[i] = new Rectangle(75,y,350,25);
			}
			else{
				rects[i] = new Rectangle(425,y,200,25);
				y+=25;
			}
		}
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontRenderContext frc = g2.getFontRenderContext();
		Font font = g2.getFont().deriveFont(16f);
		g2.setFont(font);
		for(int i = 0; i < allAccts.size(); i+=2) {
			String s = allAccts.get(i);
			float sw = (float)font.getStringBounds(s, frc).getWidth();
			LineMetrics lm = font.getLineMetrics(s, frc);
			float sh = lm.getAscent() + lm.getDescent();
			Rectangle r = rects[i];
			g2.setPaint(Color.pink);
			g2.fill(r);
			g2.setPaint(Color.black);
			g2.draw(r);
			float sx = r.x + (r.width - sw)/2;
			float sy = r.y + (r.height + sh)/2 - lm.getDescent();
			g2.drawString(s, sx, sy);
			s = allAccts.get(i+1);
			sw = (float)font.getStringBounds(s, frc).getWidth();
			lm = font.getLineMetrics(s, frc);
			sh = lm.getAscent() + lm.getDescent();
			r = rects[i +1];
			g2.setPaint(Color.pink);
			g2.fill(r);
			g2.setPaint(Color.black);
			g2.draw(r);
			sx = r.x + (r.width - sw)/2;
			sy = r.y + (r.height + sh)/2 - lm.getDescent();
			g2.drawString(s, sx, sy);

		}
	}
}
