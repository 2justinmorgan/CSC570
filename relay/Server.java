// A Java program for a Server 
import java.net.*; 
import java.io.*; 
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.lang.String;


public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 proxySocket = null; 
	private ServerSocket proxyServer = null; 
	private Socket		 moleSocket = null; 
	private ServerSocket moleServer = null; 

	private DataInputStream proxyIn	        = null; 
	private DataOutputStream proxyOut	= null; 
	private DataInputStream moleIn 	        = null; 
	private DataOutputStream moleOut	= null; 

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection 
                int proxyPort = 5018;
                int molePort = 5019;
		try
		{ 
			proxyServer = new ServerSocket(proxyPort); 
			moleServer = new ServerSocket(molePort); 
			System.out.println("Servers started"); 

			System.out.println("Waiting for a proxy ..."); 

			proxySocket = proxyServer.accept(); 
			System.out.println("Proxy accepted"); 

			System.out.println("Waiting for a mole ..."); 

			moleSocket = moleServer.accept(); 
			System.out.println("Mole accepted"); 

			// takes input from the client socket 
			proxyIn = new DataInputStream( 
				new BufferedInputStream(proxySocket.getInputStream())); 
			moleIn = new DataInputStream( 
				new BufferedInputStream(moleSocket.getInputStream())); 

			// sends output to the socket 
			proxyOut = new DataOutputStream(proxySocket.getOutputStream()); 
			moleOut = new DataOutputStream(moleSocket.getOutputStream()); 

			String line = ""; 

                        FileWriter fout = new FileWriter("server_log.txt");
                        int count = 0;

			// reads message from client until "Over" is sent 
			while (!line.equals("Over")) 
			{ 
				try
				{ 
                                        /* proxy in */
					line = proxyIn.readUTF(); 
                                        System.out.print("From proxy: ");
					System.out.println(line); 

                                        /* mole out */
                                        System.out.print("Writing to mole: ");
                                        System.out.println(line);
                                        long startTime = System.nanoTime();
                                        moleOut.writeUTF(line);

                                        /* mole in */
					line = moleIn.readUTF(); 
                                        long endTime = System.nanoTime();
                                        long elapsedTime = endTime - startTime;
                                        fout.write(Long.toString(elapsedTime));
                                        fout.write("\n");
                                        if (++count > 15) {
                                            fout.close();
                                            System.exit(0);
                                        }
                                        System.out.print("Elapsed time: ");
                                        System.out.println(elapsedTime);

                                        System.out.print("From mole: ");
					System.out.println(line); 

                                        /* proxy out */
                                        System.out.print("Writing to proxy: ");
                                        System.out.println(line);
                                        proxyOut.writeUTF(line);
                                        /*
                                        if (line.equals("Foo")) {
                                            out.writeUTF("Bar");
                                        }
                                        else {
                                            out.writeUTF("Foo");
                                        }
                                        */

				} 
				catch(IOException i) 
				{ 
					System.out.println(i); 
				} 
			} 
			System.out.println("Closing connection"); 

			// close connection 
			proxySocket.close(); 
			moleSocket.close(); 
			proxyIn.close(); 
			proxyOut.close(); 
			moleIn.close(); 
			moleOut.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
	} 
} 
