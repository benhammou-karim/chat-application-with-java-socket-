package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Client extends JFrame {

	private JPanel contentPane;
	private static DatagramSocket socket;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Client() throws SocketException {
		socket = new DatagramSocket();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 323);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Se Connecter");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connecter c = new Connecter(socket);
				c.show();
				dispose();
			}
		});
		btnNewButton.setBounds(111, 117, 154, 55);
		contentPane.add(btnNewButton);
		
		JButton btnSinscrire = new JButton("S'inscrire");
		btnSinscrire.setBackground(new Color(0, 0, 64));
		btnSinscrire.setForeground(new Color(255, 255, 255));
		btnSinscrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inscrire i = new inscrire(socket);
				i.show();
				dispose();
			}
		});
		btnSinscrire.setBounds(111, 182, 154, 55);
		contentPane.add(btnSinscrire);
		
		JLabel lblNewLabel = new JLabel("::::::::::::::: Chat App ::::::::::::::::::::");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBounds(10, 36, 388, 51);
		contentPane.add(lblNewLabel);
	}

}
