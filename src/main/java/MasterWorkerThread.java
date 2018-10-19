import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MasterWorkerThread extends Thread{
	
	private Socket socket;
	private boolean isAlive;
	private boolean isDone;
	public MasterWorkerThread(Socket socket)
	{
		this.socket = socket;
	}

	
	public void run()
	{
		try {
			
				InputStreamReader IR = new InputStreamReader(this.socket.getInputStream());
				BufferedReader BR = new BufferedReader(IR);
			while(true)
			{	
				String Message = BR.readLine();
				System.out.println(Message);
			
				
				if(Message != null)
				{
					PrintStream PS = new PrintStream(socket.getOutputStream());
					PS.println("Connection made");		
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
