package system.thread;

import system.http.HttpRequest;

public class SingleThreadGET extends Thread {
	
	int requests;
	String htmlOutput, url;
	long threadTime;
	
	public SingleThreadGET(String url, int requests) {
		this.url = url;
		this.requests = requests;
	}
	    
	/**
	 * Runs thread with circle for N request
	 */
	public void run() {
		threadTime = System.currentTimeMillis();

		for (int i = 0; i < requests; i++) {
			HttpRequest request = new HttpRequest();
			htmlOutput = request.getResponce(url);
			
			System.out.println(htmlOutput);
		}
		
		threadTime = System.currentTimeMillis() - threadTime;
		System.out.println(threadTime);
	}
}
