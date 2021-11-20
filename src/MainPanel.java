import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Choice;
import javax.swing.JComboBox;

public class MainPanel extends JFrame {
	private JTextField usernametxt;
	private JTextField statustxt;
	private JTextField TSItxt;
	private JTextField TSPtxt;
	public MainPanel() {
		setFont(new Font("Dialog", Font.BOLD, 12));
		setTitle("ClientChat");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shahrori tec\\Desktop\\icon 1.png"));
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel usernamelbl = new JLabel("Username:");
		usernamelbl.setHorizontalAlignment(SwingConstants.LEFT);
		usernamelbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernamelbl.setBounds(20, 25, 100, 20);
		getContentPane().add(usernamelbl);
		
		usernametxt = new JTextField();
		usernamelbl.setLabelFor(usernametxt);
		usernametxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		usernametxt.setBounds(90, 26, 126, 20);
		getContentPane().add(usernametxt);
		usernametxt.setColumns(10);
		
		JButton loginbtn = new JButton("Login");
		loginbtn.setForeground(Color.RED);
		loginbtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		loginbtn.setBounds(239, 10, 88, 35);
		getContentPane().add(loginbtn);
		
		JButton logoutbtn = new JButton("Logout");
		logoutbtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutbtn.setBounds(345, 12, 85, 33);
		getContentPane().add(logoutbtn);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(20, 55, 430, 239);
		getContentPane().add(scrollPane1);
		
		JTextArea textArea1 = new JTextArea();
		scrollPane1.setViewportView(textArea1);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(20, 303, 430, 86);
		getContentPane().add(scrollPane2);
		
		JTextArea textArea2 = new JTextArea();
		scrollPane2.setViewportView(textArea2);
		
		JLabel statuslbl = new JLabel("Status:");
		statuslbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		statuslbl.setBounds(20, 417, 100, 20);
		getContentPane().add(statuslbl);
		
		statustxt = new JTextField();
		statuslbl.setLabelFor(statustxt);
		statustxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		statustxt.setBounds(100, 408, 530, 30);
		getContentPane().add(statustxt);
		statustxt.setColumns(10);
		
		JLabel TSIlbl = new JLabel("TCP Server IP:");
		TSIlbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		TSIlbl.setBounds(460, 10, 115, 20);
		getContentPane().add(TSIlbl);
		
		JLabel TSPlbl = new JLabel("TCP Server Port:");
		TSPlbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		TSPlbl.setBounds(460, 40, 115, 20);
		getContentPane().add(TSPlbl);
		
		TSItxt = new JTextField();
		TSIlbl.setLabelFor(TSItxt);
		TSItxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		TSItxt.setBounds(565, 11, 115, 20);
		getContentPane().add(TSItxt);
		TSItxt.setColumns(10);
		
		TSPtxt = new JTextField();
		TSPlbl.setLabelFor(TSPtxt);
		TSPtxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		TSPtxt.setBounds(565, 40, 115, 20);
		getContentPane().add(TSPtxt);
		TSPtxt.setColumns(10);
		
		JLabel AvInterlbl = new JLabel("Available Interfaces");
		AvInterlbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		AvInterlbl.setBounds(460, 70, 120, 20);
		getContentPane().add(AvInterlbl);
		
		JComboBox comboBoxAvInter = new JComboBox();
		AvInterlbl.setLabelFor(comboBoxAvInter);
		comboBoxAvInter.setFont(new Font("Tahoma", Font.BOLD, 11));
		comboBoxAvInter.setBounds(460, 90, 255, 35);
		getContentPane().add(comboBoxAvInter);
	}
}
