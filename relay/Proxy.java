// A Java program for a Proxy 
import java.net.*; 
import java.io.*; 
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.lang.String;


public class Proxy 
{ 
	// initialize socket and input output streams 
	private Socket socket		 = null; 
	private DataInputStream input = null; 
	private DataOutputStream out	 = null; 

	private DataInputStream in	 = null; 

	// constructor to put ip address and port 
	public Proxy(String address, int port) 
	{ 
		// establish a connection 
		try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected"); 

			// takes input from terminal 
			input = new DataInputStream(System.in); 

			// sends output to the socket 
			out = new DataOutputStream(socket.getOutputStream()); 

			// takes input from the proxy socket 
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		// string to read message from input 
		String line = ""; 


		// keep reading until "Over" is input 
		while (!line.equals("Over")) 
		{ 
			try
			{ 
                                /* Writes to server */
				line = input.readLine(); 
                                long startTime = System.nanoTime();
				out.writeUTF(line); 

                                /* Response from server */
				line = in.readUTF(); 
                                long endTime = System.nanoTime();
                                long elapsedTime = endTime - startTime;
                                System.out.print("Elapsed time: ");
                                System.out.println(elapsedTime);

                                /* Print */
                                System.out.print("Received back from server: ");
				System.out.println(line); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
		} 

		// close the connection 
		try
		{ 
			input.close(); 
			out.close(); 
			socket.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		//Proxy proxy = new Proxy("10.144.170.207", 5000); 
		Proxy proxy = new Proxy("129.65.128.80", 5018); // CSL
		//Proxy proxy = new Proxy("127.0.0.1", 5000); 
	} 
} 
