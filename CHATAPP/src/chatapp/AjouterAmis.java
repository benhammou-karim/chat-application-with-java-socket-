package chatapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AjouterAmis extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;

	public AjouterAmis(String username,DatagramSocket socket) throws Exception {
		this.socket=socket;

		final DefaultListModel<String> model = new DefaultListModel<>();
		AjouterAmis.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 488);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setBackground(new Color(0, 0, 64));
		btnAjouter.setForeground(new Color(255, 255, 255));
		btnAjouter.setBounds(244, 376, 129, 52);
		contentPane.add(btnAjouter);
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(AjouterAmis.username,AjouterAmis.socket);
				m.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 376, 129, 52);
		contentPane.add(btnRetourner);
		
		sendMessage(AjouterAmis.username);
		
		String[]amis = receiveMessage().split(",");
		String[] str = new String[amis.length];
		 
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
        }
		
		
		JList list = new JList(model);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(144, 40, 129, 300);
		contentPane.add(list);
		
		for (int i = 0; i < str.length; i++) {
            model.addElement(str[i]);
       }
		
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexSel = list.getSelectedIndices();
				for(int i=0;i<indexSel.length;i++)
					try {
						Ajouter(AjouterAmis.username,(String)list.getModel().getElementAt(indexSel[i]));
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
	}
	
	//envoyer un message vers le serveur pour recevoir la list des utilisateurs
	public void sendMessage(String log) throws Exception {
	 	String s = "AjoutAmis/"+log;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	//envoyer un message vers le serveur pour ajouter un amis 
	public void Ajouter(String log,String user) throws Exception {
	 	String s = "AjouterAmis/"+log+"/"+user;
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
