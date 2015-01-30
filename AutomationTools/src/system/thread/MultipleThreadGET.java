package system.thread;

public class MultipleThreadGET {
	int threads, requests;
	String url;
	
	public MultipleThreadGET(int threads, int requests, String url) {
		this.threads = threads;
		this.requests = requests;
		this.url = url;
	}
	
	/**
	 * Run single thread in a loop
	 */
	public void run() {
		for (int i = 0; i < threads; i++) {
			new SingleThreadGET(url, requests).start();
		}
	}
}

