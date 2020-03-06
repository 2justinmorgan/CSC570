package com.example.read;

import android.util.Log;

import java.net.*;
import java.io.*;

public class Mole {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    private DataInputStream in = null;

    // constructor to put ip address and port
    public Mole(String address, int port) {
        // establish a connection
        Log.d("mole", "in mole");
        try {
            socket = new Socket(address, port);
            Log.d("mole", "Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            // takes input from the mole socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
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
            /* Writes to server */
            try {
                out.writeUTF(line);
            }
            catch (IOException i) {
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

