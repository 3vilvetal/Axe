package system.attlasian;

import java.util.List;
import java.util.Vector;

import system.http.HttpRequest;
import system.xml.XmlParser;

public class Bamboo {
	
	public static String userName = "",
			 			 password = "";
	
	/**
	 * Sample of BAMBOO API
	 * @param args
	 */
	public static void main(String args[]) {
		 
		String bambooAuthenticationUri = "https://adfsolutions.jira.com/builds/rest/api/latest/plan?os_authType=basic&os_username=" + userName + "&os_password=" + password,
				    baambooFailedTests = "https://adfsolutions.jira.com/builds/rest/api/latest/result/TG-DIS/latest?expand=testResults.failed&os_authType=basic&os_username=vlopushanskyi&os_password=vL$*(901f";
		
		HttpRequest request = new HttpRequest();
		String responce = request.getResponce(bambooAuthenticationUri);
		System.out.println(responce);
		
		responce = request.getResponce(baambooFailedTests);
		System.out.println(responce);
		
		XmlParser xmlParser = new XmlParser();
		
		Vector<String> valuesByTag = xmlParser.getValuesByTag("planName", responce);
		System.out.println(valuesByTag);
		
		valuesByTag = xmlParser.getValuesByTag("successfulTestCount", responce);
		System.out.println(valuesByTag);
		
		valuesByTag = xmlParser.getValuesByTag("failedTestCount", responce);
		System.out.println(valuesByTag);
		
		valuesByTag = xmlParser.getValuesByTag("quarantinedTestCount", responce);
		System.out.println(valuesByTag);
		
		List <?> attributesByTag = xmlParser.getAttributesByTag("link", responce);
		System.out.println(attributesByTag);
	}
}
