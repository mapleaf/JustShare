package com.logan.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String ACCOUNT_DATABASE_NAME = "account.db";
	private static final int ACCOUNT_DATABASE_VERSION = 4;

	public DBHelper(Context context) {
		super(context, ACCOUNT_DATABASE_NAME, null, ACCOUNT_DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS account"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR,name VARCHAR,"
				+ "url VARCHAR,token VARCHAR, expires_in VARCHAR, plf VARCHAR,openid VARCHAR,openkey VARCHAR)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS account");
		onCreate(db);

	}

}
