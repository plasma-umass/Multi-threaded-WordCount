import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.*;


public class WordCount implements Master {
	private int workers;
	private String[] inputFiles;
	private ConcurrentHashMap<String, Integer> fileMap;
	private ConcurrentHashMap<Integer, String> outputMap;
	private AtomicInteger statusFlag = new AtomicInteger(0); //0->no change, 1->worker stopped, 2->mapping complete
	private Map<String, Integer> reducerMap = new TreeMap<String, Integer>();
	
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
	
	public ConcurrentHashMap<Integer, String> getOutputMap() {
		return outputMap;
	}
	
	public WordCount(int workerNum, String[] filenames) throws IOException {
    	this.workers = workerNum;
    	this.inputFiles = filenames;
    	this.fileMap = new ConcurrentHashMap<String, Integer>(inputFiles.length);
		this.outputMap = new ConcurrentHashMap<Integer, String>(workers);
    	for(int i = 0; i<this.inputFiles.length; i++)
    	{
    		this.fileMap.put(this.inputFiles[i], -1);
    	}
    	this.statusFlag.set(0);
    	
    }

    public void setOutputStream(PrintStream out) {

    }

    public static void main(String[] args) throws Exception {
    	String[] filenames= {"C:\\GitHub\\fall-18-project-1-multi-threaded-word-count-sidewinder182\\src\\test\\resources\\random.txt"};
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	
    	WordCount wordCount = new WordCount(1, filenames) ;
        wordCount.setOutputStream(new PrintStream(out));
        wordCount.run();
    	
    	

    }
    
    

    private void reduce() {
		// TODO Auto-generated method stub
    	File dir = new File("C:\\GitHub\\fall-18-project-1-multi-threaded-word-count-sidewinder182\\reduce_output\\WordCount.txt");
		dir.getParentFile().mkdirs();
		if(dir.exists())
		{
			dir.delete();
		}
		dir.createNewFile();    		
		FileWriter fw = new FileWriter(dir, true);
        for(String value : this.outputMap.values())
        {    		
        	BufferedReader br = new BufferedReader(new FileReader(value));
        	String line;
//			System.out.println(filename);
			while((line = br.readLine()) != null)
			{
				String[] words = line.split(" ");
				
				
					if (words[0]!=null)
					{
						if(reducerMap.containsKey(words[0]))
						{
							this.reducerMap.compute(words[0], (k,v)->v+1);
						}
						else
						{
							this.reducerMap.put(words[0], 1);
						}
    					
					}
				
			}
			
        }
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
		
		while(statusFlag.get() != 2)
		{
			
		}
		
		wordCount.reduce();
		
		
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

