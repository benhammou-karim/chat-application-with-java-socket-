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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPasswordField;

public class inscrire extends JFrame {

	private JPanel contentPane;
	private JTextField login;
	private static DatagramSocket socket;
	private JPasswordField passwordField;

	public inscrire(DatagramSocket socket) {
		this.socket=socket;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 524, 348);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login :");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 71, 129, 52);
		contentPane.add(lblNewLabel);
		
		login = new JTextField();
		login.setBackground(new Color(0, 0, 64));
		login.setForeground(new Color(255, 255, 255));
		login.setColumns(10);
		login.setBounds(178, 83, 314, 28);
		contentPane.add(login);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPassword.setBounds(10, 133, 129, 52);
		contentPane.add(lblPassword);

		JLabel test_inscrire = new JLabel("");
		test_inscrire.setBackground(new Color(0, 0, 64));
		test_inscrire.setForeground(new Color(255, 255, 255));
		test_inscrire.setHorizontalAlignment(SwingConstants.CENTER);
		test_inscrire.setBounds(34, 183, 440, 41);
		contentPane.add(test_inscrire);
		
		JButton btnSinscrire = new JButton("S'inscrire");
		btnSinscrire.setBackground(new Color(0, 0, 64));
		btnSinscrire.setForeground(new Color(255, 255, 255));
		btnSinscrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String log = login.getText().trim();
				String pass = passwordField.getText().trim();
				if(!log.isEmpty() && !pass.isEmpty()) {
					try {
						sendMessage(log,pass);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					test_inscrire.setVisible(true);
		            test_inscrire.setText("inscrit avec success");
				}
                else {
                	test_inscrire.setVisible(true);
                	test_inscrire.setText("veuillez entrer votre login et mdp!!");
                }
			}
		});
		btnSinscrire.setBounds(227, 234, 129, 52);
		contentPane.add(btnSinscrire);
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test_inscrire.setVisible(false);
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
		btnRetourner.setBounds(67, 234, 129, 52);
		contentPane.add(btnRetourner);
		
		JLabel lblNewLabel_1 = new JLabel("::::::::::::::: Inscrire ::::::::::::::::::::");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_1.setBounds(67, 10, 352, 51);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(0, 0, 64));
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBounds(178, 154, 314, 19);
		contentPane.add(passwordField);
	}
	
	//envoyer un message vers le serveur pour ajouter un nouveau user dans la base de donnee
	 public void sendMessage(String log,String pass) throws Exception {
		 	String s = "inscrire/"+log+"/"+pass;
	        byte buffer[] = s.getBytes();
	        InetAddress address = InetAddress.getByName("127.0.0.1");
	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
	        socket.send(packet);
	    }
	 
	 

}
