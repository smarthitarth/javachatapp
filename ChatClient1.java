

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient1 extends Thread{
	
	public ChatClient1(String sname) {
		
		super(sname);
	}
	
	static DataInputStream din;
	static DataOutputStream dout;
	
	public static void readData(DataInputStream din,DataOutputStream dout) throws IOException {
		
		String str = "";
		while(true) {
			try{
			str=din.readUTF();  
			if(str!=null)
				System.out.println(str); 
			}
			catch(Exception e){
				
			}
		}
		
	}
	
	public static void writeData(DataInputStream din,DataOutputStream dout) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		System.out.println("Enter data");
		String str="",str2="";  
		while(!str.equals("EOF")){  
		str=br.readLine();  
		dout.writeUTF(str);  
		dout.flush();  
		  
	}
		
	}
	
	public void run() {
		
		if(Thread.currentThread().getName().equals("readData")) {
			try {
				readData(din,dout);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
		}else {
			try {
				writeData(din,dout);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		
		Socket s=new Socket(args[1],Integer.parseInt(args[2]));  
		din=new DataInputStream(s.getInputStream());  
		dout=new DataOutputStream(s.getOutputStream());  
		
		ChatClient1 c1 = new ChatClient1("readData");
		ChatClient1 c2 = new ChatClient1("writeData");
		
		dout.writeUTF(args[0]);
		c1.start();
		c2.start();
		

	}

		
	
}
