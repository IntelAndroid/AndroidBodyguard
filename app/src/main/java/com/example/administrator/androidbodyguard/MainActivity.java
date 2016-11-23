package com.example.administrator.androidbodyguard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.androidbodyguard.bean.InfoCall;
import com.example.administrator.androidbodyguard.db.SQlms;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        String packageName = getPackageName();
        SQlms s=new SQlms();
        SQLiteDatabase sqLiteDatabase = s.openDatabase(mContext);
        ArrayList<InfoCall> query = s.query(sqLiteDatabase, "中国移动客服", mContext);
for(int i=0;i<query.size();i++){
    String name = query.get(i).name;
    String number = query.get(i).number;
    Log.i(TAG, "onCreate: "+name);
    Log.i(TAG, "onCreate: "+number);
}

    }
}
