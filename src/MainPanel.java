import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sun.nio.sctp.SendFailedNotification;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JFrame {

	private String usernameLoggedIn;
	private List<String> Onusers = new ArrayList<String>();
	private String sendingTo;
	private JTextField usernametxt;
	private JTextField statustxt;
	private JTextField TSItxt;
	private JTextField TSPtxt;
	private JTextField LPtxt;
	private JTextArea chattxt;
	private JList list;

	public MainPanel() {
		setResizable(false);
		setPreferredSize(new Dimension(910, 500));
		pack();
		setLocationRelativeTo(null);
		// setVisible(true);

		setFont(new Font("Dialog", Font.BOLD, 12));
		setTitle("ClientChat");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shahrori tec\\Desktop\\icon 1.png"));
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
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sentence;
				String serverAnswer;
				try {
					Socket clientSocket = new Socket(InetAddress.getByName(TSItxt.getText()),
							Integer.parseInt(TSPtxt.getText()));
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					BufferedReader inFromServer = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));

					sentence = usernameLoggedIn;
					outToServer.writeBytes(sentence + "\n");

					serverAnswer = inFromServer.readLine();
					String[] tmp = serverAnswer.split(serverAnswer, '|');

					if (Onusers.size() > 0)
						for (Iterator<String> iterator = Onusers.iterator(); iterator.hasNext();) {
							String value = iterator.next();
							iterator.remove();
						}
					for (String t : tmp) {
						Onusers.add(t);
					}
					reprintOnlineUsers();
					clientSocket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		loginbtn.setForeground(Color.RED);
		loginbtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		loginbtn.setBounds(239, 10, 88, 35);
		getContentPane().add(loginbtn);

		JButton logoutbtn = new JButton("Logout");
		logoutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sentence;
				try {
					Socket clientSocket = new Socket(InetAddress.getByName(TSItxt.getText()),
							Integer.parseInt(TSPtxt.getText()));
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					sentence = "delete|" + usernameLoggedIn + "\n";
					outToServer.writeBytes(sentence + '\n');
					usernameLoggedIn = "";
					if (Onusers.size() > 0)
						for (Iterator<String> iterator = Onusers.iterator(); iterator.hasNext();) {
							String value = iterator.next();
							iterator.remove();
						}
					clientSocket.close();
					reprintOnlineUsers();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		logoutbtn.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutbtn.setBounds(345, 12, 85, 33);
		getContentPane().add(logoutbtn);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(20, 55, 430, 239);
		getContentPane().add(scrollPane1);

		chattxt = new JTextArea();
		chattxt.setEditable(false);
		scrollPane1.setViewportView(chattxt);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(20, 303, 430, 86);
		getContentPane().add(scrollPane2);

		JTextArea messageTF = new JTextArea();
		scrollPane2.setViewportView(messageTF);

		JLabel statuslbl = new JLabel("Status:");
		statuslbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		statuslbl.setBounds(20, 417, 100, 20);
		getContentPane().add(statuslbl);

		statustxt = new JTextField();
		statustxt.setEditable(false);
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

		JLabel Lplbl = new JLabel("Local Port:");
		Lplbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		Lplbl.setBounds(460, 171, 115, 20);
		getContentPane().add(Lplbl);

		LPtxt = new JTextField();
		Lplbl.setLabelFor(LPtxt);
		LPtxt.setFont(new Font("Tahoma", Font.BOLD, 11));
		LPtxt.setBounds(546, 171, 115, 20);
		getContentPane().add(LPtxt);
		LPtxt.setColumns(10);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] t = sendingTo.split(",");
				int port = Integer.parseInt(t[2]);
				InetAddress ip = null;
				try {
					ip = InetAddress.getByName(t[1]);
					sendMsg(usernameLoggedIn + " : " + messageTF.getText() + " \n", ip, port);
				} catch (SocketException | UnknownHostException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(460, 338, 100, 20);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("referesh");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sentence;
				String serverAnswer;
				try {
					Socket clientSocket = new Socket(InetAddress.getByName(TSItxt.getText()),
							Integer.parseInt(TSPtxt.getText()));
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					BufferedReader inFromServer = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));

					sentence = usernameLoggedIn;
					outToServer.writeBytes(sentence + "\n");

					serverAnswer = inFromServer.readLine();
					String[] tmp = serverAnswer.split(serverAnswer, '|');
					for (String usr : Onusers) {
						Onusers.remove(usr);
					}
					for (String t : tmp) {
						Onusers.add(t);
					}
					reprintOnlineUsers();
					clientSocket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(742, 425, 133, 21);
		getContentPane().add(btnNewButton_1);

		JLabel OnUlbl = new JLabel("Online Users");
		OnUlbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		OnUlbl.setHorizontalAlignment(SwingConstants.CENTER);
		OnUlbl.setBounds(742, 10, 126, 20);
		getContentPane().add(OnUlbl);

		JButton btnNewButton_2 = new JButton("Send to all");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (String s : Onusers) {
					String[] t = s.split(",");
					int port = Integer.parseInt(t[1]);
					InetAddress ip = null;
					try {
						ip = InetAddress.getByName(t[0]);
						sendMsg(usernameLoggedIn + " : " + messageTF.getText() + " \n", ip, port);
					} catch (SocketException | UnknownHostException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.setBounds(460, 369, 100, 20);
		getContentPane().add(btnNewButton_2);

		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendingTo = list.getSelectedValue().toString();
			}
		});
		list.setBounds(742, 40, 133, 349);
		getContentPane().add(list);
	}

	protected void reprintOnlineUsers() {
		DefaultListModel m = new DefaultListModel();
		for (String s : Onusers)
			m.addElement(s);
		list.setModel(m);
	}

	void recievingMsg() throws Exception {

		DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(LPtxt.getText()));
		byte[] receiveData = new byte[2048];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
		String sentence = new String(receivePacket.getData());
		chattxt.setText(chattxt.getText() + "\n" + sentence);

	}

	private void sendMsg(String sentence, InetAddress ip, int port) throws SocketException {
		byte[] sendData = new byte[2048];
		DatagramSocket clientSocket;
		clientSocket = new DatagramSocket();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			String user = ip + "," + port;

		}
		clientSocket.close();
	}

	public static void main(String[] args) throws Exception {
		MainPanel p = new MainPanel();
		p.setVisible(true);
		while (true)
			p.recievingMsg();
	}
}