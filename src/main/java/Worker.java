import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker {

	private int PID;
	private AtomicInteger isMapDone = new AtomicInteger(0); //0->busy, 1->idle after map, 2->all mapping done 

	

	public static void main(String[] args) throws Exception
	{
		System.out.println("In the worker main");
		Worker worker = new Worker();
    	worker.run();
	}
	
	
	public void run() throws UnknownHostException, IOException 
	{
		// TODO Auto-generated method stub
		 System.out.println("In the worker run");
		 Socket workerSocket = new Socket("localhost", 5000);
		 //172.30.184.119
		 PrintStream PS = new PrintStream(workerSocket.getOutputStream());
		 this.PID = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		 PS.println("PID " + this.PID);
		 
		 new HeartBeatWorkerThread(workerSocket, isMapDone, this.PID).start();
			
		 
		 while(true)
		 {
			 
			 
			 if(isMapDone.get() == 2)
			 {
				 System.out.println("all mapping done");
				 break;
			 }
			 
		 }
		 workerSocket.close();
	}
		 
	
		 
}
