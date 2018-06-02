package com.example.ji.lab6_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;
    EditText Name, Num;
    Button Add, Delete;
    ListView listItem;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText) findViewById(R.id.name);
        Num = (EditText) findViewById(R.id.num);
        Add = (Button) findViewById(R.id.add);
        Delete = (Button) findViewById(R.id.delete);
        listItem = (ListView) findViewById(R.id.list_item);

        helper = new MySQLiteOpenHelper(MainActivity.this, "student.db", null, 1);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName, getNum;
                getName = Name.getText().toString();
                getNum = Num.getText().toString();
                if (getName.length() == 0 || getNum.length() == 0) {
                    Toast.makeText(MainActivity.this, "모든 항목을 입력해 주세요.", Toast.LENGTH_LONG).show();
                } else {
                    insert(getName, getNum);
                    invalidate();
                }
            }


        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName;
                getName = Name.getText().toString();
                if (getName.length() == 0) {
                    Toast.makeText(MainActivity.this, "이름을 입력해주세요.", Toast.LENGTH_LONG).show();

                } else {
                    delete(getName);
                    invalidate();
                }
            }

        });

    }

    public void insert(String name,String num){
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("studentNo",num);
        db.insert("student",null,values);
    }
    public void delete(String name){
        db=helper.getWritableDatabase();
        db.delete("student","name=?",new String[]{name});
        Log.i("db1",name+"삭제 되었습니다.");
    }

    public void select(){
        db=helper.getReadableDatabase();
        Cursor c=db.query("student",null,null,null,null,null,null);
        data=new String[c.getCount()];
        int count=0;

        while (c.moveToNext()) {

            data[count]=c.getString(c.getColumnIndex("name"))+" "+
                    c.getString(c.getColumnIndex("studentNo"));
            count++;
        }
        c.close();
    }

    private void invalidate(){
        select();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listItem.setAdapter(adapter);
    }
}


