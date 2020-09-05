package com.example.salonspace;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,"Log-in.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //DB생성 맨처음!
        String sql="create table Login("+"idx integer primary key autoincrement,"
                +"ID text not null,"
                +"PW text not null)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        // 버전 바뀔때마다 호출 !
        switch(oldVersion){
            case 1:
                // 1->2 로 바뀔때
            case 2:
                // 2->으로 바뀔때
        }
    }
}
