//package project3;

/*
 *
 * @author      Saurabh Shukla
 *
 * Version:  1.0
 *     
 *     
 * UDPReceiver.java
 
 This class performs the initiation of UDP server that will receive RIP packet
 over network
 
 */

import java.io.IOException;
import java.net.DatagramPacket;



import java.net.*;

class UDPReceiver extends Thread {

	int portNumber;
	int roverID;
	//String finalUserIP[];
	int maskingValue;
	String ipSplit[];
	String dividerSplit[];
	String sIP[] = new String[4];
	String dIP[] = new String[4];
	

	public UDPReceiver(String finalUserIP, int portNumber, int roverID, int maskingValue) throws IOException {

		{
			this.ipSplit = finalUserIP.split("\\.");
			this.dividerSplit = ipSplit[3].split("\\/");
			this.sIP[0] = ipSplit[0];
			this.sIP[1] = ipSplit[1];
			this.sIP[2] = ipSplit[2];
			this.sIP[3] = ipSplit[3];
			
			}
			
		this.portNumber = portNumber;
		this.roverID = roverID;
		this.maskingValue = maskingValue;
	}

	public void run() {
		
		MulticastSocket serversocket = null;
 		
		try {
			InetAddress iplocalhost = InetAddress.getByName("224.0.0.1");
			serversocket = new MulticastSocket(52000);
			serversocket.setTimeToLive(5);
			serversocket.joinGroup(iplocalhost);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				byte[] serverData = new byte[504];
				DatagramPacket dpwdsocket = new DatagramPacket(serverData, serverData.length);
				serversocket.receive(dpwdsocket);
				byte[] clientDatapwd = (dpwdsocket.getData());
				DistanceVector dv = new DistanceVector();				
				dv.calculateDistance(clientDatapwd, maskingValue);	
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
