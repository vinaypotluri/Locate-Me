package com.example.glass_loc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class info extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.info_layout);
	
	TextView text=(TextView) findViewById(R.id.textView1);
	TextView names=(TextView) findViewById(R.id.textView2);
	
	
	
	text.setText("\nSchool of Library \n and \n Information Science \n Wayne State University");
	names.setText("\nSupervisor: Elliot Jonathan Polak \n Developer: Vinay Potluri");
	}

}
