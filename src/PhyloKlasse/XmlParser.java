package PhyloKlasse;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.res.AssetManager;


public  class XmlParser {
	private static String path = "/creatures.xml";
	
	public static PhylomonType[] parsePhylomon(InputStream xml){
		PhylomonType[] database;
		try 
		{	
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("phylomon");
			database = new PhylomonType[nodes.getLength()]; 
			//nu enkel nog de Array van phylomon initialiseren
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					PhylomonType current = new PhylomonType(element);
					database[i] = current;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			database = new PhylomonType[0];
		}
		return database;
	}
	
	public static Attack[] parseAttacks(InputStream attackStream) {
		Attack[] attacks;
		
		try 
		{	
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(attackStream);
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("attack");
			attacks = new Attack[nodes.getLength()]; 
			//nu enkel nog de Array van phylomon initialiseren
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Attack current = new Attack(element);
					attacks[i] = current;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			attacks = new Attack[0];
		}
		return attacks;
	}
	
	
	
	public static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	
}	

