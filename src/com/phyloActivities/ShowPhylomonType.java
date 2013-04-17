package com.phyloActivities;

import com.phylogame.R;

import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class ShowPhylomonType extends Activity implements OnClickListener{
	PhyloApplication myapp;
	ImageView image;
	TextView nameView;
	Button back;

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.phylomontype);
        
        // gets the database from the application
        myapp = ((PhyloApplication)getApplicationContext());
        //gets the number of the phylomon in the database
        int i = Integer.valueOf(getIntent().getStringExtra("position"));
        PhylomonType current =  myapp.getDatabase()[i];
        
        
        image = (ImageView)findViewById(R.id.imageView1);
        String picloc = current.getPicLocatie();
        
        int imageResource = getResources().getIdentifier(picloc , "drawable", getPackageName());
        image.setImageResource(imageResource);
        
        //showing the name
        nameView = (TextView)findViewById(R.id.name);
        nameView.setText(current.getName());
        
        //back Button
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }
	
	@Override
	public void onClick(View v) {
		this.onBackPressed();
		
	}
}
