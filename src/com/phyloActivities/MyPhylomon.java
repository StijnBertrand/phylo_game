package com.phyloActivities;

import java.util.ArrayList;

import com.phylogame.R;

import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyPhylomon extends Activity implements OnClickListener {
	PhyloApplication app;
	ListView listView;
	Button back;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.myphylomon);
        app = ((PhyloApplication)getApplicationContext());
        
        
        
        listView = (ListView) findViewById(R.id.phylomonlist);
        back = (Button) findViewById(R.id.back);

        //make an adapter for the ListView
        MyAdapter adapter = new MyAdapter(this,	app.getMyPhylomon());


        // Assign adapter to ListView
        listView.setAdapter(adapter);
        if(getIntent().getIntExtra("activityId",-1)== 1){
        	listView.setOnItemClickListener(new OnItemClickListenerForBattle());
        }
        
        // assign listener to button
        back.setOnClickListener(this);
	}
	
	
	
	
	
	//this is the adapter that will govern what the viewlist will display
	private class MyAdapter extends ArrayAdapter<Phylomon>{
		private  Phylomon[] phylomon;
		private final Context context;
		
		public MyAdapter(Context context, Phylomon[] array) {
			super(context, R.layout.phylomonrow, array);
			this.phylomon = array;
			this.context =context;
		}
		
		public Phylomon[] getItems(){
			return phylomon;
		}

		
		@Override
		public View getView (int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.phylomonrow, parent, false);
			
			Phylomon curr = phylomon[position];
			if(curr != null){
				TextView name = (TextView)rowView.findViewById(R.id.name);
				TextView health = (TextView)rowView.findViewById(R.id.health);
				name.setText(curr.getName());
				health.setText(curr.getHP() + "/" + curr.getMaxHp());
			}else{
				TextView text = (TextView)rowView.findViewById(R.id.name);
				text.setText(".........................");	
			}
			return rowView; 
		}		
	}
	
	private class OnItemClickListenerForBattle implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			 Intent intent=new Intent();
			 Phylomon item = app.getMyPhylomon()[position];
			 if(item != null){
				 if(!item.dead()){
					 intent.putExtra("next", position);
			         setResult(RESULT_OK, intent);
			         finish(); 
				 }	 
			 }	
		}	
	}
	

	
	@Override
	public void onClick(View v) {

		
	}
	
	
	
	

}
