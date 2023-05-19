package com.example.companyprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
    }

    public void btnLoad(View view) {

    }
}