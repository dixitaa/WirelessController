package com.jingi.keyboard;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.lang.NullPointerException;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	private ImageButton up;
	private ImageButton down;
	private ImageButton left;
	private ImageButton right;
	private ImageButton load;
	private ImageButton macro1;
	private ImageButton macro2;
	
	private TextView displayText;
	
	private HashMap<String, int[]> h;
	
	private String[] keyArray = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","1", "2", "3", "4", "5", "6", "7", "8", "9", "0","Shift", "Control", "Alt", "Space", "Enter", "BackSpace"};
	private int[][] keyCodeArray = new int[][]{{9, 10}, {11, 12}, {13, 14}, {15, 16}, {17, 18}, {19, 20}, {21, 22}, {23, 24}, {25, 26}, {27, 28}, {29, 30}, {31, 32},{33, 34}, {35, 36}, {37, 38}, {39, 40}, {41, 42}, {43, 44}, {45, 46}, {47, 48}, {49, 50}, {51, 52}, {53, 54}, {55, 56}, {57, 58}, {59, 60},{61, 62}, {63, 64}, {65, 66}, {67, 68}, {69, 70}, {71, 72}, {73, 74}, {75, 76}, {77, 78}, {79, 80}, {81, 82}, {83, 84}, {85, 86}, {87, 88}, {89, 90}, {91, 92}};
	public static String macro1Mapping = "E";
	public static String macro2Mapping = "F";
	public static String hostip = null;
	public static int defaultPort = 17896;			//change this to change the default port number on which the client listens

	private String actionListenerForUp = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n","new OnTouchListener(){",
			"private Handler mHandler;",
			"@Override",
			"public boolean onTouch(View view, MotionEvent event){",
			"displayText.setText(\"\");",
			"displayText.setText(actionListenerForUp);",
			"	switch(event.getAction()){",
			"	case MotionEvent.ACTION_DOWN:",
			"		Log.d(\"KeyBoard\",\"up pressed\");",
			"		if(mHandler != null) return true;",
			"		mHandler = new Handler();",
			"		mHandler.postDelayed(sd, 30);",
			"		break;",
			"	case MotionEvent.ACTION_UP:",
			"		if(mHandler == null) return true;",
			"		mHandler.removeCallbacks(sd);",
			"		sd.resetDelay();",
			"		mHandler = null;",
			"		new sendData(5);",
			"		break;",
			"	}",
			"	return false;",
			"}",
			"class KeyBoardRepeatClass implements Runnable{",
			"	private boolean delayFlag = true;",
			"	@Override",
			"	public void run() {",
			"		new sendData(1);",
			"		if(delayFlag == true){",
			"			mHandler.postDelayed(this, 500);",
			"			delayFlag = false;",
			"		}",
			"		else",
			"			mHandler.postDelayed(this, 30);",
			"	}",
			"	public void resetDelay(){",
			"		delayFlag = true;",
			"	}",
			"}",
			"KeyBoardRepeatClass sd = new KeyBoardRepeatClass();",
			"}");
	
	private String actionListenerForDown = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n","new OnTouchListener(){",
			"			private Handler mHandler;",
			"			@Override",
			"			public boolean onTouch(View view, MotionEvent event){",
			"			displayText.setText(\"\");",
			"			displayText.setText(actionListenerForDown);",
			"				switch(event.getAction()){",
			"				case MotionEvent.ACTION_DOWN:",
			"					Log.d(\"KeyBoard\",\"down pressed\");",
			"					if(mHandler != null) return true;",
			"					mHandler = new Handler();",
			"					mHandler.postDelayed(sd, 30);",
			"					break;",
			"				case MotionEvent.ACTION_UP:",
			"					if(mHandler == null) return true;",
			"					mHandler.removeCallbacks(sd);",
			"					sd.resetDelay();",
			"					mHandler = null;",
			"					new sendData(6);",
			"					break;",
			"				}",
			"				return false;",
			"			}",
			"			class KeyBoardRepeatClass implements Runnable{",
			"				private boolean delayFlag = true;",
			"				@Override",
			"				public void run() {",
			"					new sendData(2);",
			"					if(delayFlag == true){",
			"						mHandler.postDelayed(this, 500);",
			"						delayFlag = false;",
			"					}",
			"					else",
			"						mHandler.postDelayed(this, 30);",
			"				}",
			"				public void resetDelay(){",
			"					delayFlag = true;",
			"				}",
			"	",
			"			}",
			"			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();",
			"			",
			"			",
			"		}");
	private String actionListenerForLeft = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", "new OnTouchListener(){",
			"			private Handler mHandler;",
			"			@Override",
			"			public boolean onTouch(View view, MotionEvent event){",
			"			displayText.setText(\"\");",
			"			displayText.setText(actionListenerForLeft);",
			"				switch(event.getAction()){",
			"				case MotionEvent.ACTION_DOWN:",
			"					Log.d(\"KeyBoard\",\"left pressed\");",
			"					if(mHandler != null) return true;",
			"					mHandler = new Handler();",
			"					mHandler.postDelayed(sd, 30);",
			"					break;",
			"				case MotionEvent.ACTION_UP:",
			"					if(mHandler == null) return true;",
			"					mHandler.removeCallbacks(sd);",
			"					sd.resetDelay();",
			"					mHandler = null;",
			"					new sendData(7);",
			"					break;",
			"				}",
			"				return false;",
			"			}",
			"			class KeyBoardRepeatClass implements Runnable{",
			"				private boolean delayFlag = true;",
			"				@Override",
			"				public void run() {",
			"					new sendData(3);",
			"					if(delayFlag == true){",
			"						mHandler.postDelayed(this, 500);",
			"						delayFlag = false;",
			"					}",
			"					else",
			"						mHandler.postDelayed(this, 30);",
			"				}",
			"				public void resetDelay(){",
			"					delayFlag = true;",
			"				}",
			"			}",
			"			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();",
			"}");
	private String actionListenerForRight = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", "new OnTouchListener(){",
			"			private Handler mHandler;",
			"			@Override",
			"			public boolean onTouch(View view, MotionEvent event){",
			"			displayText.setText(\"\");",
			"			displayText.setText(actionListenerForRight);",
			"				switch(event.getAction()){",
			"				case MotionEvent.ACTION_DOWN:",
			"					Log.d(\"KeyBoard\",\"right pressed\");",
			"					if(mHandler != null) return true;",
			"					mHandler = new Handler();",
			"					mHandler.postDelayed(sd, 30);",
			"					break;",
			"				case MotionEvent.ACTION_UP:",
			"					if(mHandler == null) return true;",
			"					mHandler.removeCallbacks(sd);",
			"					sd.resetDelay();",
			"					mHandler = null;",
			"					new sendData(8);",
			"					break;",
			"				}",
			"				return false;",
			"			}",
			"			class KeyBoardRepeatClass implements Runnable{",
			"				private boolean delayFlag = true;",
			"				@Override",
			"				public void run() {",
			"					new sendData(4);",
			"					if(delayFlag == true){",
			"						mHandler.postDelayed(this, 500);",
			"						delayFlag = false;",
			"					}",
			"					else",
			"						mHandler.postDelayed(this, 30);",
			"				}",
			"				public void resetDelay(){",
			"					delayFlag = true;",
			"				}",
			"			}",
			"			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();		");
	
	private String actionListenerForMacro1 = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", "new OnTouchListener(){",
			"			private Handler mHandler;",
			"			@Override",
			"			public boolean onTouch(View view, MotionEvent event){",
			"			displayText.setText(\"\");",
			"			displayText.setText(actionListenerForMacro1);",
			"				switch(event.getAction()){",
			"				case MotionEvent.ACTION_DOWN:",
			"					Log.d(\"KeyBoard\",\"MACRO1 pressed\");",
			"					if(mHandler != null) return true;",
			"					mHandler = new Handler();",
			"					mHandler.postDelayed(sd, 30);",
			"					break;",
			"				case MotionEvent.ACTION_UP:",
			"					if(mHandler == null) return true;",
			"					mHandler.removeCallbacks(sd);",
			"					sd.resetDelay();",
			"					mHandler = null;",
			"					try{",
			"						new sendData(h.get(macro1Mapping)[1]);",
			"					}",
			"					catch(NullPointerException e){}",
			"					break;",
			"				}",
			"				return false;",
			"			}",
			"			class KeyBoardRepeatClass implements Runnable{",
			"				private boolean delayFlag = true;",
			"				@Override",
			"				public void run() {",
			"					try{",
			"						new sendData(h.get(macro1Mapping)[0]);",
			"						if(delayFlag == true){",
			"							mHandler.postDelayed(this, 500);",
			"							delayFlag = false;",
			"						}",
			"						else",
			"							mHandler.postDelayed(this, 30);",
			"					}",
			"					catch(NullPointerException e){}	",
			"				}",
			"				public void resetDelay(){",
			"					delayFlag = true;",
			"				}",
			"			}",
			"			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();");
	private String actionListenerForMacro2 = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", "new OnTouchListener(){",
			"			private Handler mHandler;",
			"			@Override",
			"			public boolean onTouch(View view, MotionEvent event){",
			"			displayText.setText(\"\");",
			"			displayText.setText(actionListenerForMacro2);",
			"				switch(event.getAction()){",
			"				case MotionEvent.ACTION_DOWN:",
			"					Log.d(\"KeyBoard\",\"MACRO1 pressed\");",
			"					if(mHandler != null) return true;",
			"					mHandler = new Handler();",
			"					mHandler.postDelayed(sd, 30);",
			"					break;",
			"				case MotionEvent.ACTION_UP:",
			"					if(mHandler == null) return true;",
			"					mHandler.removeCallbacks(sd);",
			"					sd.resetDelay();",
			"					mHandler = null;",
			"					try{",
			"						new sendData(h.get(macro2Mapping)[1]);",
			"					}",
			"					catch(NullPointerException e){}",
			"					break;",
			"				}",
			"				return false;",
			"			}",
			"			class KeyBoardRepeatClass implements Runnable{",
			"				private boolean delayFlag = true;",
			"				@Override",
			"				public void run() {",
			"					try{",
			"						new sendData(h.get(macro2Mapping)[0]);",
			"						if(delayFlag == true){",
			"							mHandler.postDelayed(this, 500);",
			"							delayFlag = false;",
			"						}",
			"					",
			"						else",
			"							mHandler.postDelayed(this, 30);",
			"					}",
			"					catch(NullPointerException e){}",
			"				}",
			"				public void resetDelay(){",
			"					delayFlag = true;",
			"				}",
			"			}",
			"			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();");
	
	
	
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		createHashMap();
		// simply getting all the references to play with
		up = (ImageButton)findViewById(R.id.up);
		down = (ImageButton)findViewById(R.id.down);
		left = (ImageButton)findViewById(R.id.left);
		right = (ImageButton)findViewById(R.id.right);
		load = (ImageButton)findViewById(R.id.load);
		macro1 = (ImageButton)findViewById(R.id.Macro1);
		macro2 = (ImageButton)findViewById(R.id.Macro2);
		displayText = (TextView)findViewById(R.id.DisplayText);
		
		
		//Modified code
		final Intent startKeyMappingActivity = new Intent(this, KeyMapping.class);
		load.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v){
				
				startActivity(startKeyMappingActivity);
				
			}
			
		});
		
		
		//callback functions start here

		macro1.setOnTouchListener(new OnTouchListener(){

			private Handler mHandler;
			@Override
			public boolean onTouch(View view, MotionEvent event){
				
				displayText.setText("");
				displayText.setText(actionListenerForMacro1);
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("KeyBoard","MACRO1 pressed");
					if(mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(sd, 30);
					break;
				case MotionEvent.ACTION_UP:
					if(mHandler == null) return true;
					mHandler.removeCallbacks(sd);
					sd.resetDelay();
					mHandler = null;
					try{
						new sendData(h.get(macro1Mapping)[1]);
					}
					catch(NullPointerException e){}
					break;
				}
				return false;
			}
			class KeyBoardRepeatClass implements Runnable{
				private boolean delayFlag = true;
				@Override
				public void run() {
					try{
						new sendData(h.get(macro1Mapping)[0]);
						if(delayFlag == true){
							mHandler.postDelayed(this, 500);
							delayFlag = false;
						}
						else
							mHandler.postDelayed(this, 30);
					}
					catch(NullPointerException e){}	
				}
				public void resetDelay(){
					delayFlag = true;
				}
	
			}
			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();
			
			
		});		
		

		macro2.setOnTouchListener(new OnTouchListener(){

			private Handler mHandler;
			@Override
			public boolean onTouch(View view, MotionEvent event){
				
				displayText.setText("");
				displayText.setText(actionListenerForMacro2);
				
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("KeyBoard","MACRO1 pressed");
					if(mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(sd, 30);
					break;
				case MotionEvent.ACTION_UP:
					if(mHandler == null) return true;
					mHandler.removeCallbacks(sd);
					sd.resetDelay();
					mHandler = null;
					try{
						new sendData(h.get(macro2Mapping)[1]);
					}
					catch(NullPointerException e){}
					break;
				}
				return false;
			}
			class KeyBoardRepeatClass implements Runnable{
				private boolean delayFlag = true;
				@Override
				public void run() {
					try{
						new sendData(h.get(macro2Mapping)[0]);
						if(delayFlag == true){
							mHandler.postDelayed(this, 500);
							delayFlag = false;
						}
					
						else
							mHandler.postDelayed(this, 30);
					}
					catch(NullPointerException e){}
				}
				public void resetDelay(){
					delayFlag = true;
				}
	
			}
			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();
			
			
		});
		
		
		
		
		
		
		
		up.setOnTouchListener(new OnTouchListener(){

			private Handler mHandler;
			@Override
			public boolean onTouch(View view, MotionEvent event){
				
				displayText.setText("");
				displayText.setText(actionListenerForUp);
				
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("KeyBoard","up pressed");
					if(mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(sd, 30);
					break;
				case MotionEvent.ACTION_UP:
					if(mHandler == null) return true;
					mHandler.removeCallbacks(sd);
					sd.resetDelay();
					mHandler = null;
					new sendData(5);
					break;
				}
				return false;
			}
			class KeyBoardRepeatClass implements Runnable{
				private boolean delayFlag = true;
				@Override
				public void run() {
					new sendData(1);
					if(delayFlag == true){
						mHandler.postDelayed(this, 500);
						delayFlag = false;
					}
					else
						mHandler.postDelayed(this, 30);
				}
				public void resetDelay(){
					delayFlag = true;
				}
	
			}
			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();
			
			
		});
			down.setOnTouchListener(new OnTouchListener(){

			private Handler mHandler;
			@Override
			public boolean onTouch(View view, MotionEvent event){
				
				displayText.setText("");
				displayText.setText(actionListenerForDown);
				
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("KeyBoard","down pressed");
					if(mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(sd, 30);
					break;
				case MotionEvent.ACTION_UP:
					if(mHandler == null) return true;
					mHandler.removeCallbacks(sd);
					sd.resetDelay();
					mHandler = null;
					new sendData(6);
					break;
				}
				return false;
			}
			class KeyBoardRepeatClass implements Runnable{
				private boolean delayFlag = true;
				@Override
				public void run() {
					new sendData(2);
					if(delayFlag == true){
						mHandler.postDelayed(this, 500);
						delayFlag = false;
					}
					else
						mHandler.postDelayed(this, 30);
				}
				public void resetDelay(){
					delayFlag = true;
				}
	
			}
			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();
			
			
		});
		left.setOnTouchListener(new OnTouchListener(){

			private Handler mHandler;
			@Override
			public boolean onTouch(View view, MotionEvent event){
				
				displayText.setText("");
				displayText.setText(actionListenerForLeft);
				
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("KeyBoard","left pressed");
					if(mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(sd, 30);
					break;
				case MotionEvent.ACTION_UP:
					if(mHandler == null) return true;
					mHandler.removeCallbacks(sd);
					sd.resetDelay();
					mHandler = null;
					new sendData(7);
					break;
				}
				return false;
			}
			class KeyBoardRepeatClass implements Runnable{
				private boolean delayFlag = true;
				@Override
				public void run() {
					new sendData(3);
					if(delayFlag == true){
						mHandler.postDelayed(this, 500);
						delayFlag = false;
					}
					else
						mHandler.postDelayed(this, 30);
				}
				public void resetDelay(){
					delayFlag = true;
				}
	
			}
			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();
			
			
		});
		right.setOnTouchListener(new OnTouchListener(){

			private Handler mHandler;
			@Override
			public boolean onTouch(View view, MotionEvent event){
				
				displayText.setText("");
				displayText.setText(actionListenerForRight);
				
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("KeyBoard","right pressed");
					if(mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(sd, 30);
					break;
				case MotionEvent.ACTION_UP:
					if(mHandler == null) return true;
					mHandler.removeCallbacks(sd);
					sd.resetDelay();
					mHandler = null;
					new sendData(8);
					break;
				}
				return false;
			}
			class KeyBoardRepeatClass implements Runnable{
				private boolean delayFlag = true;
				@Override
				public void run() {
					new sendData(4);
					if(delayFlag == true){
						mHandler.postDelayed(this, 500);
						delayFlag = false;
					}
					else
						mHandler.postDelayed(this, 30);
				}
				public void resetDelay(){
					delayFlag = true;
				}
	
			}
			KeyBoardRepeatClass sd = new KeyBoardRepeatClass();
			
			
		});

		
		
	}
	protected void onResume(){
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void createHashMap(){
		h = new HashMap<String, int[]>();
		
		for(int i = 0; i<keyArray.length; i++){
			h.put(keyArray[i], keyCodeArray[i]);
		}		
	}
	

}
class sendData implements Runnable{

	int keyCode = 1;
	Thread t = null;
	public sendData(int a){
		keyCode = a;
		t = new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		sendKey();		
	}
	public void sendKey(){
		try {
			//create a udp socket
			DatagramSocket s = new DatagramSocket();
			String str = new String(Integer.toString(keyCode));
			InetAddress ipaddr = InetAddress.getByName(MainActivity.hostip);
			Log.d("Keyboard", MainActivity.hostip+"hello address");
			DatagramPacket pack = new DatagramPacket(str.getBytes(), str.length(), ipaddr, MainActivity.defaultPort);
			s.send(pack);
			s.close();
		}
		catch (SocketException e) {

			Log.d("Keyboard",e.toString());
		}
		catch (IOException e) {
			Log.d("Keyboard",e.toString());
		}
		catch(Exception e)
		{
			Log.d("Keyboard",e.toString());
		}		
	}
}
