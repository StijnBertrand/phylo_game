package com.phylogame;

import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

public class MyApplication extends Application{
	private Phylomon database[];
	private boolean init = false; 
	
	public Phylomon[] getDatabase(){
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
        	this.database = new Phylomon[0];
            Log.e("tag", e.getMessage());
        }   
	}

}
