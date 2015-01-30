package system.thread;

public class MultipleThreadPOST {
	int threads, requests;
	String url, json;
	
	public MultipleThreadPOST (String url, String json, int threads, int requests) {
		this.url = url;
		this.json = json;
		this.threads = threads;
		this.requests = requests;
	}
	
	/**
	 * Run single thread in a loop
	 */
	public void run() {
		for (int i = 0; i < threads; i++) {
			new SingleThreadPOST(url, json, requests).start();
		}
	}
}

