package com.sayed.assaignmentproject.services.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static com.sayed.assaignmentproject.services.local.DbUtilities.*;
import static com.sayed.assaignmentproject.services.local.DbUtilities.DATABASE_NAME;
import static com.sayed.assaignmentproject.services.local.DbUtilities.DATABASE_VERSION;

public class DBhelper extends SQLiteOpenHelper {

    private Context context ;
    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_POST = "CREATE TABLE "+POST_TABLE_NAME+" (" +
                COLUMN_ID +" INTEGER NOT NULL," +
                COLUMN_USER_ID+" INTEGER NOT NULL," +
                COLUMN_TITLE+" TEXT NOT NULL," +
                COLUMN_BODY+" TEXT NOT NULL," +
                COLUMN_TIMESTAMP+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,"+
                "PRIMARY KEY(_id)" +
                ");";
        db.execSQL(CREATE_TABLE_POST);
        Toast.makeText(context , "On Create Called" , Toast.LENGTH_SHORT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        Toast.makeText(this.context , "On Update Called" , Toast.LENGTH_LONG).show();
        onCreate(db);

    }
}
