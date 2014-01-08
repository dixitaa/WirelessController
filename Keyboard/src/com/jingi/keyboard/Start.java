package com.jingi.keyboard;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Start extends Activity {
	private EditText ipaddr;
	private Button b;
	private boolean isConnected;
	private NetworkInfo activeNetwork;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		b = (Button)findViewById(R.id.Go);
		ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		activeNetwork = connectivityManager.getActiveNetworkInfo();

		
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ipaddr = (EditText)findViewById(R.id.IPtext);
				String s = ipaddr.getText().toString();
				isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();				
				if(isConnected == false){
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Connect To A Network And Try Again!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
					
				if(s.equals("") || s.equals(null)){
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Enter An IPv4 Address!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				if(!validateIPAddress(s)){
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Its an Invalid IPv4 Address, Try Again! ", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				MainActivity.hostip = s+"";
				startActivity(new Intent(Start.this, MainActivity.class));
					
				
			}
			
		});
	}

	
	public boolean validateIPAddress( String ipAddress ){
		String[] tokens = ipAddress.split("\\."); 
		try{
			if (tokens.length != 4) 
				return false;
			for (String str : tokens){
				int i = Integer.parseInt(str); 
				if ((i < 0) || (i > 255))  
					return false;  
			}
			return true;
		}
		catch(Exception e){
			return false;
		}
	} 
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}

}
