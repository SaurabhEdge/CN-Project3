//package project3;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.*;
import java.util.ArrayList;

public class DataReceiving extends Thread{
	
	
	static ArrayList<DatagramPacket> receivedData = new ArrayList<DatagramPacket>();

	public static ArrayList ACK=new ArrayList();
	DatagramSocket serversocket;
	//String sIP[] = new String[4];
	DataReceiving(DatagramSocket datagramSocket,String sIP[]){
		this.serversocket = datagramSocket;
		//this.sIP = sIP;
	}
	public void run() {
	
	 System.out.println("Listening for data on port 60000");
	// DatagramSocket serversocket = null;

		try {
			//InetAddress iplocalhost = InetAddress.getByName("224.0.0.1");
			//serversocket = new DatagramSocket(60000);
			//serversocket.setTimeToLive(5);
			

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	 
	    while(true){
	    	
	    	
	        try{
	        	byte[] serverData = new byte[17];
	        	DatagramPacket dpwdsocket = new DatagramPacket(serverData, serverData.length);
				serversocket.receive(dpwdsocket);
				//System.out.println("data received");
				byte[] clientDatapwd = (dpwdsocket.getData());
				System.out.print(" Packet received as :");
				for(int i=12;i<clientDatapwd.length;i++){
				System.out.print(clientDatapwd[i]);
					}
				System.out.println();
	                
	            if((clientDatapwd[7]&0xff)!=LunarRover.roverID){ // check for valid destination address
	               //System.out.println("forwarding data");
	            	try{
	            		int ip = (clientDatapwd[7]&0xff);
	            		//String destinationIP = getDestinationIP(ip);
	            		
	            		//need to add
	            		String desthop = getNextHop(ip);
	            		
	            		DatagramPacket datagramPacket = new DatagramPacket(clientDatapwd,clientDatapwd.length,InetAddress.getByName(desthop),60000);
	            		serversocket.send(datagramPacket);
	            	
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	
	            }
	            else{

	                if((clientDatapwd[10]&0xff) == 1){ //check if ACK
	              //     DataSending.datasend = true;
//
	                }
	                else{ //check for data received
	                	 System.out.println("DATA received");
				
	                    receivedData.add(dpwdsocket);
	                    //send ACK
	                    DatagramPacket ackPacket = acknowledgement(clientDatapwd);
	                    //System.out.println("ACK sent for sequence number = "+((clientDatapwd[10]&0xff)+1));
	                    serversocket.send(ackPacket);
			    System.out.println(" ACK SENT");
	                   
	                    
	                    if((clientDatapwd[9]&0xff) == 0){
	                        printData();
	                    }

	                }
	        }
	            
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	    
	
	}
	private void printData() {
		for(int index= 0 ; index < receivedData.size();index++) {
			
			for(int jj = 12 ; jj< (receivedData.get(index).getData()[11]&0xff);jj++) {
				System.out.print(receivedData.get(index).getData()[jj]);
			}
			//System.out.print(receivedData.get(index);


			
			
		}
		System.out.print("\n Printed all data in bytes.");
	}
	private String getNextHop(int ip) {
		
		String desthop="255.255.255.255";
		for(int i=0;i<DistanceVector.nextHOP.size();i++) {
			
			if(DistanceVector.nextHOP.get(i).equals(ip)) {
				desthop=(String) DistanceVector.ipData.get(i);
				desthop = desthop.substring(0, desthop.length()-3);
				//System.out.println("desthop is "+desthop);
				return desthop;
				
			}
		}
		
		return desthop;
	}
	private DatagramPacket acknowledgement(byte[] clientDatapwd) throws Exception {
		
		byte b[] = new byte[11];
		//source
		b[0]=(byte)(clientDatapwd[0]);
		b[1]=(byte)(clientDatapwd[1]);
		b[2]=(byte)(clientDatapwd[2]);
		b[3]=(byte)(clientDatapwd[3]);
		//destination
		b[4]=(byte)(clientDatapwd[4]);
		b[5]=(byte)(clientDatapwd[5]);
		b[6]=(byte)(clientDatapwd[6]);
		b[7]=(byte)(LunarRover.roverID);		
		
		b[8] =clientDatapwd[8];
		b[9] = 0;
		b[10] = 1;

		byte temp[] = new byte[4];
		temp[0] = b[4];
		temp[1] = b[5];
		temp[2] = b[6];
		temp[3] = b[7];
		//System.out.println((temp[0]&0xff)+"."+(temp[1]&0xff)+"."+(temp[2]&0xff)+"."+(temp[3]&0xff));
		//String ip = getNextHop(clientDatapwd[7]&0xff);
		DatagramPacket datagramPacket = new DatagramPacket(b,b.length,InetAddress.getByAddress(temp),60000);
		return datagramPacket;
		
	}
	
}
