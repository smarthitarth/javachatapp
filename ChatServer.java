





import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

	public static Map<Socket,String> connectedClients = new HashMap<>();
	
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket ss=new ServerSocket(Integer.parseInt(args[0]));  
		
		
		
		 
         
        while (true)  
        {
            
           
              
            try 
            { 
                 
               Socket s = ss.accept(); 
                
                System.out.println("A new client is connected : " + s); 
                
                 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                connectedClients.put(s,dis.readUTF());
                
                
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
            	e.printStackTrace(); 
               
                
            } 
            finally {
            	
            }
        } 

		
	}

}

class ClientHandler extends Thread  
{ 
    
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run()  
    { 	System.out.println("Thread Started");
        String received;  
        String addClientname;
        while (true)  
        { 
            try { 
                
                received = dis.readUTF(); 
                addClientname = ChatServer.connectedClients.get(s)+" : "+received;
                System.out.println("Received data from client");
                
                for(Map.Entry<Socket, String> client : ChatServer.connectedClients.entrySet()) {
                	try {
                	DataOutputStream dos_client = new DataOutputStream(client.getKey().getOutputStream());
                	dos_client.writeUTF(addClientname);
                	}catch(IOException e) {
                		ChatServer.connectedClients.remove(client.getKey());
                		e.printStackTrace();
                	}
                }
                
                if(received.equals("EOF")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                
               
                
            } catch (IOException e) {
            	ChatServer.connectedClients.remove(s);
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 
 
