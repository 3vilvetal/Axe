package system.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Universal XML parser
 * @author Vitalii Lopushanskyi
 * created 10.30.2013
 */
public class XmlParser {
	
	/**
	 * test sample method
	 * @param args
	 */
	/*
	public static void main(String args[]) {
		
		String xmlPath = "C:/Users/vitl.KYIV/Desktop/Hello.xml";
		
		ReadFile readFile = new ReadFile(); 
		String data = readFile.getString(xmlPath); 
		
		Vector<String> valuesByTag = getValuesByTag("ok", data);
		System.out.println(valuesByTag);
		
		List <?> attributesByTag = getAttributesByTag("link", data);
		System.out.println(attributesByTag);
	}
	*/
	
	public static Vector<Element> xmlElementsArray = new Vector<Element>();
	
	/**
	 * Creates collection with the list of attributes and their values for some tag
	 * @return
	 */
	public List <?> getAttributesByTag(String tag, String xmlPath) {
		
		Element root, child;
		List <?> attributes = null;  
		
		root = getRoot(xmlPath);
		xmlElementsArray.clear();
		getChildren(root);
		
		Iterator<Element> iterator = xmlElementsArray.iterator();
		while(iterator.hasNext()) {
			
			child = iterator.next();
			if (child.getName().equals(tag)) {
				attributes = child.getAttributes();
			}
		}
		
		return attributes;
	}
	
	/**
	 * Creates collection with the list of values for some tag
	 * @return
	 */
	public Vector<String> getValuesByTag(String tag, String xmlPath) {
		
		Vector<String> valuesByTag = new Vector<String>();
		Element root, child;
		
		root = getRoot(xmlPath);
		xmlElementsArray.clear();
		getChildren(root);
		
		Iterator<Element> iterator = xmlElementsArray.iterator();
		while(iterator.hasNext()) {
			
			child = iterator.next();
			if (child.getName().equals(tag)) {
				valuesByTag.add(child.getValue());
			}
		}
		
		return valuesByTag;
	}
	
	/**
	 * Recursive child get for root of Xml document 
	 * @param child
	 */
	public static void getChildren(Element child) {
		
		List<?> children = child.getChildren();
		
		if (children.isEmpty()) {
			xmlElementsArray.add(child);
		}
		else {
			for (int i = 0; i < children.size(); i++) {
				child = (Element)children.get(i);
				getChildren(child);
			}
		}
	}
	
	/**
	 * Get root element
	 * @param xmlPath
	 * @return
	 */
	public static Element getRoot(String data) {
		
         SAXBuilder builder = new SAXBuilder();
         Document document = null;
         try {
             document = builder.build(new ByteArrayInputStream(data.getBytes("UTF-8")));
         } 
         catch (JDOMException e) {
             e.printStackTrace();
         } 
         catch (IOException e) {
             e.printStackTrace();
         }
                  
         Element root = document.getRootElement(); 
         
         return root;
	}
}
