package com.phyloActivities;

import java.io.IOException;
import java.io.InputStream;

import PhyloKlasse.PhylomonType;
import PhyloKlasse.XmlParser;
import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

public class PhyloApplication extends Application{
	private PhylomonType database[];
	private boolean init = false; 
	
	public PhylomonType[] getDatabase(){
		if (!init)initialiseerDB();
		return database;
	}
	
	public void initialiseerDB(){		
		InputStream inputStream;
		AssetManager assetManager = getAssets();   
        try {
            inputStream = assetManager.open("creatures.xml");
            this.database = XmlParser.parse(inputStream);    
            this.init = true;
        } catch (IOException e) {
        	this.database = new PhylomonType[0];
            Log.e("tag", e.getMessage());
        }   
	}

}
