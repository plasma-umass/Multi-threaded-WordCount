import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WorkerSocketThread extends Thread{
	
	private int workers;
	private WordCount wordCount;
	
	public WorkerSocketThread(WordCount wordCount) {
		// TODO Auto-generated constructor stub
		this.workers = wordCount.getWorkers();
		this.wordCount = wordCount;
	}
	
	public void run()
	{
		System.out.println("In the workersocket thread");
		try {
				ProcessBuilder compileBuilder = new ProcessBuilder("javac", "Worker.java");
				String cwd = System.getProperty("user.dir");
				String workerDir = cwd + "\\src\\main\\java";
				compileBuilder.directory(new File(workerDir));
				Process compile = compileBuilder.start();
					for (int i = 0; i < this.workers; i++)
					{
					
						this.wordCount.createWorker();
		
					}	
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
