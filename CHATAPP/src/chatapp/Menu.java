package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Menu extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public Menu(String username,DatagramSocket socket) {
		this.socket=socket;

		Menu.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 373, 721);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("MES AMIS");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Amis a;
				try {
					a = new Amis(Menu.username,Menu.socket);
					a.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(67, 89, 214, 50);
		contentPane.add(btnNewButton);
		
		JButton btnAjouterUnAmis = new JButton("Ajouter un amis");
		btnAjouterUnAmis.setBackground(new Color(0, 0, 64));
		btnAjouterUnAmis.setForeground(new Color(255, 255, 255));
		btnAjouterUnAmis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AjouterAmis aj;
				try {
					aj = new AjouterAmis(Menu.username,Menu.socket);
					aj.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAjouterUnAmis.setBounds(67, 209, 214, 50);
		contentPane.add(btnAjouterUnAmis);
		
		JButton btnCreerUnGroup = new JButton("supprimer un amis");
		btnCreerUnGroup.setBackground(new Color(0, 0, 64));
		btnCreerUnGroup.setForeground(new Color(255, 255, 255));
		btnCreerUnGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupprimerAmis sa;
				try {
					sa = new SupprimerAmis(Menu.username,Menu.socket);
					sa.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCreerUnGroup.setBounds(67, 269, 214, 50);
		contentPane.add(btnCreerUnGroup);
		
		JButton btnCreerUnGroup_1 = new JButton("creer un group");
		btnCreerUnGroup_1.setBackground(new Color(0, 0, 64));
		btnCreerUnGroup_1.setForeground(new Color(255, 255, 255));
		btnCreerUnGroup_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Groupe g;
				try {
					g = new Groupe(Menu.username,Menu.socket);
					g.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCreerUnGroup_1.setBounds(67, 329, 214, 50);
		contentPane.add(btnCreerUnGroup_1);
		
		JButton btnSupprimerUnGroup = new JButton("supprimer un group");
		btnSupprimerUnGroup.setBackground(new Color(0, 0, 64));
		btnSupprimerUnGroup.setForeground(new Color(255, 255, 255));
		btnSupprimerUnGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supprimerGroupe sg;
				try {
					sg = new supprimerGroupe(Menu.username,Menu.socket);
					sg.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnSupprimerUnGroup.setBounds(67, 449, 214, 50);
		contentPane.add(btnSupprimerUnGroup);
		
		JButton btnNewButton_3_1 = new JButton("lancer mon chat");
		btnNewButton_3_1.setBackground(new Color(0, 0, 64));
		btnNewButton_3_1.setForeground(new Color(255, 255, 255));
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//afficher l'interface de chat et lancer 2 thread un pour l'envoi et l'autre pour recevoir des messages 
				Chat c;
				try {
					c = new Chat("127.0.0.1",Menu.username,Menu.socket);
					ReceptionMessage receiver = new ReceptionMessage(Menu.socket,c);
	                EnvoiMessage sender = new EnvoiMessage(Menu.socket, "127.0.0.1",c);
	                Thread receiverThread = new Thread(receiver);
	                Thread senderThread = new Thread(sender);
	                receiverThread.start();
	                senderThread.start();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3_1.setBounds(67, 509, 214, 50);
		contentPane.add(btnNewButton_3_1);
		
		JButton btnNewButton_4_1 = new JButton("Se déconnecter");
		btnNewButton_4_1.setBackground(new Color(0, 0, 64));
		btnNewButton_4_1.setForeground(new Color(255, 255, 255));
		btnNewButton_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton_4_1.setBounds(67, 624, 214, 50);
		contentPane.add(btnNewButton_4_1);
		
		JLabel lblNewLabel = new JLabel("::::::::::::::: MENU ::::::::::::::::::::");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBounds(25, 10, 329, 51);
		contentPane.add(lblNewLabel);
		
		JButton btnMesGroupes = new JButton("MES Groupes");
		btnMesGroupes.setBackground(new Color(0, 0, 64));
		btnMesGroupes.setForeground(new Color(255, 255, 255));
		btnMesGroupes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Group g;
				try {
					g = new Group(Menu.username,Menu.socket);
					g.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnMesGroupes.setBounds(67, 149, 214, 50);
		contentPane.add(btnMesGroupes);
		
		JButton btnModifierUnGroup = new JButton("modifier un group");
		btnModifierUnGroup.setBackground(new Color(0, 0, 64));
		btnModifierUnGroup.setForeground(new Color(255, 255, 255));
		btnModifierUnGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifierGroup mg;
				try {
					mg = new ModifierGroup(Menu.username,Menu.socket);
					mg.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnModifierUnGroup.setBounds(67, 389, 214, 50);
		contentPane.add(btnModifierUnGroup);
		
		JButton btnNewButton_4_1_1 = new JButton("Notification");
		btnNewButton_4_1_1.setBackground(new Color(0, 0, 64));
		btnNewButton_4_1_1.setForeground(new Color(255, 255, 255));
		btnNewButton_4_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notification n;
				try {
					n = new notification(Menu.username,Menu.socket);
					n.show();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4_1_1.setBounds(67, 564, 214, 50);
		contentPane.add(btnNewButton_4_1_1);
	}

}
