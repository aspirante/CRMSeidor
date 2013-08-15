package com.crystalis.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.crystalis.interfacesDB.IScriptTables;
import com.crystalis.interfacesDB.ISettingsDB;
import com.crystalis.interfacesDB.ITableNames;

public class MySQLiteHelper extends SQLiteOpenHelper {
	

	public MySQLiteHelper(Context context) {
	super(context, ISettingsDB.DATABASE_NAME, null, ISettingsDB.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(IScriptTables.TABLE_SALES_REPORT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL(ISettingsDB.DROPTABLE_TEXT + ITableNames.TABLE_REPORT_SALESORDER );
		onCreate(db);
	}

}
