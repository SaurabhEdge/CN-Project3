//package project3;




/*
 *
 * @author      Saurabh Shukla
 *
 * Version:  1.0
 *     
 *     
 * RIPencode.java
 
 This class performs the initiation of RIP packet for first time and then dynamic
 calculation of packets.
 
 
 */

import java.util.ArrayList;

class RIPencode {

	byte[] ripPacket;

	public byte[] encodingRIP(String[] ipSplit, int length, int maskingValue, int RoverID) {
		RouterTableGeneration router = new RouterTableGeneration();

		if (router.isEmpty() > 0) {

		//	System.out.println("if");
			ArrayList routerValue = new ArrayList();
			ArrayList costFetch = new ArrayList();
			ArrayList hopFetch = new ArrayList();
			int size = router.checkRouterTable();
			// System.out.println(size);
			ripPacket = new byte[(size * 20) + 4];
			ripPacket[0] = 2;
			ripPacket[1] = 2;
			ripPacket[2] = 0;
			ripPacket[3] = 5;

			for (int i = 0; i < size; i++) {

				// dynamic value of RIP packets
				routerValue = router.showData();
				costFetch = router.showCost();
				hopFetch = router.showhop();

				String pp = (String) routerValue.get(i);
				String tempIP[] = pp.split("\\.");
				String divide[] = tempIP[3].split("\\/");
				String FinalipVal[] = new String[4];
				FinalipVal[0] = tempIP[0];
				FinalipVal[1] = tempIP[1];
				FinalipVal[2] = tempIP[2];
				FinalipVal[3] = divide[0];

				// address family
				ripPacket[4 + 20 * i] = 0;
				ripPacket[5 + 20 * i] = 2;

				// route Tag
				ripPacket[6 + 20 * i] = 0;
				ripPacket[7 + 20 * i] = (byte) RoverID;

				// ip Address field
				ripPacket[8 + 20 * i] = (byte) Integer.parseInt(FinalipVal[0]);
				ripPacket[9 + 20 * i] = (byte) Integer.parseInt(FinalipVal[1]);
				ripPacket[10 + 20 * i] = (byte) Integer.parseInt(FinalipVal[2]);
				ripPacket[11 + 20 * i] = (byte) Integer.parseInt(FinalipVal[3]);

				// subnet mask
				int valuOfSubnet[] = router.subNetMasking(Integer.parseInt(divide[1]));

				ripPacket[12 + 20 * i] = (byte) valuOfSubnet[0];
				ripPacket[13 + 20 * i] = (byte) valuOfSubnet[1];
				ripPacket[14 + 20 * i] = (byte) valuOfSubnet[2];
				ripPacket[15 + 20 * i] = (byte) valuOfSubnet[3];

				// next hop
				ripPacket[16 + 20 * i] = (byte) Integer.parseInt(FinalipVal[0]);
				ripPacket[17 + 20 * i] = (byte) Integer.parseInt(FinalipVal[1]);
				ripPacket[18 + 20 * i] = (byte) RoverID;
				ripPacket[19 + 20 * i] = (byte) Integer.parseInt(FinalipVal[3]);

				// metric
				ripPacket[20 + 20 * i] = 0;
				ripPacket[21 + 20 * i] = 0;
				ripPacket[22 + 20 * i] = 0;
				ripPacket[23 + 20 * i] = 1;

			}

		} else {

		//	System.out.println("else");
			router.createRouterTableFirst(ipSplit, RoverID, 0, maskingValue);
			ripPacket = new byte[24];
			ripPacket[0] = 1;
			ripPacket[1] = 2;
			ripPacket[2] = 0;
			ripPacket[3] = 0;
			ripPacket[4] = 0;
			ripPacket[5] = 2;
			ripPacket[6] = 0;
			ripPacket[7] = (byte) RoverID;

			// ip Address field
			ripPacket[8] = (byte) Integer.parseInt(ipSplit[0]);
			ripPacket[9] = (byte) Integer.parseInt(ipSplit[1]);
			ripPacket[10] = (byte) Integer.parseInt(ipSplit[2]);
			ripPacket[11] = (byte) Integer.parseInt(ipSplit[3]);

			// subnet mask
			int valuOfSubnet[] = router.subNetMasking(maskingValue);

			ripPacket[12] = (byte) valuOfSubnet[0];
			ripPacket[13] = (byte) valuOfSubnet[1];
			ripPacket[14] = (byte) valuOfSubnet[2];
			ripPacket[15] = (byte) valuOfSubnet[3];

			// next hop
			ripPacket[16] = (byte) Integer.parseInt(ipSplit[0]);
			ripPacket[17] = (byte) Integer.parseInt(ipSplit[1]);
			ripPacket[18] = (byte) Integer.parseInt(ipSplit[2]);
			ripPacket[19] = (byte) Integer.parseInt(ipSplit[3]);

			// metric
			ripPacket[20] = 0;
			ripPacket[21] = 0;
			ripPacket[22] = 0;
			ripPacket[23] = 0;

		}

		return ripPacket;

	}
}
