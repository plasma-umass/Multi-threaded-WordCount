import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MasterSocketThread extends Thread{
	
	String[] inputFiles;
	private int intWorkers;
	private ConcurrentHashMap fileMap;
	private ConcurrentHashMap outputMap;
	private AtomicInteger statusFlag; //0->no change, 1->worker stopped, 2->mapping complete
	private int jobFlag = 0; //0 -> map, 1-> reduce
	
	
	public MasterSocketThread(WordCount wordCount)
	{
		this.inputFiles = wordCount.getInputFiles();
		this.intWorkers = wordCount.getWorkers();
		this.fileMap = wordCount.getFileMap();
		this.outputMap = wordCount.getOutputMap();
		this.statusFlag = wordCount.getStatusFlag();
    	
	}

	public void run()
	{
		try {
				int i = 0;
				ServerSocket master = new ServerSocket(5000);
				System.out.println("In the mastersocket thread");
				while(i < intWorkers)
				{
					Socket socket = master.accept();
					new MasterWorkerThread(socket, fileMap, outputMap, jobFlag, statusFlag).start();
					i++;
				}
				
				
				
				while(true)
				{	
					
					Socket socket = master.accept();
					if(fileMap.containsValue(-1))
					{
						
						new MasterWorkerThread(socket, fileMap, outputMap, jobFlag = 0, statusFlag).start();
					}
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("master socket closed");
		}
	}
}
