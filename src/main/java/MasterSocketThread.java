import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterSocketThread extends Thread{

	public void run()
	{
		try {
			ServerSocket master = new ServerSocket(5000);
			System.out.println("In the mastersocket thread");
			while(true)
			{
				Socket socket = master.accept();
				new MasterWorkerThread(socket).start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
