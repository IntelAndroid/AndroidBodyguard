package com.example.administrator.androidbodyguard.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.androidbodyguard.bean.InfoCall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class SQlms {

    //数据库存储路径
    String filePath = "data/data/com.example.administrator.androidbodyguard/commonnum.db";



    public  SQLiteDatabase openDatabase(Context context){

        File jhPath=new File(filePath);
        //查看数据库文件是否存在
        if(jhPath.exists()){
            Log.i("test", "存在数据库");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        }
            try {
                //得到资源
                AssetManager am= context.getAssets();
                //得到数据库的输入流
                InputStream is=am.open("commonnum.db");
                Log.i("test", is+"");
                //用输出流写到SDcard上面
                FileOutputStream fos=new FileOutputStream(jhPath);
                Log.i("test", "fos="+fos);
                Log.i("test", "jhPath="+jhPath);
                //创建byte数组  用于1KB写一次
                byte[] buffer=new byte[1024];
                int count = 0;
                while((count = is.read(buffer))>0){
                    Log.i("test", "得到");
                    fos.write(buffer,0,count);
                }

                //最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    public ArrayList<InfoCall> query(SQLiteDatabase sqliteDB,String name,Context context){

        ArrayList<InfoCall> list = new ArrayList<InfoCall>();

        //执行sql语句需要sqliteDatabase对象


        //table:表名, columns：查询的列名,如果null代表查询所有列； selection:查询条件, selectionArgs：条件占位符的参数值,
        //groupBy:按什么字段分组, having:分组的条件, orderBy:按什么字段排序
        Cursor cursor = sqliteDB.query("table3", new String[]{"_id","number","name"}, null, null, null, null, "_id asc");
        //解析Cursor中的数据
        if(cursor != null && cursor.getCount() >0){//判断cursor中是否存在数据

            //循环遍历结果集，获取每一行的内容
            while(cursor.moveToNext()){//条件，游标能否定位到下一行
                InfoCall bean = new InfoCall();
                //获取数据

                bean. name = cursor.getString(2);
                bean.number = cursor.getString(1);

                list.add(bean);


            }
            cursor.close();//关闭结果集

        }
        //关闭数据库对象
        sqliteDB.close();

        return list;
    }
    }

