package com.phyloActivities;




import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;



import com.phylogame.R;

import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.widget.ArrayAdapter;
import java.util.*; 
import android.widget.AdapterView.OnItemClickListener;






public class ShowPhylomonDatabase extends Activity implements OnClickListener, OnItemClickListener {
	PhyloApplication app;
	ListView listView;
	Button back;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.database);
         app = ((PhyloApplication)getApplicationContext());
        
        
        
        listView = (ListView) findViewById(R.id.phylomonlist);
        back = (Button) findViewById(R.id.back);

        //make an adapter for the ListView
        MyAdapter adapter = new MyAdapter(this, app.getDatabase());


        // Assign adapter to ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        // assign listener to button
        back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		this.onBackPressed();
		
	}
	

	//this is the adapter that will govern what the viewlist will display
	private class MyAdapter extends ArrayAdapter<PhylomonType>{
		private  PhylomonType[] phylomons;
		private final Context context;
		
		public MyAdapter(Context context, PhylomonType[] array) {
			super(context, R.layout.rowlayout, array);
			this.phylomons = array;
			this.context =context;
		}

		
		@Override
		public View getView (int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

			TextView text = (TextView)rowView.findViewById(R.id.name);
			text.setText(phylomons[position].getName());
			
			return rowView;
		}		
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		Intent intent = new Intent(this ,ShowPhylomon.class);
		intent.putExtra("position",Integer.toString(position) );
		this.startActivity(intent);
		
	}
	
}





//ImageView img;
//img.setImageResource(getResources().getIdentifier("com.phylogame:drawable/"+"humpback_whale.jpg",null,null));










