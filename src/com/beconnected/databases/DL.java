package com.beconnected.databases;



import android.content.Context;


public class DL {

    private SqliteConnection sqliteConnection;
    private static DL dl;

    public DL(){

    }

    public static DL getDl() {
        if (dl == null) {
            dl = new DL();

        }

        return dl;

    }

    public void setSqliteConnection(Context context){

    	sqliteConnection = new SqliteConnection(context);
    }

    public SqliteConnection getSqliteConnection(){

        return sqliteConnection;
    }




}