// A Java program for a Server 
import java.net.*; 
import java.io.*; 

public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null; 
	private DataOutputStream out	= null; 

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			socket = server.accept(); 
			System.out.println("Client accepted"); 

			// takes input from the client socket 
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 
			out = new DataOutputStream(socket.getOutputStream()); 
			

			String line = "";
			try
				{ 
					//proxy stuff
					line = in.readUTF(); 
					System.out.println("message from proxy:"); 
					System.out.println(line); 

					System.out.println("writing to proxy: 9000"); //success!
					out.writeUTF("9000");

					//mole stuff
					/*
					System.out.println("writing to mole: 00A4040007A000000003101000"); 
					out.writeUTF("00A4040007A000000003101000");

					line = in.readUTF(); 
					System.out.println("message from mole:"); 
					System.out.println(line); 


					System.out.println("writing to mole: Exit"); 
					out.writeUTF("Exit");
					*/

				} 
				catch(IOException i) 
				{ 
					System.out.println(i); 
				} 
			 
			System.out.println("Closing connection"); 

			// close connection 
			socket.close(); 
			in.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5018); 
	} 
} 
