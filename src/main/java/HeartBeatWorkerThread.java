import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartBeatWorkerThread extends Thread{

	private Socket workerSocket;
	private AtomicInteger isMapDone;
	private int PID;

	public HeartBeatWorkerThread(Socket workerSocket, AtomicInteger isMapDone, int PID) {
		// TODO Auto-generated constructor stub
		this.workerSocket = workerSocket;
		this.isMapDone = isMapDone;
		this.PID = PID;
	}
	
	public void run()
	{
		try {
			System.out.println(this.workerSocket.isClosed());
			InputStreamReader IR = new InputStreamReader(this.workerSocket.getInputStream());
			BufferedReader BR = new BufferedReader(IR);
			PrintStream PS = new PrintStream(this.workerSocket.getOutputStream());
			while(true)
			{
				
				String heartBeat = BR.readLine();
			 	String arr[] = heartBeat.split(" ");
			 	
		 		if(arr[0].equals("map"))
				{
					System.out.println("start");
					new MapperThread(arr[1], this.PID, this.isMapDone).start();
					PS.println("start");
					
				}
		 		if(heartBeat.equals("heartbeat message") && isMapDone.get() == 0)
				{
					PS.println("heartbeat message from " + this.PID);
				}
				if(heartBeat.equals("heartbeat message") && isMapDone.get() == 1)
				{
					PS.println("status filename");
					isMapDone.set(0);
				}
				if(heartBeat.equals("close"))
				{
					System.out.println("in close condition");
					isMapDone.set(2);
					break;
				}
			 	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
