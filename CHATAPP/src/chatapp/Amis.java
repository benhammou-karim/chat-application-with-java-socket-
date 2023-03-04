package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Amis extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public Amis(String username,DatagramSocket socket) throws Exception {
		this.socket=socket;

		Amis.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 261, 429);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sendMessage(Amis.username);
		
		String[]amis = receiveMessage().split(",");
		String[] str = new String[amis.length];
		 
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
        }
		
		JButton btnNewButton = new JButton("Retourner");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(Amis.username,Amis.socket);
				m.show();
				dispose();
			}
		});
		btnNewButton.setBounds(54, 288, 131, 53);
		contentPane.add(btnNewButton);
		
		JList list = new JList(str);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(54, 10, 131, 256);
		contentPane.add(list);
	}
	
	//envoyer un message vers le serveur pour recevoir la list des amis
	 public void sendMessage(String log) throws Exception {
		 	String s = "Amis/"+log;
	        byte buffer[] = s.getBytes();
	        InetAddress address = InetAddress.getByName("127.0.0.1");
	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
	        socket.send(packet);
	    }
	 
		// recevoir les donnees du serveur
	 private static String receiveMessage() throws IOException {
	        byte[] buffer = new byte[1024];
	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	        socket.receive(packet);
	        String received = new String(packet.getData(), 0, buffer.length).trim();
	        return received;
	    }
}
