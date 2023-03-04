package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
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

public class ModifierGroup extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public ModifierGroup(String username,DatagramSocket socket) throws Exception {
		this.socket=socket;

		ModifierGroup.username=username;
		final DefaultListModel<String> model = new DefaultListModel<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 393, 451);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList(model);
		list.setBackground(new Color(0, 0, 64));
		list.setForeground(new Color(255, 255, 255));
		list.setBounds(43, 51, 132, 313);
		contentPane.add(list);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(0, 0, 64));
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBounds(94, 10, 132, 31);
		contentPane.add(comboBox);
		
       
       sendMessage(ModifierGroup.username);
		
		String[]amis = receiveMessage().split(",");		 
       for (int i = 0; i < amis.length; i++) {
    	   comboBox.addItem(amis[i]);
       }
       
		// une fois selectionner un group dans le comboBox la list pour supprimer un amis va se rafraîchir selon le group
       comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemCount() != 0){					
					model.clear();
					String[] ami;
					try {
						sendMessages(comboBox.getSelectedItem().toString(),ModifierGroup.username);
						ami = receiveMessage().split(",");
						String[] str = new String[ami.length];
						 
				        for (int i = 0; i < ami.length; i++) {
				            str[i] = ami[i];
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
		
		JLabel lblNewLabel_1 = new JLabel("Groupe Name :");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(10, 10, 92, 27);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Retourner");
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(ModifierGroup.username,ModifierGroup.socket);
				m.show();
				dispose();
			}
		});
		btnNewButton.setBounds(53, 374, 112, 31);
		contentPane.add(btnNewButton);
		
		// supprimer un amis du group
		JButton btnSupprimerFrined = new JButton("Supprimer frined");
		btnSupprimerFrined.setBackground(new Color(0, 0, 64));
		btnSupprimerFrined.setForeground(new Color(255, 255, 255));
		btnSupprimerFrined.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameGroupe = comboBox.getSelectedItem().toString();
				int[] indexSel = list.getSelectedIndices();
				for(int i=0;i<indexSel.length;i++)
					try {
						Modifier((String)list.getModel().getElementAt(indexSel[i]),nameGroupe,ModifierGroup.username);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				ListSelectionModel selmodel = list.getSelectionModel();
		        int index = selmodel.getMinSelectionIndex();
		        if (index >= 0)
		          model.remove(index);
			}
		});
		btnSupprimerFrined.setBounds(185, 95, 187, 50);
		contentPane.add(btnSupprimerFrined);
	}
	
	// envoyer un message vers le serveur pour recevoir la list des groupes selon le utilisateur connecté
	public void sendMessage(String log) throws Exception {
	 	String s = "modifierGroup/"+log;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	// envoyer un message vers le serveur pour recevoir la list des amis selon le utilisateur connecté
	public void sendMessages(String group,String log) throws Exception {
	 	String s = "modifieGroup/"+log+"/"+group;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	// envoyer un message vers le serveur pour supprimer un amis dans le group
	public void Modifier(String user,String nomgroup,String log) throws Exception {
	 	String s = "updateGroup/"+log+"/"+user+"/"+nomgroup;
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
