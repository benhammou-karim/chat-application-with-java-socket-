package chatapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EnvoiMessage extends Thread {
	private final static int PORT = 1999;
    private DatagramSocket socket;
    private String hostName;
    private Chat chat;
    
   public EnvoiMessage(DatagramSocket socket, String hostName, Chat chat) {
        this.socket = socket;
        this.hostName = hostName;
        this.chat = chat;
    }
   
   private void envoiMSG(String s) throws Exception {
       byte buffer[] = s.getBytes();
       InetAddress address = InetAddress.getByName(hostName);
       DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
       socket.send(packet);
   }

   public void run() {
       while (true) {
           try {
               while (!chat.set_msg) {
                   Thread.sleep(100);
               }
               envoiMSG(chat.getMessage());
               chat.setMsg(false);
           } catch (Exception e) {
        	   chat.AfficherMessage(e.getMessage());
           }
       }
   }
}
