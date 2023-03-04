package chatapp;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class supprimerGroupe extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public supprimerGroupe(String username,DatagramSocket socket) throws Exception  {
		this.socket=socket;

		supprimerGroupe.username=username;
		final DefaultListModel<String> model = new DefaultListModel<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 323, 383);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JList list = new JList(model);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(65, 10, 126, 279);
		contentPane.add(list);
		
		sendMessage(supprimerGroupe.username);
		
		String[]amis = receiveMessage().split(",");
		String[] str = new String[amis.length];
		 
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
        }
        for (int i = 0; i < str.length; i++) {
            model.addElement(str[i]);
       }
		
		JButton btnNewButton = new JButton("Supprimer");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexSel = list.getSelectedIndices();
				int selectedIndex = list.getSelectedIndex();
				for(int i=0;i<indexSel.length;i++)
					try {
						Supprimer(supprimerGroupe.username,(String)list.getModel().getElementAt(indexSel[i]));
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
		btnNewButton.setBounds(144, 299, 126, 38);
		contentPane.add(btnNewButton);
		
		JButton btnRetourner = new JButton("retourner");
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(supprimerGroupe.username,supprimerGroupe.socket);
				m.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 299, 126, 38);
		contentPane.add(btnRetourner);
	}
	
	//envoyer un message vers le serveur pour recevoir la list des groupes
	public void sendMessage(String log) throws Exception {
	 	String s = "supprimGroup/"+log;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	//envoyer un message vers le serveur pour supprimer un group 
	public void Supprimer(String log,String user) throws Exception {
	 	String s = "supprimerGroup/"+log+"/"+user;
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
