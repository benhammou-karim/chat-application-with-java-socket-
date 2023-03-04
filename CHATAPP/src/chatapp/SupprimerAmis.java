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

public class SupprimerAmis extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public SupprimerAmis(String username,DatagramSocket socket) throws Exception {
		this.socket=socket;

		final DefaultListModel<String> model = new DefaultListModel<>();
		SupprimerAmis.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 393);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sendMessage(SupprimerAmis.username);
		
		String[]amis = receiveMessage().split(",");
		String[] str = new String[amis.length];
		 
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
        }
        
		JList list = new JList(model);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(53, 10, 127, 272);
		contentPane.add(list);
		
		for (int i = 0; i < str.length; i++) {
             model.addElement(str[i]);
        }
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(SupprimerAmis.username,SupprimerAmis.socket);
				m.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 292, 129, 52);
		contentPane.add(btnRetourner);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setForeground(new Color(255, 255, 255));
		btnSupprimer.setBackground(new Color(0, 0, 64));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexSel = list.getSelectedIndices();
				int selectedIndex = list.getSelectedIndex();
				for(int i=0;i<indexSel.length;i++)
					try {
						Supprimer(SupprimerAmis.username,(String)list.getModel().getElementAt(indexSel[i]));
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
		btnSupprimer.setBounds(148, 292, 129, 52);
		contentPane.add(btnSupprimer);
	}
	
	//envoyer un message vers le serveur pour recevoir la list des amis
	public void sendMessage(String log) throws Exception {
	 	String s = "supprimAmis/"+log;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	//envoyer un message vers le serveur pour supprimer un amis 
	public void Supprimer(String log,String user) throws Exception {
	 	String s = "supprimerAmis/"+log+"/"+user;
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
