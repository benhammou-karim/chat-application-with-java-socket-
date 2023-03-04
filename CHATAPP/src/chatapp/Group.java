package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
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

public class Group extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public Group(String username,DatagramSocket socket) throws Exception {
		this.socket=socket;

		Group.username=username;
		
		sendMessage(Group.username);
		
		String[]amis = receiveMessage().split(",");
		String[] str = new String[amis.length];
		 
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
        }

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 269, 457);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList(str);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(50, 10, 142, 318);
		contentPane.add(list);
		
		JButton btnNewButton = new JButton("Retourner");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(Group.username,Group.socket);
				m.show();
				dispose();
			}
		});
		btnNewButton.setBounds(50, 350, 142, 43);
		contentPane.add(btnNewButton);
	}
	
	
	//envoyer un message vers le serveur pour recevoir la list des groupes
	 public void sendMessage(String log) throws Exception {
		 	String s = "Groupe/"+log;
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
