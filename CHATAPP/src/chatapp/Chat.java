package chatapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;

public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private String host_name;
	private static String username;
	private static DatagramSocket socket;
	private int indexPane=0;
	private JTextPane[] textPanes;
	private String[] str;
	private String test;
	String message = "";
	boolean set_msg = false;
    
	public Chat(String host_name,String username,DatagramSocket socket) throws Exception {
		Chat.username=username;
		this.socket=socket;
		this.host_name = host_name;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 798, 525);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		sendMessage("clients/ ");
		//recevoir la list des amis
		String[]amis = receiveMessage().split(",");
		sendMessage("Groupes/ "+Chat.username);
		//recevoir la list des groupes
		String[] groupe = receiveMessage().split(",");
		str = new String[amis.length+groupe.length];
		//creer un textPane pour chaque amis et chaque groupes (avoir une conversation pour chaqu'un)
		textPanes = new JTextPane[amis.length+groupe.length];
        for (int i = 0; i < amis.length; i++) {
            str[i] = amis[i];
            JTextPane textPane = new JTextPane();
            textPane.setBounds(256, 19, 518, 382);
    		contentPane.add(textPane);
    		textPane.setVisible(false);
    		textPanes[i]=textPane;
        }
        int j=0;
        for (int i = amis.length; i < amis.length+groupe.length; i++) {	
            str[i] = groupe[j];
            JTextPane textPane = new JTextPane();
            textPane.setBounds(256, 19, 518, 382);
    		contentPane.add(textPane);
    		textPane.setVisible(false);
    		textPanes[i]=textPane;
    		j++;
        }
        
        
		JList list = new JList(str);
		list.setForeground(new Color(255, 255, 255));
		list.setBackground(new Color(0, 0, 64));
		list.setBounds(40, 19, 115, 323);
		contentPane.add(list);
		
		//afficher le textpane correspondant et envoyer un message vers le serveur pour savoir si le chat pour 2 au group
		JButton btnNewButton = new JButton("Chater");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 64));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPanes[indexPane].setVisible(false);
				int indexSel = list.getSelectedIndex();
				String user = " " ;
					 user = (String)list.getSelectedValue();
                try {
                	indexPane=indexSel;
    				textPanes[indexPane].setVisible(true);
					sendMessage("find/"+user);
                } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(40, 367, 115, 34);
		contentPane.add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setBackground(new Color(0, 0, 64));
		textField_1.setForeground(new Color(255, 255, 255));
		textField_1.setBounds(197, 411, 443, 67);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		//envoyer le message au group ou bien au desrinataire
		JButton btnEnvoyer = new JButton("envoyer");
		btnEnvoyer.setBackground(new Color(0, 0, 64));
		btnEnvoyer.setForeground(new Color(255, 255, 255));
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indexSel = list.getSelectedIndex();
				String user = " " ;
				user = (String)list.getSelectedValue();
				if (!set_msg) {
					try {
						if(test.equals("false")) {
							 message = "users/" +textField_1.getText().trim()+"/"+user.trim();
			                 textField_1.setText(null);
						}else {
							message = "grp/" +textField_1.getText().trim()+"/"+user.trim();
		                    textField_1.setText(null);
						}
	                    if (!message.equals(null) && !message.equals("")) {
	                        set_msg = true;
	                    }
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
			}
		});
		btnEnvoyer.setBounds(650, 411, 75, 67);
		contentPane.add(btnEnvoyer);
		
		JButton btnRetourner = new JButton("Retourner");
		btnRetourner.setForeground(new Color(255, 255, 255));
		btnRetourner.setBackground(new Color(0, 0, 64));
		btnRetourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu(Chat.username,Chat.socket);
				m.show();
				dispose(); 
			}
		});
		btnRetourner.setBounds(10, 444, 115, 34);
		contentPane.add(btnRetourner);
		
		//selectionner une image et l'evoyer au serveur
		JButton btnNewButton_1 = new JButton("GET");
		btnNewButton_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser select = new JFileChooser();
				select.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				select.setMultiSelectionEnabled(true);
				int result = select.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					int indexSel = list.getSelectedIndex();
					String user = " " ;
					user = (String)list.getSelectedValue();
					File fichier = select.getSelectedFile();
					String chemin = fichier.getAbsolutePath();
					try {
						sendMessage("usersIMG/ "+chemin+"/"+user.trim());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//JLabel2.();
				}
			}
		});
		btnNewButton_1.setBounds(722, 411, 62, 67);
		contentPane.add(btnNewButton_1);
		setVisible(true);
	}
	public void AfficherMessage(String receivedMessage) {
		String[] msg = receivedMessage.split("/");
		System.out.println(msg[0].trim()+ "   " +msg[msg.length-2].trim());
		if(msg[0].trim().equals("grp")) {
			//affichage dans le textpane du group correspondant
			for (int i = 0; i < str.length; i++) {
				if(str[i].equals(msg[msg.length-2].trim())) {
					indexPane=i;
					break;
				}				
	        }
			StyledDocument chat = textPanes[indexPane].getStyledDocument();
		       try {
		        chat.insertString(chat.getLength(), msg[1] + "\n", null);
		       } catch (BadLocationException e1) {
		            e1.printStackTrace();
		       }
		}else if(msg[0].trim().equals("img")){
			//affichage l'image dans le textpane 
			String too=msg[msg.length-1].trim();
			String from=msg[msg.length-2].trim();
			for (int i = 0; i < str.length; i++) {
				if(str[i].equals(too)) {
					indexPane=i;
					break;
				}
				else if(str[i].equals(from)) {
					indexPane=i;
	            	break;
				}
	        }
		       textPanes[indexPane].insertIcon(new ImageIcon(msg[1]));
		}else{
			//affichage du message dans le textpane entre 2 personne
			String too=msg[msg.length-1].trim();
			String from=msg[msg.length-2].trim();
			for (int i = 0; i < str.length; i++) {
				if(str[i].equals(too)) {
					indexPane=i;
					break;
				}
				else if(str[i].equals(from)) {
					indexPane=i;
	            	break;
				}
	        }
			StyledDocument chat = textPanes[indexPane].getStyledDocument();
		       try {
		        chat.insertString(chat.getLength(), msg[0] + "\n", null);
		       } catch (BadLocationException e1) {
		            e1.printStackTrace();
		       }
		}
		
		}
    

    public void setMsg(boolean set_msg) {
        this.set_msg = set_msg;
    }
    public void setTest(String test) {
        this.test = test;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getMessage() {
        return message;
    }
    
    
    public void sendMessage(String s) throws Exception {
        byte buffer[] = s.getBytes();
        InetAddress address = InetAddress.getByName(host_name);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1999);
        socket.send(packet);
    }
    
    private static String receiveMessage() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, buffer.length).trim();
        return received;
    }
}
