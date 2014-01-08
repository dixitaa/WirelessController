package com.jingi.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class KeyMapping extends Activity {

	
	private Spinner macro1Mapping, macro2Mapping, availableMappings;
	private Button saveMapping, loadMapping, deleteMapping;
	private EditText mappingName;
	private static String currentMappingSelection;
	private DBClass dbManager;
	private List<String>availableMappingList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key_mapping);
		macro1Mapping = (Spinner)findViewById(R.id.macro1Mapping);
		macro2Mapping = (Spinner)findViewById(R.id.macro2Mapping);
		availableMappings = (Spinner)findViewById(R.id.availableMappings);

		saveMapping = (Button)findViewById(R.id.saveMapping);
		loadMapping = (Button)findViewById(R.id.loadMapping);
		deleteMapping = (Button)findViewById(R.id.deleteMapping);
		
		mappingName = (EditText)findViewById(R.id.mappingName);
		
		dbManager = new DBClass(this);
		
		saveMapping.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				String name = mappingName.getText().toString().replace(" ", "");
				if((name.equals("") || name.equals(null))){
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Enter A Name To Save!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				else
				{
					if(availableMappingList.contains(name))
						dbManager.updateKeyMap(name, MainActivity.macro1Mapping, MainActivity.macro2Mapping);
					else
						dbManager.addKeyMap(name, MainActivity.macro1Mapping, MainActivity.macro2Mapping);
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Mapping Saved As:"+name, Toast.LENGTH_SHORT);
					toast.show();				
					addKeysToKeySpinner();
				}
			}
			
			
		});
		
		loadMapping.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				
				//TODO load from a database
				String M1 = "E";
				String M2 = "F";
				String currentMappingSelection = new String(KeyMapping.currentMappingSelection);

				if(currentMappingSelection == null || currentMappingSelection == ""){
					Log.d("CURRENT MAPPING SELECTION", currentMappingSelection);

					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "No Mappings Saved!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				String [] s = dbManager.getKeyMap(currentMappingSelection);
				if(s != null){			
					M1 = s[0];
					M2 = s[1];
					macro1Mapping.setSelection(((ArrayAdapter<String>) macro1Mapping.getAdapter()).getPosition(M1));
					macro2Mapping.setSelection(((ArrayAdapter<String>) macro1Mapping.getAdapter()).getPosition(M2));
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "M1: "+M1+", M2: "+M2, Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				else{
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "No Saved Mappings Found!", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
		
		
		deleteMapping.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				String currentMappingSelection = KeyMapping.currentMappingSelection;
				if(currentMappingSelection == null || currentMappingSelection.equals("")){
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "No Saved Mappings Found!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				if(dbManager.deleteKeyMapping(currentMappingSelection) == 1){
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "The Mapping "+currentMappingSelection+" has been deleted", Toast.LENGTH_SHORT);
					toast.show();
					addKeysToKeySpinner();
				}

			}
			
		});
		
		
		
		macro1Mapping.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adap, View v,
					int position, long id) {
					String s = (String) adap.getItemAtPosition(position);
					MainActivity.macro1Mapping = new String(s);
					//Displaying a toast
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "M1 Mapped To: "+s, Toast.LENGTH_SHORT);
					toast.show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adap) {
				//do nothing
				
			}
			
		});
		
		
		macro2Mapping.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adap, View v,
					int position, long id) {
					String s = (String) adap.getItemAtPosition(position);
					MainActivity.macro2Mapping = new String(s);
					//Displaying a toast
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "M2 Mapped To: "+s, Toast.LENGTH_SHORT);
					toast.show();
			}

			
			
			@Override
			public void onNothingSelected(AdapterView<?> adap) {
				//do nothing
				
			}
			
		});

		availableMappings.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adap, View v,
					int position, long id) {
					String s = (String) adap.getItemAtPosition(position);
					KeyMapping.currentMappingSelection = new String(s);
			}

			
			
			@Override
			public void onNothingSelected(AdapterView<?> adap) {
				//do nothing
				
			}
			
		});		
		

		addKeysToKeySpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_key_mapping, menu);
		return true;
	}

	public void addKeysToKeySpinner(){
		
		
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
		keyList.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));
		keyList.addAll(Arrays.asList("Shift", "Control", "Alt", "Space", "Enter", "BackSpace"));
		
		availableMappingList = new ArrayList<String>();
		String[] sA = dbManager.getAllMappingNames();
		if(sA.length != 0 || sA != null )
			availableMappingList.addAll(Arrays.asList(sA));
		//TODO get available mappings from the database and then populate it
		
		ArrayAdapter<String> availableMappingListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, availableMappingList);
		availableMappingListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		availableMappings.setAdapter(availableMappingListAdapter);
		
		
		//
		ArrayAdapter<String> keyListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, keyList);
		keyListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		macro1Mapping.setAdapter(keyListAdapter);
		
		ArrayAdapter<String> mappingListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, keyList);
		mappingListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		macro2Mapping.setAdapter(mappingListAdapter);
		
		
	}
}
     



 
