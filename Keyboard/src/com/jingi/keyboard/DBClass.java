package com.jingi.keyboard;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBClass extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "KeyMap";

	// Contacts table name
	private static final String KEYMAP_TABLE = "KeyMapTable";

	// Contacts Table Columns names
	private static final String KEY_MAPPING_NAME = "MappingName";
	private static final String KEY_M1 = "Macro1";
	private static final String KEY_M2 = "Macro2";

	public DBClass(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
		String CREATE_KEYMAP_TABLE = "CREATE TABLE " + KEYMAP_TABLE + "("
				+ KEY_MAPPING_NAME + " TEXT PRIMARY KEY," + KEY_M1 + " TEXT,"
				+ KEY_M2 + " TEXT" + ")";
		db.execSQL(CREATE_KEYMAP_TABLE);
		}
		catch(SQLException e){
			Log.d("SQLException from onCreate()", e.toString());
		}
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + KEYMAP_TABLE);

		// Create tables again
		onCreate(db);
		}
		catch(SQLException e){
			Log.d("SQLException from onUpgrade()", e.toString());
		}
	}


	// Adding new KeyMap
	void addKeyMap(String keyMapName, String m1Mapping, String m2Mapping) {
		try{
			SQLiteDatabase db = this.getWritableDatabase();
	
			ContentValues values = new ContentValues();
			values.put(KEY_MAPPING_NAME, keyMapName); // KeyMap Name
			values.put(KEY_M1, m1Mapping); // Macro Key One Mapping
			values.put(KEY_M2, m2Mapping); // Macro Key Two Mapping
			
			// Inserting Row
			db.insert(KEYMAP_TABLE, null, values);
			db.close(); // Closing database connection
		}
		catch(SQLException e){
			Log.d("SQLException from onCreate", e.toString());
		}
	}

	// Getting single contact
	public String[] getKeyMap(String keyMap) {
		try{
			SQLiteDatabase db = this.getReadableDatabase();
	
			Cursor cursor = db.query(KEYMAP_TABLE, new String[] { KEY_MAPPING_NAME,
					KEY_M1, KEY_M2 }, KEY_MAPPING_NAME + "=?",
					new String[] {keyMap}, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();
	
			String[] rKeyMap = new String[]{cursor.getString(1), cursor.getString(2)};
			// return KeyMap
			return rKeyMap;
		}
		catch(SQLException e){
			Log.d("SQLException from getKeyMap", e.toString());
			return null;
		}
		catch(CursorIndexOutOfBoundsException e){
			//just indicates that the cursor object didn't return any rows
			return null;
		}
	}
	
	// Getting All KeyMappings
	public String[] getAllMappingNames() {
		// Select All Mapping Names Query
		String getAllMappingNamesQuery = "SELECT " +KEY_MAPPING_NAME +" FROM " + KEYMAP_TABLE;
		List<String> sList = new ArrayList<String>();
		
		try{
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(getAllMappingNamesQuery, null);
	
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					sList.add(cursor.getString(0));
				} while (cursor.moveToNext());
			}
			String s = sList.toString();
			return  s.substring(1, s.length()-1).split(",");
			
		}
		catch(NullPointerException e){
			//Do nothing since the database table might be still empty and the user might invoke the activity which inturn invokes this function
			return null;
		}
		catch(CursorIndexOutOfBoundsException e){
			//just indicates that the cursor object didn't return any rows
			return null;
		}
	}

	// Updating a keyMapping
	public int updateKeyMap(String keyMapName, String m1Mapping, String m2Mapping) {
		try{
			SQLiteDatabase db = this.getWritableDatabase();
	
			ContentValues values = new ContentValues();
			values.put(KEY_MAPPING_NAME, keyMapName); // KeyMap Name
			values.put(KEY_M1, m1Mapping); // Macro Key One Mapping
			values.put(KEY_M2, m2Mapping); // Macro Key Two Mapping
	
			//updating row
			return db.update(KEYMAP_TABLE, values, KEY_MAPPING_NAME + " = ?",
					new String[] {keyMapName});
		}
		catch(Exception e){
			return -1;	//indicates update failure
		}
	}

	// Deleting single contact
	public int deleteKeyMapping(String keyMapName) {
		try{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(KEYMAP_TABLE, KEY_MAPPING_NAME + " = ?",
				new String[] {keyMapName});
		db.close();
		return 1;
		}
		catch(SQLException e){
			Log.d("SQLException from deleteKeyMap()", e.toString());
			return 0;
		}
	}


}
