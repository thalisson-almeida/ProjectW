package com.thalisson.android.projectw.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thali on 22/03/2018.
 */

public class SqLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "com.thalisson.android.projectw.db";
    private static final int DB_VERSION = 1;

    public SqLiteHelper(Context context){
        super(context, DB_NAME, null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Cadastro.TABLE_NAME);
        createTable_Cadastro(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Cadastro.TABLE_NAME);

        onCreate(db);
    }


    private void createTable_Cadastro(SQLiteDatabase db) {
        final String SQL_CREATE_Moeda_TABLE = "CREATE TABLE "+ Contract.Cadastro.TABLE_NAME+
                " ("+ Contract.Cadastro._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.Cadastro.COL_RAZAOSOCIAL+" TEXT NOT NULL, "+
                Contract.Cadastro.COL_NOMEFANTASIA+" TEXT NOT NULL, "+
                Contract.Cadastro.COL_CNPJ+" TEXT NOT NULL, "+
                Contract.Cadastro.COL_DDD+" TEXT NOT NULL, "+
                Contract.Cadastro.COL_TELEFONE+" TEXT NOT NULL, "+
                Contract.Cadastro.COL_SITE+" TEXT);";

        db.execSQL(SQL_CREATE_Moeda_TABLE);

    }
}
