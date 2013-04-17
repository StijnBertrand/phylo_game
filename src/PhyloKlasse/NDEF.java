package PhyloKlasse;

import java.io.IOException;
import java.nio.charset.Charset;

import com.google.gson.Gson;


import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

public class NDEF {
	public static String PHYLOMON_MIME = "phylogame/phylomon";
	public static String EMPTY_BALL_MIME = "phylogame/empty ball";
	public static String NOT_EMPTY = "not empty";
	public static String EMPTY = "empty";
	
	
	
	
	public static IntentFilter getEmptyBallFilter(){		
		try {
			return new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED, EMPTY_BALL_MIME);
		} catch (MalformedMimeTypeException e) {
			return null;
		}	
	}
	
	public static IntentFilter getPhylomonFilter(){
		try {
			return new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED, PHYLOMON_MIME);
		} catch (MalformedMimeTypeException e) {
			return null;
		}	
	}
	
	
	public static void write(NdefMessage message, Tag tag) throws IOException, FormatException {
		Ndef ndef = Ndef.get(tag);
		ndef.connect();
		ndef.writeNdefMessage(message);
		ndef.close();
	}
	
	public static NdefMessage getEmptyPhyloNdef(){
		NdefRecord mimeRecord = NdefRecord.createMime(EMPTY_BALL_MIME, EMPTY.getBytes(Charset.forName("US-ASCII")));
		NdefRecord[] records = new NdefRecord[1];
		records[0] = mimeRecord;
		NdefMessage message = new NdefMessage(records);
		return message;
	}
	
	public static NdefMessage phylomonToNdef(Phylomon phylomon){
		//de phylomon omzetten naar json;
		Gson gson = new Gson();
		String json = gson.toJson(phylomon);
		
		
		NdefRecord mimeRecord = NdefRecord.createMime(PHYLOMON_MIME, NOT_EMPTY.getBytes(Charset.forName("US-ASCII")));
		NdefRecord phyloRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, null, json.getBytes(Charset.forName("US-ASCII")));
		
		NdefRecord[] records = new NdefRecord[2];
		records[0] = mimeRecord;
		records[1] = phyloRecord;
		NdefMessage message = new NdefMessage(records);
		return message;
	}
	
	public static Phylomon ndefToPhylomon(NdefMessage message){
		try{
			Gson gson = new Gson();
			NdefRecord[] records = message.getRecords();
			String valid = new String(records[0].getPayload(),"US-ASCII"); 
			if(valid.equals(EMPTY)){
				return null;
			}else{
				String json = new String(records[1].getPayload(),"US-ASCII");
				return gson.fromJson(json, Phylomon.class);
				
			}
		}catch (Exception e) {
			return null;
		}	
	}

}
