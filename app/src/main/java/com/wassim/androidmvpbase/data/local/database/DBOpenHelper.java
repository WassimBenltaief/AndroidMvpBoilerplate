package com.wassim.androidmvpbase.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wassim.androidmvpbase.MovieModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wassim on 8/8/16.
 */
@Singleton
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "movies.db";
    private static final int DATABASE_VERSION = 2;

    @Inject
    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieModel.CREATE_TABLE);
        populate(db);
    }

    private void populate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
