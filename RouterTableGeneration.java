//package project3;


/*
 *
 * @author      Saurabh Shukla
 *
 * Version:  1.0
 *     
 *     
 * RouterTableGeneration.java
 
 this class generates router table to be displayed on console.
 Adding value of first packet and all updates on table.
 
 */


import java.util.ArrayList;

class RouterTableGeneration {

	static ArrayList ipValue = new ArrayList();
	static ArrayList nextHop = new ArrayList();
	static ArrayList costList = new ArrayList();
	int size = 0;
	static boolean print=true;
	

	public void createRouterTableFirst(String[] ipSplit, int nexTCount, int cost, int mask) {
		
		
		if(ipValue.size()==0) {
			ipValue.add(ipSplit[0] + "." + ipSplit[1] + "." + ipSplit[2] + "." + ipSplit[3] + "/" + mask);
			nextHop.add(nexTCount);
			costList.add(cost);			

		}
		else {
			
		}

	}

	public void createRouterTableSecond(ArrayList ipData, ArrayList nextHOP2, ArrayList matrics) {

	for (int i = 0; i < ipData.size(); i++) {			
			if(ipValue.contains("0.0.0.0/24") || ipValue.contains(ipData.get(i))) {}
			else {
								
				ipValue.add(i + 1, ipData.get(i));
				nextHop.add(i + 1, nextHOP2.get(i));
				costList.add(i + 1, matrics.get(i));
		
			}
		}
	
	for(int i=0;i<costList.size();i++) {
		String temp=""+costList.get(i);
		if(Integer.parseInt(temp)>=16) {
			costList.remove(temp);
			costList.add(i, "16");
			
		}
		
	}
	
	}

	public int checkRouterTable() {
//System.out.println(ipValue.size());
		return ipValue.size();
	}

	public int isEmpty() {
		if (ipValue.size() != 0) {
			return ipValue.size();
		} else
			return 0;

	}

	public int[] subNetMasking(int mask) {

		String maskingValue = "";
		for (int i = 0; i < 32; i++) {

			if (i != 0 && i % 8 == 0) {
				maskingValue += ".";
			}
			if (i < mask) {
				maskingValue += "1";
			} else
				maskingValue += "0";

		}

		String subnectMaskSplit[] = maskingValue.split("\\.");
		int subnetValue[] = new int[4];
		for (int i = 0; i < subnectMaskSplit.length; i++) {
			subnetValue[i] = Integer.parseInt(subnectMaskSplit[i], 2);
		}

		return subnetValue;
	}

	public ArrayList showData() {

		return ipValue;

	}

	public ArrayList showCost() {

		return costList;

	}

	public ArrayList showhop() {

		return nextHop;

	}
}
