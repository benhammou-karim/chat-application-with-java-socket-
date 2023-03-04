package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

public class Groupe extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static String username;
	private static DatagramSocket socket;

	public Groupe(String username,DatagramSocket socket) throws Exception {
		Groupe.username=username;
		this.socket=socket;
		final DefaultListModel<String> model = new DefaultListModel<>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 495);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBackground(new Color(0, 0, 64));
		textField.setForeground(new Color(255, 255, 255));
		textField.setBounds(101, 14, 208, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JList list = new JList(model);
		list.setBackground(new Color(0, 0, 64));
		list.setForeground(new Color(255, 255, 255));
		list.setBounds(41, 152, 180, 256);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("Groupe Name :");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 10, 92, 27);
		contentPane.add(lblNewLabel);
		
		JLabel text_error = new JLabel("");
		text_error.setBackground(new Color(0, 0, 64));
		text_error.setForeground(new Color(255, 255, 255));
		text_error.setBounds(71, 42, 254, 55);
		contentPane.add(text_error);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(0, 0, 64));
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBounds(101, 107, 180, 21);
		contentPane.add(comboBox);
		
		sendMessage(Groupe.username);
		
		String[]amis = receiveMessage().split(",");		 
       for (int i = 0; i < amis.length; i++) {
    	   comboBox.addItem(amis[i]);
       }
		// une fois selectionner un group dans le comboBox la list pour ajouter un amis va se rafraîchir selon le group
       comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemCount() != 0){					
					model.clear();
					String[] amis;
					try {
						sendMessages(comboBox.getSelectedItem().toString(),Groupe.username);
						amis = receiveMessage().split(",");
						String[] str = new String[amis.length];
						 
				        for (int i = 0; i < amis.length; i++) {
				            str[i] = amis[i];
				        }
				        for (int i = 0; i < str.length; i++) {
				            model.addElement(str[i]);
				       }
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
       
		// creer un group
		JButton btnNewButton = new JButton("Create");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grope_name = textField.getText().trim();
				if(!grope_name.isEmpty()) {
					try {
						Ajouter(grope_name,Groupe.username);
						text_error.setVisible(true);
						text_error.setText("insert avec success");
						sendMessage(Groupe.username);
						
						String[]amis = receiveMessage().split(",");		 
				       
						 comboBox.removeAllItems();
						 for (int i = 0; i < amis.length; i++) {
					    	   comboBox.addItem(amis[i]);
					       }
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
                else {
                	text_error.setVisible(true);
                	text_error.setText("veuillez entrer le nom du groupe!!");
                }
			}
			
		});
		btnNewButton.setBounds(319, 16, 85, 21);
		contentPane.add(btnNewButton);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Groupe Name :");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(10, 104, 92, 27);
		contentPane.add(lblNewLabel_1);
		
		// creer un amis au group
		JButton btnAddFriend = new JButton("ADD FRIEND");
		btnAddFriend.setBackground(new Color(0, 0, 64));
		btnAddFriend.setForeground(new Color(255, 255, 255));
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String nameGroupe = comboBox.getSelectedItem().toString();
				int[] indexSel = list.getSelectedIndices();
				for(int i=0;i<indexSel.length;i++)
					try {
						Ajouter((String)list.getModel().getElementAt(indexSel[i]),nameGroupe,Groupe.username);
						ListSelectionModel selmodel = list.getSelectionModel();
				        int index = selmodel.getMinSelectionIndex();
				        if (index >= 0)
				          model.remove(index);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		btnAddFriend.setBounds(231, 199, 136, 45);
		contentPane.add(btnAddFriend);
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(Groupe.username,Groupe.socket);
				m.show();
				dispose(); 
			}
		});
		btnRetourner.setBounds(71, 418, 115, 34);
		contentPane.add(btnRetourner);
		
		
	}
	
	// envoyer un message vers le serveur pour recevoir la list des groupes selon le utilisateur connecté
	public void sendMessage(String log) throws Exception {
	 	String s = "Group/"+log;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	// envoyer un message vers le serveur pour recevoir la list des amis selon le utilisateur connecté
	public void sendMessages(String group,String log) throws Exception {
	 	String s = "Friends/"+log+"/"+group;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	// envoyer un message vers le serveur pour ajouter un group
	public void Ajouter(String nomgroup,String log) throws Exception {
	 	String s = "ajouterGroup/"+log+"/"+nomgroup;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	// envoyer un message vers le serveur pour ajouter un amis dans le group
	public void Ajouter(String user,String nomgroup,String log) throws Exception {
	 	String s = "ajouterAmis/"+log+"/"+user+"/"+nomgroup;
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
