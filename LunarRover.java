

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Saurabh Shukla RIT
 *
 */
public class LunarRover {
	String ipAddress;
	int portNumber;
	public static int roverID;
	String ipSplit[];
	String dividerSplit[];
	public static String sIP[] = new String[4];
	public static String dIP[] = new String[4];
	int maskingValue;
	public static String filePath = "";

	LunarRover(String Source, int port, int rover, String destination, String file) {
		{
			this.ipSplit = Source.split("\\.");
			this.dividerSplit = ipSplit[3].split("\\/");
			this.sIP[0] = ipSplit[0];
			this.sIP[1] = ipSplit[1];
			this.sIP[2] = ipSplit[2];
			this.sIP[3] = ipSplit[3];

		}

		{
			this.ipSplit = destination.split("\\.");
			this.dividerSplit = ipSplit[3].split("\\/");
			this.dIP[0] = ipSplit[0];
			this.dIP[1] = ipSplit[1];
			this.dIP[2] = ipSplit[2];
			this.dIP[3] = ipSplit[3];

		}
		this.maskingValue = 24;
		this.portNumber = port;
		this.roverID = rover;
		this.filePath = file;

	}


// BOT


	LunarRover(String Source, int port, int rover) {
		{
			this.ipSplit = Source.split("\\.");
			this.dividerSplit = ipSplit[3].split("\\/");
			this.sIP[0] = ipSplit[0];
			this.sIP[1] = ipSplit[1];
			this.sIP[2] = ipSplit[2];
			this.sIP[3] = ipSplit[3];

		}

		
		this.maskingValue = 24;
		this.portNumber = port;
		this.roverID = rover;
		

	}



	public static void main(String[] args)  {
		try{
		DatagramSocket datagramSocket = new DatagramSocket(60000);
		if (args.length > 3) {

			System.out.println(" LunarRover with no destination IP (Active rover)");
			System.out.println();
			System.out.println(" Source address : " + args[0]);
			System.out.println(" Port number : " + args[1]);
			System.out.println(" LunarRover-ID : " + args[2]);
			System.out.println(" Destination : " + args[3]);
			System.out.println(" File name : " + args[4]);
			System.out
					.println("--------------------------------------------------------------------------------------");

			LunarRover lunarRover = new LunarRover(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]),
					args[3], args[4]);
			{
				Thread sender = new Thread(
						new UDPClient(lunarRover.sIP, lunarRover.portNumber, lunarRover.roverID, 24));
				sender.setName("sender");
				sender.start();
			}
			{
				Thread receiver = new Thread(new UDPReceiver(args[0], lunarRover.portNumber, lunarRover.roverID, 24));
				receiver.setName("receiver");
				receiver.start();
				Thread.sleep(10000);
			}
			
			
			
			// Data Receive
						{

							Thread data_receiver = new Thread(new DataReceiving(datagramSocket,sIP));
							data_receiver.setName("data receiver");
							data_receiver.start();
						}
						
						
			// Data send

			{
				Thread data_sender = new Thread(new DataSending(filePath,lunarRover.sIP,lunarRover.dIP,datagramSocket));
				data_sender.setName("data sender");
				data_sender.start();
			}

			

		}

		else {

			System.out.println(" LunarRover with no destination IP (Bot rover)");
			System.out.println();
			System.out.println(" Source address : " + args[0]);
			System.out.println(" Port number : " + args[1]);
			System.out.println(" LunarRover-ID : " + args[2]);
			
			System.out.println("--------------------------------------------------------------------------------------");

			LunarRover lunarRover = new LunarRover(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2])
					);
			{
				Thread sender = new Thread(
						new UDPClient(lunarRover.sIP, lunarRover.portNumber, lunarRover.roverID, 24));
				sender.setName("sender");
				sender.start();
			}
			{
				Thread receiver = new Thread(new UDPReceiver(args[0], lunarRover.portNumber, lunarRover.roverID, 24));
				receiver.setName("receiver");
				receiver.start();
				Thread.sleep(10000);
			}
			
			//data receiving and sending


			// Data Receive
						{

							Thread data_receiver = new Thread(new DataReceiving(datagramSocket,sIP));
							data_receiver.setName("data receiver");
							data_receiver.start();
						}

		}
	   }catch(Exception e){
		e.printStackTrace();
	    }

	}

}
