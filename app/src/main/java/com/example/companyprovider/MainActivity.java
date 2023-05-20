package com.example.companyprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

 TextInputEditText empName;
 TextInputEditText empPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        empName=findViewById(R.id.empName);
        empPosition=findViewById(R.id.empPosition);
    }

    public void btnSave(View view) {
        String name= Objects.requireNonNull(empName.getText()).toString().trim();
        String pos= Objects.requireNonNull(empPosition.getText()).toString().trim();
        ContentValues values = new ContentValues();
        values.put(CompanyProvider.EMP_NAME,name);
        values.put(CompanyProvider.POS, pos);
        Uri uri=getContentResolver().insert(CompanyProvider.CONTENT_URI,values);
        StringBuilder stringBuilder  =new StringBuilder();
        stringBuilder.append(uri);
        Toast.makeText(this, stringBuilder, Toast.LENGTH_SHORT).show();
    }

    public void btnLoad(View view) {
        Cursor cursor=getContentResolver().query(CompanyProvider.CONTENT_URI,null,null,null,CompanyProvider.ID);
        StringBuilder stringBuilder = new StringBuilder();

        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String pos=cursor.getString(2);
            stringBuilder.append(id+"\t\t"+name+"\t\t"+pos+"\n");
        }
        Toast.makeText(this, stringBuilder, Toast.LENGTH_SHORT).show();
        cursor.close();

    }
}