package system.thread;

import system.http.HttpPostRequest;

public class SingleThreadPOST extends Thread {

	int requests;
	String url, json;
	long threadTime, responceTime;
		
	public SingleThreadPOST(String url, String json, int requests) {
		this.url = url;
		this.json = json;
		this.requests = requests;
	}
		    
	/**
	 * Runs thread with circle for N request
	 */
	public void run() {
		threadTime = System.currentTimeMillis();
		
		for (int i = 0; i < requests; i++) {
			
			responceTime = System.currentTimeMillis();
			
			HttpPostRequest httpPostRequest	= new HttpPostRequest();
			String response = httpPostRequest.getHttpPostResponce(url, json);
											
			responceTime =  System.currentTimeMillis() - responceTime;
			
			System.out.println(responceTime);
			System.out.println(response);
		}
		threadTime = System.currentTimeMillis() - threadTime;
		System.out.println("Thread time: " + threadTime);
	}
}

