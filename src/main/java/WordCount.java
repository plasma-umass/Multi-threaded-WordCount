import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.net.*;


public class WordCount implements Master {
	private int workers;
	Map<Integer, Integer> test = new HashMap<Integer, Integer>();

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
    	WordCount wordCount = new WordCount(2, filenames) ;
        wordCount.setOutputStream(new PrintStream(out));
        wordCount.run();
    	
    	

    }
    
    public int getWorkers() {
		return workers;
	}

    public void run()
    {    					
		MasterSocketThread masterSocketThread = new MasterSocketThread();
		WorkerSocketThread workerSocketThread = new WorkerSocketThread(this);
		masterSocketThread.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		workerSocketThread.start();			
	} 
    	

 

    public Collection<Process> getActiveProcess() {
        return new LinkedList<>();
    }

    public void createWorker() throws IOException {
    	String cwd = System.getProperty("user.dir");
    	String workerDir = cwd + "\\src\\main\\java";
    	ProcessBuilder workerProcessBuilderExec = new ProcessBuilder("java", "Worker");
    	workerProcessBuilderExec.directory(new File(workerDir));
    	Process workerProcessexec = workerProcessBuilderExec.start();
    }
}

