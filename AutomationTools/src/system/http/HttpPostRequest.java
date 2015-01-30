package system.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class HttpPostRequest {
	
	HttpClient httpClient = new DefaultHttpClient();
	
	/**
	 * Get post request with JSON inside
	 * @param url
	 * @param json
	 * @return
	 */
	public String getHttpPostResponce(String url, String json) {
		
		String postResponse = null;
		
		try {
	        HttpPost request = new HttpPost(url);
	        StringEntity params = new StringEntity(json);
	        
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        
	        HttpResponse response = httpClient.execute(request);
	        postResponse = org.apache.http.util.EntityUtils.toString(response.getEntity());
	        
	    } catch (Exception ex) {
	    	System.out.println("Some HTTP post exeption...");
	        // handle exception here
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	    }
		
		return postResponse;
	}

}
