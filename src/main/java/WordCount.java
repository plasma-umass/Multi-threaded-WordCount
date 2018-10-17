import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.net.*;


public class WordCount implements Master {
	private int workers;
	private String[] inputFiles;
	

	public WordCount(int workerNum, String[] filenames) throws IOException {
    	this.workers = workerNum;
    	this.inputFiles = filenames;
    	

    }

    public void setOutputStream(PrintStream out) {

    }

    public static void main(String[] args) throws Exception {
    	String[] filenames= {"abs"};
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	WordCount wordCount = new WordCount(1, filenames) ;
        wordCount.setOutputStream(new PrintStream(out));
        wordCount.run();
    	
    	

    }

    public void run()
    {    		
		try {
			ServerSocket master = new ServerSocket(5000);			
			Socket socket = master.accept();
			InputStreamReader IR = new InputStreamReader(socket.getInputStream());
			BufferedReader BR = new BufferedReader(IR);
			
			String Message = BR.readLine();
			System.out.println(Message);
			
			if(Message != null)
			{
				PrintStream PS = new PrintStream(socket.getOutputStream());
				PS.println("Connection made");		
			}
			
				
			for (int i = 0; i < this.workers; i++)
			{
					this.createWorker();
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }

    public Collection<Process> getActiveProcess() {
        return new LinkedList<>();
    }

    public void createWorker() throws IOException {
    	String cwd = System.getProperty("user.dir");
    	ProcessBuilder workerProcess = new ProcessBuilder(cwd + "/src/main/Worker.java");
    	workerProcess.start();
    }
}

