package system.encoding;

import java.io.IOException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class Hmac {

	/**
	 * Calculates HMAC for some data with some key
	 * @param data
	 * @param key
	 * @return
	 * @throws java.security.SignatureException
	 */
	public static String calculateHMAC(String data, String key) throws java.security.SignatureException {
			String result = "";
			String HMAC_SHA256_ALGORITHM = "HmacSHA256";
			
			try {

				// get an hmac_sha1 key from the raw key bytes
				SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
	
				// get an hmac_sha1 Mac instance and initialize with the signing key
				Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
				mac.init(signingKey);
	
				// compute the hmac on input data bytes
				byte[] rawHmac = mac.doFinal(data.getBytes());
				
				// encode hmac to hex
				result = Hex.encodeHexString(rawHmac);

			} catch (Exception e) {
				throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
			}
		return result;
	}
	
	/**
	 * Tests main
	 * @param args
	 * @throws IOException
	 * @throws SignatureException 
	 */
	public static void main (String args[]) throws IOException, SignatureException {
		
		String data = "/v1/events/emit/:{\"event_id\": \"spin\", \"timestamp\": 1396510304, \"user_id\": 999, \"context\": {\"extra\": null, \"win\": 500, \"lines\": 4, \"slot_name\": \"slot_12\", \"spin\": 1, \"bet\": 10}, \"quest_id\": 2}";		
		System.out.println(calculateHMAC(data, "78209e5d60bdbc457cb498828822c64a"));
		
	}
}
