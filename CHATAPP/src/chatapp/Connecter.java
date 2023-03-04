package chatapp;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPasswordField;

public class Connecter extends JFrame {

	private JPanel contentPane;
	private JTextField login;
	private static DatagramSocket socket;
	private JPasswordField passwordField;

	public Connecter(DatagramSocket socket) {
		this.socket=socket;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login :");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(20, 94, 129, 52);
		contentPane.add(lblNewLabel);
		
		login = new JTextField();
		login.setBackground(new Color(0, 0, 64));
		login.setForeground(new Color(255, 255, 255));
		login.setBounds(188, 111, 314, 28);
		contentPane.add(login);
		login.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPassword.setBounds(20, 161, 129, 52);
		contentPane.add(lblPassword);
		
		JLabel test_conn = new JLabel("");
		test_conn.setBackground(new Color(0, 0, 64));
		test_conn.setForeground(new Color(255, 255, 255));
		test_conn.setHorizontalAlignment(SwingConstants.CENTER);
		test_conn.setBounds(61, 208, 419, 43);
		contentPane.add(test_conn);
		
		JButton btnNewButton = new JButton("Se connecter");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String log = login.getText().trim();
				String pass = passwordField.getText().trim();
				try {
					sendMessage(log,pass);
					String res = receiveMessage();
	                if(res.equals("true")){
	                	test_conn.setVisible(false);
	                	Menu m = new Menu(log,Connecter.socket);
	                	m.show();
	                	dispose();
	                }
	                else {
	                	test_conn.setVisible(true);
	                	test_conn.setText("mdp ou login incorrect");
	                }
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(124, 261, 129, 52);
		contentPane.add(btnNewButton);
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test_conn.setVisible(false);
				Client c;
				try {
					c = new Client();
					c.show();
					dispose();
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnRetourner.setBounds(293, 261, 129, 52);
		contentPane.add(btnRetourner);
		
		JLabel lblNewLabel_1 = new JLabel("::::::::::::::: Se Connecter ::::::::::::::::::::");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_1.setBounds(72, 27, 396, 51);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(0, 0, 64));
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBounds(188, 182, 314, 19);
		contentPane.add(passwordField);
		
		
	}
	
	//envoyer un message vers le serveur pour verifier le login et mdp avec la base de donnee
	 public void sendMessage(String log,String pass) throws Exception {
		 	String s = "connecter/"+log+"/"+pass;
	        byte buffer[] = s.getBytes();
	        InetAddress address = InetAddress.getByName("127.0.0.1");
	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
	        socket.send(packet);
	    }
	 
		// recevoir un message true or false pour le test du connection vers l'appli
	 private static String receiveMessage() throws IOException {
	        byte[] buffer = new byte[1024];
	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	        socket.receive(packet);
	        String received = new String(packet.getData(), 0, buffer.length).trim();
	        return received;
	    }
}
