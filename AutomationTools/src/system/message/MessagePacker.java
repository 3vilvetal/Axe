package system.message;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.msgpack.MessagePack;

public class MessagePacker {
	
	/**
	 * Packs message (hash map) and code it to base64
	 * @param hashMap
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String packMessage (HashMap <String, String> hashMap) {

		//Serialize
		byte[] raw, encodded;
		String encodedPackMessage = null;
		
		try {
			
			raw = MessagePack.pack(hashMap);
			encodded = Base64.encodeBase64(raw);
			encodedPackMessage = new String(encodded);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return encodedPackMessage;
	}
	
	/**
	 * Sample
	 * @param args
	 * @throws IOException
	 * @throws SignatureException
	 */
	public static void main (String args[]) throws IOException, SignatureException {
		
		HashMap <String, String> hashMap = new HashMap <String, String>();
		hashMap.put("test", "hello");
		
		MessagePacker messagePacker = new MessagePacker();
		System.out.println(messagePacker.packMessage(hashMap));
	}
}
