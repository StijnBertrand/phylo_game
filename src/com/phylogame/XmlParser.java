package com.phylogame;

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
	
	public static Phylomon[] parse(InputStream xml){
		Phylomon[] database;
		try 
		{	
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("phylomon");
			database = new Phylomon[nodes.getLength()]; 
			//nu enkel nog de Array van phylomon initialiseren
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Phylomon current = new Phylomon(element);
					database[i] = current;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			database = new Phylomon[0];
		}
		return database;
	}
	
	public static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}	

