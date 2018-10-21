import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
	private AtomicInteger i = new AtomicInteger(10);
	
	public AtomicInteger get()
	{
		return this.i;
	}
	
	public void set(int k)
	{
		this.i.set(k);
	}
	
	public static void main(String[] args)
	{
		Test test = new Test();
		
		new TestThread(test.get()).start();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(test.get().get());
		
	}
}

