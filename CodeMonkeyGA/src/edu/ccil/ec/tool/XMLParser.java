package edu.ccil.ec.tool;

import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;
import java.util.HashMap;


/**
 * reference : http://www.rgagnon.com/javadetails/java-0573.html
 *
 */
public class XMLParser {

  public static void main(String arg[]) {
     String xmlRecords =
      "<XML>" +
      " <data name='some1' value='some2' >" +
      "   <name>John</name>" +
      "   <value>Manager</value>" +
      " </data>" +
      " <data name='some3' value='some4'>" +
      "   <name>Sara</name>" +
      "   <value>Clerk</value>" +
      " </data>" +
      "</XML>";
     HashMap<String,String> externalData = new HashMap<String,String>();
    try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlRecords));

        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("data");

        // iterate the employees
        for (int i = 0; i < nodes.getLength(); i++) {
           Element element = (Element) nodes.item(i);

           System.out.println(element.getAttribute("name"));
           
           externalData.put(element.getAttribute("name"), element.getAttribute("value"));
           /*
           NodeList name = element.getElementsByTagName("name");
           Element line = (Element) name.item(0);
           System.out.println("Name: " + getCharacterDataFromElement(line));

           NodeList title = element.getElementsByTagName("value");
           line = (Element) title.item(0);
           System.out.println("Title: " + getCharacterDataFromElement(line));
           */
        }
        
        String jsonRecords =
            "{" +
            " \"some5\" : \"some6\", " +
            " \"some7\" : \"some8\"" +            
            "}";
        	jsonRecords = jsonRecords.substring(1,jsonRecords.length()-1).replaceAll("[\"]","");
        	String[] elements = jsonRecords.split("[,]");
        	for (int i=0 ; i <elements.length ; i++) {
        		String[] data = elements[i].split("[:]");
        		externalData.put(data[0].trim(), data[1].trim());
        	}
        
        
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    return;
    /*
    output :
        Name: John
        Title: Manager
        Name: Sara
        Title: Clerk
    */    
    
  }

  public static String getCharacterDataFromElement(Element e) {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
       CharacterData cd = (CharacterData) child;
       return cd.getData();
    }
    return "?";
  }
}
