//package project3;



/*
 *
 * @author      Saurabh Shukla
 *
 * Version:  1.0
 *     
 *     
 * DistanceVector.java
 
 This class performs the Distance vector calculation of rover and it's neighbors
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

class DistanceVector {

	 static ArrayList ipData = new ArrayList();
	static ArrayList nextHOP = new ArrayList();
	static ArrayList<Integer> Matrics = new ArrayList<Integer>();	
	 static Map<String, Integer> hm=new HashMap<String, Integer>();
	 static Map<Integer, String> nextDestination=new HashMap<Integer, String>();

	ArrayList ipDatatemp = new ArrayList();
	 ArrayList nextHOPtemp = new ArrayList();
	 ArrayList<Integer> Matricstemp = new ArrayList<Integer>();
	
	
	/*
	 * 
	 * Below method calculates the 10 sec timer behavior.
	 * Value and key is stored in hashmap and checked with router table.
	 * if value exist then don't remove it 
	 * else delete that row from router table.
	 * 
	 */
	
	
	

	/*
	 * 
	 * Below method calculates distance vector algorithm to calculate
	 * cost and hops of it's neighbor.
	 * 
	 * 
	 */
		
	
	public void calculateDistance(byte[] clientDatapwd, int maskingValue) {

		int receiverSize = 0;

//for(int i=0;i<clientDatapwd.length;i++) {
//System.out.print(clientDatapwd[i]+" ");

//}

			for (int i = 0; i < (clientDatapwd.length); i++) {
						//System.out.println(" client "+clientDatapwd[(5 + (20 * i))]);
						if (clientDatapwd[(5 + (20 * i))] == 0) {			
							receiverSize = (5 + (20 * i)) - 1;
							break;
						}

					}					


		int pp[] = new int[clientDatapwd.length];	
		
		for(int i=0;i<clientDatapwd.length;i++) {
			pp[i]=clientDatapwd[i]&0xFF;
			
		}

		int nexTCount[] = new int[(receiverSize/20)];
		int cost[] = new int[(receiverSize/20)];
		String ppp[] = new String[(receiverSize/20)];

		for (int i = 0; i < receiverSize/20; i++) {

			ppp[i] = "" + pp[8 + 20 * i] + "." + pp[9 + 20 * i] + "." + pp[10 + 20 * i] + "." + pp[11 + 20 * i];
			nexTCount[i] = pp[7 + 20 * i];
			cost[i] = pp[23 + 20 * i];

		}
		
//System.out.println("receiverSize "+receiverSize + " ppp size "+ ppp.length);

//for(int i=0;i<ppp.length;i++) {
//System.out.print(ppp[i]+" ");
//System.out.print(nexTCount[i]+" ");
//System.out.print(cost[i]+" ");
//System.out.println();

//}


String sourceIPRover="";
String temps=""+LunarRover.sIP[0]+"."+LunarRover.sIP[1]+"."+LunarRover.sIP[2]+"."+LunarRover.sIP[3]+"/24";
int roverID = LunarRover.roverID;

for(int i=0;i<cost.length;i++){

if(cost[i]==0){
sourceIPRover=ppp[i];
break;
}
}
//System.out.println("++++++++++++++++++++++++++++++++++++++++");
//for(int index = 0; index < ppp.length;index++){
//	System.out.println(" index =  "+ index +" "+ppp[index]+ "   "+" index =  "+ index +" "+cost[index]+"   "+" index =  "+ index +" "+nexTCount[index]);
//}
//System.out.println("++++++++++++++++++++++++++++++++++++++++");
for (int i = 0; i < ppp.length; i++) {
	boolean isIP=false;
	//boolean isHop=false;

	if((ppp[i]+"/24").equals(temps)){
		
		continue;
	}

	for(int index=0;index<ipData.size();index++)
	{

		if(ipData.get(index).equals(ppp[i]+"/24"))
		{
			isIP=true;
			if(cost[i]<16){
				if(Matrics.get(index) == 16 && (hm.get((String)ipData.get(index)) < hm.size()  )){
					Matrics.set(index, cost[i]+1);
					continue;
					
				}
				if(Matrics.get(index) > cost[i]){
						//System.out.println("going to make 1 .1");
			                      // cost[i]=cost[i]+1;
						Matrics.set(index,cost[i]+1);
				}
				 else if(Matrics.get(index) < cost[i] && cost[i] !=16){
					 if(sourceIPRover.equals(nexTCount[i]+"")){
					//	System.out.println("going to make 1 .2");
						//cost[i]=cost[i]+1;
						//Matrics.add(index,cost[i]+1);
					}


				}
				 else if(Matrics.get(index) == cost[i]){
					continue;
				}

			}else{
				Matrics.set(index,16);
				//cost[i]=16;
			}
		}
	}


//ipDatatemp.add(i,ppp[i]+"/24");
//nextHOPtemp.add(i,nexTCount[i]);
//Matricstemp.add(i,cost[i]);

if(!isIP){

ipData.add(i,ppp[i]+"/24");
nextHOP.add(i,nexTCount[i]);
Matrics.add(i,cost[i]+1);

}


}
/*System.out.println("================= Distance vector before timer ====================================	");
//System.out.println("ppp = "+ppp.length);
//System.out.println("cost = "+cost.length);
//System.out.println("nexTCount = "+nexTCount.length);
for(int index = 0;index < ppp.length;index++ ){
	System.out.println("  "+ppp[index]+ "   "+nexTCount[index]+"   "+cost[index]);
}
System.out.println("=================");
timerOffline(ppp,cost,nexTCount);
*/

}





public void timerOffline(String ppp[], int cost[], int nexTCount[]){


String temps=""+LunarRover.sIP[0]+"."+LunarRover.sIP[1]+"."+LunarRover.sIP[2]+"."+LunarRover.sIP[3]+"/24";
		String sourceIpOfPacket = "";
int position=0;
		for(int index=0; index < cost.length;index++){
			if(cost[index]==0){
				sourceIpOfPacket = ppp[index]+"/24";
				position=index;
				break;
			}
		}

if(sourceIpOfPacket.trim().equals("")){
		for(int index = 0;index < ppp.length;index++ ){
	//System.out.println("  "+ppp[index]+ "   "+cost[index]+"   "+nexTCount[index]);
}
		return;
	}

hm.put(sourceIpOfPacket,0);

if(nextDestination.containsKey(20) ) {
	
}
else {
	
	nextDestination.put(nexTCount[position], sourceIpOfPacket);
}


for(int index=0; index < ipData.size();index++){

if(sourceIpOfPacket.equals((String)ipData.get(index))){
//Matrics.set(index,0);
//System.out.println("khud ka mila ");
}
else{

for(int jj=0;jj<ppp.length;jj++){
	
	if(ppp[jj].equals((String)ipData.get(index))){
			if(cost[jj]==Matrics.get(index)){
//System.out.println("if ifif ");

				}
			if(cost[jj]<Matrics.get(index)){
			Matrics.set(index,cost[jj]+1);
//System.out.println("else els e ");

				}
		}
	}
	}//else
}//for

{
//System.out.println("Source IP of packet is "+sourceIpOfPacket);
Iterator iterator = hm.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry pair = (Map.Entry)iterator.next();
				if(pair.getKey().equals(temps)){
					hm.put(sourceIpOfPacket,0);
				}else{
					hm.put((String)pair.getKey(), (Integer)pair.getValue()+1);
					//System.out.println("Incremented");
				}
			}

}// 1st iterator


{
Iterator iterator = hm.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry pair = (Map.Entry)iterator.next();
				//if((Integer)pair.getValue() >=3){
				if((Integer)pair.getValue() > ipData.size()-1){	
					//System.out.println(" >=13" );
			
					//iterator over the routing table to mark all the entries with nexthop as pair.getKey() with cost 16.
					List routingTableRows = ipData;
					//for(int mytable = 0; mytable<routingTableRows.size();mytable++){
					//	Integer aa = Integer.parseInt(nextHOP.get(mytable)+"");
					//	if(aa.toString().equals((String)pair.getKey())){
					//	System.out.println(" >=16" );

					//		String ip = (String)ipData.get(mytable);
							 Matrics.set(ipData.indexOf((String)pair.getKey()),16);
						//}
					//}
				}
			}

}





		

/*System.out.println(" hashmap ");
System.out.println(hm);

*/

}





	public ArrayList showIP() {
		return ipData;
	}

	public ArrayList showHOP() {
		return nextHOP;
	}

	public ArrayList showCost() {
		return Matrics;
	}

}
