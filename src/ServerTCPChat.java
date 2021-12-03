import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ServerTCPChat extends JFrame {
	private JTextField textField;
	ArrayList<UserConnection> online = new ArrayList<UserConnection>();

	public ServerTCPChat() {

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(142, 11, 72, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Start listening at  port :");
		lblNewLabel.setBounds(10, 11, 129, 20);
		panel.add(lblNewLabel);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 91, 160, 355);
		panel.add(textArea);

		JLabel lblNewLabel_1 = new JLabel("Online Users");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(24, 77, 160, 14);
		panel.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Turn on the server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// defult
				int port = 8888;
				// get port
				port = Integer.parseInt(textField.getText());
				ServerSocket welcomeSocket;
				try {
					welcomeSocket = new ServerSocket(port);

					while (true) {
						Socket connectionSocket = welcomeSocket.accept();
						BufferedReader inFromClient = new BufferedReader(
								new InputStreamReader(connectionSocket.getInputStream()));
						DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
						String clientSentence = inFromClient.readLine();
						InetSocketAddress sockaddr = (InetSocketAddress) connectionSocket.getRemoteSocketAddress();

						InetAddress inaddr = sockaddr.getAddress();
						int clientPort = sockaddr.getPort();
						String replyingMessage;
						String[] tmp = clientSentence.split(clientSentence, '|');
						if (tmp[0].equals("delete")) {
							deleteUser(tmp[1], inaddr, clientPort);
							replyingMessage = "\n";
						} else if (clientSentence.equals("")) {
							replyingMessage = "";
						} else {
							replyingMessage = returnListOfUsers() + "\n";
							addIfNotAdded(clientSentence, inaddr, clientPort);
						}
						outToClient.writeBytes(replyingMessage);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(20, 43, 164, 23);
		panel.add(btnNewButton);

	}

	protected void deleteUser(String name, InetAddress inaddr, int clientPort) {
		for (int i = 0; i < this.online.size(); i++) {
			if (online.get(i).name.equals(name) && online.get(i).IP.equals(inaddr) && online.get(i).port == clientPort)
				online.remove(i);
		}
	}

	protected void addIfNotAdded(String name, InetAddress inaddr, int clientPort) {
		for (UserConnection userConnection : online) {
			if (userConnection.name.equals(name) && userConnection.IP.equals(inaddr)
					&& userConnection.port == clientPort)
				return;
		}
		online.add(new UserConnection(name, inaddr, clientPort));
	}

	protected String returnListOfUsers() {
		String listOfOnlineUsers = "";
		boolean f = false;
		if (online.size() == 0)
			return listOfOnlineUsers;
		for (UserConnection userConnection : online) {
			if (f)
				listOfOnlineUsers += "|";
			f = true;
			listOfOnlineUsers += userConnection.toString();
		}
		return listOfOnlineUsers;
	}

	public static void main(String[] args) {
		ServerTCPChat serverchat = new ServerTCPChat();
		serverchat.setSize(240, 500);
		serverchat.setVisible(true);
	}

	class UserConnection {
		private String name;
		private InetAddress IP;
		private int port;

		public UserConnection(String name, InetAddress IP, int port) {
			this.name = name;
			this.IP = IP;
			this.port = port;
		}

		@Override
		public String toString() {
			return name + "," + IP.toString() + "," + port;
		}
	}

}