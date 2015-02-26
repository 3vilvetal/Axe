package system.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class RandomFunctions 
{
	private static Random generator = new Random();

	
	/**
	 * Gets random value from HashMap with key that is parent node
	 * @param hashMap
	 * @param key
	 * @return
	 */
	public String getRandomHashMapValue(HashMap <String, HashMap <String, String>> hashMap, String key)
	{
		String result;
		
		HashMap<String, String> value = (HashMap<String, String>) hashMap.get(key);
		int N = value.size() - 1;
		String [] temp = new String [N];
		
		for (int i = 0; i < N; i++) 
		{
			temp [i] = value.get("value" + (i + 1));
		}
		int random = generator.nextInt(N);
		result = temp [random];
		
		return result;
	}
	
	/**
	 * Gets HashMap with XML parents keys and random values from children
	 * @param hashMap
	 * @return
	 */
	public HashMap<String, String> putValues(HashMap <String, HashMap <String, String>> hashMap)
	{
		HashMap<String, String> temp = new HashMap<String, String>();
		
		Iterator<String> k = hashMap.keySet().iterator();
	    while (k.hasNext()) 
	    {
	      String key = (String) k.next();
	      temp.put(key, getRandomHashMapValue(hashMap, key));
	    }
		return temp;
	}
	
	/**
	 * Gets random keyword from array
	 * @param array
	 * @return
	 */
	public String getRandomArrayValue(String [] array)
	{		
		int random = generator.nextInt(array.length);
		return array[random];
	}
	
	/**
	* Returns random keyword from ArrayList collection
	* @param arrayList is collection, where we take String values
	* @return String random value from collection
	*/
	public String getRandomArrayListValue(ArrayList<String> arrayList)
	{		
		String[] array = new String[arrayList.size()];
		
		for (int i = 0; i < arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		int random = generator.nextInt(array.length);
		
		return array[random];
	}
	
	/**
	* Returns random ArrayList collection from HashMap
	* @param hashMap is HashMap, where we take random ArrayList
	* @return ArrayList<String> random value from HashMap
	*/
	public ArrayList<String> getRandomArrayListValueFromHashMap(HashMap<String, ArrayList<String>> hashMap)
	{	
		int hashMapSize = generator.nextInt(hashMap.size());
		
		Iterator<String> keysIterator = hashMap.keySet().iterator();
		
		String key = null;
		for (int i = 0; i < hashMapSize; i++)
		{
			key = keysIterator.next();
		}
		return hashMap.get(key);
	}

	/**
	 * Generates random string value
	 * @param random
	 * @param characters
	 * @param length
	 * @return
	 */
	public static String generateString(Random random, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(random.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}
