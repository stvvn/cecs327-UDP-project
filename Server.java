import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {

    public static void main(String[] args)
    {
		int port; 
		Scanner scan = new Scanner(System.in);
		DatagramSocket aSocket = null;
		
		// Get port from user
		do {
            System.out.println("Enter a valid port (1-65535):");
            port=scan.nextInt();
        }
        while(port < 1 || port > 65535); // validation

		System.out.print("Listening on port " + port + ". ");
		
        try
        {
            aSocket = new DatagramSocket(port); // new socket bound to port
			//aSocket.setSoTimeout(10000); // set time socket should wait to recieve message
            byte[] buffer = new byte[1000]; // set buffer size
            while(true)
            {
                DatagramPacket request  = new DatagramPacket(buffer,  buffer.length);
                aSocket.receive(request); // recieves message from client
                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),  request.getAddress(),   request.getPort());
                aSocket.send(reply); // echo message to client
            }

        }
        catch (SocketException e)
        {
            System.out.println("Socket:  " + e.getMessage());
        }
        catch (IOException  e)
        {
            System.out.println("IO:  " + e.getMessage());
        }
        finally
        {
            if(aSocket != null) aSocket.close();
        }
    }
}
