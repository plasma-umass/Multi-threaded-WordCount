import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MapperThread extends Thread {
	private String filename;	
	private AtomicInteger isMapDone;
	
	public MapperThread(String filename, int PID, AtomicInteger isMapDone)
	{
		this.filename = filename;
		this.isMapDone = isMapDone;
	} 
	
	public void run()
	{
//		while(true)
//		{
//		isMapDone.set(1);
//		}
		System.out.println("mapper started");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isMapDone.set(1);
	}
	
	
}
