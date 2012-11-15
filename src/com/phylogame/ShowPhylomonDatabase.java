package com.phylogame;


import java.util.ListIterator;



import com.phylogame.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.widget.ArrayAdapter;
import java.util.*; 





public class ShowPhylomonDatabase extends Activity {
	DBM database;
	ListIterator<Phylomon> itt;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.database);
        
        database = new DBM();
        itt = database.getPhylomonIterator();
        
        ListView listView = (ListView) findViewById(R.id.phylomonlist);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
          "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
          "Linux", "OS/2" };

        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        MyAdapter adapter = new MyAdapter(this, database.getArray());
        MySimpleArrayAdapter adapter2 = new MySimpleArrayAdapter(this, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter); 
	}
	

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final String[] values;

  public MySimpleArrayAdapter(Context context, String[] values) {
    super(context, R.layout.rowlayout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.name);

    textView.setText(values[position]);


    return rowView;
  }
} 
	
	public class MyAdapter extends ArrayAdapter<Phylomon>{
		private  ArrayList<Phylomon> phylomons;
		private final Context context;
		
		public MyAdapter(Context context, ArrayList<Phylomon> array) {
			super(context, R.layout.rowlayout, array);
			this.phylomons = array;
			this.context =context;
			phylomons.add(new Phylomon("stijn",5,"rage",10,null));
		}
		
		@Override
		public View getView (int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

			TextView text = (TextView)rowView.findViewById(R.id.name);
			text.setText("stijn");
			ImageView img;
			img.setImageResource(getResources().getIdentifier("com.phylogame:drawable/"+"humpback_whale.jpg",null,null));
			
			
			return rowView;
		}
		
		
		
		
		
		
	}
}
















