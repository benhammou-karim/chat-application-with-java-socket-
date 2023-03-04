package chatapp;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Server extends Thread{
		private final static int PORT = 1999;
	    private final static int BUFFER = 1024;
	    
	    private static String BDD = "chatapp";
		private static String url = "jdbc:mysql://localhost:3306/" + BDD;
		private static String user = "root";
		private static String passwd = "";
		
	    private DatagramSocket socket;
	    private ArrayList<InetAddress> Users_addresses;
	    private ArrayList<Integer> Users_ports;
	    private ArrayList<String> usernames;
	    private String user1;
	    private String[] users;

	    
	    public Server() throws IOException {
	        socket = new DatagramSocket(PORT);
	        Users_addresses = new ArrayList();
	        Users_ports = new ArrayList();
	        usernames = new ArrayList();
	    }
	    
	    public void run() {
	        byte[] buffer = new byte[BUFFER];
	        while (true) {
	            try {
	                Arrays.fill(buffer, (byte) 0);
	                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	                socket.receive(packet);

	                InetAddress clientAddress = packet.getAddress();
 	                int client_port = packet.getPort();
	                
	                String message = new String(buffer, 0, buffer.length);
	                String[] do_this = message.split("/");
	                if(do_this[0].equals("connecter")) {
	                	String res = verify(do_this[1].trim(),do_this[2].trim());
	 	                if (!usernames.contains(do_this[1].trim())) {
	 	                    usernames.add(do_this[1].trim());
	 	                    Users_ports.add(client_port);
	 	                    Users_addresses.add(clientAddress);
	 	                }
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
 	                    
	                }else if(do_this[0].equals("inscrire")) {
	                	
	                	register(do_this[1].trim(),do_this[2].trim());
	                	
	                }else if(do_this[0].equals("find")) {
	                	
	                	int index = Users_ports.indexOf(client_port);
	                	String res = FindGroup(do_this[1].trim())+"/"+usernames.get(index)+"/"+do_this[1].trim();
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
 	                    
	                }else if(do_this[0].equals("Amis")) {
	                	
	                	String res = searchAmis(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	               
	                }else if(do_this[0].equals("AjoutAmis")) {
	                	
	                	String res = getClients(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	               
	                }else if(do_this[0].equals("notification")) {
	                	
	                	String res = searchnotifi(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	               
	                }else if(do_this[0].equals("AjouterAmis")) {
	                	
	                	ajouterNotifi(do_this[2].trim(),do_this[1].trim());
	               
	                }else if(do_this[0].equals("notificationD")) {
	                	
	                	ajouter(do_this[2].trim(),do_this[1].trim());
	               
	                }else if(do_this[0].equals("supprimAmis")) {
	                	
	                	String res = searchAmis(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	               
	                }else if(do_this[0].equals("supprimerAmis")) {
	                	
	                	supprimer(do_this[2].trim(),do_this[1].trim());
	                
	                }else if(do_this[0].equals("Groupe")) {
	                	
	                	String res = searchGroup(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	                
	                }else if(do_this[0].equals("Groupes")) {
	                	
	                	String res = searchGroupes(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	              
	                }else if(do_this[0].equals("supprimGroup")) {
	                	
	                	String res = searchGroup(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	               
	                }else if(do_this[0].equals("supprimerGroup")) {
	                	
	                	Supprimer(do_this[2].trim(),do_this[1].trim());
	               
	                }else if(do_this[0].equals("notificationsR")) {
	                	
	                	SupprimerNotification(do_this[2].trim(),do_this[1].trim());
	               
	                }else if(do_this[0].equals("modifierGroup")) {
	                	
	                	String res = searchGroup(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	                
	                }else if(do_this[0].equals("modifieGroup")) {
	                	
	                	String res = search(do_this[2].trim(),do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	               
	                }else if(do_this[0].equals("updateGroup")) {
	                	
	                	modifier(do_this[2].trim(),do_this[3].trim(),do_this[1].trim());
	               
	                }else if(do_this[0].equals("Group")) {
	                	
	                	String res = searchGroup(do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	              
	                }else if(do_this[0].equals("Friends")) {
	                	
	                	String res = searchAjoute(do_this[2].trim(),do_this[1].trim());
	                	byte[] buffer1 = res.getBytes();
 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
 	                    socket.send(packet);
	                
	                }else if(do_this[0].equals("ajouterGroup")) {
	                	
	                	insert_groupe(do_this[2].trim(),do_this[1].trim());
	                
	                }else if(do_this[0].equals("ajouterAmis")) {
	                	
	                	ajouter(do_this[2].trim(),do_this[3].trim(),do_this[1].trim());
	              
	                }
	                else {
	 	                
	 	                String[] test = message.split("/");
                        	
	 	                if(test[0].equals("clients")){
	 	                	
	 	                	//avoir la list des amis de client connecté
	 	                	
	 	                	int index = Users_ports.indexOf(client_port);
	 	                	String res = searchAmis(usernames.get(index));
	 	                    byte[] buffer1 = res.getBytes();
	 	                    packet = new DatagramPacket(buffer1, buffer1.length, clientAddress, client_port);
	 	                    socket.send(packet);
	 	                    
	 	                }else if(test[0].equals("users")){
	 	                	
	 	                	//defuser message entre 2 personnes
	 	                	
	 	                	int index = Users_ports.indexOf(client_port);
 	                        byte[] data = (usernames.get(index) + " : " + test[1] +" /"+ usernames.get(index) +" /"+test[test.length-1].trim()).getBytes();
 	                        InetAddress cl_address = Users_addresses.get(index);
 	                        int cl_port = Users_ports.get(index);
 	                        packet = new DatagramPacket(data, data.length, cl_address, cl_port);
 	                        socket.send(packet);
 	                        user1=test[test.length-1].trim();
 	                        index = usernames.indexOf(user1); ;
 	                        cl_address = Users_addresses.get(index);
 	                        cl_port = Users_ports.get(index);
 	                        packet = new DatagramPacket(data, data.length, cl_address, cl_port);
 	                        socket.send(packet);
	 	                
	 	                }else if(test[0].equals("usersIMG")){
	 	                	//defuser image entre 2 personnes

	 	                	int index = Users_ports.indexOf(client_port);
 	                        byte[] data = ("img/"+test[1].trim() +" /"+ usernames.get(index) +" /"+test[test.length-1].trim()).getBytes();
 	                        InetAddress cl_address = Users_addresses.get(index);
 	                        int cl_port = Users_ports.get(index);
 	                        packet = new DatagramPacket(data, data.length, cl_address, cl_port);
 	                        socket.send(packet);
 	                        user1=test[test.length-1].trim();
 	                        index = usernames.indexOf(user1); ;
 	                        cl_address = Users_addresses.get(index);
 	                        cl_port = Users_ports.get(index);
 	                        packet = new DatagramPacket(data, data.length, cl_address, cl_port);
 	                        socket.send(packet);
	 	              
	 	                }else if(test[0].equals("grp")){
	 	                	//defuser message entre les membres du group

	 	                	int index = Users_ports.indexOf(client_port);
 	                        byte[] data = ("grp/ "+usernames.get(index) + " : " + test[1] + " /"+test[test.length-1].trim()+"/ ss").getBytes();
 	                        InetAddress cl_address;
 	                        int cl_port;
 	                        String resultat=findUsers(test[test.length-1].trim());
 	                        users=resultat.split(",");
 	                        for(int i=0;i<users.length;i++) {
 	                        	index = usernames.indexOf(users[i]);
 	 	                        cl_address = Users_addresses.get(index);
 	 	                        cl_port = Users_ports.get(index);
 	 	                        packet = new DatagramPacket(data, data.length, cl_address, cl_port);
 	 	                        socket.send(packet);
 	                        }
	 	                }
	                }

	               
	            } catch (Exception e) {
	                System.err.println(e);
	            }
	        }
	    }
	    
	    //pour verify le connexion a l'appli (le login et le mot de passe)
	    public static String verify(String log, String pass){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            ResultSet res =stmt.executeQuery("select id from client where login='"+log+"' and password='"+pass+"'");
	            if(!res.next()) {
		            return "false";
	            }
	            conn.close();
	            return "true";
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
	        return "false";
	    }
	    
	    //pour enregistrer les données du user dans la base de donnee
	    public static void register(String log, String pass){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            stmt.execute("insert into client(login,password) value('"+log+"','"+pass+"')");
	            conn.close();
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
	    }
	    
	    //chercher les amis du utilisateur connecter
	    public static String searchAmis(String username){
	        try {
	        	int id =0;
	        	ArrayList<String> friend = new ArrayList<String>();
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
	            while(res.next())
	            	id = res.getInt(1);
	            res =stmt.executeQuery("select amis from amis where id_client="+id+"");
	            while(res.next())
	        		friend.add(res.getString(1));
	            if(friend.size()==-1) {
	            	friend.add("");
	            }
	            conn.close();
	            String message="";
	            for(String s : friend)
	            	message+=s+",";
	            return message;
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
			return null;
	    }
	    
	    //chercher les demandes d'amis
	    public static String searchnotifi(String usern){
	        try {
	        	ArrayList<String> friend = new ArrayList<String>();
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            ResultSet res =stmt.executeQuery("select admin from notification where amis='"+usern+"'");
	            while(res.next())
	        		friend.add(res.getString(1));
	            if(friend.size()==-1) {
	            	friend.add("");
	            }
	            conn.close();
	            String message="";
	            for(String s : friend)
	            	message+=s+",";
	            return message;
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
			return null;
	    }
	    
	    //recevoir les client qui non pas amis avec l'utilisateur connecter
	    public static String getClients(String username){
	        try {
	        	int id =0;
	        	ArrayList<String> clients = new ArrayList<String>();
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            ResultSet res =stmt.executeQuery("select login from client");            
	            while(res.next())
	            	if(!res.getString(1).equals(username))
	            		clients.add(res.getString(1));
	            res =stmt.executeQuery("select id from client where login='"+username+"'");
	            if(res.next())
	            	id = res.getInt(1);
	            res =stmt.executeQuery("select amis from amis where id_client="+id+"");
	            while(res.next())
	        		if(clients.contains(res.getString(1)))
	        			clients.remove(res.getString(1));
	            conn.close();
	            String message="";
	            for(String s : clients)
	            	message+=s+",";
	            return message;
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
			return null;
	    }
	    
	    //pour ajouter une demande d'amis
	    public static void ajouterNotifi(String friend,String username){
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(url, user, passwd);
	            Statement stmt = conn.createStatement();
	            stmt.execute("insert into notification(admin,amis) value('"+username+"','"+friend+"')");
	            conn.close();
	        } catch (Exception e){
	            e.printStackTrace();
	            System.out.println("Erreur");
	        }
	    }
		
	    //pour ajouter un amis
		 public static void ajouter(String friend,String username){
		        try {
		        	int id = 0;
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            stmt.execute("delete from notification where admin='"+friend+"' and amis='"+username+"'");
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id = res.getInt(1);
		            stmt.execute("insert into amis(id_client,amis) value("+id+",'"+friend+"')");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }
		 //pour supprimer un amis
		 public static void supprimer(String friend,String username){
		        try {
		        	int id = 0;
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            if(res.next())
		            	id = res.getInt(1);
		            stmt.execute("delete from amis where id_client="+id+" and amis='"+friend+"'");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }
		 //retourner tous les groupes du user connecté
		 public static String searchGroup(String username){
		        try {
		        	int id=0;
		        	ArrayList<String> groupe = new ArrayList<String>();
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id = res.getInt(1);
		            res =stmt.executeQuery("select nom_groupe from groupe where admin_id="+id+"");
		            while(res.next())
		            	groupe.add(res.getString(1));
		            if(groupe.size()==-1) {
		            	groupe.add("");
		            }
		            conn.close();
		            String message="";
		            for(String s : groupe)
		            	message+=s+",";
		            return message;
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
				return null;
		    }
		 
		 //retourner true si le chat est avec un group sinon false dans le cas du chat entre 2
		 public static String FindGroup(String nom){
		        try {
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select admin_id from groupe where nom_groupe='"+nom+"'");
		            if(!res.next()) {
			            return "grps/false";
		            }
		            conn.close();
		            return "grps/true";
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
				return "false";
		    }
		 
		 //retourner tous les groupes de utilisateur connecté
		 public static String searchGroupes(String username){
		        try {
		        	int id=0;
		        	ArrayList<String> groupe = new ArrayList<String>();
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id = res.getInt(1);
		            res =stmt.executeQuery("select nom_groupe from groupe where admin_id="+id+"");
		            while(res.next())
		            	groupe.add(res.getString(1));
		            if(groupe.size()==-1) {
		            	groupe.add("");
		            }
		            res =stmt.executeQuery("select id_groupe from groupe_users where id_client="+id+"");
		            while(res.next()) {
		            	res =stmt.executeQuery("select nom_groupe from groupe where id_group="+res.getInt(1)+"");
			            if(res.next())
			            	groupe.add(res.getString(1));
		            }
		            conn.close();
		            String message="";
		            for(String s : groupe)
		            	message+=s+",";
		            return message;
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
				return null;
		    }
		 //supprimer la demande d'amis
		 public static void SupprimerNotification(String friend,String username){
		        try {
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            stmt.execute("delete from notification where admin='"+friend+"' and amis='"+username+"'");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }
		 //supprimer le group
		 public static void Supprimer(String nomGroupe,String username){
		        try {
		        	int id = 0;
		        	int id_groupe=0;
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            if(res.next())
		            	id = res.getInt(1);
		            res =stmt.executeQuery("select id_group from groupe where nom_groupe='"+nomGroupe+"' and admin_id="+id+"");
		            if(res.next())
		            	id_groupe = res.getInt(1);
		            stmt.execute("delete from groupe_users where id_groupe="+id_groupe+"");
		            stmt.execute("delete from groupe where nom_groupe='"+nomGroupe+"' and admin_id="+id+"");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }
		 //supprimer amis du group
			public static void modifier(String friend,String nameGroupe,String username){
		        try {
		        	int id_client = 0;
		        	int id_groupe = 0;
		        	int id_admin=0;
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id_admin = res.getInt(1);
		            res =stmt.executeQuery("select id from client where login='"+friend+"'");
		            while(res.next())
		            	id_client = res.getInt(1);
		            res =stmt.executeQuery("select id_group from groupe where nom_groupe='"+nameGroupe+"' and admin_id="+id_admin+"");
		            while(res.next())
		            	id_groupe = res.getInt(1);
		            stmt.execute("delete from groupe_users where id_groupe='"+id_groupe+"' and id_client="+id_client+"");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }
			//retourner la list des amis appartenant au group
			public static String search(String nomGroupe,String username){
		        try {
		        	int id =0;
		        	int id_groupe=0;
		        	int id_client=0;
		        	ArrayList<String> friend = new ArrayList<String>();
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id = res.getInt(1);
		            res =stmt.executeQuery("select id_group from groupe where nom_groupe='"+nomGroupe+"' and admin_id="+id+"");
		            while(res.next())
		            	id_groupe = res.getInt(1);
		            res =stmt.executeQuery("select amis from amis where id_client="+id+"");
		            while(res.next())
		        		friend.add(res.getString(1));
		            if(friend.size()==-1) {
		            	friend.add("");
		            }
		            for(int i=0;i<friend.size();i++) {
		            	 res =stmt.executeQuery("select id from client where login='"+friend.get(i)+"'");
		                 while(res.next())
		                	 id_client = res.getInt(1);
		            	res =stmt.executeQuery("select id_groupe from groupe_users where id_groupe='"+id_groupe+"' and id_client="+id_client+"");
		                if(!res.next())
		                	friend.remove(i);
		            }
		            conn.close();
		            String message="";
		            for(String s : friend)
		            	message+=s+",";
		            return message;
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
				return null;
		    }
			//retourner la list des amis appartenant au group pour la diffusion du messages
			public static String findUsers(String nomGroupe){
		        try {
		        	int id_groupe=0;
		        	int id_client=0;
		        	ArrayList<String> friend = new ArrayList<String>();
		        	ArrayList<Integer> ids = new ArrayList<Integer>();
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id_group from groupe where nom_groupe='"+nomGroupe+"'");
		            while(res.next())
		            	id_groupe = res.getInt(1);
		            res =stmt.executeQuery("select id_client from groupe_users where id_groupe='"+id_groupe+"'");
		            while(res.next()) 
		            	ids.add(res.getInt(1));
		            for(int i=0;i<ids.size();i++) {
		            	res =stmt.executeQuery("select login from client where id="+ids.get(i)+"");
		            	while(res.next())
		                	friend.add(res.getString(1));
		            }
		            res =stmt.executeQuery("select admin_id from groupe where nom_groupe='"+nomGroupe+"'");
		            while(res.next())
		            	id_client = res.getInt(1);
		            res =stmt.executeQuery("select login from client where id="+id_client+"");
		            while(res.next())
		            	friend.add(res.getString(1));
		            conn.close();
		            String message="";
		            for(String s : friend)
		            	message+=s+",";
		            return message;
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
				return null;
		    }
			//ajouter un group
			public static void insert_groupe(String name,String username){
		        try {
		        	int id=-1;
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id = res.getInt(1);
		            stmt.execute("insert into groupe(nom_groupe,admin_id) value('"+name+"',"+id+")");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }
			//retourner la liste des amis pour ajouter un amis au group
			public static String searchAjoute(String nomGroupe,String username){
		        try {
		        	int id =0;
		        	int id_groupe=0;
		        	int id_client=0;
		        	ArrayList<String> friend = new ArrayList<String>();
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id = res.getInt(1);
		            res =stmt.executeQuery("select id_group from groupe where nom_groupe='"+nomGroupe+"' and admin_id="+id+"");
		            while(res.next())
		            	id_groupe = res.getInt(1);
		            res =stmt.executeQuery("select amis from amis where id_client="+id+"");
		            while(res.next())
		        		friend.add(res.getString(1));
		            if(friend.size()==-1) {
		            	friend.add("");
		            }
		            for(int i=0;i<friend.size();i++) {
		            	 res =stmt.executeQuery("select id from client where login='"+friend.get(i)+"'");
		                 while(res.next())
		                	 id_client = res.getInt(1);
		            	res =stmt.executeQuery("select id_groupe from groupe_users where id_groupe='"+id_groupe+"' and id_client="+id_client+"");
		                if(res.next())
		                	friend.remove(i);
		            }
		            conn.close();
		            String message="";
		            for(String s : friend)
		            	message+=s+",";
		            return message;
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
				return null;
		    }
			//ajouter un amis au group
			public static void ajouter(String friend,String nameGroupe,String username){
		        try {
		        	int id_client = 0;
		        	int id_groupe = 0;
		        	int id_admin=0;
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, user, passwd);
		            Statement stmt = conn.createStatement();
		            ResultSet res =stmt.executeQuery("select id from client where login='"+username+"'");
		            while(res.next())
		            	id_admin = res.getInt(1);
		            res =stmt.executeQuery("select id from client where login='"+friend+"'");
		            while(res.next())
		            	id_client = res.getInt(1);
		            res =stmt.executeQuery("select id_group from groupe where nom_groupe='"+nameGroupe+"' and admin_id="+id_admin+"");
		            while(res.next())
		            	id_groupe = res.getInt(1);
		            stmt.execute("insert into groupe_users(id_groupe,id_client) value("+id_groupe+","+id_client+")");
		            conn.close();
		        } catch (Exception e){
		            e.printStackTrace();
		            System.out.println("Erreur");
		        }
		    }

}
