To compile project --> javac *.java
To run project --> java LunarRover [Source IP] [portnumber=52000] [Rover-id] [Destination ip] [filename]


Assumption : 

1) I have assumed no rover will be down in between of data and ACK sending.
2) if command line argument is 3 means it is bot rover/Destination rover
 ==> LunarRover 129.32.12.23 52000 1

3) If command line is greater than 3 then it is Sender
4) Before sending of data, thread goes for 5 sec sleep just to make sure 
   Router table is generated properly.

5) At receiver end when it will be up, it will display few information
   Regarding rover and then start listening.
6) At sender side, only 5 byte data will be send from sender to Rx.
7) Packet structure is : Source(4byte) Destination(4byte) sequence number(1byte) truncate bit(1 byte) 
ack bit( 1 byte) Reserved(1byte)

8) Every packet is displayed once it recieved by receiver.
 Starting 12 byte will be packet header then Data.

9) Once data is send then Rover will be down.
10) if rover is down in between then self ACK will be received with no data.

