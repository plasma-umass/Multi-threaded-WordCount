import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Worker {

	
	public static void main(String[] args) throws Exception
	{
		Worker worker = new Worker();
    	worker.run();
	}
	
	
	public void run() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		 Socket workerSocket = new Socket("172.30.181.73", 5000);
		 PrintStream PS = new PrintStream(workerSocket.getOutputStream());
		 PS.println("Worker has connected");
		 InputStreamReader IR = new InputStreamReader(workerSocket.getInputStream());
		 BufferedReader BR = new BufferedReader(IR);
		 
		 String message = BR.readLine();
		 System.out.println(message);
		 
		 
	}
	
}
