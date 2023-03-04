package chatapp;

import java.awt.EventQueue;

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

public class notification extends JFrame {

	private JPanel contentPane;
	private static String username;
	private static DatagramSocket socket;
	
	public notification(String username,DatagramSocket socket) throws Exception {
		this.socket=socket;

		final DefaultListModel<String> model = new DefaultListModel<>();
		notification.username=username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 274, 502);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sendMessage(notification.username);
		
		String[]amis = receiveMessage().split(",");
		String[] str = new String[amis.length];
		 
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
        }
		
		JList list = new JList(model);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(31, 10, 162, 300);
		contentPane.add(list);
		
		for (int i = 0; i < str.length; i++) {
            model.addElement(str[i]);
       }
		
		//pour accepter un amis 
		JButton btnNewButton = new JButton("accepter");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexSel = list.getSelectedIndices();
				for(int i=0;i<indexSel.length;i++)
					try {
						Accepter(notification.username,(String)list.getModel().getElementAt(indexSel[i]));
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
		btnNewButton.setBounds(31, 339, 73, 21);
		contentPane.add(btnNewButton);
		
		//pour refuser la demande d'amis 
		JButton btnRefuser = new JButton("refuser");
		btnRefuser.setForeground(new Color(255, 255, 255));
		btnRefuser.setBackground(new Color(0, 0, 64));
		btnRefuser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexSel = list.getSelectedIndices();
				int selectedIndex = list.getSelectedIndex();
				for(int i=0;i<indexSel.length;i++)
					try {
						Refuser(notification.username,(String)list.getModel().getElementAt(indexSel[i]));
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
		btnRefuser.setBounds(120, 339, 73, 21);
		contentPane.add(btnRefuser);
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(notification.username,notification.socket);
				m.show();
				dispose();
			}
		});
		btnRetourner.setBounds(10, 434, 134, 29);
		contentPane.add(btnRetourner);
	}
	
	//envoyer un message vers le serveur pour recevoir la list des demandes d'amis
	public void sendMessage(String log) throws Exception {
	 	String s = "notification/"+log;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	//envoyer un message vers le serveur pour accepter un amis
	public void Accepter(String log,String user) throws Exception {
	 	String s = "notificationD/"+log+"/"+user;
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
	
	//envoyer un message vers le serveur pour refuser l'amis
	public void Refuser(String log,String user) throws Exception {
	 	String s = "notificationsR/"+log+"/"+user;
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
