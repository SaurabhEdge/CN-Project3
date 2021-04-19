//package project3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.*;
import java.util.ArrayList;

public class DataSending extends Thread{

	int portNumber;
	int roverID;
	String filepath="";
	int maskingValue;
	String sourceIP[];
	String destIP[];
	static int duplicate = 0;
	static ArrayList<DatagramPacket> data=new ArrayList<DatagramPacket>();
	public static int seqnumber=0;
	DatagramSocket ds;
	public static int dividingSize=5;	

	public DataSending(String filePath, String[] sourceAdd, String[] destAdd,DatagramSocket datagramSocket) throws IOException {

		this.filepath = filePath;
		this.sourceIP=sourceAdd;
		this.destIP=destAdd;
		this.ds = datagramSocket;
		
		}
	
	public void run(){
		try{
		DataFromFile();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void DataFromFile() throws Exception {
	
		System.out.println("Data Sending Started ......");
		System.out.println();
		//int dividingSize=1;		
		File file=new File(filepath);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[(int) (file.length())];	
		fis.read(buffer);
		byte[] bitSet=new byte[12];
		bitSet[0]=(byte)Integer.parseInt(sourceIP[0]);
		bitSet[1]=(byte)Integer.parseInt(sourceIP[1]);
		bitSet[2]=(byte)Integer.parseInt(sourceIP[2]);
		bitSet[3]=(byte)Integer.parseInt(sourceIP[3]);
		bitSet[4]=(byte)Integer.parseInt(destIP[0]);
		bitSet[5]=(byte)Integer.parseInt(destIP[1]);
		bitSet[6]=(byte)Integer.parseInt(destIP[2]);
		bitSet[7]=(byte)Integer.parseInt(destIP[3]);
		//sequence number
		bitSet[8]=10;
		// truncate bit
		bitSet[9]=0;
		//ack bit
		bitSet[10]=0;
		bitSet[11]=0;
		
		String sender=destIP[0]+"."+destIP[1]+"."+destIP[2]+"."+destIP[3];
		InetAddress iplocalhost = InetAddress.getByName(sender);

		int counter=0;
		
		int packetlength = 5;
		int datalength = packetlength - 12;
		if(file.length() <= datalength){
			byte[] FinalData=new byte[(int) file.length()];
			FinalData[0]=bitSet[0];
			FinalData[1]=bitSet[1];
			FinalData[2]=bitSet[2];
			FinalData[3]=bitSet[3];
			FinalData[4]=bitSet[4];
			FinalData[5]=bitSet[5];
			FinalData[6]=bitSet[6];
			FinalData[7]=bitSet[7];
			FinalData[8]=bitSet[8];
			FinalData[9]=0;//bitSet[9];
			FinalData[10]=0;//bitSet[10];	
			FinalData[11]=(byte)file.length();
			for(int j=12;j<FinalData.length;j++) {
				
				FinalData[j]=buffer[counter];
				counter++;
				
			}
			String ip = getNextHop(FinalData[7]&0xff);
			DatagramPacket singlePacket = new DatagramPacket(FinalData,FinalData.length,InetAddress.getByName(ip),60000);
			ds.send(singlePacket);


		System.out.println(" ACK received for packet : "+(seqnumber));
		}
		else{
			//divided
			//System.out.println("DIVIDED");		
			for(int i=0;i<(file.length()/dividingSize);i++) {
				byte[] FinalData=new byte[(int) (dividingSize+11)];
				bitSet[8]=(byte) (seqnumber+i);
				FinalData[0]=bitSet[0];
				FinalData[1]=bitSet[1];
				FinalData[2]=bitSet[2];
				FinalData[3]=bitSet[3];
				FinalData[4]=bitSet[4];
				FinalData[5]=bitSet[5];
				FinalData[6]=bitSet[6];
				FinalData[7]=bitSet[7];
				FinalData[8]=bitSet[8];
				FinalData[9]=1;//bitSet[9];
				FinalData[10]=0;//bitSet[10];	
				FinalData[11]=(byte)5;
				for(int j=12;j<FinalData.length;j++) {
					
					FinalData[j]=buffer[counter];
					counter++;
					
				}
				String ip = getNextHop(FinalData[7]&0xff);
				DatagramPacket RoverData = new DatagramPacket(FinalData, FinalData.length, InetAddress.getByName(ip), 60000);
				//data.add(RoverData);
				ds.send(RoverData);	
				System.out.println("data sent");			
				Thread.sleep(10000);
				//System.out.println("seqnumber+i  "+(seqnumber+i));
				
				System.out.println(" ACK received for packet : "+(seqnumber));
				seqnumber++;
						
			}
			
			
			if(counter!=(file.length()-1)) {
				//System.out.println("data sent yyy");

				int modulus = (int) file.length() % datalength;
				int leftover = (int)file.length() - (int)modulus;
				
					byte[] FinalData=new byte[(int) (modulus+11)];
					bitSet[8]=(byte) (0);
					FinalData[0]=bitSet[0];
					FinalData[1]=bitSet[1];
					FinalData[2]=bitSet[2];
					FinalData[3]=bitSet[3];
					FinalData[4]=bitSet[4];
					FinalData[5]=bitSet[5];
					FinalData[6]=bitSet[6];
					FinalData[7]=bitSet[7];
					FinalData[8]=bitSet[8];
					FinalData[9]=0;//bitSet[9];
					FinalData[10]=0;///bitSet[10];	
					FinalData[11]=(byte)modulus;
					for(int j=12;j<modulus;j++) {
						
						FinalData[j]=buffer[counter];
						counter++;
						
					}
					//System.out.println("printing");
					for(int j=0;j<FinalData.length;j++) {
						
						//FinalData[j]=buffer[counter];
						//System.out.print(FinalData[j]);	
						
					}
					String ip = getNextHop(FinalData[7]&0xff);
					DatagramPacket RoverData = new DatagramPacket(FinalData, FinalData.length, InetAddress.getByName(ip), 60000);
					//data.add(RoverData);
					ds.send(RoverData);
					//System.out.println("data sent");				
					Thread.sleep(10000);
					
					
					System.out.println(" ACK received for packet : "+(seqnumber));
					seqnumber++;
							
				
				
			}
		}
			
				
	}
	private String getNextHop(int ip) {
		
		String desthop="";
		for(int i=0;i<DistanceVector.nextHOP.size();i++) {
			
			if(DistanceVector.nextHOP.get(i).equals(ip)) {
				desthop=(String) DistanceVector.ipData.get(i);
				desthop = desthop.substring(0, desthop.length()-3);
				//System.out.println("desthop is "+desthop);
				return desthop;
				
			}
		}
		desthop = "255.255.255.255";
		return desthop;
	}
	
	
}
