import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
	// helper function to validate ip addresses
    public static boolean IPisValid(String IP){
        boolean validip = true;
        String[] ipArray = IP.split("\\.", 0); // splits the string on .
	
		// Makes sure ip is 4 blocks of numbers between 0-255
        if(ipArray.length == 4) {
            for (String i : ipArray) {
                int j = Integer.parseInt(i);

                if (j < 0 || j > 255) {
                    validip = false;
                }
            }
        }
        else{
            validip = false;
        }


        return validip;
    }

    public static void main(String[] args)
    {
        // args give message contents and server hostname
        DatagramSocket aSocket=null;
        String ip="";
        int port;
        String message="";
        boolean ipB;
        boolean messageB;
        Scanner scan =new Scanner(System.in);

        do {
            System.out.println("Enter an IP address:");
            ip=scan.nextLine();
        }
        while(!IPisValid(ip)); // loops until valid ip is recieved

        do {
            System.out.println("Enter a valid port (1-65535):");
            port=scan.nextInt();
        }
        while(port < 1 || port > 65535); // loops until valid port is recieved

        do {
            scan=new Scanner(System.in);
            System.out.println("Enter a message:");
            message=scan.nextLine();
            messageB=message.isEmpty();
        }
        while(messageB);	// loops until message is input

        try
        {
            try
            {
                aSocket = new DatagramSocket();	// create new socket
                byte [] m = message.getBytes();
                InetAddress aHost = InetAddress.getByName(ip); // sets ip by converting from string
                int serverPort = port;
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort); // new packet
                aSocket.send(request); // sends message packet through socket
                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(reply); // recieves and prints echoed message
                System.out.println("Reply: " + new String(reply.getData())); 

            }
            catch(UnknownHostException fd)
            {
                System.out.println("The IP was entered incorrectly!");
            }
        }
        catch (SocketException e)
        {
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("IO: " + e.getMessage());
        }
        finally
        {
            if(aSocket != null) aSocket.close();
        }
    }
}
