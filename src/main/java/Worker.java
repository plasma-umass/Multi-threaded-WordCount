import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Worker {

	int PID;
	int isActive = 0;
	int isAlive = 1;
	
	public void Worker(int PID)
	{
		this.PID = PID;
	}
	public static void main(String[] args) throws Exception
	{
		System.out.println("In the worker main");
		Worker worker = new Worker();
    	worker.run();
	}
	
	
	public void run() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		 System.out.println("In the worker run");
		 Socket workerSocket = new Socket("localhost", 5000);
		 //172.30.184.119
		 PrintStream PS = new PrintStream(workerSocket.getOutputStream());
		 int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		 PS.println("Worker with PID: " + pid + " is connected");
		 InputStreamReader IR = new InputStreamReader(workerSocket.getInputStream());
		 BufferedReader BR = new BufferedReader(IR);		 
		 String message = BR.readLine();
		 System.out.println(message);
		 
		 while(true)
		 {
			 PS.println("Worker with PID: " + pid + " is connected\n");
			 
			 try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		 
		 
		 
		 
		 
		 
		 
	}
	
}
