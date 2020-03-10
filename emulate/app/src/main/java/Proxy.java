import java.net.*;
import java.io.*;

public class Proxy {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    private DataInputStream in = null;

    // constructor to put ip address and port
    public Proxy(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            // takes input from the proxy socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
    public String getData() {
        String line = "";
        try {
            /* Response from server */
            line = in.readUTF();
        } catch (IOException i) {
            System.out.println(i);
        }
        return line;
    }

    public void sendData(String line) {

        try {
            /* Writes to server */
            out.writeUTF(line);

        } catch (IOException i) {
            System.out.println(i);
        }
    }


    public void close() {
        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}