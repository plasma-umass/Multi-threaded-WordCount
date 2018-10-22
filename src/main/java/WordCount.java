import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.*;


public class WordCount implements Master {
	private int workers;
	private String[] inputFiles;
	private ConcurrentHashMap<String, Integer> fileMap;
	private ConcurrentHashMap<String, String> outputMap;
	private AtomicInteger statusFlag = new AtomicInteger(0); //0->no change, 1->worker stopped, 2->mapping complete

	public AtomicInteger getStatusFlag() {
		return statusFlag;
	}
	public String[] getInputFiles() {
		return inputFiles;
	}
	public int getWorkers() {
		return workers;
	}


	public ConcurrentHashMap<String, Integer> getFileMap() {
		return fileMap;
	}
	
	public ConcurrentHashMap<String, String> getOutputMap() {
		return outputMap;
	}
	
	public WordCount(int workerNum, String[] filenames) throws IOException {
    	this.workers = workerNum;
    	this.inputFiles = filenames;
    	this.fileMap = new ConcurrentHashMap<String, Integer>(inputFiles.length);
		this.outputMap = new ConcurrentHashMap<String, String>(inputFiles.length);
    	for(int i = 0; i<this.inputFiles.length; i++)
    	{
    		this.fileMap.put(this.inputFiles[i], -1);
    	}
    	for(int i = 0; i<this.inputFiles.length; i++)
    	{
    		this.outputMap.put(this.inputFiles[i], "");
    	}
    	this.statusFlag.set(0);
    	
    }

    public void setOutputStream(PrintStream out) {

    }

    public static void main(String[] args) throws Exception {
    	String[] filenames= {"C:\\GitHub\\fall-18-project-1-multi-threaded-word-count-sidewinder182\\bin\\simple.txt"};
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	
    	WordCount wordCount = new WordCount(1, filenames) ;
        wordCount.setOutputStream(new PrintStream(out));
        wordCount.run();
    	
    	

    }
    
    

    public void run()
    {    					
		MasterSocketThread masterSocketThread = new MasterSocketThread(this);
		WorkerSocketThread workerSocketThread = new WorkerSocketThread(this);
		masterSocketThread.start();
		try {
			TimeUnit.SECONDS.sleep(2);
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
    	String workerDir = "C:\\GitHub\\fall-18-project-1-multi-threaded-word-count-sidewinder182\\src\\main\\java";
    	ProcessBuilder workerProcessBuilderExec = new ProcessBuilder("java", "Worker");
    	workerProcessBuilderExec.directory(new File(workerDir));
    	Process workerProcessexec = workerProcessBuilderExec.start();
    }
}

