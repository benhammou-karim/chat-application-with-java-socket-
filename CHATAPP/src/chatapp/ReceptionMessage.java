package chatapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceptionMessage extends Thread{
	private DatagramSocket socket;
	private byte buffer[];
	Chat chat;
	
	public ReceptionMessage(DatagramSocket socket, Chat chat) {
        this.socket = socket;
        buffer = new byte[1024];
        this.chat = chat;
    }
	
	public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, buffer.length).trim();
                String[] test = received.split("/");
                if(test[0].trim().equals("grps")) {
                	//pour savoir que le chat va etre un group
                	chat.setTest(test[1].trim());
                }else {
                	//pour savoir que le chat va etre entre deux personne
                    chat.AfficherMessage(received);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    
}
