//package project3;


/*
 *
 * @author      Saurabh Shukla
 *
 * Version:  1.0
 *     
 *     
 * UDPClient.java
 
 This class performs the initiation of UDP client that will send RIP packet
 over network
 
 */



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.*;

class UDPClient extends Thread {

	int portNumber;
	int roverID;
	String finalUserIP[];
	int maskingValue;

	public UDPClient(String[] finalUserIP, int portNumber, int roverID, int maskingValue) throws IOException {

		this.finalUserIP = finalUserIP;
		this.portNumber = portNumber;
		this.roverID = roverID;
		this.maskingValue = maskingValue;

	}

	public void run() {
		try{	
		InetAddress iplocalhost = InetAddress.getByName("224.0.0.1");
		MulticastSocket ds = new MulticastSocket(52000);
		ds.setTimeToLive(5);
		ds.joinGroup(iplocalhost);

		while (true) {
			try {
				RIPencode rip = new RIPencode();
				byte dataUDP[] = rip.encodingRIP(finalUserIP, finalUserIP.length, maskingValue, roverID);
				DatagramPacket RoverData = new DatagramPacket(dataUDP, dataUDP.length, iplocalhost, 52000);
				ds.send(RoverData);							

				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}catch(Exception e1){e1.printStackTrace();}
	}

}

