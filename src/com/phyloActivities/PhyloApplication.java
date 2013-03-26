package com.phyloActivities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import com.google.gson.*;

import PhyloKlasse.Attack;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import PhyloKlasse.XmlParser;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class PhyloApplication extends Application{
	private PhylomonType types[];

	
	//momenteel aan het werken (peroonlijke phylomon)
	//writable private(meaning that only this application can read and write it) file on the android device
	private static String FILE_NAME = "phylomon.json";
	private static int MAX_PHYLOMON = 6;
	private Phylomon[] phylomon;
	
	
	@Override
    public void onCreate() {
        super.onCreate();
        initialiseerDB(); 
        initializePhylomon();
	}
	
	public PhylomonType[] getDatabase(){
		return types;
	}
	
	public Phylomon[] getMyPhylomon(){
		return phylomon;
	}
	
	public int getMaxPhylomon(){
		return MAX_PHYLOMON;
	}
	
	//this method can for example be called after a battle is done to save a win or a lose. 
	public void save(){
		savePhylomon();
	}
	
	
	private void initializePhylomon(){
		Gson gson = new Gson();		
		try{
			FileInputStream fis = openFileInput(FILE_NAME);	
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String curr = null;
			String out = "";
			while((curr = br.readLine())!=null)out += curr;
			
			phylomon = gson.fromJson(out, Phylomon[].class);

		}catch(FileNotFoundException e){
			//this is executed only the first time the application is executed
			phylomon = new Phylomon[MAX_PHYLOMON];
			//dit is tijdelijk
			Phylomon first = new Phylomon(types[0],5);
			Phylomon second = new Phylomon(types[1],7);
			Phylomon third = new Phylomon(types[2],9);
			phylomon[0] = first;
			phylomon[1] = second;
			phylomon[2] = third;
			savePhylomon();
			
		} catch (IOException e) {

		}   	
	}
	
	//this stores the players personal phylomon in the applications private file
	private void savePhylomon(){
		Gson gson = new Gson();
		String json = gson.toJson(phylomon);;
		FileOutputStream fos;
		try {
			fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			fos.write(json.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}
		
	
	//method that initializes the database (the attacks array and the phylomontypes array)
	private void initialiseerDB(){		
		initPhylomonTypes();

	}
	
	//method that initializes the phylomontypes array
	private void initPhylomonTypes(){
		InputStream phylomonStream;
		AssetManager assetManager = getAssets();   
        try {
        	phylomonStream = assetManager.open("creatures.xml");
            this.types = XmlParser.parsePhylomon(phylomonStream);    
        } catch (IOException e) {
        	this.types = new PhylomonType[0];
            Log.e("tag", e.getMessage());
        }  
	}
	
}
