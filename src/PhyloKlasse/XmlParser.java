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
	private static Attack[] attacks;
	
	public static PhylomonType[] getDatabase(InputStream typesStream,InputStream attackStream){
		parseAttacksDatabase(attackStream);
		PhylomonType[] database;
		try 
		{	
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(typesStream);
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("phylomon");
			database = new PhylomonType[nodes.getLength()]; 
			//nu enkel nog de Array van phylomon initialiseren
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					PhylomonType current = parseType(element);
					database[i] = current;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			database = new PhylomonType[0];
		}
		return database;
	}
	

	
	private static PhylomonType parseType(Element element){
		
		String name = getValue("Name", element);
		int HP = Integer.valueOf(getValue("HP", element));
		int experienceGroup = Integer.valueOf(getValue("experienceGroup", element));
		int baseXp = Integer.valueOf(getValue("baseXp", element));
		
		
		Element attacksElement = (Element)element.getElementsByTagName("attacks").item(0);
		Attack[] attacks = parseAttacks(attacksElement);
				//attacks[Integer.valueOf(XmlParser.getValue("attack", element))];
		
		
		
		
		String picLocatie = XmlParser.getValue("picLocatie", element);
		return new PhylomonType(name,HP,experienceGroup,baseXp,attacks,picLocatie);
	}
	
	private static Attack[] parseAttacks(Element element){
		
		NodeList nodes = element.getElementsByTagName("attack");
		Attack[] attacks = new Attack[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				attacks[i] = parsePhylomonAttack((Element) node);
			}
		}
		return attacks;
	}
	
	private static Attack parsePhylomonAttack(Element element){
		int attackId = Integer.valueOf(XmlParser.getValue("id", element));
		return attacks[attackId];
	}
	
	
	private static void parseAttacksDatabase(InputStream attackStream) {
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
					attacks[i] = parseDatabaseAttack(element);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			attacks = null;
		}
	}
	
	//wordt gebruikt voor parseAttacksDatabase
	private static Attack parseDatabaseAttack(Element element){
		String name = XmlParser.getValue("name", element);
		int damage = Integer.valueOf(XmlParser.getValue("damage", element));
		return new Attack(name,damage);
	}
	
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	
}	

